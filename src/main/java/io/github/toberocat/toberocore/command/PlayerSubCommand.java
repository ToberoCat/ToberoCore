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
    public PlayerSubCommand(@NotNull JavaPlugin plugin, @NotNull String label) {
        super(plugin, label);
    }

    public PlayerSubCommand(@NotNull String label, @NotNull Options options) {
        super(label, options);
    }

    public PlayerSubCommand(@NotNull String permission, @NotNull String label, @NotNull Options options) {
        super(permission, label, options);
    }

    public PlayerSubCommand(@NotNull String permission, @NotNull String label, Option[] onCommandOptions, Option[] onTabOptions) {
        super(permission, label, onCommandOptions, onTabOptions);
    }

    @Override
    protected boolean handleCommand(@NotNull CommandSender sender, @NotNull String[] args)
            throws CommandException {
        if (!(sender instanceof Player player))
            throw new CommandException("base.exceptions.player-command", new HashMap<>());
        return handle(player, args);
    }

    @Override
    protected @Nullable List<String> getTabList(@NotNull CommandSender sender, @NotNull String[] args) throws CommandException {
        if (!(sender instanceof Player player))
            throw new CommandException("base.exceptions.player-command", new HashMap<>());
        return getTab(player, args);
    }

    protected abstract boolean handle(@NotNull Player player, @NotNull String[] args)
            throws CommandException;

    protected abstract @Nullable List<String> getTab(@NotNull Player player, @NotNull String[] args)
            throws CommandException;
}
