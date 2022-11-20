package io.github.toberocat.toberocore.command.subcommands;

import io.github.toberocat.toberocore.command.SubCommand;
import org.jetbrains.annotations.NotNull;

/**
 * Created: 02/11/2022
 *
 * @author Tobias Madlberger (Tobias)
 */
public interface QuickCommand<T extends QuickCommand<T>> {
    @NotNull T child(SubCommand command);
}
