package io.github.toberocat.toberocore.command.exceptions;

import io.github.toberocat.toberocore.util.PlaceholderException;
import org.jetbrains.annotations.NotNull;

import java.util.Map;


public class CommandException extends PlaceholderException {

    /**
     * Constructs a new exception with the specified detail message.  The
     * cause is not initialized, and may subsequently be initialized by
     * a call to {@link #initCause}.
     *
     * @param message      the detail message. The detail message is saved for
     *                     later retrieval by the {@link #getMessage()} method.
     * @param placeholders
     */
    public CommandException(String message, @NotNull Map<String, String> placeholders) {
        super(message, placeholders);
    }
}
