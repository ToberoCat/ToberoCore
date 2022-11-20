package io.github.toberocat.toberocore.item.placer;

import io.github.toberocat.toberocore.util.InvUtils;
import org.bukkit.configuration.ConfigurationSection;
import org.bukkit.inventory.Inventory;
import org.bukkit.inventory.ItemStack;
import org.jetbrains.annotations.NotNull;

public class BorderPlacer extends ItemPlacer {

    @Override
    public void place(@NotNull ItemStack itemStack, @NotNull ConfigurationSection configurationSection, @NotNull Inventory inventory) {
        if (configurationSection.getBoolean("border")) {
            for (int i : InvUtils.getBorderSlots(inventory.getSize() / 9)) {
                inventory.setItem(i, itemStack);
            }
        }
    }
}
