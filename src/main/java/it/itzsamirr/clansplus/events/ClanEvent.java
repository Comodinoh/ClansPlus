package it.itzsamirr.clansplus.events;

import it.itzsamirr.clansplus.model.clan.Clan;
import org.bukkit.event.Event;
import org.bukkit.event.HandlerList;

public abstract class ClanEvent extends Event {
    private static HandlerList handlers = new HandlerList();
    private Clan clan;

    public ClanEvent(Clan clan){
        this.clan = clan;
    }

    public Clan getClan() {
        return clan;
    }

    public void setClan(Clan clan) {
        this.clan = clan;
    }

    public static HandlerList getHandlerList() {
        return handlers;
    }

    @Override
    public HandlerList getHandlers() {
        return handlers;
    }
}
