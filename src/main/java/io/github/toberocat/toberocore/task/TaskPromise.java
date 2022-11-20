package io.github.toberocat.toberocore.task;

import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

import java.util.Optional;

public class TaskPromise<R> {

    private Runnable runnable;
    private R value;
    private boolean resolved;

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
        this.runnable = runnable;
    }
}
