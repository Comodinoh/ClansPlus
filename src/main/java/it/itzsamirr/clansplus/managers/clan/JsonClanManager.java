package it.itzsamirr.clansplus.managers.clan;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.google.gson.stream.JsonReader;
import it.itzsamirr.clansplus.ClansPlus;
import it.itzsamirr.clansplus.model.clan.Clan;
import it.itzsamirr.clansplus.model.clan.JsonClan;
import it.itzsamirr.clansplus.utils.LoggerUtils;
import lombok.SneakyThrows;

import java.io.File;
import java.io.FileReader;
import java.io.FileWriter;
import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

public class JsonClanManager implements ClanManager{
    private List<Clan> clans = new ArrayList<>();
    private final ClansPlus plugin;
    private File file;
    private Gson gson;

    public JsonClanManager(ClansPlus plugin) {
        this.plugin = plugin;
        this.gson = new GsonBuilder()
                .setPrettyPrinting()
                .create();
        this.file = new File(plugin.getDataFolder(),  plugin.getConfig().getString("clans.data.json.file")+ ".json");
    }

    public void removeClan(Clan clan){
        clans.remove(clan);
        save();
    }

    public JsonClan addClan(String name, UUID leader){
        JsonClan clan;
        clans.add(clan = new JsonClan(name, leader, plugin.getConfig().getDouble("initial-balance")));
        save();
        return clan;
    }

    @Override
    public Clan getClan(String name) {
        return clans.stream()
                .filter(c -> c.getName().equalsIgnoreCase(name))
                .findFirst().orElse(null);
    }

    @Override
    public Clan getClan(UUID player) {
        return clans.stream()
                .filter(c -> c.isMember(player) ||
                        c.isLeader(player) ||
                        c.isCoLeader(player))
                .findFirst()
                .orElse(null);
    }

    @Override
    public List<Clan> getClans() {
        return this.clans;
    }

    @SneakyThrows
    public synchronized void save(){
        LoggerUtils.info("Saving clans...").send();
        if(!file.exists()) {
            file.createNewFile();
        }
        FileWriter writer = new FileWriter(file, false);
        writer.write(gson.toJson(clans, List.class));
        writer.flush();
        writer.close();
        LoggerUtils.debug("Saved " + clans.size() + " clans").send();
    }

    @SneakyThrows
    public synchronized void load(){
        LoggerUtils.info("Loading clans...").send();
        if(!file.exists()){
            file.createNewFile();
            return;
        }
        FileReader reader = new FileReader(file);
        List<JsonClan> clans = gson.fromJson(reader, List.class);
        if(clans == null) return;
        List<Clan> clans2 = new ArrayList<>(clans);
        this.clans.clear();
        this.clans.addAll(clans2);
        reader.close();
        LoggerUtils.debug("Loaded " + this.clans.size() + " clans").send();
    }
}
