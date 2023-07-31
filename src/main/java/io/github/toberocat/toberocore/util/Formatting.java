package io.github.toberocat.toberocore.util;

import io.github.toberocat.toberocore.ToberoCore;
import org.bukkit.configuration.ConfigurationSection;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

import java.math.BigDecimal;
import java.math.MathContext;
import java.math.RoundingMode;
import java.text.DecimalFormat;
import java.text.DecimalFormatSymbols;
import java.util.*;
import java.util.Map.Entry;

@SuppressWarnings("unused")
public final class Formatting {

    private static final DecimalFormatSymbols SYMBOLS = new DecimalFormatSymbols(Locale.US);
    private static final DecimalFormat DECIMAL_FORMAT = new DecimalFormat("#,##0.00", SYMBOLS);
    private static final DecimalFormat DECIMAL_POTENTIAL_FORMAT = new DecimalFormat("#,##0.##", SYMBOLS);
    private static final DecimalFormat INTEGER_FORMAT = new DecimalFormat("#,##0", SYMBOLS);
    private static @Nullable ToberoCore jobSystem;

    public static void initialize(@NotNull ToberoCore jobSystem) {
        if (Formatting.jobSystem == null) {
            Formatting.jobSystem = jobSystem;
        } else {
            throw new IllegalStateException(getName() + " has already been initialized");
        }
    }

    private static @NotNull ToberoCore getPlugin() {
        if (jobSystem == null) throw new IllegalStateException(getName() + " has not been initialized yet");

        return jobSystem;
    }

    private static @NotNull String getName() {
        return Formatting.class.getSimpleName();
    }

    /* Number */

    public static @NotNull String integer(double arg) {
        return INTEGER_FORMAT.format(arg);
    }

    public static @NotNull String integer(@NotNull BigDecimal arg) {
        return INTEGER_FORMAT.format(arg);
    }

    public static @NotNull String decimal(double arg) {
        return DECIMAL_FORMAT.format(arg);
    }

    public static @NotNull String decimal(@NotNull BigDecimal arg) {
        return DECIMAL_FORMAT.format(arg);
    }

    public static @NotNull String decimalPotential(double arg) {
        return DECIMAL_POTENTIAL_FORMAT.format(arg);
    }

    public static @NotNull String decimalPotential(@NotNull BigDecimal arg) {
        return DECIMAL_POTENTIAL_FORMAT.format(arg);
    }

    public static @NotNull List<Entry<String, BigDecimal>> numberSymbols() {
        ConfigurationSection section =
                getPlugin().getConfig().getConfigurationSection("number-symbols");
        if (section == null)
            throw new RuntimeException("Couldnt get the number symbols from config file");

        List<Entry<String, BigDecimal>> decs = new ArrayList<>();
        for (String key : section.getKeys(false)) {
            BigDecimal dec = new BigDecimal(Objects.requireNonNull(section.getString(key, "0")));
            decs.add(Map.entry(key, dec));
        }

        decs.sort(Entry.comparingByValue());
        return decs;
    }

    public static @NotNull String shorten(@NotNull BigDecimal arg) {
        Entry<String, BigDecimal> checkpoint = null;

        for (Entry<String, BigDecimal> entry : numberSymbols()) {
            BigDecimal number = entry.getValue();

            if (arg.compareTo(number) >= 0) {
                checkpoint = entry;
            }
        }

        if (checkpoint == null) {
            return integer(arg);
        }

        BigDecimal multiplierValue = checkpoint.getValue();
        MathContext context = new MathContext(5, RoundingMode.FLOOR);
        BigDecimal divide = arg.divide(multiplierValue, context);

        return decimalPotential(divide) + checkpoint.getKey();
    }

    public static @NotNull String shorten(double arg) {
        return shorten(new BigDecimal(arg));
    }
}