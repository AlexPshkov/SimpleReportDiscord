package ru.alexpshkov.reportplugin.configs;

import com.sun.istack.internal.NotNull;
import org.bukkit.configuration.file.FileConfiguration;
import org.bukkit.configuration.file.YamlConfiguration;
import ru.alexpshkov.reportplugin.ReportPlugin;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;

public abstract class AbstractConfiguration {
    private final ReportPlugin reportplugin;
    private FileConfiguration fileConfiguration;
    private final File absolutFile;
    private final String relativeFilePath;

    /**
     * Construct
     * @param reportplugin Plugin
     * @param relativeFilePath Config filepath
     */
    public AbstractConfiguration(@NotNull ReportPlugin reportplugin, @NotNull String relativeFilePath) {
        this.reportplugin = reportplugin;
        this.relativeFilePath = relativeFilePath;
        this.absolutFile = new File(reportplugin.getDataFolder() + File.separator + relativeFilePath);
    }

    /**
     * Init configuration
     * @throws IOException
     */
    public void init() throws IOException {
        if (absolutFile.exists()) return;
        if (!reportplugin.getDataFolder().exists()) reportplugin.getDataFolder().mkdirs();
        try (InputStream inputStream = getClass().getClassLoader().getResourceAsStream(relativeFilePath)) {
            if (inputStream == null) return;
            try (FileOutputStream out = new FileOutputStream(absolutFile)) {
                byte[] buffer = new byte[1024];
                int bytesRead;
                while ((bytesRead = inputStream.read(buffer)) != -1) {
                    out.write(buffer, 0, bytesRead);
                }
                out.flush();
            }
        }
    }

    /**
     * Load YAML configuration
     * @return
     */
    public FileConfiguration loadConfiguration() {
        fileConfiguration = YamlConfiguration.loadConfiguration(absolutFile);
        return fileConfiguration;
    }

    /**
     * Get YAML configuration
     * @return
     */
    public FileConfiguration getFileConfiguration() {
        return fileConfiguration;
    }
}
