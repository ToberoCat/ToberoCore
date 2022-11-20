package io.github.toberocat.toberocore.gui.slot;

import org.bukkit.inventory.ItemStack;

public abstract class BoolSlot extends EnumSlot<BoolSlot.BoolSelector> {

    public BoolSlot(ItemStack stack, boolean def, Runnable rerender) {
        super(stack, BoolSelector.class, def ? 1 : 0, rerender);
    }

    @Override
    public void changeSelected(String newValue) {
        change(newValue.equals("§7Aktiviert"));
    }

    public abstract void change(boolean value);

    public enum BoolSelector {
        FALSE,
        TRUE;

        @Override
        public String toString() {
            return "§7" +
                    (name().equals("TRUE") ? "Aktiviert" : "Deaktiviert");
        }
    }
}
