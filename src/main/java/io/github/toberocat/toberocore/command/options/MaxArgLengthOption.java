package io.github.toberocat.toberocore.command.options;

import io.github.toberocat.toberocore.command.exceptions.CommandException;
import io.github.toberocat.toberocore.util.placeholder.PlaceholderBuilder;
import org.bukkit.Bukkit;
import org.bukkit.command.CommandSender;
import org.bukkit.plugin.java.JavaPlugin;
import org.jetbrains.annotations.NotNull;

import java.util.HashSet;
import java.util.Set;
import java.util.UUID;

public class MaxArgLengthOption implements Option {
    private final int maxLengthOfArg;
    private final int index;


    public MaxArgLengthOption(int index, int maxLengthOfArg) {
        this.maxLengthOfArg = maxLengthOfArg;
        this.index = index;
    }

    @Override
    public @NotNull String[] execute(@NotNull CommandSender sender, @NotNull String[] args) throws CommandException {
        if (index >= args.length)
            return args;

        int length = args[index].length();
        if (length > maxLengthOfArg) {
            throw new CommandException("base.exceptions.max-arg-length-exceeded", new PlaceholderBuilder<>()
                    .placeholder("max-characters", maxLengthOfArg)
                    .placeholder("position", index)
                    .placeholder("provided", args[index].length())
                    .getPlaceholders());
        }

        return args;
    }
}
