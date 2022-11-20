package io.github.toberocat.toberocore.gui;

import io.github.toberocat.toberocore.gui.page.AutoNavPage;
import io.github.toberocat.toberocore.gui.page.AutoPage;
import io.github.toberocat.toberocore.gui.page.Page;
import io.github.toberocat.toberocore.gui.slot.Slot;
import org.bukkit.entity.Player;
import org.bukkit.inventory.Inventory;
import org.bukkit.inventory.ItemStack;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

import java.util.function.BiConsumer;
import java.util.function.Consumer;

public abstract class AutoGui extends AbstractGui {
    protected final int[] usableSlots;

    public AutoGui(@NotNull Player player, @NotNull Inventory inventory, int[] usableSlots) {
        super(player, inventory);
        this.usableSlots = usableSlots;
    }

    @Override
    public void clear() {
        pages.clear();
        addPage();

        if (currentPage < pages.size())
            pages.get(currentPage).render(inventory);
    }

    @Override
    protected void addPage() {
        if (usableSlots == null) return;
        pages.add(new AutoNavPage(inventory.getSize(), usableSlots));
    }

    public void addSlot(Slot slot) {
        if (slot == null) return;
        Page page = pages.get(pages.size() - 1);

        boolean execute = false;
        if (page instanceof AutoPage) execute = ((AutoPage) page).addSlot(slot);
        else if (page instanceof AutoNavPage) execute = ((AutoNavPage) page).addSlot(slot);
        if (execute) addPage();
    }

    public void addSlot(ItemStack stack, BiConsumer<Player, ItemStack> click) {
        addSlot(new Slot(stack) {
            @Override
            public void click(@NotNull Player player, @Nullable ItemStack cursor) {
                click.accept(player, cursor);
            }

        });
    }

    public void addSlot(ItemStack stack, Consumer<Player> click) {
        addSlot(stack, (p, c) -> click.accept(p));
    }
}
