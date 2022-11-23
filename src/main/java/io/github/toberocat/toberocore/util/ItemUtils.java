package io.github.toberocat.toberocore.util;


import net.kyori.adventure.text.Component;
import org.bukkit.Material;
import org.bukkit.NamespacedKey;
import org.bukkit.OfflinePlayer;
import org.bukkit.enchantments.Enchantment;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.ItemMeta;
import org.bukkit.inventory.meta.SkullMeta;
import org.bukkit.persistence.PersistentDataType;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

import java.lang.reflect.Field;
import java.util.*;

import static io.github.toberocat.toberocore.util.StringUtils.format;

public final class ItemUtils {
    public static ItemStack setLore(ItemStack stack, String[] lore) {
        ItemStack newStack = new ItemStack(stack);
        ItemMeta meta = newStack.getItemMeta();
        assert meta != null;
        meta.setLore(Arrays.stream(lore).map(StringUtils::format).toList());
        newStack.setItemMeta(meta);
        return newStack;
    }

    public static ItemStack createItem(final Material material, final String name) {
        final ItemStack item = new ItemStack(material, 1);
        final ItemMeta meta = item.getItemMeta();

        meta.displayName(Component.text(format(name)));
        item.setItemMeta(meta);

        return item;
    }

    public static ItemStack createItem(final Material material, final String name, final String[] lore) {
        final ItemStack item = new ItemStack(material, 1);
        final ItemMeta meta = item.getItemMeta();

        meta.setDisplayName(format(name));

        meta.setLore(setLore(item, lore).getItemMeta().getLore());

        item.setItemMeta(meta);

        return item;
    }

    public static @NotNull ItemStack createSkull(OfflinePlayer player, int count, String name, String[] lore) {
        ItemStack item = new ItemStack(Material.PLAYER_HEAD, count, (short) 3);
        SkullMeta skull = (SkullMeta) item.getItemMeta();
        skull.setDisplayName(name);
        skull.setLore(Arrays.asList(lore));
        skull.setOwningPlayer(player);
        item.setItemMeta(skull);
        return item;
    }

    public static ItemStack modify(ItemStack stack, String title, String... lore) {
        ItemMeta meta = stack.getItemMeta();
        meta.setDisplayName(format(title));
        meta.setLore(setLore(stack, lore).getItemMeta().getLore());
        ItemStack item = new ItemStack(stack);
        item.setItemMeta(meta);
        return item;
    }

    public static ItemStack addEnchantment(ItemStack stack, Enchantment enchantment, int strength) {
        ItemMeta meta = stack.getItemMeta();
        if (meta == null) return stack;

        meta.addEnchant(enchantment, strength, true);
        stack.setItemMeta(meta);

        return stack;
    }

    public static <T, Z> @Nullable Z getPersistent(@NotNull ItemStack item,
                                         @NotNull NamespacedKey key,
                                         @NotNull PersistentDataType<T, Z> type) {
        return item.getItemMeta().getPersistentDataContainer().get(key, type);
    }

    public static <T, Z> boolean hasPersistent(@NotNull ItemStack item,
                                         @NotNull NamespacedKey key,
                                         @NotNull PersistentDataType<T, Z> type) {
        return item.getItemMeta().getPersistentDataContainer().has(key, type);
    }

    public static List<String> getLore(ItemStack stack) {
        ItemMeta meta = stack.getItemMeta();
        if (meta == null || meta.getLore() == null) return new ArrayList<>();
        return meta.getLore();
    }
}
