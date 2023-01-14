package it.itzsamirr.clansplus.managers.invite;

import it.itzsamirr.clansplus.model.clan.Clan;
import it.itzsamirr.clansplus.model.invite.Invite;

import java.util.List;
import java.util.UUID;

public interface InviteManager<T extends Invite> {
    List<T> getInvitesFor(UUID uuid);
    T getInviteFor(UUID uuid, Clan clan);
    List<T> getInvitedFrom(UUID uuid);
    T createInvite(UUID inviter, UUID invited, Clan clan);

}
