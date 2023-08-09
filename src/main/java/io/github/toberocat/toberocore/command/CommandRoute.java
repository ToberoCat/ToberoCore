package io.github.toberocat.toberocore.command;

import io.github.toberocat.toberocore.command.arguments.Argument;
import io.github.toberocat.toberocore.command.options.Options;
import org.bukkit.plugin.java.JavaPlugin;
import org.jetbrains.annotations.NotNull;

public abstract class CommandRoute extends PlayerSubCommand {
    private final JavaPlugin plugin;

    public CommandRoute(@NotNull String label,
                        @NotNull JavaPlugin plugin) {
        super(label);
        this.plugin = plugin;
    }

    @Override
    protected @NotNull Options options() {
        return Options.getFromConfig(plugin, label);
    }

    @Override
    protected @NotNull Argument<?>[] arguments() {
        return new Argument[0];
    }
}
