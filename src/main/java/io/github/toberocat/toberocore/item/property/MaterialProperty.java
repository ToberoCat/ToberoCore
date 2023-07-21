package io.github.toberocat.toberocore.item.property;

import io.github.toberocat.toberocore.item.Priority;
import io.github.toberocat.toberocore.item.ItemInfo;
import org.bukkit.Material;
import org.bukkit.configuration.ConfigurationSection;
import org.bukkit.inventory.ItemStack;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

public class MaterialProperty extends ItemProperty {

    @Override
    public @NotNull Priority priority() {
        return Priority.MATERIAL;
    }

    private @Nullable String getValue(@NotNull ConfigurationSection configurationSection) {
        return configurationSection.getString("material");
    }

    @Override
    public @NotNull ItemStack apply(@NotNull ItemStack item, @NotNull ConfigurationSection configurationSection, @NotNull ItemInfo itemInfo) {
        String materialName = getValue(configurationSection);
        System.out.println(configurationSection);
        if (materialName == null) return item;

        Material material = Material.getMaterial(materialName);

        if (material != null) {
            item.setType(material);
        }

        return item;
    }
}
