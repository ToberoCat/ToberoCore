package io.github.toberocat.toberocore.item.property;

import io.github.toberocat.toberocore.item.Priority;
import io.github.toberocat.toberocore.ToberoCore;
import io.github.toberocat.toberocore.item.ItemInfo;
import io.github.toberocat.toberocore.util.Formatting;
import io.github.toberocat.toberocore.util.StringUtils;
import me.clip.placeholderapi.PlaceholderAPI;
import net.kyori.adventure.text.Component;
import org.bukkit.Bukkit;
import org.bukkit.OfflinePlayer;
import org.bukkit.configuration.ConfigurationSection;
import org.bukkit.inventory.meta.ItemMeta;
import org.jetbrains.annotations.NotNull;

import java.util.UUID;

public class NameProperty extends ItemProperty {

    private final ToberoCore ToberoCore;

    public NameProperty(@NotNull ToberoCore ToberoCore) {
        this.ToberoCore = ToberoCore;
    }

    @Override
    public @NotNull Priority priority() {
        return Priority.META;
    }

    @Override
    public boolean apply(@NotNull ItemMeta meta, @NotNull ConfigurationSection configurationSection, @NotNull ItemInfo itemInfo) {
        String name = configurationSection.getString("name");
        if (name == null || name.isEmpty()) return false;

        name = StringUtils.replace(name, itemInfo.placeholders());
        UUID uuid = itemInfo.uuid();

        if (uuid != null && ToberoCore.placeholderAPI()) {
            OfflinePlayer offlinePlayer = Bukkit.getOfflinePlayer(uuid);
            name = PlaceholderAPI.setPlaceholders(offlinePlayer, name);
        }

        meta.displayName(Component.text(StringUtils.format(name)));
        return true;
    }
}
