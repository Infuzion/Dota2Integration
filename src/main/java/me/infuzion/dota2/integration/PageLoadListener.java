package me.infuzion.dota2.integration;

import me.infuzion.dota2.integration.event.PageLoadEvent;
import me.infuzion.dota2.integration.nodes.Node;

public interface PageLoadListener {
    void pageLoad(PageLoadEvent event, Node node, String page);
}
