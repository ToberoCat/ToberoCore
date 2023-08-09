package io.github.toberocat.toberocore.command.arguments;

import io.github.toberocat.toberocore.command.exceptions.CommandException;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

import java.util.List;

public interface Argument<R> {
    R parse(@NotNull Player player, @NotNull String arg) throws CommandException;

    default R defaultValue(@NotNull Player player) {
        return null;
    }

    @Nullable List<String> tab(@NotNull CommandSender player) throws CommandException;

    @NotNull String descriptionKey();

    @NotNull String usage();

}
