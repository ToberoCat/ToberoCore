package io.github.toberocat.toberocore.action;

import io.github.toberocat.toberocore.util.StringUtils;
import org.bukkit.command.CommandSender;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

import java.util.*;

public class Actions {

    private final String[] strings;
    private final Map<String, String> placeholders = new HashMap<>();
    private final Set<Action> localActions = new HashSet<>();

    public Actions(@NotNull String[] strings) {
        this.strings = strings;
    }

    public Actions(@NotNull List<String> strings) {
        this.strings = strings.toArray(String[]::new);
    }

    public Actions(@NotNull String string) {
        this.strings = new String[]{string};
    }

    public Actions() {
        this(new ArrayList<>());
    }

    public @NotNull Actions placeholder(@Nullable String what, @Nullable String with) {
        placeholders.put(what, with);
        return this;
    }

    public @NotNull Actions placeholders(@NotNull Map<String, String> placeholders) {
        this.placeholders.putAll(placeholders);
        return this;
    }

    public @NotNull Actions localActions(@NotNull Set<Action> actions) {
        localActions.addAll(actions);
        return this;
    }

    /**
     * @param commandSender the sender executing
     * @return true if all actions succeeded with their execution
     */
    public boolean run(@NotNull CommandSender commandSender) {
        boolean success = true;

        for (String string : strings) {
            string = StringUtils.replace(string, placeholders);
            success = success && ActionCore.run(string, commandSender, localActions);
        }

        return success;
    }
}
