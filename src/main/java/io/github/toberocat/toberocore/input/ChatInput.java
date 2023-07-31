package io.github.toberocat.toberocore.input;

import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.player.AsyncPlayerChatEvent;
import org.bukkit.plugin.java.JavaPlugin;
import org.jetbrains.annotations.NotNull;

import java.util.UUID;
import java.util.function.Consumer;
import java.util.function.Function;

/**
 * Created: 16.06.2023
 *
 * @author Tobias Madlberger (Tobias)
 */
public class ChatInput implements Listener {
    private final UUID target;
    private final Function<String, Action> callback;

    public ChatInput(UUID target, Function<String, Action> callback) {
        this.target = target;
        this.callback = callback;
    }

    public static void prompt(@NotNull JavaPlugin plugin,
                              @NotNull Player player,
                              @NotNull String message,
                              @NotNull Consumer<String> callback) {
        prompt(plugin, player, message, msg -> {
            callback.accept(msg);
            return Action.SUCCESS;
        });
    }

    public static void prompt(@NotNull JavaPlugin plugin,
                              @NotNull Player player,
                              @NotNull String message,
                              @NotNull Function<String, Action> callback) {
        player.sendMessage(String.format(message));
        ChatInput chatInput = new ChatInput(player.getUniqueId(), callback);
        plugin.getServer().getPluginManager().registerEvents(chatInput, plugin);
    }

    @EventHandler
    private void onChat(AsyncPlayerChatEvent event) {
        Player player = event.getPlayer();
        if (player.getUniqueId() != target)
            return;

        event.setCancelled(true);
        String message = event.getMessage();
        Action result = callback.apply(message);
        event.setMessage("");
        if (result != Action.RETRY)
            event.getHandlers().unregister(this);
    }

    public enum Action {
        SUCCESS,
        CANCEL,
        RETRY
    }
}
