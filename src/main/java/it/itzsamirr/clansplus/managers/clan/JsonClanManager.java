package it.itzsamirr.clansplus.managers.clan;

import com.google.common.reflect.TypeToken;
import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.google.gson.stream.JsonReader;
import it.itzsamirr.clansplus.ClansPlus;
import it.itzsamirr.clansplus.events.ClanDeleteEvent;
import it.itzsamirr.clansplus.model.clan.Clan;
import it.itzsamirr.clansplus.model.clan.JsonClan;
import it.itzsamirr.clansplus.utils.ArrayUtils;
import it.itzsamirr.clansplus.utils.LoggerUtils;
import lombok.SneakyThrows;

import java.io.File;
import java.io.FileReader;
import java.io.FileWriter;
import java.lang.reflect.Array;
import java.util.*;

public class JsonClanManager implements ClanManager{
    private ArrayList<JsonClan> clans = new ArrayList<>();
    private final ClansPlus plugin;
    private File file;
    private Gson gson;

    public JsonClanManager(ClansPlus plugin) {
        this.plugin = plugin;
        this.gson = new GsonBuilder()
                .setLenient()
                .serializeNulls()
                .setPrettyPrinting()
                .create();
        this.file = new File(plugin.getDataFolder(),  plugin.getConfig().getString("clans.data.json.file")+ ".json");
    }

    public boolean removeClan(String who, Clan clan){
        if(!(clan instanceof JsonClan)) return false;
        ClanDeleteEvent event = new ClanDeleteEvent(who, clan);
        plugin.getServer().getPluginManager().callEvent(event);
        if(event.isCancelled()) return false;
        clans.remove(clan);
        save();
        return true;
    }

    public JsonClan addClan(String name, UUID leader){
        JsonClan clan;
        clans.add(clan = new JsonClan(name, leader, plugin.getConfig().getDouble("initial-balance")));
        save();
        return clan;
    }

    @Override
    public Clan getClan(String name) {
        return clans.parallelStream()
                .filter(c -> c.getName().equalsIgnoreCase(name))
                .findFirst().orElse(null);
    }

    @Override
    public Clan getClan(UUID player) {
        return clans.parallelStream()
                .filter(c -> c.isMember(player) ||
                        c.isLeader(player) ||
                        c.isCoLeader(player))
                .findFirst()
                .orElse(null);
    }

    @Override
    public ArrayList<Clan> getClans() {
        return new ArrayList<>(clans);
    }

    @SneakyThrows
    public synchronized void save(){
        LoggerUtils.debug("Saving clans...").send();
        if(!file.exists()) {
            file.createNewFile();
        }
        FileWriter writer = new FileWriter(file, false);
        Map<String, ArrayList<JsonClan>> clanMap = new HashMap<String, ArrayList<JsonClan>>(){{put("clans", clans);}};
        writer.write(gson.toJson(clanMap, new TypeToken<Map<String, ArrayList<JsonClan>>>(){}.getType()));
        writer.flush();
        writer.close();
        LoggerUtils.debug("Saved " + clans.size() + " clans").send();
    }

    @SneakyThrows
    @Override
    public void reload() {
        LoggerUtils.info("Reloading clans...").send();
        if(!file.exists()){
            file.createNewFile();
            return;
        }
        FileReader reader = new FileReader(file);
        Map<String, ArrayList<JsonClan>> clanMap = gson.fromJson(reader, new TypeToken<Map<String, ArrayList<JsonClan>>>(){}.getType());
        if(clanMap == null) return;
        ArrayList<JsonClan> clans = clanMap.getOrDefault("clans", null);
        if(clans == null) return;
        this.clans = new ArrayList<>(clans);
        reader.close();
        LoggerUtils.debug("Reloaded " + this.clans.size() + " clans")
                .append(LoggerUtils.Level.DEBUG, this.clans.toString())
                .send();
    }

    @SneakyThrows
    public synchronized void load(){
        LoggerUtils.info("Loading clans...").send();
        if(!file.exists()){
            file.createNewFile();
            return;
        }
        FileReader reader = new FileReader(file);
        Map<String, ArrayList<JsonClan>> clanMap = gson.fromJson(reader, new TypeToken<Map<String, ArrayList<JsonClan>>>(){}.getType());
        if(clanMap == null) return;
        ArrayList<JsonClan> clans = clanMap.getOrDefault("clans", null);
        if(clans == null) return;
        this.clans = new ArrayList<>(clans);
        reader.close();
        LoggerUtils.debug("Loaded " + this.clans.size() + " clans")
                .append(LoggerUtils.Level.DEBUG, this.clans.toString())
                .send();
        LoggerUtils.debug(this.clans.toString()).send();
    }
}
