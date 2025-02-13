package com.brogabe.sweetbosses.Utils;

import com.massivecraft.factions.Board;
import com.massivecraft.factions.FLocation;
import org.bukkit.Bukkit;
import org.bukkit.Location;

public class FactionsSupport {

    public static boolean isWarzone(Location location) {
        if(Bukkit.getPluginManager().getPlugin("Factions") == null) {
            System.out.println("[SweetBosses] No Faction support found.");
            return false;
        }
        return Board.getInstance().getFactionAt(new FLocation(location)).isWarZone();
    }
}
