package io.github.toberocat.toberocore.util.placeholder;

import org.jetbrains.annotations.NotNull;

import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.util.Arrays;
import java.util.HashMap;
import java.util.Map;
import java.util.function.Function;
import java.util.stream.Collectors;

public class PlaceholderBuilder<B extends PlaceholderBuilder<B>> {
    private final Map<String, String> placeholders = new HashMap<>();

    public @NotNull B placeholder(@NotNull String key, @NotNull String value) {
        placeholders.put(key, value);
        return self();
    }

    public @NotNull B placeholder(@NotNull String key, int value) {
        return placeholder(key, String.valueOf(value));
    }

    public Map<String, String> getPlaceholders() {
        return placeholders;
    }

    public @NotNull B placeholder(@NotNull String namespace, @NotNull Object value) {
        placeholders.putAll(extractMethods(namespace, value));
        return self();
    }

    public static @NotNull Map<String, String> extractMethods(@NotNull String namespace, @NotNull Object instance) {
        return Arrays.stream(instance.getClass().getMethods()).filter(x -> x.getParameterCount() == 0 && !x.getReturnType().equals(void.class) && Arrays.stream(x.getAnnotations()).noneMatch(y -> y.annotationType().equals(PlaceholderIgnore.class))).collect(Collectors.toMap(x -> getPlaceholderName(namespace, x), getPlaceholderValue(instance), (o, n) -> o));
    }

    @NotNull
    private static Function<Method, String> getPlaceholderValue(@NotNull Object instance) {
        return x -> {
            try {
                return String.valueOf(x.invoke(instance));
            } catch (IllegalAccessException e) {
                throw new RuntimeException(e);
            } catch (InvocationTargetException ignored) {
                return "invalid";
            }
        };
    }

    @NotNull
    private static String getPlaceholderName(@NotNull String namespace, @NotNull Method x) {
        return "{" + namespace + "-" + Arrays.stream(x.getAnnotations()).filter(m -> m.annotationType().equals(PlaceholderGetter.class)).findAny().map(m -> ((PlaceholderGetter) m).name()).orElse(x.getName()) + "}";
    }

    @SuppressWarnings("unchecked")
    protected @NotNull B self() {
        return (B) this;
    }
}
