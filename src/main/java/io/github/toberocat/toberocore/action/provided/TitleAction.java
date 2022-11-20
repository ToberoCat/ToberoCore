package io.github.toberocat.toberocore.action.provided;

import io.github.toberocat.toberocore.ToberoCore;
import io.github.toberocat.toberocore.action.Action;
import me.clip.placeholderapi.PlaceholderAPI;
import org.bukkit.entity.Player;
import org.jetbrains.annotations.NotNull;

public class TitleAction extends Action {

    private final io.github.toberocat.toberocore.ToberoCore ToberoCore;

    public TitleAction(@NotNull ToberoCore ToberoCore) {
        this.ToberoCore = ToberoCore;
    }

    @Override
    public @NotNull String label() {
        return "title";
    }

    @Override
    public void run(@NotNull Player player, @NotNull String[] args) {
        int length = args.length;
        if (args.length < 1) return;

        String title = args[0].replace("_", " ");
        if (ToberoCore.placeholderAPI()) title = PlaceholderAPI.setPlaceholders(player, title);

        String subtitle = null;

        if (length >= 2) {
            subtitle = args[1];

            if (ToberoCore.placeholderAPI()) {
                subtitle = PlaceholderAPI.setPlaceholders(player, subtitle);
            }
        }

        int in = 20;

        if (length >= 3) {
            try {
                in = Integer.parseInt(args[2]);
            } catch (NumberFormatException ignored) {
                // ignored
            }
        }

        int stay = 5 * 20;

        if (length >= 4) {
            try {
                stay = Integer.parseInt(args[3]);
            } catch (NumberFormatException ignored) {
                // ignored
            }
        }

        int out = 20;

        if (length >= 3) {
            try {
                out = Integer.parseInt(args[4]);
            } catch (NumberFormatException ignored) {
                // ignored
            }
        }

        player.sendTitle(title, subtitle, in, stay, out);
    }
}
