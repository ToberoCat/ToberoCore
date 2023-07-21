package io.github.toberocat.toberocore.gui.page;

import io.github.toberocat.toberocore.gui.slot.Slot;
import org.bukkit.Material;
import org.bukkit.inventory.Inventory;
import org.bukkit.inventory.ItemStack;

import static io.github.toberocat.toberocore.item.ItemUtils.createItem;

public class AutoNavPage extends NavigationPage {
    protected final int[] freeSlots;
    protected static final ItemStack GRAY = createItem(Material.GRAY_STAINED_GLASS_PANE, "", 1);

    protected int lastFree;

    public AutoNavPage(int inventorySize, int[] freeSlots) {
        super(inventorySize);
        this.freeSlots = freeSlots;
    }

    @Override
    public void clear() {
        super.clear();
        lastFree = 0;
    }

    @Override
    public void render(Inventory inventory) {
        inventory.clear();

        for (int i = inventory.getSize() - 9; i < inventory.getSize(); i++)
            inventory.setItem(i, new ItemStack(Material.GRAY_STAINED_GLASS_PANE));

        for (int slot : freeSlots)
            inventory.setItem(slot, GRAY);


        renderSlots(inventory);
    }

    public boolean addSlot(Slot slot) {
        int invSlot = freeSlots[lastFree];
        slots[invSlot] = slot;
        lastFree++;
        isEmpty = false;

        return lastFree >= freeSlots.length;
    }

}
