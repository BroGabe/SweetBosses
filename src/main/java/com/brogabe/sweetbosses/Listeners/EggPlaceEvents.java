package com.brogabe.sweetbosses.Listeners;

import com.brogabe.sweetbosses.Spawning.SpawnBase;
import com.brogabe.sweetbosses.SweetBosses;
import com.brogabe.sweetbosses.Utils.*;
import com.cryptomorin.xseries.XSound;
import de.tr7zw.nbtapi.NBTCompound;
import de.tr7zw.nbtapi.NBTItem;
import org.bukkit.Location;
import org.bukkit.Material;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.block.Action;
import org.bukkit.event.player.PlayerInteractEvent;

import java.io.File;

public class EggPlaceEvents implements Listener {

    private final SweetBosses plugin;

    public EggPlaceEvents(SweetBosses plugin) {this.plugin = plugin;}

    @EventHandler
    public void onInteract(PlayerInteractEvent event) {

        if(event.getPlayer().getItemInHand().getType() == Material.AIR) {
            return;
        }

        NBTItem nbtItem = new NBTItem(event.getPlayer().getItemInHand());
        NBTCompound nbtCompound = nbtItem.getCompound("SweetBosses");


        if(nbtCompound == null) {
            return;
        }

        if(event.getAction() != Action.RIGHT_CLICK_BLOCK) {
            return;
        }

        if(!EggPlaceUtil.canPlace(event.getPlayer().getItemInHand(), event.getClickedBlock().getLocation())) {
            event.getPlayer().sendMessage(ColorUtil.color(plugin.getConfig().getString("messages.cannot-place")));
            XSound.BLOCK_NOTE_BLOCK_BASS.play(event.getPlayer());
            return;
        }

        event.setCancelled(true);

        File eggFile = BossFileUtil.getBossFile(nbtCompound.getString("FileName"));

        Location location = event.getClickedBlock().getLocation().clone().add(0, 1, 0).getBlock().getLocation();

        SpawnBase spawnBase = new SpawnBase(location, eggFile);
        plugin.spawnBaseList.add(spawnBase);

        event.getPlayer().setItemInHand(null);
    }

}
