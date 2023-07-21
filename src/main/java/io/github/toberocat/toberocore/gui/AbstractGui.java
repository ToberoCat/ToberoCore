package io.github.toberocat.toberocore.gui;

import io.github.toberocat.toberocore.gui.page.Page;
import io.github.toberocat.toberocore.gui.settings.GuiSettings;
import io.github.toberocat.toberocore.gui.slot.Slot;
import io.github.toberocat.toberocore.listener.GuiListener;
import io.github.toberocat.toberocore.util.MathUtils;
import io.github.toberocat.toberocore.util.StringUtils;
import org.bukkit.Bukkit;
import org.bukkit.Material;
import org.bukkit.entity.Player;
import org.bukkit.event.inventory.InventoryClickEvent;
import org.bukkit.event.inventory.InventoryCloseEvent;
import org.bukkit.event.inventory.InventoryDragEvent;
import org.bukkit.inventory.Inventory;
import org.bukkit.inventory.ItemStack;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

import java.util.ArrayList;
import java.util.List;
import java.util.function.BiConsumer;
import java.util.function.Consumer;

import static io.github.toberocat.toberocore.item.ItemUtils.createItem;

public abstract class AbstractGui {

    protected final GuiSettings settings;
    protected final Player player;
    protected Inventory inventory;
    protected int currentPage;
    protected Runnable close;
    protected List<Page> pages;

    public AbstractGui(@NotNull Player player, @NotNull Inventory inventory) {
        GuiListener.GUIS.add(this);

        this.inventory = inventory;
        this.pages = new ArrayList<>();
        this.currentPage = 0;
        this.settings = readSettings();
        this.player = player;

        player.openInventory(inventory);
        addPage();
    }

    /* Static utility function */
    public static Inventory createInventory(Player player, int size, String title) {
        return Bukkit.createInventory(player, size, StringUtils.format(title));
    }

    /* Abstract methods */

    protected abstract void addPage();

    protected abstract GuiSettings readSettings();


    /* Event methods */

    public void click(InventoryClickEvent event) {
        event.setCancelled(true);
        int lastCurrentPage = currentPage;

        if (settings.isPageArrows()) {
            ItemStack clicked = event.getCurrentItem();
            if (clicked == null) return;

            if (event.getSlot() == inventory.getSize() - 9 &&
                    clicked.getType() == Material.ARROW) currentPage--;

            if (event.getSlot() == inventory.getSize() - 1 &&
                    clicked.getType() == Material.ARROW) currentPage++;
        }

        if (settings.getQuitGui() != null && event.getSlot() == inventory.getSize() - 5) {
            settings.getQuitGui().run();
            return;
        }

        currentPage += pages.get(currentPage).click(event, currentPage);
        currentPage = MathUtils.clamp(currentPage, 0, pages.size());


        if (lastCurrentPage != currentPage) render();
    }

    public void drag(InventoryDragEvent event) {
        event.setCancelled(true);
    }

    public final void close(@Nullable InventoryCloseEvent event) {
        if (event == null) return;

        if (event.getInventory().equals(inventory)) {
            GuiListener.GUIS.remove(this);
            if (close != null) close.run();
        }
    }


    /* Inventory Management */

    public void render() {
        pages.get(currentPage).render(inventory);
        if (settings.isPageArrows()) renderArrows(inventory, currentPage, pages.size() - 1);
        if (settings.getQuitGui() != null) inventory.setItem(inventory.getSize() - 5,
                createItem(Material.BARRIER, "&cExit", 1));
    }

    protected void renderArrows(Inventory inventory, int current, int max) {
        if (current != 0) inventory.setItem(inventory.getSize() - 9,
                createItem(Material.ARROW, "&c&lBack", 1));
        if (max != current && max > 0 && !pages.get(max).isEmpty())
            inventory.setItem(inventory.getSize() - 1,
                createItem(Material.ARROW, "&a&lNext", 1));
    }

    public void clear() {
        pages.get(currentPage).clear();
        pages.get(currentPage).render(inventory);
    }

    public void addSlot(Slot slot, int page, int invSlot) {
        if (pages.get(page).addSlot(slot, invSlot)) addPage();
    }


    public void addSlot(ItemStack stack, int page, int invSlot, BiConsumer<Player, ItemStack> click) {
        if (pages.get(page).addSlot(new Slot(stack) {
            @Override
            public void click(@NotNull Player player, @Nullable ItemStack cursor) {
                click.accept(player, cursor);
            }
        }, invSlot)) addPage();
    }

    public void addSlot(ItemStack stack, int page, int invSlot, Consumer<Player> click) {
        addSlot(stack, page, invSlot, (p, c) -> click.accept(p));
    }

    public void changePage(int amount) {
        currentPage = MathUtils.clamp(currentPage + amount, 0, pages.size());
        render();
    }

    /* Getter and Setter */

    public void closeEvent(Runnable close) {
        this.close = close;
    }

    public Inventory getInventory() {
        return inventory;
    }

    public void setInventory(Inventory inventory) {
        this.inventory = inventory;
    }
}
