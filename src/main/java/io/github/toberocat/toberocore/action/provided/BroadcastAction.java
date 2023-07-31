package io.github.toberocat.toberocore.action.provided;

import io.github.toberocat.toberocore.action.Action;
import io.github.toberocat.toberocore.util.StringUtils;
import org.bukkit.Bukkit;
import org.bukkit.command.CommandSender;
import org.jetbrains.annotations.NotNull;

public class BroadcastAction extends Action {

    @Override
    public @NotNull String label() {
        return "broadcast";
    }

    @Override
    public void run(@NotNull CommandSender commandSender, @NotNull String provided) {
        Bukkit.broadcastMessage(StringUtils.format(provided));
    }
}
