package io.github.toberocat.toberocore.command.arguments;

import org.jetbrains.annotations.NotNull;

import java.util.List;

public class ArgumentBuilder {
    private List<Argument<?>> args;

    public @NotNull ArgumentBuilder add(Argument<?> arg) {
        args.add(arg);
        return this;
    }

    public @NotNull Argument<?>[] finish() {
        return args.toArray(Argument[]::new);
    }
}
