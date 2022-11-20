package io.github.toberocat.toberocore.item.property;

import io.github.toberocat.toberocore.item.Priority;
import io.github.toberocat.toberocore.item.ItemInfo;
import org.bukkit.Bukkit;
import org.bukkit.Material;
import org.bukkit.OfflinePlayer;
import org.bukkit.configuration.ConfigurationSection;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.ItemMeta;
import org.bukkit.inventory.meta.SkullMeta;
import org.jetbrains.annotations.NotNull;

import java.util.UUID;

public class PlayerHeadProperty extends ItemProperty {

    @Override
    public @NotNull Priority priority() {
        return Priority.MATERIAL;
    }

    @Override
    public @NotNull ItemStack apply(@NotNull ItemStack item, @NotNull ConfigurationSection configurationSection, @NotNull ItemInfo itemInfo) {
        if (!configurationSection.getBoolean("player-head")) {
            return item;
        }

        item.setType(Material.PLAYER_HEAD);

        ItemMeta meta = item.getItemMeta();
        if (!(meta instanceof SkullMeta skullMeta)) return item;

        UUID uuid = itemInfo.uuid();
        if (uuid == null) return item;

        OfflinePlayer offlinePlayer = Bukkit.getOfflinePlayer(uuid);
        skullMeta.setOwningPlayer(offlinePlayer);

        item.setItemMeta(skullMeta);
        return item;
    }
}
