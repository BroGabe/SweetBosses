package com.brogabe.sweetbosses.Abilities;

import com.brogabe.sweetbosses.Abilities.CustomAbilities.*;
import com.brogabe.sweetbosses.Mob.MobBehavior;
import com.brogabe.sweetbosses.SweetBosses;

import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

public class AbilityManager {

    private final SweetBosses plugin;
    private final MobBehavior mobBehavior;

    private final List<Ability> assignedAbilities = new ArrayList<>();

    private final List<UUID> activeDebuffedPlayers = new ArrayList<>();

    public AbilityManager(SweetBosses plugin, MobBehavior mobBehavior) {
        this.plugin = plugin;
        this.mobBehavior = mobBehavior;

        initializeAbilities();
    }

    public void doAbilities() {
        for(Ability ability : assignedAbilities) {
            ability.doAbility();
        }
    }

    private void initializeAbilities() {
        for(String abilityConfigString : mobBehavior.getConfig().getStringList("abilities")) {
            String[] abilityString = abilityConfigString.split(":");

            switch (abilityString[0].toUpperCase()) {
                case "LIGHTNING":
                    assignedAbilities.add(new LightningAbility(plugin, mobBehavior, Integer.parseInt(abilityString[1])));
                    continue;
                case "WEBBED":
                    assignedAbilities.add(new WebbedAbility(plugin, mobBehavior, Integer.parseInt(abilityString[1]), this));
                    continue;
                case "LAUNCH":
                    assignedAbilities.add(new LaunchAbility(plugin, mobBehavior, Integer.parseInt(abilityString[1])));
                    continue;
                case "FIREBALL":
                    assignedAbilities.add(new FireballAbility(plugin, mobBehavior, Integer.parseInt(abilityString[1])));
                    continue;
                case "ARROW":
                    assignedAbilities.add(new ArrowAbility(plugin, mobBehavior, Integer.parseInt(abilityString[1])));
                    continue;
                case "SHUFFLE":
                    assignedAbilities.add(new ShuffleAbility(plugin, mobBehavior, Integer.parseInt(abilityString[1])));
                    continue;
                case "CAGE":
                    assignedAbilities.add(new CageAbility(plugin, mobBehavior, Integer.parseInt(abilityString[1]), this));
            }
        }
    }

    public boolean hasActiveDebuff(UUID uuid) {
        return getActiveDebuffedPlayers().contains(uuid);
    }

    public void setPlayerDebuff(UUID uuid) {
        getActiveDebuffedPlayers().add(uuid);
    }

    public void removePlayerDebuff(UUID uuid) {
        getActiveDebuffedPlayers().remove(uuid);
    }

    private List<UUID> getActiveDebuffedPlayers() {
        return this.activeDebuffedPlayers;
    }
}
