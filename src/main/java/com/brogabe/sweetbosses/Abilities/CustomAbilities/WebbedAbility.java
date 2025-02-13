package com.brogabe.sweetbosses.Abilities.CustomAbilities;

import com.brogabe.sweetbosses.Abilities.Ability;
import com.brogabe.sweetbosses.Abilities.AbilityManager;
import com.brogabe.sweetbosses.Mob.MobBehavior;
import com.brogabe.sweetbosses.SweetBosses;
import com.brogabe.sweetbosses.Utils.ColorUtil;
import com.cryptomorin.xseries.XSound;
import org.bukkit.Bukkit;
import org.bukkit.Location;
import org.bukkit.Material;
import org.bukkit.entity.Entity;
import org.bukkit.entity.Player;

import java.util.Random;

public class WebbedAbility extends Ability {

    private final AbilityManager abilityManager;

    public WebbedAbility(SweetBosses plugin, MobBehavior mobBehavior, int chance, AbilityManager abilityManager) {
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

            if(player.getLocation().getBlock().getType() != Material.AIR) continue;

            player.sendMessage(ColorUtil.color(getMobBehavior().getConfig().getString("boss-prefix") +
        " &fYOU HAVE BEEN WEBBED"));
            XSound.ENTITY_SPIDER_HURT.play(player);

            Location blockLocation = player.getLocation().getBlock().getLocation();
            blockLocation.getBlock().setType(Material.WEB);

            abilityManager.setPlayerDebuff(player.getUniqueId());

            Bukkit.getScheduler().runTaskLater(getPlugin(), () -> {
                blockLocation.getBlock().setType(Material.AIR);
                abilityManager.removePlayerDebuff(player.getUniqueId());
            }, 20 * 3);
        }
    }
}
