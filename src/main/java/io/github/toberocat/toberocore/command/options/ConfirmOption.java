package io.github.toberocat.toberocore.command.options;

import io.github.toberocat.toberocore.command.exceptions.CommandException;
import org.bukkit.command.CommandSender;
import org.jetbrains.annotations.NotNull;

import java.util.HashMap;

public class ConfirmOption implements Option {
    @Override
    public @NotNull String[] execute(@NotNull CommandSender sender, @NotNull String[] args) throws CommandException {
        int lastArg = args.length - 1;
        if (lastArg < 0 || !args[lastArg].equals("confirm"))
            throw new CommandException("base.exceptions.confirmation-needed", new HashMap<>());
        return args;
    }
}
