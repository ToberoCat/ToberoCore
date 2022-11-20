package io.github.toberocat.toberocore.action.provided;

import io.github.toberocat.toberocore.action.Action;
import org.bukkit.Bukkit;
import org.bukkit.command.CommandSender;
import org.jetbrains.annotations.NotNull;

public class ConsoleCommandAction extends Action {

    @Override
    public @NotNull String label() {
        return "console";
    }

    @Override
    public void run(@NotNull CommandSender commandSender, @NotNull String provided) {
        Bukkit.dispatchCommand(Bukkit.getConsoleSender(), provided);
    }
}
