package io.github.toberocat.toberocore.command.exceptions;

import org.jetbrains.annotations.NotNull;

/**
 * Created: 02/11/2022
 *
 * @author Tobias Madlberger (Tobias)
 */
public class CommandHasParentException extends RuntimeException {

    public CommandHasParentException(@NotNull String command) {
        super("Command " + command + " already has a parent. Can't have more than one parent");
    }
}
