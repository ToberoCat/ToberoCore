package io.github.toberocat.toberocore.action.provided;

import com.google.common.io.ByteArrayDataOutput;
import com.google.common.io.ByteStreams;
import io.github.toberocat.toberocore.action.Action;
import org.bukkit.entity.Player;
import org.bukkit.plugin.java.JavaPlugin;
import org.jetbrains.annotations.NotNull;

public class ConnectAction extends Action {
    private final JavaPlugin plugin;
    public ConnectAction(@NotNull JavaPlugin plugin) {
        plugin.getServer().getMessenger().registerOutgoingPluginChannel(plugin, "BungeeCord");
        this.plugin = plugin;
    }

    @Override
    public @NotNull String label() {
        return "connect";
    }

    @Override
    public void run(@NotNull Player player, @NotNull String server) {
        byte[] message = ("TransferPlayer:" + player.getName() + ":" + server).getBytes();
        player.sendPluginMessage(plugin, "BungeeCord", message);

        ByteArrayDataOutput out = ByteStreams.newDataOutput();
        out.writeUTF("Connect");
        out.writeUTF(server);

        player.sendPluginMessage(plugin, "BungeeCord", out.toByteArray());
    }
}
