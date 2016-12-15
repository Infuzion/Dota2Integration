package me.infuzion.dota2.integration.nodes;

public class Ability {

    public final String name;
    public final int level;
    public final boolean can_cast;
    public final boolean passive;
    public final boolean ability_active;
    public final int cooldown;
    public final boolean ultimate;

    public Ability(String name, int level, boolean can_cast, boolean passive, boolean ability_active,
                   int cooldown, boolean ultimate) {
        this.name = name;
        this.level = level;
        this.can_cast = can_cast;
        this.passive = passive;
        this.ability_active = ability_active;
        this.cooldown = cooldown;
        this.ultimate = ultimate;
    }
}
