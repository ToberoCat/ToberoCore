package io.github.toberocat.toberocore.command;

import io.github.toberocat.toberocore.util.StringUtils;
import io.github.toberocat.toberocore.command.exceptions.CommandExceptions;
import org.bukkit.Bukkit;
import org.bukkit.command.CommandSender;
import org.bukkit.command.PluginCommand;
import org.bukkit.command.TabExecutor;
import org.bukkit.configuration.file.FileConfiguration;
import org.bukkit.plugin.java.JavaPlugin;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

import java.util.*;
import java.util.function.Function;


/**
 * Created: 02/11/2022
 *
 * @author Tobias Madlberger (Tobias)
 */
public class CommandExecutor extends Command<SubCommand> implements TabExecutor {

    private static final Map<String, CommandExecutor> registeredExecutors = new HashMap<>();
    private NothingAction action;

    private Function<String[], List<String>> tab;

    private CommandExecutor(@NotNull PluginCommand command, @NotNull String prefix) {
        super(command.getLabel(), command.getLabel());
        this.prefix = "";

        command.setExecutor(this);
        command.setTabCompleter(this);

        registeredExecutors.put(label, this);
    }

    //<editor-fold desc="Accessing Executors">
    public static @Nullable CommandExecutor getExecutor(@NotNull String command) {
        return registeredExecutors.get(command);
    }

    public static @NotNull CommandExecutor createExecutor(@NotNull JavaPlugin plugin,
                                                          @NotNull String command) {
        return createExecutor(String.format("§8[§e%s§8]§7", plugin.getName()), command);
    }

    public static @NotNull CommandExecutor createExecutor(@NotNull FileConfiguration config,
                                                          @NotNull String command) {
        return createExecutor(
                config.getString("prefix", "§8[§eSpivakNetwork§8]§7"), command);
    }

    public static @NotNull CommandExecutor createExecutor(@NotNull String prefix,
                                                          @NotNull String command) {
        CommandExecutor executor = registeredExecutors.get(command);
        if (executor != null) return executor;

        PluginCommand pluginCommand = Bukkit.getPluginCommand(command);
        if (pluginCommand == null)
            throw new RuntimeException("Plugin Command " + command + " is null");

        return new CommandExecutor(pluginCommand, prefix);
    }
    //</editor-fold>

    //<editor-fold desc="Command Handling">
    @Override
    public boolean onCommand(@NotNull CommandSender sender,
                             @NotNull org.bukkit.command.Command command,
                             @NotNull String label,
                             @NotNull String[] args) {
        SubCommand sub = args.length == 0 ? null : children.get(args[0]);

        try {
            return sub != null
                    ? sub.routeCall(sender, args)
                    : action != null && action.execute(sender, args);
        } catch (CommandExceptions e) {
            sender.sendMessage(prefix + "§c" + StringUtils.format(e.getMessageId()));
            return false;
        }
    }

    @Override
    public @Nullable List<String> onTabComplete(@NotNull CommandSender sender,
                                                @NotNull org.bukkit.command.Command command,
                                                @NotNull String label,
                                                @NotNull String[] args) {
        List<String> unsorted = getTab(sender, args);
        if (unsorted == null) return null;

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

    private @Nullable List<String> getTab(@NotNull CommandSender sender, @NotNull String[] args) {
        if (args.length <= 1) return tab == null ? tabComplete : tab.apply(args);

        SubCommand sub = children.get(args[0]);

        return sub == null ? null : sub.routeTab(sender, args);
    }
    //</editor-fold>

    //<editor-fold desc="Getters and Setters">

    public CommandExecutor setNothingAction(@NotNull Function<CommandSender, Boolean> action) {
        this.action = (sender, args) -> action.apply(sender);
        return this;
    }

    public CommandExecutor setNothingAction(@NotNull NothingAction action) {
        this.action = action;
        return this;
    }

    public CommandExecutor setTab(Function<String[], List<String>> tab) {
        this.tab = tab;
        return this;
    }

    //</editor-fold>

    @FunctionalInterface
    public interface NothingAction {
        boolean execute(@NotNull CommandSender sender, @NotNull String[] args)
                throws CommandExceptions;
    }
}
