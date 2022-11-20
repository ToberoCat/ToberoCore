package io.github.toberocat.toberocore.command.subcommands;

import io.github.toberocat.toberocore.command.SubCommand;
import io.github.toberocat.toberocore.command.exceptions.CommandExceptions;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

import java.util.List;

/**
 * Created: 02/11/2022
 *
 * @author Tobias Madlberger (Tobias)
 */
public abstract class PlayerSubCommand extends SubCommand {
    public PlayerSubCommand(@NotNull String label) {
        super(label);
    }

    protected abstract boolean runPlayer(@NotNull Player player,
                                         @NotNull String[] args)
            throws CommandExceptions;

    protected abstract @Nullable List<String> runPlayerTab(@NotNull Player player,
                                                           @NotNull String[] args);

    @Override
    protected boolean run(@NotNull CommandSender sender, @NotNull String[] args)
            throws CommandExceptions {
        if (sender instanceof Player player) return runPlayer(player, args);
        throw new CommandExceptions("This command can only get run by players");
    }

    @Override
    protected @Nullable List<String> runTab(@NotNull CommandSender sender, @NotNull String[] args) {
        if (sender instanceof Player player) return runPlayerTab(player, args);
        return null;
    }

}
