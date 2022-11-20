package io.github.toberocat.toberocore.command.exceptions;

import org.jetbrains.annotations.NotNull;

/**
 * Created: 02/11/2022
 *
 * @author Tobias Madlberger (Tobias)
 */
public class CommandExceptions extends Exception {
    private final String message;

    public CommandExceptions(@NotNull String message) {
        this.message = message;
    }

    public String getMessageId() {
        return message;
    }
}
