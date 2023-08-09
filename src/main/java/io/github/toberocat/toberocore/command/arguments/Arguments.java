package io.github.toberocat.toberocore.command.arguments;

import io.github.toberocat.toberocore.command.exceptions.CommandException;
import org.bukkit.entity.Player;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

import java.util.HashMap;

public class Arguments {
    private final @NotNull String[] args;
    private final @NotNull Player player;
    private final @NotNull Argument<?>[] argumentProcessors;

    public Arguments(@NotNull Player player,
                     @NotNull String[] args,
                     @NotNull Argument<?>[] argumentProcessors)
            throws CommandException {
        if (argumentProcessors.length == args.length)
            throw new CommandException("base.command.exceptions.insufficnet-argumnets-provided",
                    new HashMap<>());

        this.args = args;
        this.argumentProcessors = argumentProcessors;
        this.player = player;
    }

    public <R> @NotNull R get(int index) throws CommandException {
        return (R) argumentProcessors[index].parse(player, args[index]);
    }
    public <R> @Nullable R getOrNull(int index) throws CommandException {
        if (index >= argumentProcessors.length || index >= args.length)
            return null;
        return (R) argumentProcessors[index].parse(player, args[index]);
    }
}
