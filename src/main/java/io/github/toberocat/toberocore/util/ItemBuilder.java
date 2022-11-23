package io.github.toberocat.toberocore.util;

import net.kyori.adventure.text.Component;
import org.bukkit.Material;
import org.bukkit.NamespacedKey;
import org.bukkit.enchantments.Enchantment;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.ItemMeta;
import org.bukkit.persistence.PersistentDataType;
import org.jetbrains.annotations.NotNull;

import java.util.Arrays;

import static io.github.toberocat.toberocore.util.StringUtils.format;

/**
 * Created: 23/11/2022
 *
 * @author Tobias Madlberger (Tobias)
 */
@SuppressWarnings("unused")
public class ItemBuilder {
    private final ItemStack item;
    private final ItemMeta meta;

    public ItemBuilder() {
        this(new ItemStack(Material.AIR));
    }

    public ItemBuilder(ItemStack item) {
        this.item = item;
        this.meta = item.getItemMeta();
    }

    public @NotNull ItemBuilder title(@NotNull String title) {
        meta.displayName(component(title));
        return this;
    }

    public @NotNull ItemBuilder material(@NotNull Material material) {
        item.setType(material);
        return this;
    }

    public @NotNull ItemBuilder amount(int amount) {
        item.setAmount(amount);
        return this;
    }

    public @NotNull ItemBuilder lore(@NotNull String... lore) {
        meta.lore(Arrays
                .stream(lore)
                .map(this::component)
                .toList());
        return this;
    }

    public @NotNull ItemBuilder enchant(@NotNull Enchantment enchantment, int strength) {
        meta.addEnchant(enchantment, strength, true);
        return this;
    }

    public @NotNull <T, Z> ItemBuilder persistent(@NotNull NamespacedKey key,
                                                  @NotNull PersistentDataType<T, Z> type,
                                                  Z item) {
        meta.getPersistentDataContainer().set(key, type, item);
        return this;
    }

    public @NotNull ItemStack create() {
        item.setItemMeta(meta);
        return item;
    }

    private @NotNull Component component(@NotNull String title) {
        return Component.text(format(title));
    }
}
