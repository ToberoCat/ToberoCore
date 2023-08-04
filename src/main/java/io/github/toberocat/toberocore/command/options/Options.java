package io.github.toberocat.toberocore.command.options;

import io.github.toberocat.toberocore.command.SubCommand;
import io.github.toberocat.toberocore.util.CooldownManager;
import org.bukkit.configuration.ConfigurationSection;
import org.bukkit.plugin.java.JavaPlugin;
import org.jetbrains.annotations.NotNull;

import java.util.*;
import java.util.concurrent.TimeUnit;
import java.util.function.BiConsumer;

public class Options {
    public static final Set<OptionFactory> CONFIG_OPTION_FACTORIES = new HashSet<>();
    private final List<Option> tab = new LinkedList<>();
    private final List<Option> command = new LinkedList<>();

    static {
        addFactory((plugin, config, options) -> {
            CooldownManager cooldownManager = CooldownManager.createManager(Optional.ofNullable(config.getString("cooldown-unit")).map(TimeUnit::valueOf).orElse(TimeUnit.SECONDS), config.getInt("cooldown", 0));
            options.cmdOpt(new CooldownOption(plugin, cooldownManager))
                    .tabOpt(new CooldownTabOption(cooldownManager, config.getBoolean("cooldown-hide", false)));
        });
    }

    public static void addFactory(@NotNull OptionFactory factory) {
        CONFIG_OPTION_FACTORIES.add(factory);
    }

    public static @NotNull Options getFromConfig(@NotNull JavaPlugin plugin, @NotNull String path) {
        return getFromConfig(plugin, path, ((options, configurationSection) -> {
        }));
    }

    public static @NotNull Options getFromConfig(@NotNull JavaPlugin plugin,
                                                 @NotNull String path,
                                                 @NotNull BiConsumer<Options, ConfigurationSection> loadExtra) {
        Options options = new Options();
        ConfigurationSection configFile = SubCommand.getConfig(plugin, path);
        CONFIG_OPTION_FACTORIES.forEach(x -> x.create(plugin, configFile, options));
        loadExtra.accept(options, configFile);
        return options;
    }

    public @NotNull Options opt(@NotNull Option option) {
        command.add(option);
        tab.add(option);
        return this;
    }

    public @NotNull Options cmdOpt(@NotNull Option option) {
        command.add(option);
        return this;
    }

    public @NotNull Options tabOpt(@NotNull Option option) {
        tab.add(option);
        return this;
    }

    public @NotNull Option[] getCommandOptions() {
        return command.toArray(Option[]::new);
    }

    public @NotNull Option[] getTabOptions() {
        return tab.toArray(Option[]::new);
    }
}
