package it.itzsamirr.clansplus.managers.clan;

import it.itzsamirr.clansplus.ClansPlus;
import it.itzsamirr.clansplus.events.ClanDeleteEvent;
import it.itzsamirr.clansplus.model.clan.Clan;
import it.itzsamirr.clansplus.model.clan.YamlClan;
import it.itzsamirr.clansplus.model.configuration.Configuration;
import it.itzsamirr.clansplus.utils.LoggerUtils;

import java.util.ArrayList;
import java.util.UUID;
import java.util.stream.Collectors;

public class YamlClanManager implements ClanManager{
    private final ClansPlus plugin;
    private final Configuration configuration;
    private ArrayList<YamlClan> clans = new ArrayList<>();

    public YamlClanManager(ClansPlus plugin) {
        this.plugin = plugin;
        this.configuration = new Configuration(plugin, plugin.getConfig().getString("clans.data.yaml.file"), false);
    }

    @Override
    public void load() {
        LoggerUtils.info("Loading clans...").send();
        this.configuration.load();
        clans = new ArrayList<>();
        if(!configuration.getConfig().contains("clans")){
            return;
        }
        clans = configuration.getConfig().getConfigurationSection("clans")
                .getKeys(false).stream().map(key -> (YamlClan) configuration.getConfig().get("clans." + key)).collect(Collectors.toCollection(ArrayList::new));
        LoggerUtils.debug("Loaded " + clans.size() + " clans").send();
        LoggerUtils.debug(this.clans.toString()).send();
    }

    @Override
    public boolean removeClan(String who, Clan clan) {
        if(!(clan instanceof YamlClan)) return false;
        ClanDeleteEvent event = new ClanDeleteEvent(who, clan);
        plugin.getServer().getPluginManager().callEvent(event);
        if(event.isCancelled()) return false;
        this.clans.remove(clan);
        save();
        return true;
    }

    @Override
    public Clan addClan(String name, UUID leader) {
        YamlClan clan = new YamlClan(name, leader);
        clans.add(clan);
        save();
        return clan;
    }

    @Override
    public Clan getClan(String name) {
        return clans.parallelStream()
                .filter(clan -> clan.getName().equalsIgnoreCase(name))
                .findFirst()
                .orElse(null);
    }

    @Override
    public Clan getClan(UUID player) {
       return clans.parallelStream()
                .filter(c -> c.isMember(player) || c.isLeader(player) ||
                       c.isCoLeader(player))
                .findFirst()
                .orElse(null);
    }



    @Override
    public ArrayList<Clan> getClans() {
        return new ArrayList<>(clans);
    }

    @Override
    public void save() {
        LoggerUtils.debug("Saving clans...").send();
        clans.forEach(clan -> {
            configuration.getConfig().set("clans." + clan.getName(), clan);
        });
        configuration.save();
        LoggerUtils.debug("Saved " + clans.size() + " clans").send();
    }

    @Override
    public void reload() {
        LoggerUtils.info("Reloading clans...").send();
        this.configuration.load();
        clans = new ArrayList<>();
        if(!configuration.getConfig().isSet("clans")){
            return;
        }
        clans = configuration.getConfig().getConfigurationSection("clans")
                .getKeys(false).stream().map(key -> (YamlClan) configuration.getConfig().get("clans." + key)).collect(Collectors.toCollection(ArrayList::new));
        LoggerUtils.debug("Reloaded " + clans.size() + " clans").send();
        LoggerUtils.debug(this.clans.toString()).send();
    }
}
