package io.github.toberocat.toberocore.action;

import io.github.toberocat.toberocore.util.StreamUtils;
import org.bukkit.Bukkit;
import org.bukkit.command.CommandSender;
import org.bukkit.command.ConsoleCommandSender;
import org.bukkit.entity.Player;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

import java.util.Arrays;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

public final class ActionCore {

    private static final Set<Action> actions = new HashSet<>();

    public static void register(@NotNull Action action) {
        actions.add(action);
    }

    private static @Nullable Action getAction(@NotNull String formattedLabel, @NotNull Set<Action> localActions) {
        Set<Action> actions = new HashSet<>(localActions);
        actions.addAll(ActionCore.actions);

        Action action = StreamUtils.find(actions, x -> formatLabel(x.label()).equals(formattedLabel));
        return action;
    }

    /**
     * @param string        the action to run
     * @param commandSender the sender executing
     * @return if the action was found or not
     */
    public static boolean run(@NotNull String string,
                              @NotNull CommandSender commandSender,
                              @NotNull Set<Action> localActions) {
        /* Args */

        String[] argsWithLabel = string.split("\\s+");
        if (argsWithLabel.length == 0) return false;

        String label = argsWithLabel[0];
        String[] args = new String[argsWithLabel.length - 1];

        System.arraycopy(argsWithLabel, 1, args, 0, argsWithLabel.length - 1);

        /* Provided */

        String provided = string.substring(label.length()).stripLeading();

        /* Action */

        Action action = getAction(label, localActions);
        if (action == null) return false;

        /* Run */

        action.run(commandSender);
        action.run(commandSender, args);
        action.run(commandSender, provided);

        if (commandSender instanceof ConsoleCommandSender consoleCommandSender) {
            action.run(consoleCommandSender);
            action.run(consoleCommandSender, args);
            action.run(consoleCommandSender, provided);
        } else if (commandSender instanceof Player player) {
            action.run(player);
            action.run(player, args);
            action.run(player, provided);
        }

        return true;
    }

    /**
     * @param strings       the list of actions to run
     * @param commandSender the sender executing
     * @return true if all actions succeeded with their execution
     */
    public static boolean run(@NotNull List<String> strings, @NotNull CommandSender commandSender) {
        boolean success = true;

        for (String string : strings) {
            success = success && run(string, commandSender, new HashSet<>());
        }

        return success;
    }

    private static @NotNull String formatLabel(@NotNull String string) {
        return '[' + string.toLowerCase().replace(' ', '-') + ']';
    }
}
