package io.github.toberocat.toberocore.individual;

import org.jetbrains.annotations.NotNull;

/**
 * Created: 03/11/2022
 *
 * @author Tobias Madlberger (Tobias)
 */
public class FileIsSavingRuntimeException extends RuntimeException {

    public FileIsSavingRuntimeException(@NotNull Object key) {
        super("The file for " + key + " is currently unloading, " +
                "that's why it can't be loaded directly after the unload call");
    }
}
