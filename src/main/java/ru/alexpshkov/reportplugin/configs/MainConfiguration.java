package ru.alexpshkov.reportplugin.configs;

import com.sun.istack.internal.NotNull;
import ru.alexpshkov.reportplugin.ReportPlugin;

import java.util.Optional;

public class MainConfiguration extends AbstractConfiguration {

    /**
     * Construct
     *
     * @param reportplugin Plugin
     */
    public MainConfiguration(@NotNull ReportPlugin reportplugin) {
        super(reportplugin, "config.yaml");
    }

    /**
     * Get discord webhook
     * @return webhook
     */
    public Optional<String> getDiscordGuildWebHook() {
        return Optional.ofNullable(super.getFileConfiguration().getString("discord.guild_webhook"));
    }
}
