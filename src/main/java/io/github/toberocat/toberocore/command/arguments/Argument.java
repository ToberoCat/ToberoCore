package io.github.toberocat.toberocore.command.arguments;

import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

import java.util.List;

public interface Argument<R> {
    @NotNull R parse(Player player, String arg);

    @Nullable List<String> tab(CommandSender player);

    @NotNull String descriptionKey();

    @NotNull String usageKey();
}
