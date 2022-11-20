package io.github.toberocat.toberocore.action.provided;

import io.github.toberocat.toberocore.util.Formatting;
import io.github.toberocat.toberocore.action.Action;
import io.github.toberocat.toberocore.util.StringUtils;
import net.kyori.adventure.text.Component;
import org.bukkit.entity.Player;
import org.jetbrains.annotations.NotNull;

public class ActionbarAction extends Action {

    @Override
    public @NotNull String label() {
        return "actionbar";
    }

    @Override
    public void run(@NotNull Player player, @NotNull String provided) {
        player.sendActionBar(Component.text(StringUtils.format(provided)));
    }
}
