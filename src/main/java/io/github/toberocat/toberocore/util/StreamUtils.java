package io.github.toberocat.toberocore.util;

import org.jetbrains.annotations.NotNull;

import java.util.Collection;
import java.util.function.Predicate;
import java.util.stream.Stream;

/**
 * Created: 20/11/2022
 *
 * @author Tobias Madlberger (Tobias)
 */
public class StreamUtils {

    public static <T> T find(@NotNull T[] array,
                             @NotNull Predicate<T> predicate) {
        return Stream.of(array)
                .filter(predicate)
                .findFirst()
                .orElse(null);
    }

    public static <T> T find(@NotNull Collection<T> list,
                             @NotNull Predicate<T> predicate) {
        return list
                .stream()
                .filter(predicate)
                .findFirst()
                .orElse(null);
    }
}
