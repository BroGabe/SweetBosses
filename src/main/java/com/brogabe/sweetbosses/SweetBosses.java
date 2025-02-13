package com.brogabe.sweetbosses;
import com.brogabe.sweetbosses.Abilities.Listeners.ArrowListener;
import com.brogabe.sweetbosses.Abilities.Listeners.FireballListener;
import com.brogabe.sweetbosses.Commands.BossCommands;
import com.brogabe.sweetbosses.Configuration.GingerBossConfig;
import com.brogabe.sweetbosses.Configuration.ItemsConfig;
import com.brogabe.sweetbosses.Listeners.BossDamageEvents;
import com.brogabe.sweetbosses.Listeners.EggPlaceEvents;
import com.brogabe.sweetbosses.Listeners.SpawnDesignEvents;
import com.brogabe.sweetbosses.Mob.MobBehavior;
import com.brogabe.sweetbosses.Spawning.SpawnBase;
import org.bukkit.Bukkit;
import org.bukkit.event.Listener;
import org.bukkit.plugin.java.JavaPlugin;

import java.util.*;

public final class SweetBosses extends JavaPlugin implements Listener {

    private static SweetBosses inst;

    public ItemsConfig itemsConfig;
    public GingerBossConfig gingerBossConfig;

    public Map<UUID, MobBehavior> bossMap = new HashMap<>();
    public List<SpawnBase> spawnBaseList = new ArrayList<>();


    @Override
    public void onEnable() {
        // Plugin startup logic
        saveDefaultConfig();
        itemsConfig = new ItemsConfig(this);
        gingerBossConfig = new GingerBossConfig(this);

        Bukkit.getPluginManager().registerEvents(new EggPlaceEvents(this), this);
        Bukkit.getPluginManager().registerEvents(new BossDamageEvents(this), this);
        Bukkit.getPluginManager().registerEvents(new FireballListener(), this);
        Bukkit.getPluginManager().registerEvents(new ArrowListener(), this);
        Bukkit.getPluginManager().registerEvents(new SpawnDesignEvents(this), this);

        getCommand("sweetbosses").setExecutor(new BossCommands(this));

        inst = this;
    }

    @Override
    public void onDisable() {
        // Plugin shutdown logic

        for (MobBehavior mobBehavior : bossMap.values()) {
            mobBehavior.clearMob();
        }

        for (SpawnBase spawnBase : spawnBaseList) {
            spawnBase.removeSpawn();
        }
    }

    public static SweetBosses getInst() {return inst;}
}
