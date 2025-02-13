package com.brogabe.sweetbosses.Utils;

import org.bukkit.Bukkit;
import org.bukkit.entity.Player;

import java.util.Comparator;
import java.util.Map;
import java.util.UUID;

public class DamageComparator implements Comparator<UUID> {

    private Map<UUID, Integer> damagersMap;

    public DamageComparator(Map<UUID, Integer> damagersMap) {
        this.damagersMap = damagersMap;
    }

    @Override
    public int compare(UUID o1, UUID o2) {
        if(damagersMap.get(o1) >= damagersMap.get(o2)) {
            return -1;
        } else {
            return 1;
        }
    }
}
