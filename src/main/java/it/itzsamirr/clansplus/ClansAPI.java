package it.itzsamirr.clansplus;

import it.itzsamirr.clansplus.managers.clan.JsonClanManager;
import it.itzsamirr.clansplus.managers.clan.YamlClanManager;
import it.itzsamirr.clansplus.managers.command.CommandManager;
import it.itzsamirr.clansplus.managers.configuration.lang.LangManager;
import it.itzsamirr.clansplus.managers.notify.NotificationManager;
import it.itzsamirr.clansplus.model.generic.Loadable;
import it.itzsamirr.clansplus.model.generic.Reloadable;
import it.itzsamirr.clansplus.tasks.NotificationTask;
import it.itzsamirr.clansplus.utils.LoggerUtils;
import org.bukkit.configuration.file.FileConfiguration;

import java.util.ArrayList;
import java.util.List;

public class ClansAPI {
    private List<Reloadable> reloadables;
    private List<Loadable> loadables;

    private static ClansPlus plugin;
    private static ClansAPI instance = null;

    private ClansAPI(){
        // Initializing
        init();

        // Loading
        load();
    }

    private void init(){
        this.reloadables = new ArrayList<>();
        this.loadables = new ArrayList<>();
        add(new LangManager(plugin));
        add(new CommandManager(plugin));
        switch (plugin.getConfig().getString("clans.data.type").toLowerCase()){
            case "json":
                add((Reloadable) new JsonClanManager(plugin));
                break;
            case "yaml":
                add((Reloadable)new YamlClanManager(plugin));
        }
        add((Reloadable)new NotificationManager(plugin));
        add((Reloadable) NotificationTask.start(plugin, this));
    }
    private void add(Reloadable reloadable){
        if(reloadable instanceof Loadable) add((Loadable) reloadable);
        this.reloadables.add(reloadable);
    }

    private void add(Loadable loadable){
        loadables.add(loadable);
    }

    private void load(){
        loadables.forEach(Loadable::load);
    }

    protected static void init(ClansPlus plugin){
        LoggerUtils.info("Initializing API...").send();
        ClansAPI.plugin = plugin;
        instance = new ClansAPI();
    }

    public long reload(){
        LoggerUtils.separators(LoggerUtils.Level.INFO)
                .append(LoggerUtils.Level.INFO, "Reloading...").send();
        long time = System.currentTimeMillis();
        reloadables.forEach(Reloadable::reload);
        long dt = (System.currentTimeMillis()-time)+(plugin.reload());
        LoggerUtils.info("Reloaded in " + dt + " ms (" + dt/1000.0 + "s)")
                .appendSeparators(LoggerUtils.Level.INFO)
                .send();
        return dt;
    }

    public <T extends Loadable> T get(Class<T> clazz){
        return clazz.cast(loadables.stream().filter(m -> clazz.isInstance(m) || m.getClass().isAssignableFrom(clazz)).findFirst().orElse(null));
    }

    public FileConfiguration getConfig(){
        return plugin.getConfig();
    }

    public static ClansAPI getInstance() {
        return instance;
    }
}
