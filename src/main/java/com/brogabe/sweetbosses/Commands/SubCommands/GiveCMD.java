package com.brogabe.sweetbosses.Commands.SubCommands;

import com.brogabe.sweetbosses.Commands.SubCommand;
import com.brogabe.sweetbosses.EggItem.SpawnEggItem;
import com.brogabe.sweetbosses.SweetBosses;
import com.brogabe.sweetbosses.Utils.ColorUtil;
import com.cryptomorin.xseries.XSound;
import org.bukkit.Bukkit;
import org.bukkit.command.CommandSender;
import org.bukkit.configuration.file.YamlConfiguration;
import org.bukkit.entity.Player;

import java.io.File;

public class GiveCMD implements SubCommand {


    @Override
    public void doCommand(SweetBosses plugin, CommandSender sender, String[] args) {
        if(!sender.hasPermission(getPermission())) {
            sender.sendMessage(ColorUtil.color("&5&lSWEETBOSSES &fYou do not have permissions for this!"));
            return;
        }

        if(args.length < 4) {
            sender.sendMessage(ColorUtil.color("&4Invalid Usage: &c/sweetbosses give <player> <boss> <amount>"));
            if(sender instanceof Player) {
                XSound.BLOCK_ANVIL_LAND.play((Player) sender);
            }
            return;
        }

        Player player = Bukkit.getPlayerExact(args[1]);

        if(player == null || !player.isOnline()) {
            sender.sendMessage(ColorUtil.color("&5&lSWEETBOSSES &fPlayer is invalid!"));
            return;
        }

        File bossFolder = new File(plugin.getDataFolder(), "Bosses");

        if(!bossFolder.exists()) {
            sender.sendMessage(ColorUtil.color("&5&lSWEETBOSSES &fBoss is invalid!"));
            return;
        }

        File eggFile = new File(bossFolder, args[2] + ".yml");

        if(!eggFile.exists()) {
            sender.sendMessage(ColorUtil.color("&5&lSWEETBOSSES &fBoss is invalid!"));
            return;
        }

        int amount;

        try {
            amount = Integer.parseInt(args[3]);
        } catch (NumberFormatException exception) {
            sender.sendMessage(ColorUtil.color("&5&lSWEETBOSSES &fNumber is invalid!"));
            return;
        }

        player.getInventory().addItem(SpawnEggItem.getSpawnEgg(YamlConfiguration.loadConfiguration(eggFile), args[2], amount));

        if(sender instanceof Player) {
            Player sendingPlayer = (Player) sender;
            XSound.ENTITY_PLAYER_LEVELUP.play(sendingPlayer);
            sendingPlayer.sendMessage(ColorUtil.color(plugin.getConfig().getString("messages.give-cmd").replace("%player%",
                    player.getName())));
        }

        player.sendMessage(ColorUtil.color(plugin.getConfig().getString("messages.receive-msg")));
    }

    @Override
    public String getPermission() {
        return "sweetbosses.admin";
    }
}
