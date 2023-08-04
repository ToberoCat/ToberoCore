package io.github.toberocat.toberocore.command.options;

import io.github.toberocat.toberocore.command.exceptions.CommandException;
import org.bukkit.command.CommandSender;
import org.jetbrains.annotations.NotNull;

public interface Option {

    @NotNull String[] execute(@NotNull CommandSender sender, @NotNull String[] args) throws CommandException;

    default boolean show(@NotNull CommandSender sender, @NotNull String[] args) {
        return true;
    }
}
