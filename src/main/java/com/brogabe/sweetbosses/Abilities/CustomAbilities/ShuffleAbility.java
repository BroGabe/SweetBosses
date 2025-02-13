package com.brogabe.sweetbosses.Abilities.CustomAbilities;

import com.brogabe.sweetbosses.Abilities.Ability;
import com.brogabe.sweetbosses.Mob.MobBehavior;
import com.brogabe.sweetbosses.SweetBosses;
import com.brogabe.sweetbosses.Utils.ColorUtil;
import org.bukkit.entity.Entity;
import org.bukkit.entity.Player;
import org.bukkit.inventory.ItemStack;

import java.util.Random;

public class ShuffleAbility extends Ability {
    public ShuffleAbility(SweetBosses plugin, MobBehavior mobBehavior, int chance) {
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
            ItemStack[] playerContents = player.getInventory().getContents();

            Random randomIndex = new Random();
            int index = randomIndex.nextInt(8) +1;

            ItemStack replacement = playerContents[index];
            playerContents[index] = playerContents[player.getInventory().getHeldItemSlot()];
            playerContents[player.getInventory().getHeldItemSlot()] = replacement;

            player.getInventory().setContents(playerContents);

            player.sendMessage(ColorUtil.color(getMobBehavior().getConfig().getString("boss-prefix") +
                    " &fDEALT A NEW HAND!!"));
        }
    }
}
