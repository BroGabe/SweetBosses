package com.brogabe.sweetbosses.EggItem;

import com.brogabe.sweetbosses.Utils.ItemBuilder;
import de.tr7zw.nbtapi.NBTCompound;
import de.tr7zw.nbtapi.NBTItem;
import org.bukkit.Material;
import org.bukkit.configuration.ConfigurationSection;
import org.bukkit.configuration.file.FileConfiguration;
import org.bukkit.inventory.ItemStack;

public class SpawnEggItem {

    public static ItemStack getSpawnEgg(FileConfiguration config, String configName, int amount) {
        ConfigurationSection configSection = config.getConfigurationSection("spawn-egg");


        ItemStack itemStack = new ItemBuilder(configSection.getString(".name"),
                Material.valueOf(configSection.getString(".material")),
                configSection.getInt(".data"),
                configSection.getStringList(".lore"),
                configSection.getBoolean(".glowing")).getItemStack();

        itemStack.setAmount(amount);

        NBTItem nbtItem = new NBTItem(itemStack);
        NBTCompound bossCompound = nbtItem.addCompound("SweetBosses");
        bossCompound.setString("FileName", configName);

        return nbtItem.getItem();
    }
}
