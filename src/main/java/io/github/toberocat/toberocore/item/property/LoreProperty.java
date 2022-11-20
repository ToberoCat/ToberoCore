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

import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

public class LoreProperty extends ItemProperty {

    private final ToberoCore ToberoCore;

    public LoreProperty(@NotNull ToberoCore ToberoCore) {
        this.ToberoCore = ToberoCore;
    }

    @Override
    public @NotNull Priority priority() {
        return Priority.META;
    }

    @Override
    public boolean apply(@NotNull ItemMeta itemMeta, @NotNull ConfigurationSection configurationSection, @NotNull ItemInfo itemInfo) {
        List<String> lore = configurationSection.getStringList("lore");
        if (lore.isEmpty()) return false;

        List<Component> components = new ArrayList<>();

        for (String line : lore) {
            line = StringUtils.replace(line, itemInfo.placeholders());
            UUID uuid = itemInfo.uuid();

            if (uuid != null && ToberoCore.placeholderAPI()) {
                OfflinePlayer offlinePlayer = Bukkit.getOfflinePlayer(uuid);
                line = PlaceholderAPI.setPlaceholders(offlinePlayer, line);
            }

            components.add(Component.text(StringUtils.format(line)));
        }

        itemMeta.lore(components);
        return true;
    }
}
