package io.github.toberocat.toberocore.command;

import io.github.toberocat.toberocore.util.StringUtils;
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
public abstract class SubCommand extends Command<SubCommand> {

    protected final String label;
    protected @Nullable String permission;

    public SubCommand(@NotNull String label) {
        super(null, label);
        this.label = label;
    }

    public boolean routeCall(@NotNull CommandSender sender,
                             @NotNull String[] args)
            throws CommandExceptions {
        if (permission != null && !sender.hasPermission(permission))
            throw new CommandExceptions("You don't have enough permissions to run this command");
        if (args.length == 0) return run(sender, args);

        String[] newArgs = new String[args.length - 1];
        System.arraycopy(args, 1, newArgs, 0, newArgs.length);

        if (newArgs.length >= 1 && children.containsKey(newArgs[0]))
            return children.get(newArgs[0]).routeCall(sender, newArgs);
        return run(sender, newArgs);
    }

    public @Nullable List<String> routeTab(@NotNull CommandSender sender,
                                           @NotNull String[] args) {
        if (permission != null && !sender.hasPermission(permission))
            return null;
        if (args.length == 0) return runTab(sender, args);

        String[] newArgs = new String[args.length - 1];
        System.arraycopy(args, 1, newArgs, 0, newArgs.length);

        if (newArgs.length >= 1 && children.containsKey(newArgs[0]))
            return children.get(newArgs[0]).routeTab(sender, newArgs);
        return runTab(sender, newArgs);
    }

    protected void sendMessage(@NotNull CommandSender sender,
                               @NotNull String message,
                               Object... placeholders) {
        sender.sendMessage(prefix + String.format(StringUtils.format(message), placeholders));
    }

    protected abstract boolean run(@NotNull CommandSender sender,
                                   @NotNull String[] args)
            throws CommandExceptions;

    protected abstract @Nullable List<String> runTab(@NotNull CommandSender sender,
                                                     @NotNull String[] args);
}
