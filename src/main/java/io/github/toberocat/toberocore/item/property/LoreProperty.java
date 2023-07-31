package io.github.toberocat.toberocore.item.property;

import io.github.toberocat.toberocore.item.ItemInfo;
import io.github.toberocat.toberocore.item.Priority;
import io.github.toberocat.toberocore.util.StringUtils;
import org.bukkit.configuration.ConfigurationSection;
import org.bukkit.inventory.meta.ItemMeta;
import org.jetbrains.annotations.NotNull;

import java.util.ArrayList;
import java.util.List;

public class LoreProperty extends ItemProperty {

    @Override
    public @NotNull Priority priority() {
        return Priority.META;
    }

    @Override
    public boolean apply(@NotNull ItemMeta itemMeta, @NotNull ConfigurationSection configurationSection, @NotNull ItemInfo itemInfo) {
        List<String> lore = configurationSection.getStringList("lore");
        if (lore.isEmpty()) return false;

        List<String> components = new ArrayList<>();

        for (String line : lore) {
            line = StringUtils.replace(line, itemInfo.placeholders());
            components.add(StringUtils.format(line));
        }

        itemMeta.setLore(components);
        return true;
    }
}
