package io.github.toberocat.toberocore.task;

/**
 * Created: 20/11/2022
 *
 * @author Tobias Madlberger (Tobias)
 */
public class PromiseTest {
    public static void main(String[] args) {
        firstPromise()
                .then(PromiseTest::secondPromise)
                .then(value -> {
                    System.out.println(value);
                });
    }

    private static TaskPromise<String> firstPromise() {
        TaskPromise<String> promise = new TaskPromise<>();

        new Task<>(() -> {
            try {
                Thread.sleep(1000);
                promise.resolve("Hallo");
            } catch (InterruptedException e) {
                throw new RuntimeException(e);
            }
        });
        return promise;
    }

    private static TaskPromise<String> secondPromise() {
        TaskPromise<String> promise = new TaskPromise<>();

        new Task<>(() -> {
            try {
                Thread.sleep(1000);
                promise.resolve("Welt");
            } catch (InterruptedException e) {
                throw new RuntimeException(e);
            }
        });
        return promise;
    }
}
