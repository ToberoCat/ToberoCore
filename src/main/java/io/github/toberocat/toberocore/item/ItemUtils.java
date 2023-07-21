package io.github.toberocat.toberocore.item;


import com.destroystokyo.paper.profile.PlayerProfile;
import com.destroystokyo.paper.profile.ProfileProperty;
import io.github.toberocat.toberocore.util.StringUtils;
import net.kyori.adventure.text.Component;
import org.bukkit.Bukkit;
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

import java.util.*;

import static io.github.toberocat.toberocore.util.StringUtils.format;

public final class ItemUtils {

    @Deprecated
    public static ItemStack setLore(ItemStack stack, String[] lore) {
        ItemStack newStack = new ItemStack(stack);
        ItemMeta meta = newStack.getItemMeta();
        assert meta != null;
        meta.setLore(Arrays.stream(lore).map(StringUtils::format).toList());
        newStack.setItemMeta(meta);
        return newStack;
    }

    @Deprecated
    public static ItemStack createItem(final Material material, final String name) {
        final ItemStack item = new ItemStack(material, 1);
        final ItemMeta meta = item.getItemMeta();

        meta.displayName(Component.text(format(name)));
        item.setItemMeta(meta);

        return item;
    }

    @Deprecated
    public static ItemStack createItem(final Material material, final String name, final String[] lore) {
        final ItemStack item = new ItemStack(material, 1);
        final ItemMeta meta = item.getItemMeta();

        meta.setDisplayName(format(name));

        meta.setLore(setLore(item, lore).getItemMeta().getLore());

        item.setItemMeta(meta);

        return item;
    }

    public static @NotNull ItemStack createItem(@NotNull Material material,
                                                @NotNull String name,
                                                int amount,
                                                @NotNull String... lore) {
        ItemStack item = new ItemStack(material, amount);
        item.editMeta(meta -> {
            meta.displayName(Component.text(name));
            meta.lore(Arrays.stream(lore)
                    .map(ItemUtils::component)
                    .toList());
        });
        return item;
    }

    public static @NotNull ItemStack createSkull(OfflinePlayer player, int count, String name, String[] lore) {
        ItemStack item = createItem(Material.PLAYER_HEAD, name, count, lore);
        item.editMeta(SkullMeta.class, skull -> skull.setOwningPlayer(player));
        return item;
    }

    public static @NotNull ItemStack createHead(@NotNull String textureId,
                                                @NotNull String title,
                                                int amount,
                                                @NotNull String... lore) {
        ItemStack head = createItem(Material.PLAYER_HEAD, title, amount, lore);
        if (!(head.getItemMeta() instanceof SkullMeta headMeta))
            return head;

        PlayerProfile profile = Bukkit.createProfile(UUID.randomUUID(), "");
        profile.setProperty(new ProfileProperty("textures", textureId));
        headMeta.setPlayerProfile(profile);

        head.setItemMeta(headMeta);
        return head;
    }

    @Deprecated
    public static ItemStack modify(ItemStack stack, String title, String... lore) {
        ItemMeta meta = stack.getItemMeta();
        meta.setDisplayName(format(title));
        meta.setLore(setLore(stack, lore).getItemMeta().getLore());
        ItemStack item = new ItemStack(stack);
        item.setItemMeta(meta);
        return item;
    }

    public static @NotNull ItemStack addEnchantment(@NotNull ItemStack stack,
                                                    @NotNull Enchantment enchantment,
                                                    int strength) {
        ItemMeta meta = stack.getItemMeta();
        if (meta == null) return stack;

        meta.addEnchant(enchantment, strength, true);
        stack.setItemMeta(meta);

        return stack;
    }

    public static <T, Z> @Nullable Z getPersistent(@Nullable ItemStack item,
                                                   @NotNull NamespacedKey key,
                                                   @NotNull PersistentDataType<T, Z> type) {
        if (item == null)
            return null;

        return item.getItemMeta().getPersistentDataContainer().get(key, type);
    }

    public static <T, Z> boolean hasPersistent(@NotNull ItemStack item,
                                               @NotNull NamespacedKey key,
                                               @NotNull PersistentDataType<T, Z> type) {
        return item.getItemMeta().getPersistentDataContainer().has(key, type);
    }

    @Deprecated
    public static List<String> getLore(ItemStack stack) {
        ItemMeta meta = stack.getItemMeta();
        if (meta == null || meta.getLore() == null) return new ArrayList<>();
        return meta.getLore();
    }

    private static @NotNull Component component(@NotNull String title) {
        return Component.text(format(title));
    }
}
