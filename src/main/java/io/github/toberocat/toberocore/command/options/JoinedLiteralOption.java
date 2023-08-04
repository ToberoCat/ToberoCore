package io.github.toberocat.toberocore.command.options;

import io.github.toberocat.toberocore.command.exceptions.CommandException;
import org.bukkit.command.CommandSender;
import org.jetbrains.annotations.NotNull;

public class JoinedLiteralOption implements Option {
    private final int maxLiteralLength;

    public JoinedLiteralOption() {
        this(-1);
    }

    public JoinedLiteralOption(int maxLiteralLength) {
        this.maxLiteralLength = maxLiteralLength;
    }

    @Override
    public @NotNull String[] execute(@NotNull CommandSender sender, @NotNull String[] args) throws CommandException {
        int maxJoinLength = maxLiteralLength > 0 ? Math.min(args.length, maxLiteralLength) : args.length;

        StringBuilder builder = new StringBuilder();
        for (int i = 0; i < maxJoinLength; i++)
            builder.append(args[i]).append(' ');
        String[] newArr = new String[args.length - maxJoinLength + 1];
        newArr[0] = builder.toString().trim();
        System.arraycopy(args, maxJoinLength, newArr, 1, newArr.length - 1);
        return newArr;
    }
}
