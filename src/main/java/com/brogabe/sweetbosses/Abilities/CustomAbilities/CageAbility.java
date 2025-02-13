package com.brogabe.sweetbosses.Abilities.CustomAbilities;

import com.brogabe.sweetbosses.Abilities.Ability;
import com.brogabe.sweetbosses.Abilities.AbilityManager;
import com.brogabe.sweetbosses.Mob.MobBehavior;
import com.brogabe.sweetbosses.SweetBosses;
import com.brogabe.sweetbosses.Utils.ColorUtil;
import org.bukkit.Bukkit;
import org.bukkit.Location;
import org.bukkit.Material;
import org.bukkit.entity.Entity;
import org.bukkit.entity.Player;

import java.util.*;

public class CageAbility extends Ability {

    private final AbilityManager abilityManager;

    public CageAbility(SweetBosses plugin, MobBehavior mobBehavior, int chance, AbilityManager abilityManager) {
        super(plugin, mobBehavior, chance);
        this.abilityManager = abilityManager;
    }

    @Override
    public void doAbility() {
        Random random = new Random();

        if(getChance() < random.nextInt(101)) {
            return;
        }

        for(Entity entity : getMobBehavior().getLivingEntity().getNearbyEntities(12, 12, 12)) {
            if(!(entity instanceof Player)) continue;
            if(!getMobBehavior().getDamagersMap().containsKey(entity.getUniqueId())) continue;
            if(abilityManager.hasActiveDebuff(entity.getUniqueId())) continue;
            Player player = (Player) entity;
            Location spawnLocation = player.getLocation().getBlock().getLocation().clone();
            spawnLocation.setY(spawnLocation.getY() +1);


            Map<Location, Material> queuedBlocks = new HashMap<>();
            if(!cagePlayer(player, spawnLocation, queuedBlocks)) return;

            player.sendMessage(ColorUtil.color(getMobBehavior().getConfig().getString("boss-prefix") +
                    " &fGET CAGED!"));
        }
    }

    private boolean cagePlayer(Player player, Location spawnPoint, Map<Location, Material> queuedBlocks) {
        List<Location> deniedLocations = new ArrayList<>();
        Location airLocation = spawnPoint.clone();
        airLocation.setY(airLocation.getY() +1);
        deniedLocations.add(spawnPoint);
        deniedLocations.add(airLocation);

        if(!createCageLayer(player, deniedLocations, 1, queuedBlocks) ||
                !createCageLayer(player, deniedLocations, 2, queuedBlocks) ||
                !createObsidianLayer(player, 0, queuedBlocks) || !createObsidianLayer(player, 3, queuedBlocks)) {
            queuedBlocks.clear();
            return false;
        }

        abilityManager.setPlayerDebuff(player.getUniqueId());
        queuedBlocks.forEach((loc, mat) -> loc.getBlock().setType(mat));

        player.teleport(spawnPoint);

        Bukkit.getScheduler().runTaskLater(getPlugin(), () ->  {
            queuedBlocks.forEach((loc, mat) -> loc.getBlock().setType(Material.AIR));
            queuedBlocks.clear();
            abilityManager.removePlayerDebuff(player.getUniqueId());
        }, 20 * 4);

        // Issue because its on a runnable every 4 seconds, if ability procs
        // too often. It will clear the queue before the next cage disappears
        // leaving the cage to stay.

        return true;
    }

    private boolean createCageLayer(Player player, List<Location> deniedLocations, int height, Map<Location, Material> queuedBlocks) {
        for(int i = 0; i<3; i++) {
            Location createWidth = player.getLocation().getBlock().getLocation().clone();
            createWidth.setY(createWidth.getY() + height);
            createWidth.setX(createWidth.getX() - 1);
            createWidth.setX(createWidth.getX() + i);
            if(createWidth.getBlock().getType() != Material.AIR) return false;
            if(!deniedLocations.contains(createWidth)) {
                queuedBlocks.put(createWidth, Material.IRON_FENCE);
            }
            for(int k = 0; k<3; k++) {
                Location createHeight = createWidth.clone();
                createHeight.setZ(createWidth.getZ() -1);
                createHeight.setZ(createHeight.getZ() + k);
                if(createHeight.getBlock().getType() != Material.AIR) return false;
                if(deniedLocations.contains(createHeight)) continue;
                queuedBlocks.put(createHeight, Material.IRON_FENCE);
            }
        }
        return true;
    }

    private boolean createObsidianLayer(Player player, int height, Map<Location, Material> queuedBlocks) {
        for(int i = 0; i<3; i++) {
            Location createWidth = player.getLocation().getBlock().getLocation();
            createWidth.setY(createWidth.getY() + height);
            createWidth.setX(createWidth.getX() - 1);
            createWidth.setX(createWidth.getX() + i);
            if(createWidth.getBlock().getType() != Material.AIR) return false;
            queuedBlocks.put(createWidth, Material.OBSIDIAN);
            for(int k = 0; k<3; k++) {
                Location createHeight = createWidth.clone();
                createHeight.setZ(createWidth.getZ() -1);
                createHeight.setZ(createHeight.getZ() + k);
                if(createHeight.getBlock().getType() != Material.AIR) return false;
                queuedBlocks.put(createHeight, Material.OBSIDIAN);
            }
        }
        return true;
    }
}
