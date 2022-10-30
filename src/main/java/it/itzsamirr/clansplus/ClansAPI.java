package it.itzsamirr.clansplus;

import it.itzsamirr.clansplus.managers.Manager;
import it.itzsamirr.clansplus.managers.clan.ClanManager;
import it.itzsamirr.clansplus.managers.clan.JsonClanManager;
import it.itzsamirr.clansplus.managers.command.CommandManager;
import it.itzsamirr.clansplus.managers.configuration.lang.LangManager;
import it.itzsamirr.clansplus.utils.LoggerUtils;

import java.util.ArrayList;
import java.util.Collections;
import java.util.HashMap;
import java.util.List;

public class ClansAPI {

    private List<Manager> managers = new ArrayList<>();
    private static ClansPlus plugin;
    private static ClansAPI instance = null;

    private ClansAPI(){
        // Initializing
        init();

        // Loading
        load();
    }

    private void init(){
        this.managers.add(new LangManager(plugin));
        this.managers.add(new CommandManager(plugin));
        switch (plugin.getConfig().getString("clans.data.type")){
            case "json":
                this.managers.add(new JsonClanManager(plugin));
                break;
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

    public <T extends Manager> T getManager(Class<T> clazz){
        return clazz.cast(managers.stream().filter(m -> m.getClass().isAssignableFrom(clazz)).findFirst().orElse(null));
    }

    public static ClansAPI getInstance() {
        return instance;
    }
}
