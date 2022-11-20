package io.github.toberocat.toberocore.task;

import io.github.toberocat.toberocore.ToberoCore;
import org.bukkit.Bukkit;
import org.jetbrains.annotations.NotNull;

import java.util.function.Consumer;
import java.util.function.Function;
import java.util.function.Predicate;
import java.util.function.Supplier;

public class TaskChain<R> {

    private final Task<R> rTask;
    private Runnable finished;

    public TaskChain(Task<R> task, Runnable finished) {
        rTask = task;
        this.finished = finished;
    }

    public static @NotNull <R> TaskChain<R> asyncFirst(@NotNull Supplier<R> action) {
        return new TaskChain<>(new Task<>(action), null);
    }

    public @NotNull <T> TaskChain<T> async(@NotNull Function<R, T> supplier) {
        Task<T> tTask = new Task<>();
        rTask.then(r ->
                tTask.supply(() ->
                        supplier.apply(r)).start());

        return new TaskChain<>(tTask, finished);
    }

    public @NotNull TaskPromise<R> lastAsync(@NotNull Consumer<R> consumer) {
        TaskPromise<R> promise = new TaskPromise<>();
        rTask.then(r -> {
            consumer.accept(r);
            promise.resolve(r);
            if (finished != null) finished.run();
        });

        return promise;
    }

    public @NotNull <T> TaskPromise<T> lastAsync(@NotNull Function<R, T> function) {
        TaskPromise<T> promise = new TaskPromise<>();
        rTask.then(r -> {
            promise.resolve(function.apply(r));
            if (finished != null) finished.run();
        });

        return promise;
    }

    public @NotNull <T> TaskChain<T> sync(@NotNull Function<R, T> supplier) {
        Task<T> tTask = new Task<>();
        rTask.then(r ->
                Bukkit.getScheduler().runTask(ToberoCore.getPlugin(), () ->
                        tTask.supply(() ->
                                supplier.apply(r)).start()));

        return new TaskChain<>(tTask, finished);
    }

    public @NotNull TaskPromise<R> lastSync(@NotNull Consumer<R> consumer) {
        TaskPromise<R> promise = new TaskPromise<>();
        rTask.then(r -> Bukkit.getScheduler().runTask(ToberoCore.getPlugin(), () -> {
            consumer.accept(r);
            promise.resolve(r);
            if (finished != null) finished.run();
        }));

        return promise;
    }

    public @NotNull <T> TaskPromise<T> lastSync(@NotNull Function<R, T> function) {
        TaskPromise<T> promise = new TaskPromise<>();
        rTask.then(r -> Bukkit.getScheduler().runTask(ToberoCore.getPlugin(), () -> {
            promise.resolve(function.apply(r));
            if (finished != null) finished.run();
        }));

        return promise;
    }

    public @NotNull TaskChain<R> abortIf(@NotNull Predicate<R> abort, @NotNull Consumer<R> aborted) {
        Task<R> tTask = new Task<>();
        rTask.then(r -> {
            if (abort.test(r)) aborted.accept(r);
            else tTask.supply(() -> r).start();
        });
        return new TaskChain<>(tTask, finished);
    }

    public @NotNull TaskChain<R> abortIfNull(@NotNull Runnable aborted) {
        Task<R> tTask = new Task<>();
        rTask.then(r -> {
            if (r == null) aborted.run();
            else tTask.supply(() -> r).start();
        });
        return new TaskChain<>(tTask, finished);
    }

    public synchronized void then(@NotNull Runnable finished) {
        this.finished = finished;
    }
}
