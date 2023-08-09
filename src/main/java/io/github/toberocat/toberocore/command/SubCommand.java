package io.github.toberocat.toberocore.command;

import io.github.toberocat.toberocore.command.arguments.Argument;
import io.github.toberocat.toberocore.command.arguments.Arguments;
import io.github.toberocat.toberocore.command.exceptions.CommandException;
import io.github.toberocat.toberocore.command.options.Option;
import io.github.toberocat.toberocore.command.options.Options;
import io.github.toberocat.toberocore.util.file.YamlLoader;
import io.github.toberocat.toberocore.util.placeholder.PlaceholderBuilder;
import org.bukkit.command.CommandSender;
import org.bukkit.configuration.ConfigurationSection;
import org.bukkit.configuration.file.FileConfiguration;
import org.bukkit.entity.Player;
import org.bukkit.plugin.java.JavaPlugin;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

import java.io.File;
import java.io.IOException;
import java.util.Arrays;
import java.util.HashMap;
import java.util.List;
import java.util.Objects;
import java.util.stream.Stream;


public abstract class SubCommand extends Command {

    protected final String label;
    protected final Option[] onCommandOptions;
    protected final Option[] onTabOptions;

    protected final Argument<?>[] args;

    public SubCommand(@NotNull String label) {
        this(label, label);
    }

    public SubCommand(@NotNull String permission, @NotNull String label) {
        super(permission, label);
        this.label = label;

        Options options = options();
        onCommandOptions = options.getCommandOptions();
        onTabOptions = options.getTabOptions();

        args = arguments();
    }

    protected abstract @NotNull Options options();
    protected abstract @NotNull Argument<?>[] arguments();

    protected @NotNull Arguments parseArgs(@NotNull Player player, String[] args)
            throws CommandException {
        return new Arguments(player, args, this.args);
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
            throw new CommandException("base.exceptions.not-enough-permissions", new PlaceholderBuilder<>().placeholder("permission", getPermission()).getPlaceholders());
        if (args.length == 0) return handleWithOptions(sender, args);


        String[] newArgs = new String[args.length - 1];
        System.arraycopy(args, 1, newArgs, 0, newArgs.length);

        if (newArgs.length >= 1 && children.containsKey(newArgs[0]))
            return children.get(newArgs[0]).routeCall(sender, newArgs);
        return handleWithOptions(sender, newArgs);
    }

    public @Nullable List<String> routeTab(@NotNull CommandSender sender, @NotNull String[] args)
            throws CommandException {
        if (!sender.hasPermission(getPermission())) return null;
        if (args.length == 0)
            return childrenTabList(sender, args);

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
        if (args.length - 1 >= this.args.length)
            throw new CommandException("base.exceptions.too-many-args", new HashMap<>());

        return this.args[args.length - 1].tab(sender);
    }

    @Override
    public boolean showInTab(@NotNull CommandSender sender, @NotNull String[] args) {
        if (!sender.hasPermission(getPermission()))
            return false;

        return Stream.concat(Arrays.stream(onTabOptions), Arrays.stream(onCommandOptions))
                .allMatch(x -> x.show(sender, args));
    }

    public @NotNull Argument<?>[] getArgs() {
        return args;
    }

    public @NotNull ConfigurationSection getConfig(@NotNull JavaPlugin plugin) {
        return getConfig(plugin, getPermission());
    }

    protected abstract boolean handleCommand(@NotNull CommandSender sender, @NotNull String[] args) throws CommandException;
}
