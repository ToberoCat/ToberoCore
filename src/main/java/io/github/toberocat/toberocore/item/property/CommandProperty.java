package io.github.toberocat.toberocore.item.property;

import io.github.toberocat.toberocore.item.Priority;
import io.github.toberocat.toberocore.item.ItemInfo;
import org.bukkit.Bukkit;
import org.bukkit.command.ConsoleCommandSender;
import org.bukkit.configuration.ConfigurationSection;
import org.bukkit.entity.Player;
import org.bukkit.inventory.ItemStack;
import org.jetbrains.annotations.NotNull;

import java.util.UUID;

public class CommandProperty extends ItemProperty {

    @Override
    public @NotNull Priority priority() {
        return Priority.IGNORANT;
    }

    @Override
    public @NotNull ItemStack apply(@NotNull ItemStack itemStack, @NotNull ConfigurationSection configurationSection, @NotNull ItemInfo itemInfo) {
        String name = null;
        UUID uuid = itemInfo.uuid();

        if (uuid != null) {
            Player player = Bukkit.getPlayer(uuid);

            if (player != null) {
                name = player.getName();
            }
        }

        ConsoleCommandSender consoleCommandSender = Bukkit.getConsoleSender();

        for (String command : configurationSection.getStringList("commands")) {
            if (name != null) {
                command = command.replace("{player}", name);
            }

            Bukkit.dispatchCommand(consoleCommandSender, command);
        }

        return itemStack;
    }
}
