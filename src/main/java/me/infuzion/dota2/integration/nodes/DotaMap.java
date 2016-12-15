package me.infuzion.dota2.integration.nodes;

public class DotaMap {
    public final String name;
    public final long matchid;
    public final int game_time;
    public final int clock_time;
    public final boolean daytime;
    public final boolean nightstalker_night;
    public final String game_state_string;
    public final Gamestate game_state;
    public final String win_team;
    public final String custom_game_name;
    public final int ward_purchase_cooldown;

    public DotaMap(java.util.Map<String, String> map) {
        if (map != null && map.containsKey("name") && map.get("name") != null) {
            name = map.get("name");
            matchid = Double.valueOf(map.get("matchid")).intValue();
            game_time = Double.valueOf(map.get("game_time")).intValue();
            clock_time = Double.valueOf(map.get("clock_time")).intValue();
            daytime = Boolean.valueOf(map.get("daytime"));
            nightstalker_night = Boolean.valueOf(map.get("nightstalker_night"));
            game_state_string = map.get("game_state");
            game_state = Gamestate.valueOf(map.get("game_state"));
            win_team = map.get("win_team");
            custom_game_name = map.get("customgamename");
            if (map.containsKey("ward_purchase_cooldown")) {
                ward_purchase_cooldown = Double.valueOf(map.get("ward_purchase_cooldown")).intValue();
            } else {
                ward_purchase_cooldown = -1;
            }
        } else {
            name = null;
            matchid = -1;
            game_time = -1;
            clock_time = -1;
            daytime = false;
            nightstalker_night = false;
            game_state_string = null;
            win_team = null;
            custom_game_name = null;
            ward_purchase_cooldown = -1;
            game_state = Gamestate.Undefined;
        }
    }
}
