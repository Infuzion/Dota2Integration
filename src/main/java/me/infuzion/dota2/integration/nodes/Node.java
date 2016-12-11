package me.infuzion.dota2.integration.nodes;

import java.util.HashMap;
import java.util.Map;

public class Node {
    //    public final Auth auth;
    public final Hero hero;
    public final Player player;
    public final Provider provider;

    public Node(Map<String, Map<String, Object>> map) {
        Map<String, Map<String, String>> newMap = new HashMap<>();
        for (Map.Entry<String, Map<String, Object>> entry : map.entrySet()) {
            newMap.put(entry.getKey(), new HashMap<>());
            for(Map.Entry<String, Object> entry1 : entry.getValue().entrySet()){
                Map tempMap = newMap.get(entry.getKey());
                tempMap.put(entry1.getKey(), entry1.getValue().toString());
            }
        }
        provider = new Provider(newMap.get("provider"));
        hero = new Hero(newMap.get("hero"));
        player = new Player(newMap.get("player"));
    }
}
