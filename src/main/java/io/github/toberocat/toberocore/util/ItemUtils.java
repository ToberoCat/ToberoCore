package io.github.toberocat.toberocore.util;

import com.mojang.authlib.GameProfile;
import com.mojang.authlib.properties.Property;
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
import org.bukkit.profile.PlayerProfile;
import org.bukkit.profile.PlayerTextures;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

import java.lang.reflect.Field;
import java.net.MalformedURLException;
import java.net.URL;
import java.util.*;
import java.util.function.Consumer;

import static io.github.toberocat.toberocore.util.StringUtils.format;

public final class ItemUtils {

    public static <T extends ItemMeta> void editMeta(@NotNull ItemStack stack, @NotNull Class<T> clazz, @NotNull Consumer<T> metaConsumer) {
        ItemMeta meta = stack.getItemMeta();
        if (meta == null) return;
        metaConsumer.accept(clazz.cast(meta));
        stack.setItemMeta(meta);
    }

    public static void editMeta(@NotNull ItemStack stack, @NotNull Consumer<ItemMeta> metaConsumer) {
        ItemMeta meta = stack.getItemMeta();
        if (meta == null) return;
        metaConsumer.accept(meta);
        stack.setItemMeta(meta);
    }

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

        meta.setDisplayName(format(name));
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

    public static @NotNull ItemStack createItem(@NotNull Material material, @NotNull String name, int amount, @NotNull String... lore) {
        ItemStack item = new ItemStack(material, amount);
        editMeta(item, meta -> {
            meta.setDisplayName(name);
            meta.setLore(Arrays.stream(lore).toList());
        });
        return item;
    }

    public static @NotNull ItemStack createSkull(OfflinePlayer player, int count, String name, String[] lore) {
        ItemStack item = createItem(Material.PLAYER_HEAD, name, count, lore);
        editMeta(item, SkullMeta.class, skull -> skull.setOwningPlayer(player));
        return item;
    }

    public static @NotNull ItemStack createHead(@NotNull String textureId,
                                                @NotNull String title,
                                                int amount,
                                                @NotNull String... lore) {
        ItemStack head = ItemUtils.createItem(Material.PLAYER_HEAD, title, amount, lore);
        if (textureId.isEmpty()) {
            return head;
        }
        try {
            PlayerProfile profile = getProfile(textureId);
            SkullMeta meta = (SkullMeta) head.getItemMeta();
            assert meta != null;
            meta.setOwnerProfile(profile);
            head.setItemMeta(meta);
        } catch (MalformedURLException e) {
            throw new RuntimeException(e);
        }
        return head;
    }

    private static PlayerProfile getProfile(String base64) throws MalformedURLException {
        PlayerProfile profile = Bukkit.createPlayerProfile(UUID.randomUUID()); // Get a new player profile
        PlayerTextures textures = profile.getTextures();
        URL urlObject = getUrlFromBase64(base64);
        textures.setSkin(urlObject); // Set the skin of the player profile to the URL
        profile.setTextures(textures); // Set the textures back to the profile
        return profile;
    }

    public static URL getUrlFromBase64(String base64) throws MalformedURLException {
        String decoded = new String(Base64.getDecoder().decode(base64));
        return new URL(decoded.substring("{\"textures\":{\"SKIN\":{\"url\":\"".length(), decoded.length() - "\"}}}".length()));
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

    public static @NotNull ItemStack addEnchantment(@NotNull ItemStack stack, @NotNull Enchantment enchantment, int strength) {
        ItemMeta meta = stack.getItemMeta();
        if (meta == null) return stack;

        meta.addEnchant(enchantment, strength, true);
        stack.setItemMeta(meta);

        return stack;
    }

    public static <T, Z> @Nullable Z getPersistent(@NotNull ItemStack item, @NotNull NamespacedKey key, @NotNull PersistentDataType<T, Z> type) {
        return item.getItemMeta().getPersistentDataContainer().get(key, type);
    }

    public static <T, Z> boolean hasPersistent(@NotNull ItemStack item, @NotNull NamespacedKey key, @NotNull PersistentDataType<T, Z> type) {
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
