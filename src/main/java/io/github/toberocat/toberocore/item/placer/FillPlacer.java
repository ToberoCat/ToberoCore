package io.github.toberocat.toberocore.item.placer;

import org.bukkit.configuration.ConfigurationSection;
import org.bukkit.inventory.Inventory;
import org.bukkit.inventory.ItemStack;
import org.jetbrains.annotations.NotNull;

public class FillPlacer extends ItemPlacer {

    @Override
    public void place(@NotNull ItemStack itemStack, @NotNull ConfigurationSection configurationSection, @NotNull Inventory inventory) {
        if (configurationSection.getBoolean("fill")) {
            for (int i = 0; i < inventory.getSize(); i++) {
                inventory.setItem(i, itemStack);
            }
        }
    }
}
