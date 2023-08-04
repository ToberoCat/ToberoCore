package io.github.toberocat.toberocore.command;

import io.github.toberocat.toberocore.command.exceptions.CommandException;
import io.github.toberocat.toberocore.command.options.Option;
import io.github.toberocat.toberocore.command.options.Options;
import io.github.toberocat.toberocore.util.file.YamlLoader;
import io.github.toberocat.toberocore.util.placeholder.PlaceholderBuilder;
import org.bukkit.command.CommandSender;
import org.bukkit.configuration.ConfigurationSection;
import org.bukkit.configuration.file.FileConfiguration;
import org.bukkit.plugin.java.JavaPlugin;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

import java.io.File;
import java.io.IOException;
import java.util.Arrays;
import java.util.List;
import java.util.Objects;


public abstract class SubCommand extends Command {

    protected final String label;
    protected final Option[] onCommandOptions;
    protected final Option[] onTabOptions;


    public SubCommand(@NotNull JavaPlugin plugin, @NotNull String label) {
        this(label, Options.getFromConfig(plugin, label));
    }

    public SubCommand(@NotNull String label, @NotNull Options options) {
        this(label, label, options);
    }

    public SubCommand(@NotNull String permission, @NotNull String label, @NotNull Options options) {
        this(permission, label, options.getCommandOptions(), options.getTabOptions());
    }

    public SubCommand(@NotNull String permission, @NotNull String label, Option[] onCommandOptions, Option[] onTabOptions) {
        super(permission, label);
        this.label = label;
        this.onCommandOptions = onCommandOptions;
        this.onTabOptions = onTabOptions;
    }

    public static @NotNull ConfigurationSection getConfig(@NotNull JavaPlugin plugin, @NotNull String path) {
        File file = new File(plugin.getDataFolder(), "commands.yml");
        FileConfiguration config = new YamlLoader(file, plugin).load().fileConfiguration();
        if (config.contains(path)) return Objects.requireNonNull(config.getConfigurationSection(path));
        ConfigurationSection section = config.createSection(path);

        try {
            config.save(file);
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
        return section;
    }

    public boolean routeCall(@NotNull CommandSender sender, @NotNull String[] args) throws CommandException {
        if (!sender.hasPermission(getPermission()))
            throw new CommandException("exceptions.not-enough-permissions", new PlaceholderBuilder<>().placeholder("permission", getPermission()).getPlaceholders());
        if (args.length == 0) return handleWithOptions(sender, args);


        String[] newArgs = new String[args.length - 1];
        System.arraycopy(args, 1, newArgs, 0, newArgs.length);

        if (newArgs.length >= 1 && children.containsKey(newArgs[0]))
            return children.get(newArgs[0]).routeCall(sender, newArgs);
        return handleWithOptions(sender, newArgs);
    }

    public @Nullable List<String> routeTab(@NotNull CommandSender sender, @NotNull String[] args) throws CommandException {
        if (!sender.hasPermission(getPermission())) return null;
        if (args.length == 0) return childrenTabList(sender, args);

        String[] newArgs = new String[args.length - 1];
        System.arraycopy(args, 1, newArgs, 0, newArgs.length);

        if (newArgs.length >= 1 && children.containsKey(newArgs[0]))
            return children.get(newArgs[0]).routeTab(sender, newArgs);
        return getTabWithOptions(sender, newArgs);
    }

    private boolean handleWithOptions(@NotNull CommandSender sender, @NotNull String[] args) throws CommandException {
        for (Option option : onCommandOptions)
            args = option.execute(sender, args);
        return handleCommand(sender, args);
    }

    private @Nullable List<String> getTabWithOptions(@NotNull CommandSender sender, @NotNull String[] args) throws CommandException {
        for (Option option : onTabOptions)
            args = option.execute(sender, args);
        return getTabList(sender, args);
    }

    @Override
    public boolean showInTab(@NotNull CommandSender sender, @NotNull String[] args) {
        return Arrays.stream(onTabOptions).allMatch(x -> x.show(sender, args));
    }

    public @NotNull ConfigurationSection getConfig(@NotNull JavaPlugin plugin) {
        return getConfig(plugin, getPermission());
    }

    protected abstract boolean handleCommand(@NotNull CommandSender sender, @NotNull String[] args) throws CommandException;

    protected abstract @Nullable List<String> getTabList(@NotNull CommandSender sender, @NotNull String[] args) throws CommandException;
}
