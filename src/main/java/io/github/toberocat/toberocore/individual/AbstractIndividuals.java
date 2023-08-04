package io.github.toberocat.toberocore.individual;

import io.github.toberocat.toberocore.task.Task;
import org.jetbrains.annotations.NotNull;

import java.util.HashMap;
import java.util.Map;

public abstract class AbstractIndividuals<K, V> {

    private final Map<K, V> loaded = new HashMap<>();
    private final Map<K, Task<V>> computing = new HashMap<>();

    protected abstract @NotNull Task<V> read(@NotNull K k);

    protected abstract void write(@NotNull K k);

    protected @NotNull V remove(@NotNull K k) {
        synchronized (loaded) {
            return loaded.remove(k);
        }
    }

    /**
     * @param k The key you'd like to get the value from
     * @return A task which might promise the requested keys item
     * @throws FileIsSavingRuntimeException This exception gets thrown when
     *                                      an item just got marked as unloaded,
     *                                      and you try to access it again
     */
    public final @NotNull Task<V> get(@NotNull K k) {
        synchronized (computing) {
            if (computing.containsKey(k))
                return computing.get(k);
        }

        synchronized (loaded) {
            if (loaded.containsKey(k))
                return Task.returnItem(loaded.get(k));
        }

        synchronized (computing) {
            Task<V> task = read(k);
            computing.put(k, task);

            addComputationSafety(k, task);
            return task;
        }
    }

    public final void unload(@NotNull K k) {
        synchronized (loaded) {
            loaded.remove(k);
        }
    }

    public final void set(@NotNull K k, @NotNull V v) {
        synchronized (loaded) {
            loaded.put(k, v);
            write(k);
        }
    }

    private void addComputationSafety(@NotNull K k, @NotNull Task<V> task) {
        task.then(r -> {
            synchronized (loaded) {
                loaded.put(k, r);
            }
            synchronized (computing) {
                computing.remove(k);
            }
        });
    }
}
