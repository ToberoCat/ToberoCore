package io.github.toberocat.toberocore.util.file;

import org.bukkit.Bukkit;
import org.bukkit.Location;
import org.bukkit.World;
import org.bukkit.configuration.ConfigurationSection;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

import java.util.HashSet;
import java.util.Optional;
import java.util.Set;

/**
 * Created: 01.07.2023
 *
 * @author Tobias Madlberger (Tobias)
 */
public class ConfigParser {
    private final @NotNull ConfigurationSection configurationSection;

    public ConfigParser(@NotNull ConfigurationSection configurationSection) {
        this.configurationSection = configurationSection;
    }

    public @NotNull Optional<ConfigParser> section(@NotNull String path) {
        ConfigurationSection section = configurationSection.getConfigurationSection(path);
        return section == null ? Optional.empty() : Optional.of(new ConfigParser(section));
    }

    public @Nullable Location asLocation() {
        String worldName = configurationSection.getString("world");
        if (worldName == null)
            return null;

        World world = Bukkit.getWorld(worldName);
        if (world == null)
            return null;

        double x = configurationSection.getDouble("x");
        double y = configurationSection.getDouble("y");
        double z = configurationSection.getDouble("z");
        float yaw = (float) configurationSection.getDouble("yaw");
        float pitch = (float) configurationSection.getDouble("pitch");
        return new Location(world, x, y, z, yaw, pitch);
    }

    public @NotNull Set<String> list(@NotNull String path) {
        return section(path)
                .map(ConfigParser::list)
                .orElse(new HashSet<>());
    }

    public @NotNull Set<String> list() {
        return configurationSection.getKeys(false);
    }

    public @NotNull ConfigurationSection get() {
        return configurationSection;
    }
}
