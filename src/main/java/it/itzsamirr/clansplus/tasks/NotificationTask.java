package it.itzsamirr.clansplus.tasks;

import it.itzsamirr.clansplus.ClansAPI;
import it.itzsamirr.clansplus.ClansPlus;
import it.itzsamirr.clansplus.managers.notify.NotificationManager;
import it.itzsamirr.clansplus.model.generic.Loadable;
import it.itzsamirr.clansplus.model.generic.Reloadable;
import org.bukkit.scheduler.BukkitRunnable;

public class NotificationTask extends BukkitRunnable implements Reloadable, Loadable {
    private static NotificationTask instance = null;
    private final NotificationManager manager;
    private ClansPlus plugin = null;
    private long expire;
    private boolean initialized = false;

    private NotificationTask(ClansPlus plugin, ClansAPI api) {
        this.plugin = plugin;
        expire = plugin.getConfig().getLong("notifications.keep-for");
        this.manager = api.get(NotificationManager.class);
    }

    public static NotificationTask getInstance() {
        return instance;
    }

    public static NotificationTask start(ClansPlus plugin, ClansAPI api){
        if(instance == null || !instance.initialized){
            instance = new NotificationTask(plugin, api);
            instance.initialized = true;
            return instance;
        }
        return null;
    }

    @Override
    public void load(){
        if(!this.initialized) return;
        instance.runTaskTimerAsynchronously(plugin, 1L, 1L);
    }

    @Override
    public void run() {
        manager.getNotifications()
                .forEach((user, query) -> {
                    query.stream().filter(notification -> !notification.isValid(expire))
                            .forEach(manager::removeNotification);
                });
    }

    @Override
    public void reload() {
        expire = plugin.getConfig().getLong("notifications.keep-for");
    }
}
