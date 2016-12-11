package me.infuzion.dota2.integration.nodes;

import java.util.Map;

public class Provider {
    public final String name;
    public final double appid;
    public final double version;
    public final long timestamp;

    public Provider(Map<String, String> map) {
        name = map.get("name");
        appid = Double.parseDouble(map.get("appid"));
        version = Double.parseDouble(map.get("version"));
        timestamp = Double.valueOf(map.get("timestamp")).longValue();
    }
}
