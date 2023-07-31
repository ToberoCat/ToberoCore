package io.github.toberocat.toberocore.item.property;

import io.github.toberocat.toberocore.item.ItemInfo;
import io.github.toberocat.toberocore.item.Priority;
import io.github.toberocat.toberocore.util.StringUtils;
import org.bukkit.configuration.ConfigurationSection;
import org.bukkit.inventory.meta.ItemMeta;
import org.jetbrains.annotations.NotNull;

public class NameProperty extends ItemProperty {

    @Override
    public @NotNull Priority priority() {
        return Priority.META;
    }

    @Override
    public boolean apply(@NotNull ItemMeta meta, @NotNull ConfigurationSection configurationSection, @NotNull ItemInfo itemInfo) {
        String name = configurationSection.getString("name");
        if (name == null || name.isEmpty()) return false;

        name = StringUtils.replace(name, itemInfo.placeholders());
        meta.setDisplayName(StringUtils.format(name));
        return true;
    }
}
