package me.infuzion.dota2.integration.nodes;

import java.util.Map;

@SuppressWarnings("WeakerAccess")
public class Player {
    public final String steamid;
    public final String name;
    public final String activity;
    public final int kills;
    public final int deaths;
    public final int assists;
    public final int last_hits;
    public final int denies;
    public final int kill_streak;
    public final String team_name;
    public final int gold;
    public final int gold_reliable;
    public final int gold_unreliable;
    public final int gpm;
    public final int xpm;

    public Player(Map<String, String> map) {
        if(map != null && map.get("steamid") != null) {
            steamid = map.get("steamid");
            name = map.get("name");
            activity = map.get("activity");
            kills = Double.valueOf(map.get("kills")).intValue();
            deaths = Double.valueOf(map.get("deaths")).intValue();
            assists = Double.valueOf(map.get("assists")).intValue();
            last_hits = Double.valueOf(map.get("last_hits")).intValue();
            denies = Double.valueOf(map.get("denies")).intValue();
            kill_streak = Double.valueOf(map.get("kill_streak")).intValue();
            team_name = map.get("team_name");
            gold = Double.valueOf(map.get("gold")).intValue();
            gold_reliable = Double.valueOf(map.get("gold_reliable")).intValue();
            gold_unreliable = Double.valueOf(map.get("gold_unreliable")).intValue();
            gpm = Double.valueOf(map.get("gpm")).intValue();
            xpm = Double.valueOf(map.get("xpm")).intValue();
        } else {
            steamid = null;
            name = null;
            activity = null;
            kills = -1;
            deaths = -1;
            assists = -1;
            last_hits = -1;
            denies = -1;
            kill_streak = -1;
            team_name = null;
            gold = -1;
            gold_reliable = -1;
            gold_unreliable = -1;
            gpm = -1;
            xpm = -1;
        }
    }
}
