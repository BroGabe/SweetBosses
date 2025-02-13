package com.brogabe.sweetbosses.Abilities.Listeners;

import org.bukkit.entity.Fireball;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.entity.EntityDamageByEntityEvent;
import org.bukkit.event.entity.EntityDamageEvent;
import org.bukkit.event.entity.EntityExplodeEvent;
import org.bukkit.potion.PotionEffect;
import org.bukkit.potion.PotionEffectType;

public class FireballListener implements Listener {

    @EventHandler
    public void onExplode(EntityExplodeEvent event) {
        if(!(event.getEntity() instanceof Fireball)) return;

        Fireball fireball = (Fireball) event.getEntity();

        if(!fireball.hasMetadata("AbilityFireball")) return;

        event.setCancelled(true);
    }

    @EventHandler
    public void onPlayerDamage(EntityDamageByEntityEvent event) {
        if(!(event.getDamager() instanceof Fireball)) return;
        if(!(event.getEntity() instanceof Player)) return;
        Player damaged = (Player) event.getEntity();
        Fireball attacker = (Fireball) event.getDamager();

        if(!attacker.hasMetadata("AbilityFireball")) return;

        damaged.addPotionEffect(new PotionEffect(PotionEffectType.BLINDNESS, 20 * 3, 1));
        damaged.addPotionEffect(new PotionEffect(PotionEffectType.WITHER, 20 * 3, 1));
        damaged.addPotionEffect(new PotionEffect(PotionEffectType.POISON, 20 * 3, 1));

    }

    @EventHandler
    public void onFireballDamage(EntityDamageEvent event) {
        if(!(event.getEntity() instanceof Fireball)) return;
        if(!event.getEntity().hasMetadata("AbilityFireball")) return;
        event.setCancelled(true);
    }
}
