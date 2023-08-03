package io.github.toberocat.toberocore.task;

import org.bukkit.plugin.java.JavaPlugin;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.TimeUnit;
import java.util.function.Consumer;
import java.util.function.Supplier;
import java.util.logging.Level;
import java.util.logging.Logger;

public class Task<R> implements Runnable {

    private static final ExecutorService threadPool = Executors.newCachedThreadPool();

    private Supplier<R> supplier;
    private final List<Consumer<R>> then;
    private R result;
    private boolean run;

    public Task() {
        this.then = new ArrayList<>();
    }

    public Task(@NotNull Supplier<R> supplier) {
        this();
        this.supplier = supplier;
        start();
    }

    public Task(@NotNull Runnable runnable) {
        this();
        this.supplier = () -> {
            runnable.run();
            return null;
        };
        start();
    }

    public static @NotNull <R> Task<R> returnItem(@Nullable R value) {
        Task<R> task = new Task<>();
        task.result = value;
        task.run = true;
        return task;
    }

    public static void dispose(Logger logger) {
        logger.log(Level.INFO, "Waiting for all tasks to finish");
        threadPool.shutdown();

        for (int i = 0; i < 100; i++) {
            try {
                if (threadPool.awaitTermination(100, TimeUnit.MILLISECONDS))
                    break;
            } catch (InterruptedException ignored) {
            }
        }
        logger.log(Level.INFO, "Shutdown continued");
    }

    public @NotNull Task<R> start() {
        threadPool.submit(this);
        return this;
    }

    public @NotNull Task<R> supply(@NotNull Supplier<R> supplier) {
        this.supplier = supplier;
        return this;
    }

    public synchronized void then(@NotNull Consumer<R> then) {
        this.then.add(then);
        if (run)
            this.then.forEach(x -> x.accept(result));
    }

    public @NotNull TaskChain<R> chain(@NotNull JavaPlugin plugin) {
        return new TaskChain<>(plugin, this, null);
    }

    public @NotNull Optional<R> await() {
        return promise().await();
    }

    public @NotNull TaskPromise<R> promise() {
        TaskPromise<R> promise = new TaskPromise<>();
        then(promise::resolve);
        return promise;
    }

    @Override
    public void run() {
        result = supplier.get();

        run = true;
        then.forEach(x -> x.accept(result));
    }
}
