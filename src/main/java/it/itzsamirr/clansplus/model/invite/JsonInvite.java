package it.itzsamirr.clansplus.model.invite;

import java.io.Serializable;
import java.util.UUID;

public abstract class JsonInvite implements Invite, Serializable {
    private UUID invited, inviter;


    protected JsonInvite(UUID invited, UUID inviter) {
        this.invited = invited;
        this.inviter = inviter;
    }

    @Override
    public UUID getInviter() {
        return inviter;
    }

    @Override
    public UUID getInvited() {
        return invited;
    }

    @Override
    public void setInvited(UUID invited) {
        this.invited = invited;
    }

    @Override
    public void setInviter(UUID inviter) {
        this.inviter = inviter;
    }
}
