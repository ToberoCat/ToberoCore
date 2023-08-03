package io.github.toberocat.toberocore.individual.player;

import io.github.toberocat.toberocore.individual.AbstractIndividuals;
import org.bukkit.Bukkit;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerJoinEvent;
import org.bukkit.event.player.PlayerQuitEvent;
import org.bukkit.plugin.java.JavaPlugin;
import org.jetbrains.annotations.NotNull;

import java.util.UUID;

public abstract class PlayerIndividuals<V> extends AbstractIndividuals<UUID, V> implements Listener {

    public PlayerIndividuals(@NotNull JavaPlugin plugin) {
        Bukkit.getPluginManager().registerEvents(this, plugin);
    }


    @EventHandler
    private void onPlayerJoin(@NotNull PlayerJoinEvent e) {
        load(e.getPlayer().getUniqueId());
    }

    @EventHandler
    private void onPlayerQuit(@NotNull PlayerQuitEvent e) {
        unload(e.getPlayer().getUniqueId());
    }
}
