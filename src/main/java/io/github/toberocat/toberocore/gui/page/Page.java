package io.github.toberocat.toberocore.gui.page;

import io.github.toberocat.toberocore.gui.slot.Slot;
import org.bukkit.entity.Player;
import org.bukkit.event.inventory.InventoryClickEvent;
import org.bukkit.inventory.Inventory;

public class Page {
    protected Slot[] slots;

    protected boolean isEmpty = true;

    public Page(int inventorySize) {
        slots = new Slot[inventorySize];
    }

    public void render(Inventory inventory) {
        inventory.clear();

        renderSlots(inventory);
    }

    protected void renderSlots(Inventory inventory) {
        for (int i = 0; i < inventory.getSize(); i++) {
            Slot slot = slots[i];
            if (slot == null) continue;

            inventory.setItem(i, slot.getStack());
        }
    }

    /**
     * @return Amount the current page should get changed
     */
    public int click(InventoryClickEvent event, int currentPage) {
        if (event.getClickedInventory() == null && event.getCurrentItem() == null) return 0;

        if (event.getWhoClicked() instanceof Player player) {

            Slot slot = slots[event.getSlot()];
            if (slot == null) return 0;
            if (event.getClick().isRightClick()) slot.rightClick(player, event.getCursor());
            else slot.click(player, event.getCursor());
        }
        return 0;
    }

    public boolean addSlot(Slot slot, int slotPos) {
        slots[slotPos] = slot;
        this.isEmpty = false;
        return false;
    }

    public void clear() {
        slots = new Slot[slots.length];
    }

    public Slot[] getSlots() {
        return slots;
    }

    public boolean isEmpty() {
        return isEmpty;
    }
}
