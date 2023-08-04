package io.github.toberocat.toberocore.currency;

import io.github.toberocat.toberocore.currency.provided.LocalCurrency;
import org.bukkit.plugin.java.JavaPlugin;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

import java.util.HashMap;
import java.util.Map;

public final class CurrencyCore {

    private static final Map<String, Currency> registeredCurrencies = new HashMap<>();

    public static void registerCurrency(@NotNull String id, @NotNull Currency currency) {
        if (registeredCurrencies.containsKey(id)) {
            throw new IllegalArgumentException("The currency ID '" + id + "' has already been registered");
        }

        registeredCurrencies.put(id, currency);
    }

    public static @Nullable Currency getCurrency(@NotNull String id) {
        return registeredCurrencies.get(id);
    }

    public static @NotNull Currency createCurrency(@NotNull JavaPlugin plugin, @NotNull String id) {
        Currency currency = new LocalCurrency(plugin, id);
        registerCurrency(id, currency);
        return currency;
    }
}
