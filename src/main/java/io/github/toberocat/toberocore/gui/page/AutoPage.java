package io.github.toberocat.toberocore.gui.page;

import io.github.toberocat.toberocore.gui.slot.Slot;

public class AutoPage extends Page {

    private final int[] freeSlots;
    protected int lastFree;

    public AutoPage(int inventorySize, int[] freeSlots) {
        super(inventorySize);
        this.freeSlots = freeSlots;
    }

    @Override
    public void clear() {
        super.clear();
        lastFree = 0;
    }

    public boolean addSlot(Slot slot) {
        int invSlot = freeSlots[lastFree];
        slots[invSlot] = slot;
        lastFree++;

        return lastFree >= freeSlots.length;
    }
}
