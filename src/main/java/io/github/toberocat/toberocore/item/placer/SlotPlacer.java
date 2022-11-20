package io.github.toberocat.toberocore.item.placer;

import io.github.toberocat.toberocore.ToberoCore;
import org.bukkit.configuration.ConfigurationSection;
import org.bukkit.inventory.Inventory;
import org.bukkit.inventory.ItemStack;
import org.jetbrains.annotations.NotNull;

public class SlotPlacer extends ItemPlacer {

    private final io.github.toberocat.toberocore.ToberoCore ToberoCore;

    public SlotPlacer(@NotNull ToberoCore ToberoCore) {
        this.ToberoCore = ToberoCore;
    }

    @Override
    public void place(@NotNull ItemStack itemStack, @NotNull ConfigurationSection configurationSection, @NotNull Inventory inventory) {
        if (configurationSection.isInt("slot")) {
            int slot = configurationSection.getInt("slot");

            try {
                inventory.setItem(slot, itemStack);
            } catch (ArrayIndexOutOfBoundsException ex) {
                ToberoCore.getLogger().warning("Item in slot " + slot + " is too high");
                ex.printStackTrace();
            }
        }
    }
}
