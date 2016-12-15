package me.infuzion.dota2.integration.nodes;

import java.util.Iterator;
import java.util.Map;

public class Items implements Iterable<Item> {
    public final Item slot0;
    public final Item slot1;
    public final Item slot2;
    public final Item slot3;
    public final Item slot4;
    public final Item slot5;
    public final Item stash0;
    public final Item stash1;
    public final Item stash2;
    public final Item stash3;
    public final Item stash4;
    public final Item stash5;

    public Items(java.util.Map<String, Map<String, Object>> map) {
        if (map != null) {
            slot0 = new Item(map.get("slot0"));
            slot1 = new Item(map.get("slot1"));
            slot2 = new Item(map.get("slot2"));
            slot3 = new Item(map.get("slot3"));
            slot4 = new Item(map.get("slot4"));
            slot5 = new Item(map.get("slot5"));
            stash0 = new Item(map.get("stash0"));
            stash1 = new Item(map.get("stash1"));
            stash2 = new Item(map.get("stash2"));
            stash3 = new Item(map.get("stash3"));
            stash4 = new Item(map.get("stash4"));
            stash5 = new Item(map.get("stash5"));
        } else {
            slot0 = null;
            slot1 = null;
            slot2 = null;
            slot3 = null;
            slot4 = null;
            slot5 = null;
            stash0 = null;
            stash1 = null;
            stash2 = null;
            stash3 = null;
            stash4 = null;
            stash5 = null;
        }
    }

    @Override
    public Iterator<Item> iterator() {
        //noinspection unchecked
        return new Iterator() {
            int cur = 0;

            @Override
            public boolean hasNext() {
                return cur < 12 && nextnoinc() != null;
            }

            @Override
            public Object next() {
                Item toRet = nextnoinc();
                cur++;
                return toRet;
            }
            private Item nextnoinc(){
                switch (cur){
                    case 0:
                        return slot0;
                    case 1:
                        return slot1;
                    case 2:
                        return slot2;
                    case 3:
                        return slot3;
                    case 4:
                        return slot4;
                    case 5:
                        return slot5;
                    case 6:
                        return stash0;
                    case 7:
                        return stash1;
                    case 8:
                        return stash2;
                    case 9:
                        return stash3;
                    case 10:
                        return stash4;
                    case 11:
                        return stash5;
                    default:
                        return null;
                }
            }
        };
    }
}
