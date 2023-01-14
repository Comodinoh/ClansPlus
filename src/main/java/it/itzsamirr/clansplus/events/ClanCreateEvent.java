package it.itzsamirr.clansplus.events;

import it.itzsamirr.clansplus.model.clan.Clan;

public class ClanCreateEvent extends ClanEvent{
    public ClanCreateEvent(Clan clan) {
        super(clan);
    }
}
