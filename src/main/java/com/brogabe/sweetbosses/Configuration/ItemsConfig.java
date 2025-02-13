package com.brogabe.sweetbosses.Configuration;

import com.brogabe.sweetbosses.SweetBosses;
import org.bukkit.configuration.file.FileConfiguration;
import org.bukkit.configuration.file.YamlConfiguration;

import java.io.File;
import java.io.IOException;
import java.nio.file.Files;

public class ItemsConfig {

    private FileConfiguration config;
    private File file;

    private final SweetBosses plugin;

    public ItemsConfig(SweetBosses plugin) {
        this.plugin = plugin;
        initializeConfig();
    }

    private void initializeConfig() {
        File folder = new File(plugin.getDataFolder(), "CustomItems");
        if(!folder.exists()) {
            folder.mkdirs();
        }

        file = new File(folder, "customitems.yml");

        if(file.exists()) {
            config = YamlConfiguration.loadConfiguration(file);
            return;
        }

        plugin.saveResource("customitems.yml", false);

        try {
            Files.move(new File(plugin.getDataFolder(), "customitems.yml").toPath(), new File(folder, "customitems.yml").toPath());
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