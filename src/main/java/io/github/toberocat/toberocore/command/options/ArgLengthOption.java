package io.github.toberocat.toberocore.command.options;

import io.github.toberocat.toberocore.command.exceptions.CommandException;
import io.github.toberocat.toberocore.util.placeholder.PlaceholderBuilder;
import org.bukkit.command.CommandSender;
import org.jetbrains.annotations.NotNull;

public class ArgLengthOption implements Option {
    private final int length;
    public ArgLengthOption(int length) {
        this.length = length;
    }

    @Override
    public @NotNull String[] execute(@NotNull CommandSender sender, @NotNull String[] args)
            throws CommandException {
        if (args.length != length) {
            throw new CommandException("base.exceptions.invalid-argument-length", new PlaceholderBuilder<>()
                    .placeholder("provided", args.length)
                    .placeholder("required", length)
                    .getPlaceholders());
        }
        return args;
    }
}
