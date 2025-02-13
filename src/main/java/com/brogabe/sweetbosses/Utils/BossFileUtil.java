package com.brogabe.sweetbosses.Utils;

import com.brogabe.sweetbosses.SweetBosses;

import java.io.File;

public class BossFileUtil {

    public static File getBossFile(String name) {
        File bossFolder = new File(SweetBosses.getInst().getDataFolder(), "Bosses");

        File eggFile = new File(bossFolder, name + ".yml");

        if(!bossFolder.exists()|| !eggFile.exists()) return null;

        return eggFile;
    }
}
