package io.github.toberocat.toberocore.command.arguments;

import io.github.toberocat.toberocore.command.exceptions.CommandException;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

import java.util.List;

public interface Argument<R> {
    @NotNull R parse(@NotNull Player player, @NotNull String arg) throws CommandException;

    @Nullable List<String> tab(@NotNull CommandSender player) throws CommandException;

    @NotNull String descriptionKey();

    @NotNull String usage();
}
