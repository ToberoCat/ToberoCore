package io.github.toberocat.toberocore.item.placer;

import io.github.toberocat.toberocore.item.ItemCore;
import org.bukkit.configuration.ConfigurationSection;
import org.bukkit.inventory.Inventory;
import org.bukkit.inventory.ItemStack;
import org.jetbrains.annotations.NotNull;

public abstract class ItemPlacer {

    public final void register() {
        ItemCore.registerItemPlacer(this);
    }

    public abstract void place(@NotNull ItemStack itemStack, @NotNull ConfigurationSection configurationSection, @NotNull Inventory inventory);
}
