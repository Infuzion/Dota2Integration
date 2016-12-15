package me.infuzion.dota2.integration.nodes;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Map;

public class Abilities {
    public final List<Ability> abilityList;

    public Abilities(Map<String, Map<String, Object>> map) {
        ArrayList<Ability> list = new ArrayList<>();

        if (map != null) {
            for (Map.Entry<String, Map<String, Object>> e : map.entrySet()) {
                String name = (String) e.getValue().get("name");
                int level = Double.valueOf(String.valueOf(e.getValue().get("level"))).intValue();
                boolean can_cast = Boolean.valueOf(String.valueOf(e.getValue().get("can_cast")));
                boolean passive = Boolean.valueOf(String.valueOf(e.getValue().get("passive")));
                boolean ability_active = Boolean.valueOf(String.valueOf(e.getValue().get("ability_active")));
//                int cooldown = Double.valueOf(String.valueOf(e.getValue().get("cooldown"))).intValue();
                boolean ultimate = Boolean.valueOf(String.valueOf(e.getValue().get("ultimate")));
                if(name != null) {
                    list.add(new Ability(name, level, can_cast, passive, ability_active, 0, ultimate));
                }

            }
        }
        abilityList = Collections.unmodifiableList(list);
    }
}
