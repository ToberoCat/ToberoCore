package io.github.toberocat.toberocore.item.property;

import io.github.toberocat.toberocore.item.Priority;
import io.github.toberocat.toberocore.item.ItemInfo;
import org.bukkit.NamespacedKey;
import org.bukkit.configuration.ConfigurationSection;
import org.bukkit.enchantments.Enchantment;
import org.bukkit.inventory.meta.ItemMeta;
import org.jetbrains.annotations.NotNull;

import java.util.List;

public class EnchantmentsProperty extends ItemProperty {

    @Override
    public @NotNull Priority priority() {
        return Priority.META;
    }

    @Override
    public boolean apply(@NotNull ItemMeta itemMeta, @NotNull ConfigurationSection configurationSection, @NotNull ItemInfo itemInfo) {
        boolean apply = false;

        for (String a : List.of("enchantments", "enchants")) {
            if (configurationSection.isConfigurationSection(a)) {
                ConfigurationSection enchantsSection = configurationSection.getConfigurationSection(a);

                if (enchantsSection != null) {
                    for (String key : enchantsSection.getKeys(false)) {
                        Enchantment enchantment = Enchantment.getByKey(NamespacedKey.minecraft(key));
                        int level = enchantsSection.getInt(key);

                        if (enchantment != null) {
                            itemMeta.addEnchant(enchantment, level, true);
                            apply = true;
                        }
                    }
                }
            } else {
                List<String> strings = configurationSection.getStringList(a);
                if (strings.isEmpty()) continue;

                for (String string : strings) {
                    String[] parts = string.split(":");
                    Enchantment enchantment = Enchantment.getByKey(NamespacedKey.minecraft(parts[0].toLowerCase()));

                    if (enchantment == null) {
                        continue;
                    }

                    int level = 1;

                    if (parts.length == 2) {
                        try {
                            level = Integer.parseInt(parts[1]);
                        } catch (NumberFormatException ignored) {
                            // ignored
                        }
                    }

                    itemMeta.addEnchant(enchantment, level, true);
                    apply = true;
                }
            }
        }

        return apply;
    }
}
