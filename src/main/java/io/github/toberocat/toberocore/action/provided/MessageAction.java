package io.github.toberocat.toberocore.action.provided;

import io.github.toberocat.toberocore.ToberoCore;
import io.github.toberocat.toberocore.action.Action;
import io.github.toberocat.toberocore.util.Formatting;
import io.github.toberocat.toberocore.util.StringUtils;
import me.clip.placeholderapi.PlaceholderAPI;
import net.kyori.adventure.text.Component;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;
import org.jetbrains.annotations.NotNull;

public class MessageAction extends Action {

    private final io.github.toberocat.toberocore.ToberoCore ToberoCore;

    public MessageAction(@NotNull ToberoCore ToberoCore) {
        this.ToberoCore = ToberoCore;
    }

    @Override
    public @NotNull String label() {
        return "message";
    }

    @Override
    public void run(@NotNull CommandSender commandSender, @NotNull String provided) {
        if (ToberoCore.placeholderAPI() && commandSender instanceof Player player) {
            provided = PlaceholderAPI.setPlaceholders(player, provided);
        }

        commandSender.sendMessage(Component.text(StringUtils.format(provided)));
    }
}
