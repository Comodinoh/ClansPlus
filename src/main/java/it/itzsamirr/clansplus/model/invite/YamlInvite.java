package it.itzsamirr.clansplus.model.invite;

import org.bukkit.configuration.serialization.ConfigurationSerializable;

import java.util.HashMap;
import java.util.Map;
import java.util.UUID;

public abstract class YamlInvite implements Invite, ConfigurationSerializable {
    private UUID inviter, invited;


    protected YamlInvite(UUID inviter, UUID invited) {
        this.inviter = inviter;
        this.invited = invited;
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

    @Override
    public Map<String, Object> serialize() {
        Map<String, Object> map = new HashMap<>();
        map.put("invited", invited.toString());
        map.put("inviter", inviter.toString());
        return map;
    }
}
