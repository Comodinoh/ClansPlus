package it.itzsamirr.clansplus.model.clan;

import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

public class JsonClan implements Clan {
    private String name;
    private List<UUID> members;
    private UUID leader;
    private UUID coLeader;
    private double balance;

    public JsonClan(String name, UUID leader, double initialBalance){
        this(name, new ArrayList<>(), leader, null, initialBalance);
    }

    public JsonClan(String name, List<UUID> members, UUID leader, UUID coLeader, double balance) {
        this.name = name;
        this.members = members;
        this.leader = leader;
        this.coLeader = coLeader;
        this.balance = balance;
    }

    public boolean hasLeader(){
        return leader != null;
    }

    public boolean hasCoLeader(){
        return coLeader != null;
    }

    @Override
    public boolean hasMembers() {
        return !members.isEmpty();
    }

    public boolean isMember(UUID player){
        return members.contains(player);
    }

    public boolean isLeader(UUID player){
        return hasLeader() && leader.equals(player);
    }

    public boolean isCoLeader(UUID player){
        return hasCoLeader() && coLeader.equals(player);
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public List<UUID> getMembers() {
        return members;
    }

    public void setMembers(List<UUID> members) {
        this.members = members;
    }

    public UUID getLeader() {
        return leader;
    }

    public void setLeader(UUID leader) {
        this.leader = leader;
    }

    public UUID getCoLeader() {
        return coLeader;
    }

    @Override
    public double getBalance() {
        return balance;
    }

    @Override
    public void setBalance(double d) {
        this.balance = d;
    }

    public void setCoLeader(UUID coLeader) {
        this.coLeader = coLeader;
    }

    @Override
    public String toString() {
        return "JsonClan{" +
                "name='" + name + '\'' +
                ", members=" + members +
                ", leader=" + leader +
                ", coLeader=" + coLeader +
                ", balance=" + balance +
                '}';
    }
}
