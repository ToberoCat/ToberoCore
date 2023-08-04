package io.github.toberocat.toberocore.command.options;

import io.github.toberocat.toberocore.command.exceptions.CooldownException;
import io.github.toberocat.toberocore.util.CooldownManager;
import org.bukkit.entity.Player;
import org.bukkit.plugin.java.JavaPlugin;
import org.jetbrains.annotations.NotNull;

public class CooldownOption implements PlayerOption {
    private final @NotNull CooldownManager cooldownManager;
    private final @NotNull JavaPlugin plugin;

    public CooldownOption(@NotNull JavaPlugin plugin, @NotNull CooldownManager cooldownManager) {
        this.cooldownManager = cooldownManager;
        this.plugin = plugin;
    }

    @Override
    public @NotNull String[] executePlayer(@NotNull Player sender, @NotNull String[] args) throws CooldownException {
        cooldownManager.runCooldown(plugin, sender.getUniqueId());
        return args;
    }
}
