package me.infuzion.dota2.integration.nodes;


import java.util.Map;

public class Item {
    public final String name;
    public final boolean can_cast;
    public final int cooldown;
    public final boolean passive;
    public final int charges;

    public Item(Map<String, Object> map) {
        name = String.valueOf(map.get("name"));
        if(!name.equalsIgnoreCase("empty")) {
            passive = Boolean.parseBoolean(String.valueOf(map.get("passive")));
            can_cast = !passive && Boolean.parseBoolean(String.valueOf(map.get("can_cast")));
            if (can_cast) {
                cooldown = Double.valueOf(String.valueOf(map.get("cooldown"))).intValue();
            } else {
                cooldown = -1;
            }

            Double temp;
            try {
                temp = Double.valueOf(String.valueOf(map.get("charges")));
            } catch (NumberFormatException e) {
                temp = -1D;
            }
            charges = temp.intValue();
        } else {
            can_cast = false;
            cooldown = -1;
            passive = false;
            charges = -1;
        }
    }
}
