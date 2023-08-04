package io.github.toberocat.toberocore.util;

import org.bukkit.configuration.ConfigurationSection;
import org.bukkit.plugin.java.JavaPlugin;
import org.jetbrains.annotations.NotNull;

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

    private final @NotNull JavaPlugin plugin;

    public Formatting(@NotNull JavaPlugin plugin) {
        this.plugin = plugin;
    }

    public @NotNull String integer(double arg) {
        return INTEGER_FORMAT.format(arg);
    }

    public @NotNull String integer(@NotNull BigDecimal arg) {
        return INTEGER_FORMAT.format(arg);
    }

    public @NotNull String decimal(double arg) {
        return DECIMAL_FORMAT.format(arg);
    }

    public @NotNull String decimal(@NotNull BigDecimal arg) {
        return DECIMAL_FORMAT.format(arg);
    }

    public @NotNull String decimalPotential(double arg) {
        return DECIMAL_POTENTIAL_FORMAT.format(arg);
    }

    public @NotNull String decimalPotential(@NotNull BigDecimal arg) {
        return DECIMAL_POTENTIAL_FORMAT.format(arg);
    }

    public @NotNull List<Entry<String, BigDecimal>> numberSymbols() {
        ConfigurationSection section = plugin.getConfig().getConfigurationSection("number-symbols");
        if (section == null) throw new RuntimeException("Couldnt get the number symbols from config file");

        List<Entry<String, BigDecimal>> decs = new ArrayList<>();
        for (String key : section.getKeys(false)) {
            BigDecimal dec = new BigDecimal(Objects.requireNonNull(section.getString(key, "0")));
            decs.add(Map.entry(key, dec));
        }

        decs.sort(Entry.comparingByValue());
        return decs;
    }

    public @NotNull String shorten(@NotNull BigDecimal arg) {
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

    public @NotNull String shorten(double arg) {
        return shorten(new BigDecimal(arg));
    }
}