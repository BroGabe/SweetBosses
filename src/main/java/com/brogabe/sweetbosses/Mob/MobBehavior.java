package com.brogabe.sweetbosses.Mob;
import com.brogabe.sweetbosses.Abilities.AbilityManager;
import com.brogabe.sweetbosses.Mob.Equipment.EquipmentManager;
import com.brogabe.sweetbosses.Rewards.RewardManager;
import com.brogabe.sweetbosses.Spawning.SpawnBase;
import com.brogabe.sweetbosses.SweetBosses;
import com.brogabe.sweetbosses.Utils.ColorUtil;
import com.brogabe.sweetbosses.Utils.DamageComparator;
import org.bukkit.Location;
import org.bukkit.configuration.file.FileConfiguration;
import org.bukkit.entity.EntityType;
import org.bukkit.entity.LivingEntity;
import org.bukkit.potion.PotionEffect;
import org.bukkit.potion.PotionEffectType;
import org.bukkit.scheduler.BukkitRunnable;
import org.bukkit.scheduler.BukkitTask;


import java.util.*;

public class MobBehavior {

    private final SweetBosses plugin;

    private final FileConfiguration config;

    private final Map<UUID, Integer> damagersMap = new HashMap<>();

    private final String name;
    private final UUID uuid;
    private final LivingEntity livingEntity;
    private final Location location;

    private final AbilityManager abilityManager;
    private final RewardManager rewardManager;

    private SpawnBase spawnBase;

    private BukkitTask movementTask;

    public MobBehavior(SweetBosses plugin, FileConfiguration config, Location location, SpawnBase spawnBase) {
        this(plugin, config, location);
        this.spawnBase = spawnBase;
    }

    public MobBehavior(SweetBosses plugin, FileConfiguration config, Location location) {
        this.plugin = plugin;
        this.config = config;
        this.name = config.getString("display-name");
        this.location = location;
        this.livingEntity = (LivingEntity) location.getWorld().spawnEntity(location, EntityType.valueOf(config.getString("entity")));
        this.uuid = livingEntity.getUniqueId();

        this.abilityManager = new AbilityManager(plugin, this);
        this.rewardManager = new RewardManager(plugin, this);

        this.livingEntity.setMaxHealth(config.getInt("health"));
        this.livingEntity.setHealth(config.getInt("health"));
        this.livingEntity.setCustomName(getName());

        new EquipmentManager(plugin, this).initializeEquipment();
        initializePotionEffects();

        updateName();

        movementTask = new BukkitRunnable() {

            @Override
            public void run() {
                if(livingEntity.isDead() || !livingEntity.isValid()) {
                    clearMob();
                    this.cancel();
                }
                if(isMobMaxDistance()) {
                    livingEntity.teleport(location);
                }
            }
        }.runTaskTimer(plugin, 20 * 4, 20 * 4);
    }

    private void initializePotionEffects() {
        config.getStringList("potion-effects").forEach(potionString -> {
            String[] potions = potionString.split(":");
            livingEntity.addPotionEffect(new PotionEffect(PotionEffectType.getByName(potions[0]), Integer.MAX_VALUE, Integer.parseInt(potions[1]) -1));
        });
    }

    public TreeMap<UUID, Integer> getSortedDamagersMap() {
        TreeMap<UUID, Integer> sortedMap = new TreeMap<>(new DamageComparator(damagersMap));
        sortedMap.putAll(damagersMap);

        return sortedMap;
    }

    public boolean isMobMaxDistance() {
        return livingEntity.getLocation().distanceSquared(getSpawnLocation()) > config.getInt("settings.boss-distance");
    }

    public void clearMob() {
        this.livingEntity.remove();
        this.plugin.bossMap.remove(getUuid());
        movementTask.cancel();

        if(getSpawnBase() == null) return;

        getSpawnBase().removeSpawn();
        plugin.spawnBaseList.remove(getSpawnBase());
    }

    public String getBossPrefix() {return ColorUtil.color(config.getString("boss-prefix"));}

    public BukkitTask getMovementTask() {
        return movementTask;
    }

    public void updateName() {
        livingEntity.setCustomName(getName());
    }

    public Map<UUID, Integer> getDamagersMap() {
        return damagersMap;
    }

    public UUID getUuid() {
        return uuid;
    }

    public RewardManager getRewardManager() {
        return rewardManager;
    }

    public AbilityManager getAbilityManager() {
        return abilityManager;
    }

    public LivingEntity getLivingEntity() {
        return livingEntity;
    }

    public Location getSpawnLocation() {
        return location;
    }

    public FileConfiguration getConfig() {
        return config;
    }

    public SpawnBase getSpawnBase() {
        return spawnBase;
    }

    private String getName() {
        return ColorUtil.color(name.replace("%health%", String.valueOf((int) livingEntity.getHealth())));
    }
}
