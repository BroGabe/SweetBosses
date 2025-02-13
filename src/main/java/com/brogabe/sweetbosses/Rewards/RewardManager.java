package com.brogabe.sweetbosses.Rewards;

import com.brogabe.sweetbosses.Mob.MobBehavior;
import com.brogabe.sweetbosses.SweetBosses;
import com.cryptomorin.xseries.XSound;
import org.bukkit.Bukkit;
import org.bukkit.entity.Player;

import java.util.List;
import java.util.Map;
import java.util.UUID;

public class RewardManager {

    private final SweetBosses plugin;
    private final MobBehavior mobBehavior;

    public RewardManager(SweetBosses plugin, MobBehavior mobBehavior) {
        this.plugin = plugin;
        this.mobBehavior = mobBehavior;
    }

    public void rewardPlayers() {
        if(mobBehavior.getConfig().getBoolean("reward-settings.top-damagers.enabled")) {
            Map<UUID, Integer> damageMap = mobBehavior.getSortedDamagersMap();
            int rewardCount = 1;

            for(UUID uuid : damageMap.keySet()) {
                List<String> rewardsList = mobBehavior.getConfig().getStringList("reward-settings.top-damagers.rewards." + rewardCount);
                if(rewardCount >= mobBehavior.getConfig().getConfigurationSection("reward-settings.top-damagers.rewards").getKeys(false).size()) {
                    break;
                }

                rewardsList.stream().forEach(commandString -> {
                    Player player = Bukkit.getPlayer(uuid);
                    if(player != null && player.isOnline()) {
                        Bukkit.dispatchCommand(Bukkit.getConsoleSender(), commandString.replace("%player%", player.getName()));
                        XSound.ENTITY_PLAYER_LEVELUP.play(player);
                    }
                });

                rewardCount++;
            }

            return;
        }

        Map<UUID, Integer> damageMap = mobBehavior.getSortedDamagersMap();
        List<String> rewardsList = mobBehavior.getConfig().getStringList("reward-settings.all-attackers.rewards");

        for(UUID uuid : damageMap.keySet()) {
            Player player = Bukkit.getPlayer(uuid);

            if(player == null || !player.isOnline()) continue;

            int percentage = damageMap.get(uuid) * 100 / (int) mobBehavior.getLivingEntity().getMaxHealth();

            if(percentage < mobBehavior.getConfig().getInt("reward-settings.all-attackers.required-percentage")) continue;

            rewardsList.stream().forEach(commandString -> Bukkit.dispatchCommand(Bukkit.getConsoleSender(),
                    commandString.replace("%player%", player.getName())));

            XSound.ENTITY_PLAYER_LEVELUP.play(player);
        }

    }
}
