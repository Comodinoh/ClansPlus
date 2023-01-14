package it.itzsamirr.clansplus.model.invite.clan;

import it.itzsamirr.clansplus.model.invite.JsonInvite;

import java.util.Date;
import java.util.UUID;

public final class JsonClanInvite extends JsonInvite implements ClanInvite {
    private String clan;
    private Date timeStamp;

    protected JsonClanInvite(UUID invited, UUID inviter, String clan, Date timeStamp) {
        super(invited, inviter);
        this.clan = clan;
        this.timeStamp = timeStamp;
    }

    public Date getTimeStamp() {
        return timeStamp;
    }

    public void setTimeStamp(Date timeStamp) {
        this.timeStamp = timeStamp;
    }

    @Override
    public String getClan() {
        return clan;
    }

    @Override
    public void setClan(String clan) {
        this.clan = clan;
    }
}
