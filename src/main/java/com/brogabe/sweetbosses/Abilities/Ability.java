package com.brogabe.sweetbosses.Abilities;

import com.brogabe.sweetbosses.Mob.MobBehavior;
import com.brogabe.sweetbosses.SweetBosses;

public abstract class Ability {
    private final int chance;
    private final SweetBosses plugin;
    private final MobBehavior mobBehavior;

    public Ability(SweetBosses plugin, MobBehavior mobBehavior, int chance) {
        this.chance = chance;
        this.plugin = plugin;
        this.mobBehavior = mobBehavior;
    }

    public abstract void doAbility();

    public int getChance(){
        return chance;
    }

    public SweetBosses getPlugin() {
        return plugin;
    }

    public MobBehavior getMobBehavior() {
        return mobBehavior;
    }
}
