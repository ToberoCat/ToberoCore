package io.github.toberocat.toberocore.command.options;

import org.bukkit.configuration.ConfigurationSection;
import org.bukkit.plugin.java.JavaPlugin;
import org.jetbrains.annotations.NotNull;

public interface OptionFactory {
    void create(@NotNull JavaPlugin plugin,
                @NotNull ConfigurationSection config,
                @NotNull Options options);
}
