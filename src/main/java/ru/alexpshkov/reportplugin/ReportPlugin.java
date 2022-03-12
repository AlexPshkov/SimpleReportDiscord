package ru.alexpshkov.reportplugin;

import com.sun.istack.internal.Nullable;
import org.bukkit.plugin.java.JavaPlugin;
import ru.alexpshkov.reportplugin.commands.ReportCommand;
import ru.alexpshkov.reportplugin.commands.ReportPluginCommand;
import ru.alexpshkov.reportplugin.configs.MainConfiguration;
import ru.alexpshkov.reportplugin.discord.DiscordWebHook;

import java.io.IOException;
import java.util.Optional;

/**
 * @author AlexPshkov
 */

public final class ReportPlugin extends JavaPlugin {
    @Nullable private DiscordWebHook discordWebHook;
    @Nullable private MainConfiguration mainConfiguration;

    @Override
    public void onEnable() {
        initConfigs();
        initDiscordWebHook();
        registerCommands();
        this.getLogger().info("Successfully enabled!");
    }

    @Override
    public void onDisable() {
        this.getLogger().info("Disabled");
    }

    /**
     * Get discordWebHook
     * @return
     */
    @Nullable
    public DiscordWebHook getDiscordWebHook() {
        return discordWebHook;
    }

    /**
     * Register commands
     */
    private void registerCommands() {
        getServer().getPluginCommand("report").setExecutor(new ReportCommand(this));
        getServer().getPluginCommand("reportplugin").setExecutor(new ReportPluginCommand(this));
    }

    /**
     * Hande plugin reload
     */
    public void handleReload() {
        this.getLogger().info("Reloading...");
        mainConfiguration.loadConfiguration();
    }

    /**
     * Init configs
     * @return
     */
    private boolean initConfigs() {
        mainConfiguration = new MainConfiguration(this);
        try {
            mainConfiguration.init();
            mainConfiguration.loadConfiguration();
        } catch (IOException e) {
            e.printStackTrace();
            return false;
        }
        return true;
    }

    /**
     * Init discord webhook
     * @return
     */
    private boolean initDiscordWebHook() {
        Optional<String> webHook = mainConfiguration.getDiscordGuildWebHook();
        if(!webHook.isPresent()) return false;
        discordWebHook = new DiscordWebHook(webHook.get());
        return true;
    }
}
