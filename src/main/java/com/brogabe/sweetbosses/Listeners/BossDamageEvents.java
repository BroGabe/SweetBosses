package com.brogabe.sweetbosses.Listeners;

import com.brogabe.sweetbosses.Mob.MobBehavior;
import com.brogabe.sweetbosses.SweetBosses;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.entity.EntityDamageByEntityEvent;
import org.bukkit.event.entity.EntityDamageEvent;
import org.bukkit.event.entity.EntityDeathEvent;

public class BossDamageEvents implements Listener {

    private final SweetBosses plugin;

    public BossDamageEvents(SweetBosses plugin) {this.plugin = plugin;}

    @EventHandler
    public void onBossDamage(EntityDamageEvent event) {
        if(!plugin.bossMap.containsKey(event.getEntity().getUniqueId())) return;

        MobBehavior mobBehavior = plugin.bossMap.get(event.getEntity().getUniqueId());
        mobBehavior.updateName();
        mobBehavior.getSpawnBase().updateHologram();
    }

    @EventHandler
    public void onBossDeath(EntityDeathEvent event) {
        if(!plugin.bossMap.containsKey(event.getEntity().getUniqueId())) return;

        event.getDrops().clear();

        MobBehavior mobBehavior = plugin.bossMap.get(event.getEntity().getUniqueId());

        mobBehavior.getRewardManager().rewardPlayers();
        mobBehavior.getSpawnBase().removeSpawn();
    }

    @EventHandler
    public void onBossDamage(EntityDamageByEntityEvent event) {
        if(!plugin.bossMap.containsKey(event.getEntity().getUniqueId())) return;
        if(!(event.getDamager() instanceof Player)) return;

        MobBehavior mobBehavior = plugin.bossMap.get(event.getEntity().getUniqueId());

        Player player = (Player) event.getDamager();

        mobBehavior.getAbilityManager().doAbilities();

        if(mobBehavior.getDamagersMap().containsKey(player.getUniqueId())) {
            mobBehavior.getDamagersMap().put(player.getUniqueId(),
                    mobBehavior.getDamagersMap().get(player.getUniqueId()) + (int) event.getDamage());
            return;
        }
        mobBehavior.getDamagersMap().put(player.getUniqueId(), (int) event.getDamage());
    }
}
