package com.brogabe.sweetbosses.Commands;

import com.brogabe.sweetbosses.SweetBosses;
import org.bukkit.command.CommandSender;

public interface SubCommand {

    void doCommand(SweetBosses plugin, CommandSender sender, String[] args);

    String getPermission();
}
