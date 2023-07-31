package io.github.toberocat.toberocore.action.provided;

import io.github.toberocat.toberocore.action.Action;
import io.github.toberocat.toberocore.util.StringUtils;
import org.bukkit.command.CommandSender;
import org.jetbrains.annotations.NotNull;

public class MessageAction extends Action {

    @Override
    public @NotNull String label() {
        return "message";
    }

    @Override
    public void run(@NotNull CommandSender commandSender, @NotNull String provided) {
        commandSender.sendMessage(StringUtils.format(provided));
    }
}
