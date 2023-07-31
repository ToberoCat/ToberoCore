package io.github.toberocat.toberocore.command.subcommands;

import io.github.toberocat.toberocore.command.SubCommand;
import io.github.toberocat.toberocore.command.exceptions.CommandExceptions;
import org.bukkit.command.CommandSender;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

import java.util.List;

/**
 * Created: 31.07.2023
 *
 * @author Tobias Madlberger (Tobias)
 */
public class SectionSubCommand extends SubCommand {
    public SectionSubCommand(@NotNull String label) {
        super(label);
    }

    @Override
    protected boolean run(@NotNull CommandSender sender, @NotNull String[] args) throws CommandExceptions {
        return false;
    }

    @Override
    protected @Nullable List<String> runTab(@NotNull CommandSender sender, @NotNull String[] args) {
        return tabComplete;
    }
}
