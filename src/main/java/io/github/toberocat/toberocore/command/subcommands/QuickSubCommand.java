package io.github.toberocat.toberocore.command.subcommands;

import io.github.toberocat.toberocore.command.SubCommand;
import io.github.toberocat.toberocore.command.exceptions.CommandExceptions;
import org.bukkit.command.CommandSender;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

import java.util.List;

/**
 * Created: 02/11/2022
 *
 * @author Tobias Madlberger (Tobias)
 */
public class QuickSubCommand extends SubCommand implements QuickCommand<QuickSubCommand> {

    private final QuickRun run;

    public QuickSubCommand(@NotNull String label, @NotNull QuickRun run) {
        super(label);
        this.run = run;
    }


    @Override
    protected boolean run(@NotNull CommandSender sender, @NotNull String[] args) throws CommandExceptions {
        return run.apply(sender, args);
    }

    @Override
    protected @Nullable List<String> runTab(@NotNull CommandSender sender, @NotNull String[] args) {
        return null;
    }

    @Override
    public @NotNull QuickSubCommand child(SubCommand command) {
        addChild(command);
        return this;
    }

    public interface QuickRun {
        boolean apply(@NotNull CommandSender sender, @NotNull String[] args) throws CommandExceptions;
    }
}
