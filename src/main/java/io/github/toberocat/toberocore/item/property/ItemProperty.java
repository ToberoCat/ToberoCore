package io.github.toberocat.toberocore.item.property;

import io.github.toberocat.toberocore.item.ItemResponse;
import io.github.toberocat.toberocore.item.Priority;
import io.github.toberocat.toberocore.item.ItemCore;
import io.github.toberocat.toberocore.item.ItemInfo;
import org.bukkit.configuration.ConfigurationSection;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.ItemMeta;
import org.jetbrains.annotations.NotNull;

public abstract class ItemProperty {

    public final void register() {
        ItemCore.registerItemProperty(this);
    }

    /*
        Tells the system when this ItemProperty should be used.
     */
    public abstract @NotNull Priority priority();

    public final int applicationWeight() {
        return priority().weight();
    }

    /**
     * Uses the given {@code ConfigurationSection} and {@code ItemInfo} to create an item or modify the given item.
     * Also provides the path to the item's click actions since certain properties may have nested click actions.
     *
     * @param itemStack            the item that will be modified
     * @param configurationSection the configuration data provided
     * @param itemInfo             the item information provided
     * @return the newly created or modified item, as well as the path to the item's click actions
     */
    public @NotNull ItemResponse advancedApply(@NotNull ItemStack itemStack, @NotNull ConfigurationSection configurationSection, @NotNull ItemInfo itemInfo) {
        ItemStack item = apply(itemStack, configurationSection, itemInfo);
        return new ItemResponse(item, null);
    }

    /**
     * Uses the given {@code ConfigurationSection} and {@code ItemInfo} to create an item or modify the given item.
     *
     * @param itemStack            the item that will be modified
     * @param configurationSection the configuration data provided
     * @param itemInfo             the item information provided
     * @return the newly created or modified item
     */
    protected @NotNull ItemStack apply(@NotNull ItemStack itemStack, @NotNull ConfigurationSection configurationSection, @NotNull ItemInfo itemInfo) {
        ItemMeta meta = itemStack.getItemMeta();

        if (meta != null && apply(meta, configurationSection, itemInfo)) {
            itemStack.setItemMeta(meta);
        }

        return itemStack;
    }

    /**
     * Uses the given {@code ConfigurationSection} and {@code ItemInfo} to modify the given item meta.
     *
     * @param itemMeta             the meta to modify
     * @param configurationSection the configuration data provided
     * @param itemInfo             the item information provided
     * @return true if the item meta was modified and should be applied to the item
     */
    protected boolean apply(@NotNull ItemMeta itemMeta, @NotNull ConfigurationSection configurationSection, @NotNull ItemInfo itemInfo) {
        return false;
    }
}
