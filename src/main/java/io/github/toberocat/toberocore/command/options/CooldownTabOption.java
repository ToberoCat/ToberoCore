package io.github.toberocat.toberocore.command.options;

import io.github.toberocat.toberocore.command.exceptions.CommandException;
import io.github.toberocat.toberocore.command.exceptions.CooldownException;
import io.github.toberocat.toberocore.util.CooldownManager;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;
import org.jetbrains.annotations.NotNull;

public class CooldownTabOption implements PlayerOption {

    private final @NotNull CooldownManager cooldownManager;
    private final boolean hideWhenInCooldown;

    public CooldownTabOption(@NotNull CooldownManager cooldownManager, boolean hideWhenInCooldown) {
        this.cooldownManager = cooldownManager;
        this.hideWhenInCooldown = hideWhenInCooldown;
    }

    @Override
    public @NotNull String[] executePlayer(@NotNull Player sender, @NotNull String[] args) throws CommandException {
        if (cooldownManager.hasCooldown(sender.getUniqueId()))
            throw new CooldownException(cooldownManager, sender.getUniqueId());
        return args;
    }

    @Override
    public boolean show(@NotNull CommandSender sender, @NotNull String[] args) {
        if (!(sender instanceof Player player)) return false;
        return !hideWhenInCooldown || !cooldownManager.hasCooldown(player.getUniqueId());
    }
}