package io.github.toberocat.toberocore.item.property;

import io.github.toberocat.toberocore.item.Priority;
import io.github.toberocat.toberocore.item.ItemInfo;
import org.bukkit.configuration.ConfigurationSection;
import org.bukkit.inventory.ItemFlag;
import org.bukkit.inventory.meta.ItemMeta;
import org.jetbrains.annotations.NotNull;

public class FlagsProperty extends ItemProperty {

    @Override
    public @NotNull Priority priority() {
        return Priority.META;
    }

    @Override
    public boolean apply(@NotNull ItemMeta itemMeta, @NotNull ConfigurationSection configurationSection, @NotNull ItemInfo itemInfo) {
        if (configurationSection.getBoolean("flags")) {
            itemMeta.addItemFlags(ItemFlag.values());
            return true;
        }

        return false;
    }
}
