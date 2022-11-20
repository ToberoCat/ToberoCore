package io.github.toberocat.toberocore.currency.provided;

import io.github.toberocat.toberocore.ToberoCore;
import io.github.toberocat.toberocore.currency.Currency;
import io.github.toberocat.toberocore.individual.player.PlayerDecimalIndividuals;
import io.github.toberocat.toberocore.task.Task;
import org.bukkit.OfflinePlayer;
import org.jetbrains.annotations.NotNull;

import java.io.File;
import java.math.BigDecimal;

/*
    A currency that saves to the plugin's data folder.
 */
public class LocalCurrency implements Currency {

    private final PlayerDecimalIndividuals playerDecimalIndividuals;

    public LocalCurrency(@NotNull String currencyId) {
        ToberoCore plugin = ToberoCore.getPlugin(ToberoCore.class);
        File directory = new File(plugin.getDataFolder(), "Currencies/" + currencyId);
        playerDecimalIndividuals = new PlayerDecimalIndividuals(directory, plugin);
    }

    @Override
    public @NotNull BigDecimal get(@NotNull OfflinePlayer player) {
        return playerDecimalIndividuals
                        .get(player.getUniqueId())
                        .await()
                        .orElse(BigDecimal.ZERO);
    }

    @Override
    public @NotNull Task<BigDecimal> getAsync(@NotNull OfflinePlayer player) {
        return playerDecimalIndividuals.get(player.getUniqueId());
    }

    @Override
    public void set(@NotNull OfflinePlayer player, @NotNull BigDecimal value) {
        playerDecimalIndividuals.set(player.getUniqueId(), value);
    }
}
