package com.brogabe.sweetbosses.Abilities.CustomAbilities;

import com.brogabe.sweetbosses.Abilities.Ability;
import com.brogabe.sweetbosses.Mob.MobBehavior;
import com.brogabe.sweetbosses.SweetBosses;
import com.brogabe.sweetbosses.Utils.ColorUtil;
import org.bukkit.entity.Arrow;
import org.bukkit.entity.Entity;
import org.bukkit.entity.Player;
import org.bukkit.metadata.FixedMetadataValue;

import java.util.Random;

public class ArrowAbility extends Ability {
    public ArrowAbility(SweetBosses plugin, MobBehavior mobBehavior, int chance) {
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

            Arrow arrow = getMobBehavior().getLivingEntity().launchProjectile(Arrow.class, player.getVelocity());
            arrow.setMetadata("AbilityArrow", new FixedMetadataValue(getPlugin(), "Empty"));

            player.sendMessage(ColorUtil.color(getMobBehavior().getConfig().getString("boss-prefix") +
                    " &fGET BACK!"));

            arrow.setKnockbackStrength(arrow.getKnockbackStrength() + 3);
            arrow.setVelocity(player.getLocation().getDirection().multiply(-2));
        }
    }
}
