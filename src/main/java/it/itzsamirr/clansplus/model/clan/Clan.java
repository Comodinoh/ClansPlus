package it.itzsamirr.clansplus.model.clan;

import java.io.Serializable;
import java.util.Date;
import java.util.List;
import java.util.UUID;

public interface Clan extends Serializable, Comparable<Clan> {
    String getName();
    List<UUID> getMembers();
    UUID getLeader();
    UUID getCoLeader();
    Date getCreationDate();
    double getBalance();
    void setCreationDate(Date date);
    void setBalance(double d);
    void setLeader(UUID leader);
    void setCoLeader(UUID coLeader);
    void setMembers(List<UUID> members);
    void setName(String name);
    boolean hasLeader();
    boolean hasCoLeader();
    boolean hasMembers();
    boolean isMember(UUID player);
    boolean isLeader(UUID player);
    boolean isCoLeader(UUID player);
    @Override
    default int compareTo(Clan o){
        return getName().compareToIgnoreCase(o.getName());
    }
}
