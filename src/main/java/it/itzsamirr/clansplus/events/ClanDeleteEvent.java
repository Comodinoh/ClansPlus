package it.itzsamirr.clansplus.events;

import it.itzsamirr.clansplus.model.clan.Clan;
import org.bukkit.event.Cancellable;

import java.util.UUID;

public class ClanDeleteEvent extends ClanEvent implements Cancellable {
    private String who;
    private boolean cancelled = false;
    public ClanDeleteEvent(String who, Clan clan) {
        super(clan);
        this.who = who;
    }

    public String getWho() {
        return who;
    }

    public void setWho(String who) {
        this.who = who;
    }

    @Override
    public void setCancelled(boolean cancelled) {
        this.cancelled = cancelled;
    }

    @Override
    public boolean isCancelled() {
        return cancelled;
    }
}
