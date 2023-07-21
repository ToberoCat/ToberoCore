package io.github.toberocat.toberocore.util;

import io.github.toberocat.toberocore.ToberoCore;
import org.bukkit.Bukkit;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerQuitEvent;
import org.bukkit.inventory.ItemStack;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

import java.util.*;

/**
 * Created: 09.07.2023
 *
 * @author Tobias Madlberger (Tobias)
 */
public class PlayerSession {
    private static final Map<UUID, PlayerSession> playerSessions = new HashMap<>();
    static {
        Bukkit.getServer().getPluginManager().registerEvents(new LeaveListener(), ToberoCore.getPlugin());
    }

    private final @NotNull Player player;
    private final ItemStack[] previousInventory;

    public PlayerSession(@NotNull Player player) {
        playerSessions.put(player.getUniqueId(), this);
        this.player = player;
        this.previousInventory = player.getInventory().getContents().clone();
        player.getInventory().clear();
    }

    public void closeSession() {
        playerSessions.remove(player.getUniqueId());

        player.getInventory().clear();
        player.getInventory().setContents(previousInventory);
    }

    public @NotNull Optional<PlayerSession> createSession(@NotNull Player player) {
        if (playerSessions.containsKey(player.getUniqueId()))
            return Optional.empty();
        return Optional.of(new PlayerSession(player));
    }

    public @Nullable PlayerSession getSession(@NotNull UUID player) {
        return playerSessions.get(player);
    }

    private static class LeaveListener implements Listener {
        @EventHandler
        private void leave(@NotNull PlayerQuitEvent event) {
            playerSessions.remove(event.getPlayer().getUniqueId());
        }
    }
}
