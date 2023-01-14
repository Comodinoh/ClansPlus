package it.itzsamirr.clansplus.model.clan;

import it.itzsamirr.clansplus.ClansAPI;
import org.bukkit.configuration.serialization.ConfigurationSerializable;
import org.bukkit.configuration.serialization.SerializableAs;

import java.text.DateFormat;
import java.time.Instant;
import java.time.format.DateTimeFormatter;
import java.util.*;
import java.util.stream.Collectors;

@SerializableAs("YamlClan")
public class YamlClan implements Clan, ConfigurationSerializable {
    private final ClansAPI api;
    private String name;
    private UUID leader;
    private Date creationDate;
    private List<UUID> members = new ArrayList<>();
    private UUID coLeader = null;
    private double balance;

    public YamlClan(Map<String, Object> map) {
        this.api = ClansAPI.getInstance();
        this.name = (String) map.get("name");
        String sLeader = (String)map.get("leader");
        String sCoLeader = (String)map.get("co-leader");
        this.leader =  sLeader != null ? UUID.fromString(sLeader) : null;
        this.coLeader = sCoLeader != null ? UUID.fromString(sCoLeader) : null;
        this.members = ((List<String>)map.get("members")).stream().map(UUID::fromString).collect(Collectors.toList());
        this.balance = (Double) map.get("balance");
        this.creationDate = Date.from(Instant.parse((String)map.get("creation-date")));
    }

    public YamlClan(String name, UUID leader){
        this.api = ClansAPI.getInstance();
        this.name = name;
        this.leader = leader;
        this.balance = api.getConfig().getDouble("clans.initial-balance");
        this.creationDate = Date.from(Instant.now());
    }

    @Override
    public String getName() {
        return this.name;
    }

    @Override
    public List<UUID> getMembers() {
        return this.members;
    }

    @Override
    public UUID getLeader() {
        return this.leader;
    }

    @Override
    public UUID getCoLeader() {
        return this.coLeader;
    }

    @Override
    public Date getCreationDate() {
        return this.creationDate;
    }

    @Override
    public double getBalance() {
        return this.balance;
    }

    @Override
    public void setCreationDate(Date date) {
        this.creationDate = date;
    }

    @Override
    public void setBalance(double d) {
        this.balance = d;
    }

    @Override
    public void setLeader(UUID leader) {
        this.leader = leader;
    }

    @Override
    public void setCoLeader(UUID coLeader) {
        this.coLeader = coLeader;
    }

    @Override
    public void setMembers(List<UUID> members) {
        this.members = new ArrayList<>(members);
    }

    @Override
    public void setName(String name) {
        this.name = name;
    }

    @Override
    public boolean hasLeader() {
        return leader != null;
    }

    @Override
    public boolean hasCoLeader() {
        return coLeader != null;
    }

    @Override
    public boolean hasMembers() {
        return members != null && !members.isEmpty();
    }

    @Override
    public boolean isMember(UUID player) {
        return hasMembers() && members.contains(player);
    }

    @Override
    public boolean isLeader(UUID player) {
        return hasLeader() && leader.equals(player);
    }

    @Override
    public boolean isCoLeader(UUID player) {
        return hasCoLeader() && coLeader.equals(player);
    }

    @Override
    public Map<String, Object> serialize() {
        LinkedHashMap<String, Object> map = new LinkedHashMap<>();
        map.put("name", name);
        map.put("leader", hasLeader() ? leader.toString() : null);
        map.put("co-leader", hasCoLeader() ? coLeader.toString() : null);
        map.put("members", hasMembers() ? members.stream().map(UUID::toString).collect(Collectors.toList()) : new ArrayList<String>());
        map.put("balance", balance);
        map.put("creation-date", DateTimeFormatter.ISO_DATE_TIME.format(creationDate.toInstant()));
        return map;
    }
}
