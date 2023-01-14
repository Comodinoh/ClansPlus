package it.itzsamirr.clansplus.model.invite;

import java.util.Date;
import java.util.UUID;

public interface Invite {
    UUID getInviter();
    UUID getInvited();
    void setInviter(UUID inviter);
    void setInvited(UUID invited);
    Date getTimeStamp();
    void setTimeStamp(Date date);
}
