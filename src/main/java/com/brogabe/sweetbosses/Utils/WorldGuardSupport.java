package com.brogabe.sweetbosses.Utils;

import com.sk89q.worldguard.bukkit.RegionContainer;
import com.sk89q.worldguard.bukkit.RegionQuery;
import com.sk89q.worldguard.bukkit.WorldGuardPlugin;
import com.sk89q.worldguard.protection.ApplicableRegionSet;
import com.sk89q.worldguard.protection.regions.ProtectedRegion;
import org.bukkit.Location;

public class WorldGuardSupport {

    public static boolean containsRegion(Location location, String name) {

        RegionContainer container = WorldGuardPlugin.inst().getRegionContainer();
        RegionQuery regionQuery = container.createQuery();
        ApplicableRegionSet applicableRegionSet = regionQuery.getApplicableRegions(location);

        for(ProtectedRegion region : applicableRegionSet.getRegions()) {
            if(region.getId().equalsIgnoreCase(name)) {
                return true;
            }
        }
        return false;
    }
}
