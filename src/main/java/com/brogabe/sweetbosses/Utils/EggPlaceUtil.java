package com.brogabe.sweetbosses.Utils;

import com.brogabe.sweetbosses.SweetBosses;
import de.tr7zw.nbtapi.NBTCompound;
import de.tr7zw.nbtapi.NBTItem;
import org.bukkit.Bukkit;
import org.bukkit.Difficulty;
import org.bukkit.Location;
import org.bukkit.Material;
import org.bukkit.configuration.file.FileConfiguration;
import org.bukkit.configuration.file.YamlConfiguration;
import org.bukkit.inventory.ItemStack;

import java.io.File;

public class EggPlaceUtil {

    public static boolean canPlace(ItemStack itemStack, Location location) {
        if(itemStack == null || itemStack.getType() == Material.AIR) {
            return false;
        }

        if(SweetBosses.getInst().bossMap.size() >= SweetBosses.getInst().getConfig().getInt("settings.max-bosses")) {
            return false;
        }

        if(!location.getWorld().getAllowMonsters()) return false;
        if(location.getWorld().getDifficulty() == Difficulty.PEACEFUL) return false;

        NBTItem nbtItem = new NBTItem(itemStack);

        if(nbtItem.getCompound("SweetBosses") == null) {
            return false;
        }

        NBTCompound nbtCompound = nbtItem.getCompound("SweetBosses");

        assert nbtCompound != null;
        if(!nbtCompound.hasTag("FileName")) {
            return false;
        }

        File bossFile;

        try {
            bossFile = BossFileUtil.getBossFile(nbtCompound.getString("FileName"));
        } catch (Exception exception) {
            return false;
        }

        assert bossFile != null;
        FileConfiguration configuration = YamlConfiguration.loadConfiguration(bossFile);


        if(!worldGuardSupport(configuration, location)) return false;

        if(configuration.getBoolean("settings.warzone-only")) {
            if(!FactionsSupport.isWarzone(location)) {
                return false;
            }
        }

        return validArea(location);
    }

    private static boolean worldGuardSupport(FileConfiguration configuration, Location location) {
        if(Bukkit.getPluginManager().getPlugin("WorldGuard") == null) return false;
        if(!configuration.getStringList("settings.whitelisted-regions").isEmpty()) {
            boolean isWhitelisted = false;
            for(String whitelistedString : configuration.getStringList("settings.whitelisted-region")) {
                if(WorldGuardSupport.containsRegion(location, whitelistedString)) {
                    isWhitelisted = true;
                }
            }
            if(!isWhitelisted) return false;

        } else {
            for(String blacklistedString : configuration.getStringList("settings.blacklisted-regions")) {
                if(WorldGuardSupport.containsRegion(location, blacklistedString)) {
                    return false;
                }
            }
        }
        return true;
    }

    private static boolean validArea(Location location) {
        Location placementArea = location.clone().add(0, 1, 0);

        return placementArea.getBlock().getType() == Material.AIR &&
                placementArea.clone().add(0, 1, 0).getBlock().getType() == Material.AIR;
    }
}
