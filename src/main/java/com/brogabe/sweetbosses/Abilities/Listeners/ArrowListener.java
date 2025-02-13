package com.brogabe.sweetbosses.Abilities.Listeners;

import org.bukkit.entity.Arrow;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.entity.EntityDamageByEntityEvent;
import org.bukkit.potion.PotionEffect;
import org.bukkit.potion.PotionEffectType;

public class ArrowListener implements Listener {

    @EventHandler
    public void onPlayerDamage(EntityDamageByEntityEvent event) {
        if(!(event.getDamager() instanceof Arrow)) return;
        if(!(event.getEntity() instanceof Player)) return;
        Player damaged = (Player) event.getEntity();
        Arrow attacker = (Arrow) event.getDamager();

        if(!attacker.hasMetadata("AbilityArrow")) return;

        damaged.addPotionEffect(new PotionEffect(PotionEffectType.POISON, 20 * 3, 1));
    }
}
