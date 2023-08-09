package io.github.toberocat.toberocore.command;

import io.github.toberocat.toberocore.command.exceptions.CommandException;
import io.github.toberocat.toberocore.command.options.Option;
import io.github.toberocat.toberocore.command.options.Options;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;
import org.bukkit.plugin.java.JavaPlugin;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

import java.util.HashMap;
import java.util.List;

public abstract class PlayerSubCommand extends SubCommand {
    public PlayerSubCommand(@NotNull String label) {
        super(label, label);
    }

    public PlayerSubCommand(@NotNull String permission, @NotNull String label) {
        super(permission, label);
    }

    @Override
    protected boolean handleCommand(@NotNull CommandSender sender, @NotNull String[] args)
            throws CommandException {
        if (!(sender instanceof Player player))
            throw new CommandException("base.exceptions.player-command", new HashMap<>());
        return handle(player, args);
    }

    protected abstract boolean handle(@NotNull Player player, @NotNull String[] args)
            throws CommandException;
}
