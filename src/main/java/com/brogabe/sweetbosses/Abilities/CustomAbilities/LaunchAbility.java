package com.brogabe.sweetbosses.Abilities.CustomAbilities;

import com.brogabe.sweetbosses.Abilities.Ability;
import com.brogabe.sweetbosses.Mob.MobBehavior;
import com.brogabe.sweetbosses.SweetBosses;
import com.brogabe.sweetbosses.Utils.ColorUtil;
import org.bukkit.entity.Entity;
import org.bukkit.entity.Player;
import org.bukkit.util.Vector;

import java.util.Random;

public class LaunchAbility extends Ability {


    public LaunchAbility(SweetBosses plugin, MobBehavior mobBehavior, int chance) {
        super(plugin, mobBehavior, chance);
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

            Player player = (Player) entity;

            player.setVelocity(new Vector(3, 3, 3));

            player.sendMessage(ColorUtil.color(getMobBehavior().getConfig().getString("boss-prefix") +
                    " &fYou have been &d&nLAUNCHED"));
        }
    }
}
