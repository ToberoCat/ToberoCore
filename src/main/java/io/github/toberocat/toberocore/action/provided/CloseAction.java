package io.github.toberocat.toberocore.action.provided;

import io.github.toberocat.toberocore.action.Action;
import org.bukkit.entity.Player;
import org.jetbrains.annotations.NotNull;

public class CloseAction extends Action {

    @Override
    public @NotNull String label() {
        return "close";
    }

    @Override
    public void run(@NotNull Player player) {
        player.closeInventory();
    }
}
