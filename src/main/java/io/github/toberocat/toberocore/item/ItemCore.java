package io.github.toberocat.toberocore.item;

import io.github.toberocat.toberocore.item.property.*;
import org.bukkit.Material;
import org.bukkit.NamespacedKey;
import org.bukkit.configuration.ConfigurationSection;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.ItemMeta;
import org.bukkit.persistence.PersistentDataContainer;
import org.bukkit.persistence.PersistentDataType;
import org.jetbrains.annotations.NotNull;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Map.Entry;
import java.util.Set;

public final class ItemCore {

    static {
        new AmountProperty().register();
        new CommandProperty().register();
        new EnchantmentsProperty().register();
        new FlagsProperty().register();
        new GlowProperty().register();
        new LeatherArmorColorProperty().register();
        new LoreProperty().register();
        new MaterialProperty().register();
        new NameProperty().register();
        new PlayerHeadProperty().register();
    }

    private static NamespacedKey tagsKey;

    private static final Set<ItemProperty> itemProperties = new HashSet<>();

    /* Register */

    public static void registerItemProperty(@NotNull ItemProperty itemProperty) {
        itemProperties.add(itemProperty);
    }

    /* Get */

    private static @NotNull List<ItemProperty> getItemProperties() {
        List<ItemProperty> properties = new ArrayList<>(itemProperties);
        properties.sort((a, b) -> b.applicationWeight() - a.applicationWeight());
        return properties;
    }

    /* Create */

    public static @NotNull ItemResponse advancedCreate(@NotNull ConfigurationSection configurationSection, @NotNull ItemInfo itemInfo) {
        ItemStack itemStack = new ItemStack(Material.AIR);
        String clickActionsPath = null;

        for (ItemProperty itemProperty : getItemProperties()) {
            ItemResponse itemResponse = itemProperty.advancedApply(itemStack, configurationSection, itemInfo);
            itemStack = itemResponse.item();

            if (clickActionsPath == null) {
                String propertyClickActionsPath = itemResponse.clickActionsPath();

                if (propertyClickActionsPath != null) {
                    clickActionsPath = propertyClickActionsPath;
                }
            }
        }

        /* Persistent Data */

        ItemMeta itemMeta = itemStack.getItemMeta();

        if (itemMeta != null) {
            PersistentDataContainer persistentDataContainer = itemMeta.getPersistentDataContainer();
            boolean apply = false;

            for (Entry<NamespacedKey, Integer> entry : itemInfo.persistentInts().entrySet()) {
                persistentDataContainer.set(entry.getKey(), PersistentDataType.INTEGER, entry.getValue());
                apply = true;
            }
            for (Entry<NamespacedKey, String> entry : itemInfo.persistentStrings().entrySet()) {
                persistentDataContainer.set(entry.getKey(), PersistentDataType.STRING, entry.getValue());
                apply = true;
            }

            if (apply) {
                itemStack.setItemMeta(itemMeta);
            }
        }

        return new ItemResponse(itemStack, clickActionsPath);
    }

    public static @NotNull ItemStack create(@NotNull ConfigurationSection configurationSection, @NotNull ItemInfo itemInfo) {
        return advancedCreate(configurationSection, itemInfo).item();
    }

    public static @NotNull ItemStack create(@NotNull ConfigurationSection configurationSection) {
        return create(configurationSection, new ItemInfo());
    }
}
