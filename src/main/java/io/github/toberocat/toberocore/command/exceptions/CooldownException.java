package io.github.toberocat.toberocore.command.exceptions;

import io.github.toberocat.toberocore.util.CooldownManager;
import io.github.toberocat.toberocore.util.placeholder.PlaceholderBuilder;
import org.jetbrains.annotations.NotNull;

import java.util.UUID;

/**
 * Created: 08/02/2023
 *
 * @author Tobias Madlberger (Tobias)
 */
public class CooldownException extends CommandException {
    public CooldownException(@NotNull CooldownManager manager, @NotNull UUID target) {
        super("base.exceptions.cooldown", new PlaceholderBuilder<>()
                .placeholder("left", manager.getLeftTime(target))
                .getPlaceholders());
    }
}
