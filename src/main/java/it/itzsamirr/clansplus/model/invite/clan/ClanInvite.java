package it.itzsamirr.clansplus.model.invite.clan;

import it.itzsamirr.clansplus.model.clan.Clan;
import it.itzsamirr.clansplus.model.invite.Invite;

import java.util.UUID;

public interface ClanInvite extends Invite{
    String getClan();
    void setClan(String clan);
}
