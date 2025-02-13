package com.brogabe.sweetbosses.Configuration;

import com.brogabe.sweetbosses.SweetBosses;
import org.bukkit.configuration.file.FileConfiguration;
import org.bukkit.configuration.file.YamlConfiguration;

import java.io.File;
import java.io.IOException;
import java.nio.file.Files;

public class GingerBossConfig {

    private FileConfiguration config;
    private File file;

    private final SweetBosses plugin;

    public GingerBossConfig(SweetBosses plugin) {
        this.plugin = plugin;
        initializeConfig();
    }

    private void initializeConfig() {
        File folder = new File(plugin.getDataFolder(), "Bosses");
        if(!folder.exists()) {
            folder.mkdirs();
        }

        file = new File(folder, "GingerbreadMan.yml");

        if(file.exists()) {
            config = YamlConfiguration.loadConfiguration(file);
            return;
        }

        plugin.saveResource("GingerbreadMan.yml", false);

        try {
            Files.move(new File(plugin.getDataFolder(), "GingerbreadMan.yml").toPath(), new File(folder, "GingerbreadMan.yml").toPath());
        } catch (IOException e) {
            throw new RuntimeException(e);
        }

        config = YamlConfiguration.loadConfiguration(file);
    }

    public FileConfiguration getConfig() {
        return config;
    }

    public File getFile() {
        return file;
    }
}
