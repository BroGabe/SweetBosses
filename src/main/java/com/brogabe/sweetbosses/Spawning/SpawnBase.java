package com.brogabe.sweetbosses.Spawning;

import com.brogabe.sweetbosses.Holograms.HologramBase;
import com.brogabe.sweetbosses.Mob.MobBehavior;
import com.brogabe.sweetbosses.SweetBosses;
import com.brogabe.sweetbosses.Utils.ColorUtil;
import com.brogabe.sweetbosses.Utils.HealthBarUtil;
import org.bukkit.Location;
import org.bukkit.Material;
import org.bukkit.configuration.file.FileConfiguration;
import org.bukkit.configuration.file.YamlConfiguration;
import org.bukkit.entity.Entity;
import org.bukkit.entity.EntityType;
import org.bukkit.metadata.FixedMetadataValue;
import org.bukkit.scheduler.BukkitRunnable;
import org.bukkit.scheduler.BukkitTask;

import java.io.File;
import java.util.ArrayList;
import java.util.List;

public class SpawnBase {

    private final SweetBosses plugin = SweetBosses.getInst();
    private HologramBase hologramBase;

    private final Location location;
    private BukkitTask countdownTask;

    private final FileConfiguration config;

    private int time;

    private MobBehavior mobBehavior = null;

    private Entity crystalEntity = null;

    public SpawnBase(Location location, File file) {
        this.location = location;

        config = YamlConfiguration.loadConfiguration(file);

        time = config.getInt("settings.spawn-timer");
        handleSpawn();
    }

    private void handleSpawn() {
        if(config.getString("settings.spawner-design").equalsIgnoreCase("IMMEDIATE")) {
            mobBehavior = new MobBehavior(plugin, config, location);
            plugin.bossMap.put(mobBehavior.getUuid(), mobBehavior);
            return;
        }

        createSpawnHolder();
        startCountdown();
    }

    private void spawnMob() {
        try {
            mobBehavior = new MobBehavior(plugin, config, location.clone().add(0, 1, 0), this);
        } catch (Exception exception) {
            countdownTask.cancel();
            removeSpawn();
            exception.printStackTrace();
            return;
        }
        plugin.bossMap.put(mobBehavior.getUuid(), mobBehavior);
    }

    private void startCountdown() {
        countdownTask = new BukkitRunnable() {
            @Override
            public void run() {
                if(time <= 0) {
                    spawnMob();
                    countdownTask.cancel();
                }
                time--;
                updateHologram();
            }
        }.runTaskTimer(plugin, 20, 20);
    }

    public void removeSpawn() {
        if(crystalEntity != null) {
            crystalEntity.remove();
        }

        location.getBlock().setType(Material.AIR);
        getHologramBase().deleteHologram();
    }

    private void createSpawnHolder() {
        List<String> hologramLines = new ArrayList<>();
        config.getStringList("Hologram.pending-spawn").forEach(string
                -> hologramLines.add(ColorUtil.color(string
                .replace("%time%", String.valueOf(time))
                .replace("%bossprefix%", config.getString("boss-prefix")))));

        if (config.getString("settings.spawner-design").equalsIgnoreCase("END_CRYSTAL")) {
            crystalEntity = location.getWorld().spawnEntity(location.clone().add(0, 1, 0), EntityType.ENDER_CRYSTAL);
            crystalEntity.setMetadata("BossHolder", new FixedMetadataValue(plugin, "Empty"));

            hologramBase = new HologramBase(plugin, location, hologramLines, 4, false);
        } else {
            location.getBlock().setType(Material.CHEST);
            location.getBlock().setMetadata("BossHolder", new FixedMetadataValue(plugin, "Empty"));
            hologramBase = new HologramBase(plugin, location.getBlock().getLocation(), hologramLines, 2, true);
        }
    }

    public void updateHologram() {
        if(mobBehavior != null) {
            List<String> hologramLines = new ArrayList<>();
            config.getStringList("Hologram.spawned-design").forEach(s ->
                    hologramLines.add(ColorUtil.color(s
                            .replace("%bossprefix%", config.getString("boss-prefix"))
                            .replace("%healthbar%", HealthBarUtil.getHealthBar((int) mobBehavior.getLivingEntity().getMaxHealth(),
                                    (int) mobBehavior.getLivingEntity().getHealth())))));

            hologramBase.setLines(hologramLines);
            return;
        }

        List<String> hologramLines = new ArrayList<>();
        config.getStringList("Hologram.pending-spawn").forEach(string
                -> hologramLines.add(ColorUtil.color(string
                .replace("%time%", String.valueOf(time))
                .replace("%bossprefix%", config.getString("boss-prefix")))));

        hologramBase.setLines(hologramLines);
    }

    public HologramBase getHologramBase() {
        return hologramBase;
    }

    public MobBehavior getMobBehavior() {
        return mobBehavior;
    }
}
