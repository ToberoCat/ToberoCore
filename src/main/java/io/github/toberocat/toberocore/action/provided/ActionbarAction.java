package io.github.toberocat.toberocore.action.provided;

import io.github.toberocat.toberocore.action.Action;
import net.md_5.bungee.api.ChatMessageType;
import net.md_5.bungee.api.chat.TextComponent;
import org.bukkit.Bukkit;
import org.bukkit.entity.Player;
import org.jetbrains.annotations.NotNull;

public class ActionbarAction extends Action {

    @Override
    public @NotNull String label() {
        return "actionbar";
    }

    @Override
    public void run(@NotNull Player player, @NotNull String provided) {
        player.spigot().sendMessage(ChatMessageType.ACTION_BAR, new TextComponent(provided));
    }
}
