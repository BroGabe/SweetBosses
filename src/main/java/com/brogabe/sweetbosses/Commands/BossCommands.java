package com.brogabe.sweetbosses.Commands;

import com.brogabe.sweetbosses.Commands.SubCommands.GiveCMD;
import com.brogabe.sweetbosses.SweetBosses;
import com.brogabe.sweetbosses.Utils.ColorUtil;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;

import java.util.HashMap;
import java.util.Map;

public class BossCommands implements CommandExecutor {

    private final SweetBosses plugin;

    private final Map<String, SubCommand> subCommandsMap = new HashMap<>();

    public BossCommands(SweetBosses plugin) {
        this.plugin = plugin;

        subCommandsMap.put("give", new GiveCMD());
    }

    @Override
    public boolean onCommand(CommandSender sender, Command command, String label, String[] args) {

        if(args.length == 0) {

            for(String string : plugin.getConfig().getStringList("messages.default-msg")) {
                sender.sendMessage(ColorUtil.color(string));
            }

            return false;
        }

        if(!subCommandsMap.containsKey(args[0].toLowerCase())) {
            System.out.println("Sub command wasnt in the map");
            return false;
        }

        subCommandsMap.get(args[0].toLowerCase()).doCommand(plugin, sender, args);
        return false;
    }

}
