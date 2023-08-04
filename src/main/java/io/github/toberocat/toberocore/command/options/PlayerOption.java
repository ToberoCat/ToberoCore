package io.github.toberocat.toberocore.command.options;

import io.github.toberocat.toberocore.command.exceptions.CommandException;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;
import org.jetbrains.annotations.NotNull;

import java.util.HashMap;

public interface PlayerOption extends Option {
    @Override
    default @NotNull String[] execute(@NotNull CommandSender sender, @NotNull String[] args) throws CommandException {
        if (!(sender instanceof Player player))
            throw new CommandException("base.exceptions.player-command", new HashMap<>());
        return executePlayer(player, args);
    }

    @NotNull String[] executePlayer(@NotNull Player sender,
                                    @NotNull String[] args) throws CommandException;
}
