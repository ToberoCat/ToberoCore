package io.github.toberocat.toberocore.currency.provided;

import io.github.toberocat.toberocore.currency.Currency;
import io.github.toberocat.toberocore.task.Task;
import net.milkbowl.vault.economy.Economy;
import org.bukkit.OfflinePlayer;
import org.jetbrains.annotations.NotNull;

import java.math.BigDecimal;

public class VaultCurrency implements Currency {

    private final Economy economy;

    public VaultCurrency(@NotNull Economy economy) {
        this.economy = economy;
    }

    @Override
    public void deposit(@NotNull OfflinePlayer offlinePlayer, @NotNull BigDecimal amount) {
        deposit(offlinePlayer, amount.doubleValue());
    }

    @Override
    public void deposit(@NotNull OfflinePlayer offlinePlayer, double amount) {
        economy.depositPlayer(offlinePlayer, amount);
    }

    @Override
    public void withdraw(@NotNull OfflinePlayer offlinePlayer, @NotNull BigDecimal amount) {
        withdraw(offlinePlayer, amount.doubleValue());
    }

    @Override
    public void withdraw(@NotNull OfflinePlayer offlinePlayer, double amount) {
        economy.withdrawPlayer(offlinePlayer, amount);
    }

    @Override
    public @NotNull BigDecimal get(@NotNull OfflinePlayer offlinePlayer) {
        return BigDecimal.valueOf(economy.getBalance(offlinePlayer));
    }

    @Override
    public @NotNull Task<BigDecimal> getAsync(@NotNull OfflinePlayer offlinePlayer) {
        return Task.returnItem(BigDecimal.valueOf(economy.getBalance(offlinePlayer)));
    }

    @Override
    public void set(@NotNull OfflinePlayer offlinePlayer, @NotNull BigDecimal amount) {
        set(offlinePlayer, amount.doubleValue());
    }

    @Override
    public void set(@NotNull OfflinePlayer offlinePlayer, double amount) {
        double current = economy.getBalance(offlinePlayer);
        double difference = amount - current;

        if (difference > 0) {
            economy.depositPlayer(offlinePlayer, difference);
        } else if (difference < 0) {
            economy.withdrawPlayer(offlinePlayer, -difference);
        }
    }

    @Override
    public boolean has(@NotNull OfflinePlayer offlinePlayer, @NotNull BigDecimal amount) {
        return has(offlinePlayer, amount.doubleValue());
    }

    @Override
    public boolean has(@NotNull OfflinePlayer offlinePlayer, double amount) {
        return economy.has(offlinePlayer, amount);
    }
}
