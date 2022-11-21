package io.github.toberocat.toberocore;

import io.github.toberocat.toberocore.action.provided.*;
import io.github.toberocat.toberocore.individual.AbstractIndividuals;
import io.github.toberocat.toberocore.item.property.*;
import io.github.toberocat.toberocore.listener.GuiListener;
import io.github.toberocat.toberocore.bossbar.SimpleBar;
import io.github.toberocat.toberocore.currency.CurrencyCore;
import io.github.toberocat.toberocore.item.ItemCore;
import io.github.toberocat.toberocore.item.placer.BorderPlacer;
import io.github.toberocat.toberocore.item.placer.FillPlacer;
import io.github.toberocat.toberocore.item.placer.SlotPlacer;
import io.github.toberocat.toberocore.task.Task;
import io.github.toberocat.toberocore.util.Formatting;
import net.milkbowl.vault.economy.Economy;
import org.bukkit.Bukkit;
import org.bukkit.plugin.PluginManager;
import org.bukkit.plugin.RegisteredServiceProvider;
import org.bukkit.plugin.java.JavaPlugin;
import org.jetbrains.annotations.NotNull;

import java.util.Optional;

public final class ToberoCore extends JavaPlugin {

    private Economy economy;

    private boolean includesVault = false;
    private boolean includesPAPI = false;

    @Override
    public void onEnable() {
        checkDepends();

        saveDefaultConfig();

        CurrencyCore.initialize(this);
        Formatting.initialize(this);
        ItemCore.initialize(this);

        setupVaultEconomy();
        registerListeners();
        registerActions();
        registerItemComponents();
    }

    @Override
    public void onDisable() {
        getServer().getScheduler().cancelTasks(this);

        AbstractIndividuals.cleanup();
        CurrencyCore.dispose();
        SimpleBar.cleanup();
        Task.dispose(); // Make sure all threads close safely
    }

    private void checkDepends() {
        PluginManager pluginManager = Bukkit.getPluginManager();

        for (String dependency : getDescription().getSoftDepend()) {
            if (pluginManager.isPluginEnabled(dependency)) {
                switch (dependency) {
                    case "PlaceholderAPI" -> includesPAPI = true;
                    case "Vault" -> includesVault = true;
                }
            }
        }
    }

    private void setupVaultEconomy() {
        if (includesVault) {
            RegisteredServiceProvider<Economy> rsp = getServer()
                    .getServicesManager()
                    .getRegistration(Economy.class);

            if (rsp != null) {
                economy = rsp.getProvider();
            }
        }
    }

    private void registerListeners() {
        PluginManager pluginManager = getServer().getPluginManager();

        pluginManager.registerEvents(new GuiListener(), this);
    }

    private void registerActions() {
        new AddExpAction().register();
        new ActionbarAction().register();
        new BroadcastAction(this).register();
        new CloseAction().register();
        new ConsoleCommandAction().register();
        new MessageAction(this).register();
        new SoundAction().register();
        new PlayerCommandAction().register();
        new TitleAction(this).register();
    }

    private void registerItemComponents() {
        new AmountProperty().register();
        new CommandProperty().register();
        new EnchantmentsProperty().register();
        new FlagsProperty().register();
        new GlowProperty().register();
        new LeatherArmorColorProperty().register();
        new LoreProperty(this).register();
        new MaterialProperty().register();
        new NameProperty(this).register();
        new PlayerHeadProperty().register();

        new BorderPlacer().register();
        new FillPlacer().register();
        new SlotPlacer(this).register();
    }

    public static @NotNull ToberoCore getPlugin() {
        return getPlugin(ToberoCore.class);
    }

    public @NotNull Optional<Economy> getEconomy() {
        return Optional.ofNullable(economy);
    }

    public boolean placeholderAPI() {
        return includesPAPI;
    }
}
