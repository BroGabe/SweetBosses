package com.brogabe.sweetbosses.Mob.Equipment;

import com.brogabe.sweetbosses.Mob.MobBehavior;
import com.brogabe.sweetbosses.SweetBosses;
import com.brogabe.sweetbosses.Utils.ColorUtil;
import com.brogabe.sweetbosses.Utils.ItemBuilder;
import org.bukkit.Material;
import org.bukkit.configuration.ConfigurationSection;
import org.bukkit.configuration.file.FileConfiguration;
import org.bukkit.inventory.ItemStack;

public class EquipmentManager {

    private final SweetBosses plugin;
    private final MobBehavior mobBehavior;

    public EquipmentManager(SweetBosses plugin, MobBehavior mobBehavior) {
        this.plugin = plugin;
        this.mobBehavior = mobBehavior;
    }

    public void initializeEquipment() {
        for (String bossEquipmentSection : mobBehavior.getConfig().getConfigurationSection("equipment").getKeys(false)) {
            ConfigurationSection configSection = mobBehavior.getConfig().getConfigurationSection("equipment." + bossEquipmentSection);
            ItemStack itemStack;

            if (configSection.getString(".material").contains("CUSTOMITEM")) {
                String[] itemSection = configSection.getString(".material").split(":");
                itemStack = getCustomItem(plugin.itemsConfig.getConfig(), itemSection[1]);
            } else {
                itemStack = new ItemBuilder("&7", Material.valueOf(configSection.getString(".material")), 0, false, "&7").getItemStack();
                ItemBuilder.initializeEnchants(itemStack, configSection.getStringList(".enchants"));
            }
            applyEquipmentPiece(bossEquipmentSection.toUpperCase(), itemStack);
        }
    }

    private void applyEquipmentPiece(String section, ItemStack itemStack) {
        switch (section.toUpperCase()) {
            case "HELMET":
                mobBehavior.getLivingEntity().getEquipment().setHelmet(itemStack);
                return;
            case "CHESTPLATE":
                mobBehavior.getLivingEntity().getEquipment().setChestplate(itemStack);
                return;
            case "LEGGINGS":
                mobBehavior.getLivingEntity().getEquipment().setLeggings(itemStack);
                return;
            case "BOOTS":
                mobBehavior.getLivingEntity().getEquipment().setBoots(itemStack);
                return;
            default:
                mobBehavior.getLivingEntity().getEquipment().setItemInHand(itemStack);
        }
    }

    private ItemStack getCustomItem(FileConfiguration config, String section) {
        ConfigurationSection configSection = config.getConfigurationSection("items." + section);
        Material material = Material.valueOf(configSection.getString(".material"));
        ItemStack itemStack;

        if(ItemBuilder.isLeatherArmor(material)) {
            itemStack = new ItemBuilder(configSection.getString(".name"), material,
                    configSection.getStringList(".lore"), configSection.getStringList(".enchants"),
                    ColorUtil.colorFromString(configSection.getString(".color"))).getItemStack();

            return itemStack;
        } else if (ItemBuilder.isSkullItem(material)) {
            itemStack = new ItemBuilder(configSection.getString(".name"),
                    configSection.getStringList(".lore"), configSection.getString(".texture")).getItemStack();

            return itemStack;
        }

        itemStack = new ItemBuilder(configSection.getString(".name"),
                material, configSection.getInt(".data"), configSection.getStringList(".lore"),
                configSection.getStringList(".enchants")).getItemStack();


        return itemStack;
    }
}
