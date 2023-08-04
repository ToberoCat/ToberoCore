package io.github.toberocat.toberocore.task;

import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

import java.util.Optional;
import java.util.function.Consumer;
import java.util.function.Supplier;

public class TaskPromise<R> {

    private Runnable runnable;
    private R value;
    private boolean resolved;

    public TaskPromise() {

    }

    public TaskPromise(@NotNull Consumer<Consumer<R>> callback) {
        callback.accept(this::resolve);
    }

    public void resolve(@Nullable R r) {
        synchronized (this) {
            value = r;
            resolved = true;
            notifyAll();
            if (runnable != null) runnable.run();
        }
    }

    public @NotNull Optional<R> await() {
        if (resolved) return Optional.of(value);
        synchronized (this) {
            try {
                wait();
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }
        return Optional.of(value);
    }

    public void then(@NotNull Runnable runnable) {
        if (resolved) runnable.run();
        else this.runnable = runnable;
    }

    public void then(@NotNull Consumer<R> runnable) {
        then(() -> runnable.accept(value));
    }

    public <T> TaskPromise<T> then(@NotNull Supplier<TaskPromise<T>> runnable) {
        TaskPromise<T> promise = new TaskPromise<>();

        then(() -> {
            TaskPromise<T> newPromise = runnable.get();
            newPromise.then(() -> promise.resolve(newPromise.value));
        });

        return promise;
    }
}
