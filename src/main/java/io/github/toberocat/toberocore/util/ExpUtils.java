package io.github.toberocat.toberocore.util;

import org.bukkit.entity.Player;
import org.jetbrains.annotations.NotNull;

public final class ExpUtils {

    public static int inLevel(int level) {
        if (level < 0) {
            return 0;
        } else if (level <= 15) {
            return 2 * level + 7;
        } else if (level <= 30) {
            return 5 * level - 38;
        } else {
            return 9 * level - 158;
        }
    }

    public static int toLevel(int level) {
        if (level <= 16) {
            return (int) (Math.pow(level, 2) + 6 * level);
        } else if (level <= 31) {
            return (int) (2.5 * Math.pow(level, 2) - 40.5 * level + 360.0);
        } else {
            return (int) (4.5 * Math.pow(level, 2) - 162.5 * level + 2220.0);
        }
    }

    public static void addPoints(@NotNull Player player, int amount) {
        int current = getPoints(player);
        setPoints(player, current + amount);
    }

    public static void removePoints(@NotNull Player player, int amount) {
        int current = getPoints(player);
        setPoints(player, current - amount);
    }

    public static int getPoints(@NotNull Player player) {
        int xp = 0;
        int level = player.getLevel();

        // past levels
        xp += toLevel(level);

        // current level
        xp += Math.round(inLevel(level) * player.getExp());

        return xp;
    }

    public static void setPoints(@NotNull Player player, int amount) {
        player.setExp(0);
        player.setLevel(0);
        player.giveExp(amount);
    }
}
