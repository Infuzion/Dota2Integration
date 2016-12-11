package me.infuzion.dota2.integration.nodes;

import java.util.Map;

public class Hero {
    public final int id;
    public final String name;
    public final int level;
    public final boolean alive;
    public final int respawn_seconds;
    public final int buyback_cost;
    public final int buyback_cooldown;
    public final int health;
    public final int max_health;
    public final double health_percent;
    public final int mana;
    public final int max_mana;
    public final double mana_percent;
    public final boolean silenced;
    public final boolean stunned;
    public final boolean disarmed;
    public final boolean magicimmune;
    public final boolean hexed;
    public final boolean muted;
    public final boolean break_; //underscore to prevent it being a keyword
    public final boolean has_debuff;

    public Hero(Map<String, String> map) {
        if(map != null && map.get("id") != null && map.get("level") != null) {
            id = Double.valueOf(map.get("id")).intValue();
            name = map.get("name");
            level = Double.valueOf(map.get("level")).intValue();
            alive = Boolean.parseBoolean(map.get("alive"));
            respawn_seconds = Double.valueOf(map.get("respawn_seconds")).intValue();
            buyback_cost = Double.valueOf(map.get("buyback_cost")).intValue();
            buyback_cooldown = Double.valueOf(map.get("buyback_cooldown")).intValue();
            health = Double.valueOf(map.get("health")).intValue();
            max_health = Double.valueOf(map.get("max_health")).intValue();
            health_percent = Double.parseDouble(map.get("health_percent"));
            mana = Double.valueOf(map.get("mana")).intValue();
            max_mana = Double.valueOf(map.get("max_mana")).intValue();
            mana_percent = Double.parseDouble(map.get("mana_percent"));
            silenced = Boolean.parseBoolean(map.get("silenced"));
            stunned = Boolean.parseBoolean(map.get("stunned"));
            disarmed = Boolean.parseBoolean(map.get("disarmed"));
            magicimmune = Boolean.parseBoolean(map.get("magicimmune"));
            hexed = Boolean.parseBoolean(map.get("hexed"));
            muted = Boolean.parseBoolean(map.get("muted"));
            break_ = Boolean.parseBoolean(map.get("break"));
            has_debuff = Boolean.parseBoolean(map.get("has_debuff"));
        } else {
            id = -1;
            name = null;
            level = -1;
            alive = false;
            respawn_seconds = -1;
            buyback_cost = -1;
            buyback_cooldown = -1;
            health = -1;
            max_health = -1;
            health_percent = -1;
            mana = -1;
            max_mana = -1;
            mana_percent = -1;
            silenced = false;
            stunned = false;
            disarmed = false;
            magicimmune = false;
            hexed = false;
            muted = false;
            break_ = false;
            has_debuff = false;

        }
    }
}
