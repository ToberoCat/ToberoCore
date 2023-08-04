package io.github.toberocat.toberocore.command.options;

import io.github.toberocat.toberocore.command.exceptions.CommandException;
import io.github.toberocat.toberocore.util.placeholder.PlaceholderBuilder;
import org.bukkit.Bukkit;
import org.bukkit.command.CommandSender;
import org.bukkit.plugin.java.JavaPlugin;
import org.jetbrains.annotations.NotNull;

import java.util.HashSet;
import java.util.Set;

public class ArgLengthOption implements Option {
    private final @NotNull Set<String> antiSpam;
    private final @NotNull JavaPlugin plugin;
    private final int length;
    private final int spamEntryLifetime;

    public ArgLengthOption(@NotNull JavaPlugin plugin, int length, int spamEntryLifetime) {
        this.length = length;
        this.plugin = plugin;
        this.antiSpam = new HashSet<>();
        this.spamEntryLifetime = spamEntryLifetime;
    }

    @Override
    public @NotNull String[] execute(@NotNull CommandSender sender, @NotNull String[] args)
            throws CommandException {
        if (args.length != length && !antiSpam.contains(sender.getName())) {
            addToSent(sender);
            throw new CommandException("base.exceptions.not-enough-arguments", new PlaceholderBuilder<>()
                    .placeholder("provided", args.length)
                    .placeholder("required", length)
                    .getPlaceholders());
        }
        return args;
    }

    private void addToSent(@NotNull CommandSender sender) {
        antiSpam.add(sender.getName());
        Bukkit.getScheduler().runTaskLater(plugin, () -> antiSpam.remove(sender.getName()),
                spamEntryLifetime);
    }
}
