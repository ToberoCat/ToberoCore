package io.github.toberocat.toberocore.action.provided;

import io.github.toberocat.toberocore.action.Action;
import io.github.toberocat.toberocore.util.ExpUtils;
import io.github.toberocat.toberocore.util.MathUtils;
import org.bukkit.entity.Player;
import org.jetbrains.annotations.NotNull;

public class AddExpAction extends Action {
    @Override
    public @NotNull String label() {
        return "add_exp";
    }

    @Override
    public void run(@NotNull Player player, @NotNull String provided) {
        if (!MathUtils.isNumeric(provided)) return;

        ExpUtils.addPoints(player, Integer.parseInt(provided));
    }
}
