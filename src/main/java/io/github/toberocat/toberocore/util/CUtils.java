package io.github.toberocat.toberocore.util;

import org.apache.commons.lang.WordUtils;
import org.bukkit.Bukkit;
import org.bukkit.Location;
import org.bukkit.Material;
import org.bukkit.OfflinePlayer;
import org.bukkit.block.Block;
import org.bukkit.command.Command;
import org.bukkit.command.CommandMap;
import org.bukkit.configuration.ConfigurationSection;
import org.bukkit.configuration.file.YamlConfiguration;
import org.bukkit.entity.Item;
import org.bukkit.entity.Player;
import org.bukkit.event.Listener;
import org.bukkit.inventory.ItemStack;
import org.bukkit.plugin.Plugin;
import org.bukkit.plugin.PluginManager;
import org.bukkit.plugin.java.JavaPlugin;
import org.bukkit.util.Vector;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

import java.lang.reflect.Field;
import java.util.*;
import java.util.Map.Entry;
import java.util.stream.Collectors;

@SuppressWarnings("unused")
public final class CUtils {

    public static final String MISSING = "N/A";
    private static final Random RANDOM = new Random();

    public static void registerListeners(@NotNull JavaPlugin plugin,
                                         @NotNull Listener... listeners) {
        PluginManager manager = plugin.getServer().getPluginManager();
        for (Listener listener : listeners) manager.registerEvents(listener, plugin);
    }

    /* Command */

    /**
     * Registers a command so that it can be used in-game.
     *
     * @param command        the command to register
     * @param fallbackPrefix the fallback prefix to use for the command
     */
    public static void registerCommand(@NotNull Command command, @NotNull String fallbackPrefix) {
        CommandMap commandMap = Bukkit.getServer().getCommandMap();
        commandMap.register(fallbackPrefix, command);
    }

    /**
     * Registers a command so that it can be used in-game.
     *
     * @param command the command to register
     * @param plugin  the plugin that the command is being registered from
     */
    public static void registerCommand(@NotNull Command command, @NotNull Plugin plugin) {
        registerCommand(command, plugin.getName());
    }

    /**
     * Unregisters a command so that it can no longer be used in-game.
     *
     * @param command the command to unregister
     */
    public static void unregisterCommand(@NotNull Command command) {
        CommandMap commandMap = Bukkit.getCommandMap();
        Map<String, Command> knownCommands = commandMap.getKnownCommands();

        command.unregister(commandMap);

        for (Entry<String, Command> entry : Map.copyOf(knownCommands).entrySet()) {
            Command val = entry.getValue();

            if (val.equals(command)) {
                knownCommands.remove(entry.getKey());
            }
        }
    }

    /* Config */

    public static @NotNull ConfigurationSection getOrCreateCS(@NotNull ConfigurationSection configurationSection, @NotNull String path) {
        ConfigurationSection a = configurationSection.getConfigurationSection(path);
        return (a == null) ? new YamlConfiguration() : a;
    }

    /* Format */

    public static @NotNull String enumKey(@NotNull Enum<?> arg) {
        return arg.toString().toLowerCase(Locale.ROOT).replace('_', '-');
    }

    public static @NotNull String enumFormat(@NotNull String string) {
        return string.replace('-', '_').toUpperCase(Locale.ROOT);
    }

    public static @NotNull String enumName(@NotNull Enum<?> arg) {
        return WordUtils.capitalize(arg.toString().toLowerCase(Locale.ROOT).replace('_', ' '));
    }

    /* Location */

    public static @NotNull Location centre(@NotNull Block block) {
        return block.getLocation().add(.5, .5, .5);
    }

    /* Material */

    /**
     * Gets the material with the given name.
     * The material name does not have to be formatted like an enum material.
     *
     * @param name The name of the material to get
     * @return The material, or null if no material exists with the given name
     */
    @Deprecated
    public static @Nullable Material material(@NotNull String name) {
        return Material.getMaterial(enumFormat(name));
    }

    /* Player */

    /**
     * Attempts to give a player an item.
     * Will drop the item on the floor if their inventory is full.
     *
     * @param player the player to give the item to
     * @param item   the item to give
     */
    public static void give(@NotNull Player player, @NotNull ItemStack item) {
        for (ItemStack overflow : player.getInventory().addItem(item).values()) {
            Item drop = player.getWorld().dropItem(player.getLocation(), overflow);
            drop.setVelocity(new Vector());
        }
    }

    /**
     * Gets a player's name from their UUID.
     *
     * @param uuid the {@link UUID} to use to get the name
     * @return the player name
     */
    public static @NotNull String nameFromUuid(@NotNull UUID uuid) {
        OfflinePlayer offlinePlayer = Bukkit.getOfflinePlayer(uuid);
        String name = offlinePlayer.getName();
        return (name == null) ? MISSING : name;
    }

    /**
     * Gets an offline player from their name.
     *
     * @param name the name to use to get the {@link OfflinePlayer}
     * @return the offline player
     * @deprecated use {@link Bukkit#getOfflinePlayerIfCached(String)}
     */
    @Deprecated
    public static OfflinePlayer offlinePlayerFromName(@NotNull String name) {
        return StreamUtils.find(Bukkit.getOfflinePlayers(), x -> name.equals(x.getName()));
    }

    /* Random */

    @Deprecated
    public static double randomDouble(double from, double to) {
        return random(from, to);
    }

    public static int random(int from, int to) {
        return from + RANDOM.nextInt(to - from + 1);
    }

    public static double random(double from, double to) {
        return from + RANDOM.nextDouble() * (to - from + Double.MIN_VALUE);
    }

    public static boolean chance(int percentage) {
        int r = random(0, 99);
        return percentage > r;
    }

    public static boolean chance(double percentage) {
        double r = random(0.0D, 99.0D);
        return percentage > r;
    }

    /* Reflection */

    public static @Nullable Object getField(@NotNull Object object, @NotNull String field) {
        try {
            Class<?> clazz = object.getClass();
            Field objectField = clazz.getDeclaredField(field);
            objectField.setAccessible(true);
            Object result = objectField.get(object);
            objectField.setAccessible(false);
            return result;
        } catch (NoSuchFieldException | IllegalAccessException | IllegalArgumentException | SecurityException e) {
            return null;
        }
    }

    /* Deprecated */

    @Deprecated
    public static void registerCommand(@NotNull Command command) {
        registerCommand(command, command.getName());
    }

    @Deprecated
    public static @NotNull String replace(@NotNull String string, @NotNull Map<String, String> placeholders, boolean caseSensitive) {
        return StringUtils.replace(string, placeholders, caseSensitive);
    }

    @Deprecated
    public static @NotNull String replace(@NotNull String string, @NotNull Map<String, String> placeholders) {
        return StringUtils.replace(string, placeholders);
    }

    public static <K, V> HashMap<K, V> sortMap(HashMap<K, V> map, Comparator<Entry<K, V>> comparable) {
        return map.entrySet().parallelStream()
                .sorted(comparable)
                .collect(Collectors.toMap(Entry::getKey,
                        Entry::getValue, (e1, e2) -> e1, LinkedHashMap::new));
    }
}
