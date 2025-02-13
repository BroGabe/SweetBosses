package com.brogabe.sweetbosses.Utils;

import de.tr7zw.nbtapi.NBT;
import de.tr7zw.nbtapi.iface.ReadWriteNBT;
import org.bukkit.Color;
import org.bukkit.Material;
import org.bukkit.enchantments.Enchantment;
import org.bukkit.inventory.ItemFlag;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.ItemMeta;
import org.bukkit.inventory.meta.LeatherArmorMeta;

import java.util.*;

public class ItemBuilder {

    private ItemStack itemStack;
    private ItemMeta itemMeta;

    public ItemBuilder(String name, Material material, int data, boolean glow, String... lore) {
        itemStack = data <= 0 ? new ItemStack(material) : new ItemStack(material, 1, (short) data);
        itemMeta = itemStack.getItemMeta();
        initializeName(name);
        initializeLore(Arrays.asList(lore));

        if(glow) {
            setGlowing();
        }
    }

    public ItemBuilder(String name, Material material, int data, List<String> lore, List<String> enchants) {
        itemStack = data <= 0 ? new ItemStack(material) : new ItemStack(material, 1, (short) data);
        itemMeta = itemStack.getItemMeta();

        initializeName(name);
        initializeLore(lore);
        initializeEnchants(enchants);
    }

    public ItemBuilder(String name, Material material, int data, List<String> lore, boolean glow) {
        itemStack = data <= 0 ? new ItemStack(material) : new ItemStack(material, 1, (short) data);
        itemMeta = itemStack.getItemMeta();

        initializeName(name);
        initializeLore(lore);
        if(glow) {
            setGlowing();
        }
    }

    public ItemBuilder(String name, List<String> lore, String texture) {
        itemStack = new ItemStack(Material.SKULL_ITEM, 1, (short) 3);
        itemMeta = itemStack.getItemMeta();

        initializeName(name);
        initializeLore(lore);

        NBT.modify(itemStack, nbt -> {
            final ReadWriteNBT skullOwnerCompound = nbt.getOrCreateCompound("SkullOwner");

            skullOwnerCompound.setUUID("Id", UUID.randomUUID());
            skullOwnerCompound.getOrCreateCompound("Properties")
                    .getCompoundList("textures")
                    .addCompound()
                    .setString("Value", texture);
        });
    }

    public ItemBuilder(String name, Material material, List<String> lore, List<String> enchants, Color color) {
        itemStack = new ItemStack(material);
        LeatherArmorMeta leatherArmorMeta = (LeatherArmorMeta) itemStack.getItemMeta();
        leatherArmorMeta.setColor(color);
        itemMeta = leatherArmorMeta;

        initializeName(name);
        initializeLore(lore);
        initializeEnchants(enchants);
    }

    private void initializeLore(List<String> lore) {
        if(lore == null || lore.isEmpty()) {
            return;
        }
        List<String> finalLore = new ArrayList<>();
        lore.stream().forEach(s -> finalLore.add(ColorUtil.color(s)));

        itemMeta.setLore(finalLore);
        itemStack.setItemMeta(itemMeta);
        itemMeta = itemStack.getItemMeta();
    }

    private void initializeName(String name) {
        itemMeta.setDisplayName(ColorUtil.color(name));
        itemStack.setItemMeta(itemMeta);
        itemMeta = itemStack.getItemMeta();
    }

    private void initializeEnchants(List<String> enchants) {
        if(enchants == null || enchants.isEmpty()) {
            return;
        }
        Map<Enchantment, Integer> enchantments = new HashMap<>();

        for(String enchantString : enchants) {
            String[] splitString = enchantString.split(":");
            enchantments.put(Enchantment.getByName(splitString[0]), Integer.valueOf(splitString[1]));
        }

        itemStack.addUnsafeEnchantments(enchantments);
        itemMeta = itemStack.getItemMeta();
    }

    public static void initializeEnchants(ItemStack itemStack, List<String> enchants) {
        if(enchants == null || enchants.isEmpty()) {
            return;
        }
        Map<Enchantment, Integer> enchantments = new HashMap<>();

        for(String enchantString : enchants) {
            String[] splitString = enchantString.split(":");
            enchantments.put(Enchantment.getByName(splitString[0]), Integer.valueOf(splitString[1]));
        }

        itemStack.addUnsafeEnchantments(enchantments);
    }

    private void setGlowing() {
        itemStack.addUnsafeEnchantment(Enchantment.PROTECTION_ENVIRONMENTAL, 1);
        itemMeta = itemStack.getItemMeta();
        itemMeta.addItemFlags(ItemFlag.HIDE_ENCHANTS);
        itemStack.setItemMeta(itemMeta);
    }

    public static boolean isLeatherArmor(Material material) {
        switch (material) {
            case LEATHER_BOOTS:
            case LEATHER_CHESTPLATE:
            case LEATHER_HELMET:
            case LEATHER_LEGGINGS:
                return true;
            default: return false;
        }
    }

    public static boolean isSkullItem(Material material) {
        return material == Material.SKULL_ITEM;
    }

    public ItemStack getItemStack() {
        return itemStack;
    }
}
