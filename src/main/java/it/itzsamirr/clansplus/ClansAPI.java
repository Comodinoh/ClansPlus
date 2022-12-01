package it.itzsamirr.clansplus;

import it.itzsamirr.clansplus.managers.Manager;
import it.itzsamirr.clansplus.managers.clan.ClanManager;
import it.itzsamirr.clansplus.managers.clan.JsonClanManager;
import it.itzsamirr.clansplus.managers.clan.YamlClanManager;
import it.itzsamirr.clansplus.managers.command.CommandManager;
import it.itzsamirr.clansplus.managers.configuration.lang.LangManager;
import it.itzsamirr.clansplus.model.configuration.Configuration;
import it.itzsamirr.clansplus.utils.LoggerUtils;
import org.bukkit.configuration.file.FileConfiguration;

import java.util.ArrayList;
import java.util.Collections;
import java.util.HashMap;
import java.util.List;

public class ClansAPI {

    private List<Manager> managers;
    private static ClansPlus plugin;
    private static ClansAPI instance = null;

    private ClansAPI(){
        // Initializing
        init();

        // Loading
        load();
    }

    private void init(){
        this.managers = new ArrayList<>();
        this.managers.add(new LangManager(plugin));
        this.managers.add(new CommandManager(plugin));
        switch (plugin.getConfig().getString("clans.data.type").toLowerCase()){
            case "json":
                this.managers.add(new JsonClanManager(plugin));
                break;
            case "yaml":
                this.managers.add(new YamlClanManager(plugin));
        }
    }

    private void load(){
        managers.forEach(Manager::load);
    }

    protected static void init(ClansPlus plugin){
        LoggerUtils.info("Initializing API...").send();
        ClansAPI.plugin = plugin;
        instance = new ClansAPI();
    }

    public static long reload(){
        LoggerUtils.separators(LoggerUtils.Level.INFO)
                .append(LoggerUtils.Level.INFO, "Reloading...").send();
        long time = System.currentTimeMillis();
        instance.getManager(LangManager.class).reload();
        instance.getManager(ClanManager.class).reload();
        long dt = (System.currentTimeMillis()-time)+(plugin.reload());
        LoggerUtils.info("Reloaded in " + dt + " ms (" + dt/1000.0 + "s)")
                .appendSeparators(LoggerUtils.Level.INFO)
                .send();
        return dt;
    }

    public <T extends Manager> T getManager(Class<T> clazz){
        return clazz.cast(managers.stream().filter(m -> clazz.isInstance(m) || m.getClass().isAssignableFrom(clazz)).findFirst().orElse(null));
    }

    public FileConfiguration getConfig(){
        return plugin.getConfig();
    }

    public static ClansAPI getInstance() {
        return instance;
    }
}
