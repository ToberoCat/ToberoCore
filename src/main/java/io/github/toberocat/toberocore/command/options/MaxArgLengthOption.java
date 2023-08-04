package io.github.toberocat.toberocore.command.options;

import io.github.toberocat.toberocore.command.exceptions.CommandException;
import io.github.toberocat.toberocore.util.placeholder.PlaceholderBuilder;
import org.bukkit.Bukkit;
import org.bukkit.command.CommandSender;
import org.bukkit.plugin.java.JavaPlugin;
import org.jetbrains.annotations.NotNull;

import java.util.HashSet;
import java.util.Set;

public class MaxArgLengthOption implements Option {
    private final @NotNull Set<String> antiSpam;
    private final @NotNull JavaPlugin plugin;
    private final int maxLengthOfArg;
    private final int index;
    private final int spamEntryLifetime;


    public MaxArgLengthOption(@NotNull JavaPlugin plugin, int index, int maxLengthOfArg, int spamEntryLifetime) {
        this.maxLengthOfArg = maxLengthOfArg;
        this.index = index;
        this.antiSpam = new HashSet<>();
        this.spamEntryLifetime = spamEntryLifetime;
        this.plugin = plugin;
    }

    @Override
    public @NotNull String[] execute(@NotNull CommandSender sender, @NotNull String[] args) throws CommandException {
        if (index >= args.length)
            return args;

        int length = args[index].length();
        if (length > maxLengthOfArg && !antiSpam.contains(sender.getName())) {
            addToSent(sender);
            throw new CommandException("base.exceptions.max-arg-length-exceeded", new PlaceholderBuilder<>()
                    .placeholder("max-characters", maxLengthOfArg)
                    .placeholder("position", index)
                    .placeholder("provided", args[index].length())
                    .getPlaceholders());
        }

        if (length <= maxLengthOfArg && antiSpam.contains(sender.getName())) {
            removeFromSent(sender);
            throw new CommandException("base.exceptions.max-arg-length-bounded", new PlaceholderBuilder<>()
                    .placeholder("max-characters", maxLengthOfArg)
                    .getPlaceholders());
        }

        return args;
    }

    private void addToSent(@NotNull CommandSender sender) {
        antiSpam.add(sender.getName());
        Bukkit.getScheduler().runTaskLater(plugin, () -> antiSpam.remove(sender.getName()), spamEntryLifetime);
    }

    private void removeFromSent(@NotNull CommandSender sender) {
        antiSpam.remove(sender.getName());
    }
}
