package io.github.toberocat.toberocore.util;

import org.jetbrains.annotations.NotNull;

import java.security.PrivilegedActionException;
import java.util.Map;

/**
 * Created: 04.08.2023
 *
 * @author Tobias Madlberger (Tobias)
 */
public class PlaceholderException extends Exception {
    private final Map<String, String> placeholders;


    /**
     * Constructs a new exception with {@code null} as its detail message.
     * The cause is not initialized, and may subsequently be initialized by a
     * call to {@link #initCause}.
     */
    public PlaceholderException(@NotNull Map<String, String> placeholders) {
        this.placeholders = placeholders;
    }

    /**
     * Constructs a new exception with the specified detail message.  The
     * cause is not initialized, and may subsequently be initialized by
     * a call to {@link #initCause}.
     *
     * @param message the detail message. The detail message is saved for
     *                later retrieval by the {@link #getMessage()} method.
     */
    public PlaceholderException(String message, @NotNull Map<String, String> placeholders) {
        super(message);
        this.placeholders = placeholders;
    }

    /**
     * Constructs a new exception with the specified detail message and
     * cause.  <p>Note that the detail message associated with
     * {@code cause} is <i>not</i> automatically incorporated in
     * this exception's detail message.
     *
     * @param message the detail message (which is saved for later retrieval
     *                by the {@link #getMessage()} method).
     * @param cause   the cause (which is saved for later retrieval by the
     *                {@link #getCause()} method).  (A {@code null} value is
     *                permitted, and indicates that the cause is nonexistent or
     *                unknown.)
     * @since 1.4
     */
    public PlaceholderException(String message, Throwable cause, @NotNull Map<String, String> placeholders) {
        super(message, cause);
        this.placeholders = placeholders;
    }

    /**
     * Constructs a new exception with the specified cause and a detail
     * message of {@code (cause==null ? null : cause.toString())} (which
     * typically contains the class and detail message of {@code cause}).
     * This constructor is useful for exceptions that are little more than
     * wrappers for other throwables (for example, {@link
     * PrivilegedActionException}).
     *
     * @param cause the cause (which is saved for later retrieval by the
     *              {@link #getCause()} method).  (A {@code null} value is
     *              permitted, and indicates that the cause is nonexistent or
     *              unknown.)
     * @since 1.4
     */
    public PlaceholderException(Throwable cause, @NotNull Map<String, String> placeholders) {
        super(cause);
        this.placeholders = placeholders;
    }

    /**
     * Constructs a new exception with the specified detail message,
     * cause, suppression enabled or disabled, and writable stack
     * trace enabled or disabled.
     *
     * @param message            the detail message.
     * @param cause              the cause.  (A {@code null} value is permitted,
     *                           and indicates that the cause is nonexistent or unknown.)
     * @param enableSuppression  whether or not suppression is enabled
     *                           or disabled
     * @param writableStackTrace whether or not the stack trace should
     *                           be writable
     * @since 1.7
     */
    public PlaceholderException(String message, Throwable cause,
                                boolean enableSuppression,
                                boolean writableStackTrace,
                                @NotNull Map<String, String> placeholders) {
        super(message, cause, enableSuppression, writableStackTrace);
        this.placeholders = placeholders;
    }

    public Map<String, String> getPlaceholders() {
        return placeholders;
    }
}
