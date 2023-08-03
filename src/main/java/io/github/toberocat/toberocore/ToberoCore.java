package io.github.toberocat.toberocore;

import io.github.toberocat.toberocore.bossbar.SimpleBar;
import io.github.toberocat.toberocore.currency.CurrencyCore;
import io.github.toberocat.toberocore.individual.AbstractIndividuals;
import io.github.toberocat.toberocore.task.Task;
import io.github.toberocat.toberocore.util.Formatting;
import org.bukkit.plugin.java.JavaPlugin;

public final class ToberoCore extends JavaPlugin {


    @Override
    public void onEnable() {
        saveDefaultConfig();
        Formatting.initialize(this);
    }

    @Override
    public void onDisable() {
        getServer().getScheduler().cancelTasks(this);

        AbstractIndividuals.cleanup();
        CurrencyCore.dispose();
        SimpleBar.cleanup();
        Task.dispose(getLogger()); // Make sure all threads close safely
    }
}
