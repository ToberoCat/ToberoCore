package io.github.toberocat.toberocore.command.subcommands;

import io.github.toberocat.toberocore.command.SubCommand;
import io.github.toberocat.toberocore.command.exceptions.CommandExceptions;
import org.bukkit.entity.Player;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

import java.util.List;

/**
 * Created: 02/11/2022
 *
 * @author Tobias Madlberger (Tobias)
 */
public class QuickPlayerSubCommand extends PlayerSubCommand implements QuickCommand<QuickPlayerSubCommand> {

    private final QuickPlayerRun run;

    public QuickPlayerSubCommand(@NotNull String label, @NotNull QuickPlayerRun run) {
        super(label);
        this.run = run;
    }

    @Override
    public @NotNull QuickPlayerSubCommand child(SubCommand command) {
        addChild(command);
        return this;
    }

    @Override
    protected boolean runPlayer(@NotNull Player player, @NotNull String[] args) throws CommandExceptions {
        return run.apply(player, args);
    }

    @Override
    protected @Nullable List<String> runPlayerTab(@NotNull Player player, @NotNull String[] args) {
        return null;
    }

    public interface QuickPlayerRun {
        boolean apply(@NotNull Player player, @NotNull String[] args) throws CommandExceptions;
    }
}
