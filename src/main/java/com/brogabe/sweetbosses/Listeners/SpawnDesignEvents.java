package com.brogabe.sweetbosses.Listeners;

import com.brogabe.sweetbosses.Mob.MobBehavior;
import com.brogabe.sweetbosses.SweetBosses;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.block.Action;
import org.bukkit.event.block.BlockBreakEvent;
import org.bukkit.event.entity.EntityDamageEvent;
import org.bukkit.event.player.PlayerInteractEvent;
import org.bukkit.event.server.PluginDisableEvent;

import java.util.Iterator;

public class SpawnDesignEvents implements Listener {

    private final SweetBosses plugin;

    public SpawnDesignEvents(SweetBosses plugin) {
        this.plugin = plugin;
    }

    @EventHandler
    public void onDamage(EntityDamageEvent event) {
        if(!event.getEntity().hasMetadata("BossHolder")) return;
        event.setCancelled(true);
    }

    @EventHandler
    public void onBreak(BlockBreakEvent event) {
        if(!event.getBlock().hasMetadata("BossHolder")) return;
        event.setCancelled(true);
    }

    @EventHandler
    public void onInteract(PlayerInteractEvent event) {
        if(event.getAction() != Action.RIGHT_CLICK_BLOCK) return;
        if(!event.getClickedBlock().hasMetadata("BossHolder")) return;
        event.setCancelled(true);
    }

//    @EventHandler
//    public void onShutdown(PluginDisableEvent event) {
//        if(event.getPlugin() != plugin) {
//            System.out.println("Plugin that shut down isnt this.");
//            return;
//        }
//        Iterator<MobBehavior> iterator = plugin.bossMap.values().iterator();
//        System.out.println("Iterator: " + iterator);
//        System.out.println("Boss values size: " + plugin.bossMap.values().size());
//        System.out.println("Boss map size: " + plugin.bossMap.size());
//
//        while (iterator.hasNext()) {
//            MobBehavior mobBehavior = iterator.next();
//            mobBehavior.clearMob();
//        }
//    }

}
