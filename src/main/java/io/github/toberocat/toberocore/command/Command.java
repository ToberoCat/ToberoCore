package io.github.toberocat.toberocore.command;

import io.github.toberocat.toberocore.command.exceptions.CommandHasParentException;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * Created: 02/11/2022
 *
 * @author Tobias Madlberger (Tobias)
 */
public abstract class Command<T extends Command<T>> {

    protected final Map<String, T> children = new HashMap<>();
    protected final List<String> tabComplete = new ArrayList<>();
    protected @Nullable Command<T> parent;
    protected @Nullable String permission;
    protected @Nullable String prefix;
    protected String label;

    public Command(@Nullable String permission, String label) {
        this.permission = permission;
        this.label = label;
    }

    //<editor-fold desc="Children Handling">
    public void addChild(@NotNull T command) {
        if (command.parent != null) throw new CommandHasParentException(command.label);

        command.parent = this;
        command.permission = permission + "." + command.label;
        command.prefix = prefix;
        tabComplete.add(command.label);
        children.put(command.label, command);
    }

    public @NotNull Map<String, T> getChildren() {
        return children;
    }

    //</editor-fold>

    //<editor-fold desc="Getters and Setters">

    public @Nullable Command<T> getParent() {
        return parent;
    }

    public @Nullable String getPermission() {
        return permission;
    }

    public @NotNull String getLabel() {
        return label;
    }


    //</editor-fold>
}
