package io.github.toberocat.toberocore.util;

import io.github.toberocat.toberocore.ToberoCore;
import org.bukkit.Bukkit;
import org.bukkit.Material;
import org.bukkit.NamespacedKey;
import org.bukkit.enchantments.Enchantment;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerDropItemEvent;
import org.bukkit.event.player.PlayerInteractEvent;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.ItemMeta;
import org.bukkit.persistence.PersistentDataType;
import org.jetbrains.annotations.NotNull;

import java.util.*;
import java.util.function.Consumer;

import static io.github.toberocat.toberocore.util.StringUtils.format;

/**
 * Created: 23/11/2022
 *
 * @author Tobias Madlberger (Tobias)
 */
@SuppressWarnings("unused")
public class ItemBuilder {
    private static final NamespacedKey ID_KEY = new NamespacedKey(ToberoCore.getPlugin(), "item-id");
    private final ItemEvents events;
    private final ItemStack item;
    private final ItemMeta meta;
    private final UUID id;

    public ItemBuilder() {
        this(new ItemStack(Material.DIRT, 1));
    }

    public ItemBuilder(ItemStack item) {
        this.item = item;
        this.meta = item.getItemMeta();
        this.events = new ItemEvents();
        this.id = UUID.randomUUID();
        Bukkit.getServer().getPluginManager().registerEvents(events, ToberoCore.getPlugin());
    }

    public @NotNull ItemBuilder title(@NotNull String title) {
        meta.setDisplayName(component(title));
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
        meta.setLore(Arrays.stream(lore).map(this::component).toList());
        return this;
    }

    public @NotNull ItemBuilder click(@NotNull Consumer<PlayerInteractEvent> callback) {
        events.click.add(callback);
        return this;
    }

    public @NotNull ItemBuilder drop(@NotNull Consumer<PlayerDropItemEvent> callback) {
        events.drop.add(callback);
        return this;
    }

    public @NotNull ItemBuilder enchant(@NotNull Enchantment enchantment, int strength) {
        meta.addEnchant(enchantment, strength, true);
        return this;
    }

    public @NotNull <T, Z> ItemBuilder persistent(@NotNull NamespacedKey key, @NotNull PersistentDataType<T, Z> type, Z item) {
        meta.getPersistentDataContainer().set(key, type, item);
        return this;
    }

    public @NotNull ItemBuilder editMeta(@NotNull Consumer<ItemMeta> callback) {
        callback.accept(meta);
        return this;
    }

    public @NotNull <T extends ItemMeta> ItemBuilder editMeta(@NotNull Class<T> clazz, @NotNull Consumer<T> callback) {
        if (meta.getClass() == clazz) callback.accept(clazz.cast(meta));
        return this;
    }

    public @NotNull ItemStack create() {
        persistent(ID_KEY, PersistentDataType.STRING, id.toString());
        item.setItemMeta(meta);
        return item;
    }

    private @NotNull String component(@NotNull String title) {
        return format(title);
    }

    private class ItemEvents implements Listener {
        private final @NotNull List<Consumer<PlayerInteractEvent>> click;
        private final @NotNull List<Consumer<PlayerDropItemEvent>> drop;

        public ItemEvents() {
            this.click = new ArrayList<>();
            this.drop = new ArrayList<>();
        }

        @EventHandler
        private void click(@NotNull PlayerInteractEvent event) {
            if (!Objects.equals(ItemUtils.getPersistent(event.getItem(), ID_KEY, PersistentDataType.STRING), id.toString()))
                return;
            click.forEach(x -> x.accept(event));
        }

        @EventHandler
        private void drop(@NotNull PlayerDropItemEvent event) {
            if (!Objects.equals(ItemUtils.getPersistent(event.getItemDrop().getItemStack(), ID_KEY, PersistentDataType.STRING), id.toString()))
                return;
            drop.forEach(x -> x.accept(event));
        }
    }
}
