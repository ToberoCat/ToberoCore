package io.github.toberocat.toberocore.command;

import io.github.toberocat.toberocore.command.exceptions.CommandException;
import org.bukkit.Bukkit;
import org.bukkit.command.CommandSender;
import org.bukkit.command.PluginCommand;
import org.bukkit.command.TabExecutor;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;


public class CommandExecutor extends Command implements TabExecutor {
    private CommandExecutor(@NotNull PluginCommand command) {
        super(command.getLabel(), command.getLabel());

        command.setExecutor(this);
        command.setTabCompleter(this);
    }

    public static @NotNull CommandExecutor createExecutor(@NotNull String command) {
        PluginCommand pluginCommand = Bukkit.getPluginCommand(command);
        if (pluginCommand == null)
            throw new RuntimeException("Plugin Command " + command + " is null");

        return new CommandExecutor(pluginCommand);
    }

    @Override
    public boolean onCommand(@NotNull CommandSender sender,
                             @NotNull org.bukkit.command.Command command,
                             @NotNull String label,
                             @NotNull String[] args) {
        SubCommand sub = args.length == 0 ? null : children.get(args[0]);
        if (sub == null)
            return false;

        try {
            return sub.routeCall(sender, args);
        } catch (CommandException e) {
            sendException(sender, e);
            return false;
        }
    }

    private void sendException(@NotNull CommandSender sender, @NotNull CommandException e) {
    }

    @Override
    public @Nullable List<String> onTabComplete(@NotNull CommandSender sender,
                                                @NotNull org.bukkit.command.Command command,
                                                @NotNull String label,
                                                @NotNull String[] args) {
        List<String> unsorted = null;
        try {
            unsorted = getTab(sender, args);
        } catch (CommandException e) {
            sendException(sender, e);
        }

        if (unsorted == null)
            return Collections.emptyList();

        List<String> results = new ArrayList<>();
        for (String arg : args) {
            for (String a : unsorted) {
                if (a.toLowerCase().startsWith(arg.toLowerCase())) {
                    results.add(a);
                }
            }
        }

        return results;
    }

    private @Nullable List<String> getTab(@NotNull CommandSender sender,
                                          @NotNull String[] args)
            throws CommandException {
        if (args.length <= 1) return childrenTabList(sender, args);

        SubCommand sub = children.get(args[0]);

        return sub == null ? null : sub.routeTab(sender, args);
    }

    @Override
    public boolean showInTab(@NotNull CommandSender sender,
                             @NotNull String[] args) {
        return true;
    }
}
