package it.itzsamirr.clansplus.model.invite.clan;

import it.itzsamirr.clansplus.model.invite.Invite;
import it.itzsamirr.clansplus.model.invite.YamlInvite;

import java.util.Date;
import java.util.HashMap;
import java.util.Map;
import java.util.UUID;

public final class YamlClanInvite extends YamlInvite implements Invite {
    private String clan;
    private Date timeStamp;

    protected YamlClanInvite(UUID inviter, UUID invited, String clan, long timeStamp) {
        super(inviter, invited);
        this.clan = clan;
        this.timeStamp = new Date(timeStamp);
    }

    @Override
    public Date getTimeStamp() {
        return timeStamp;
    }

    @Override
    public void setTimeStamp(Date timeStamp) {
        this.timeStamp = timeStamp;
    }

    @Override
    public Map<String, Object> serialize() {
        Map<String, Object> map = super.serialize();
        map.put("clan", clan);
        map.put("timeStamp", timeStamp.getTime());
        return map;
    }

    public String getClan() {
        return clan;
    }

    public void setClan(String clan) {
        this.clan = clan;
    }
}
