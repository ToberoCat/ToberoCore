package io.github.toberocat.toberocore.currency;

import io.github.toberocat.toberocore.task.Task;
import org.bukkit.OfflinePlayer;
import org.jetbrains.annotations.NotNull;

import java.math.BigDecimal;

public interface Currency {

    default void deposit(@NotNull OfflinePlayer offlinePlayer, @NotNull BigDecimal amount) {
        set(offlinePlayer, get(offlinePlayer).add(amount));
    }

    default void deposit(@NotNull OfflinePlayer offlinePlayer, double amount) {
        deposit(offlinePlayer, BigDecimal.valueOf(amount));
    }

    default void withdraw(@NotNull OfflinePlayer offlinePlayer, @NotNull BigDecimal amount) {
        set(offlinePlayer, get(offlinePlayer).subtract(amount));
    }

    default void withdraw(@NotNull OfflinePlayer offlinePlayer, double amount) {
        withdraw(offlinePlayer, BigDecimal.valueOf(amount));
    }

    /**
     * Get the balance of the account with the given id
     * The id can be bound to a player or something else
     * <p>
     * If no bank was found with this id, a new one will be created
     *
     * @param offlinePlayer The UUID of the bank to get the balance of
     * @return The value associated to this account
     */
    @NotNull BigDecimal get(@NotNull OfflinePlayer offlinePlayer);

    @NotNull Task<BigDecimal> getAsync(@NotNull OfflinePlayer offlinePlayer);

    /**
     * Sets the bank's balance to the specified amount
     * The id can be bound to a player or something else
     * <p>
     * If no bank was found with this id, a new one will be created
     *
     * @param offlinePlayer The player to set the balance of
     * @param amount        The new value
     */
    void set(@NotNull OfflinePlayer offlinePlayer, @NotNull BigDecimal amount);


    default void set(@NotNull OfflinePlayer offlinePlayer, double amount) {
        set(offlinePlayer, BigDecimal.valueOf(amount));
    }

    default boolean has(@NotNull OfflinePlayer offlinePlayer, @NotNull BigDecimal amount) {
        return (get(offlinePlayer).compareTo(amount) >= 0);
    }

    default boolean has(@NotNull OfflinePlayer offlinePlayer, double amount) {
        return has(offlinePlayer, BigDecimal.valueOf(amount));
    }
}
