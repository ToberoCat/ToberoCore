package io.github.toberocat.toberocore.listener;

import io.github.toberocat.toberocore.gui.AbstractGui;
import org.bukkit.Bukkit;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.inventory.InventoryClickEvent;
import org.bukkit.event.inventory.InventoryCloseEvent;
import org.bukkit.event.inventory.InventoryDragEvent;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.UUID;

public class GuiListener implements Listener {

    public static final ArrayList<AbstractGui> GUIS = new ArrayList<>();
    private static final HashMap<UUID, Integer> currentTests = new HashMap<>();

    @EventHandler
    public void onInventoryClick(final InventoryClickEvent e) {
        if (GUIS.stream().noneMatch(x -> x.getInventory() == e.getClickedInventory())) return;

        for (AbstractGui gui : new ArrayList<>(GUIS)) gui.click(e);
    }

    @EventHandler
    public void onInventoryDrag(final InventoryDragEvent e) {
        for (AbstractGui gui : new ArrayList<>(GUIS)) gui.drag(e);

    }

    @EventHandler
    public void onInventoryClose(final InventoryCloseEvent e) {
        for (AbstractGui gui : new ArrayList<>(GUIS)) gui.close(e);

    }

    public static void cleanup() {
        Bukkit.getOnlinePlayers().forEach((player) ->
                player.closeInventory(InventoryCloseEvent.Reason.UNLOADED));
    }
}
