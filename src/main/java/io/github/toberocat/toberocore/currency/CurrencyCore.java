package io.github.toberocat.toberocore.currency;

import io.github.toberocat.toberocore.ToberoCore;
import io.github.toberocat.toberocore.currency.provided.LocalCurrency;
import io.github.toberocat.toberocore.currency.provided.VaultCurrency;
import net.milkbowl.vault.economy.Economy;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

import java.util.HashMap;
import java.util.Map;
import java.util.Optional;

public final class CurrencyCore {

    private static final Map<String, Currency> registeredCurrencies = new HashMap<>();
    private static @Nullable Currency mobcoinsCurrency;
    private static Currency vaultCurrency;

    public static void initialize(@NotNull ToberoCore ToberoCore) {
        Optional<Economy> economy = ToberoCore.getEconomy();
        mobcoinsCurrency = createCurrency("mobcoins"); // TODO get from mobcoins plugin
        vaultCurrency = (economy.isEmpty()) ?
                createCurrency("vault") :
                new VaultCurrency(economy.get());
    }

    public static void registerCurrency(@NotNull String id, @NotNull Currency currency) {
        if (registeredCurrencies.containsKey(id)) {
            throw new IllegalArgumentException("The currency ID '" + id + "' has already been registered");
        }

        registeredCurrencies.put(id, currency);
    }

    public static @Nullable Currency getCurrency(@NotNull String id) {
        return registeredCurrencies.get(id);
    }

    public static @NotNull Currency createCurrency(@NotNull String id) {
        Currency currency = new LocalCurrency(id);
        registerCurrency(id, currency);
        return currency;
    }

    public static @Nullable Currency getMobcoinsCurrency() {
        return mobcoinsCurrency;
    }

    public static @NotNull Currency getVaultCurrency() {
        return vaultCurrency;
    }

    public static void dispose() {
        registeredCurrencies.values().forEach(Currency::dispose);
        registeredCurrencies.clear();
    }
}
