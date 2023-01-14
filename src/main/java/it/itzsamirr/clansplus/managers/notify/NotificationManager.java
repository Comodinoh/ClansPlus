package it.itzsamirr.clansplus.managers.notify;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import it.itzsamirr.clansplus.ClansPlus;
import it.itzsamirr.clansplus.managers.Manager;
import it.itzsamirr.clansplus.model.generic.Reloadable;
import it.itzsamirr.clansplus.model.notify.Notification;
import it.itzsamirr.clansplus.utils.LoggerUtils;
import lombok.SneakyThrows;

import java.io.File;
import java.io.FileReader;
import java.io.FileWriter;
import java.util.HashMap;
import java.util.UUID;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.ConcurrentLinkedQueue;
import java.util.concurrent.ConcurrentMap;

public final class NotificationManager implements Manager, Reloadable {
    private ConcurrentMap<UUID, ConcurrentLinkedQueue<Notification>> notifications = new ConcurrentHashMap<>();
    private Gson gson;
    private File file;
    private final ClansPlus plugin;

    public NotificationManager(ClansPlus plugin) {
        this.plugin = plugin;
        this.file = new File(
                plugin.getDataFolder(),
                plugin.getConfig()
                        .getString("notifications.file") + ".json"
        );
        this.gson = new GsonBuilder().setPrettyPrinting()
                .setLenient().serializeNulls()
                .create();
    }

    public ConcurrentLinkedQueue<Notification> getNotifications(UUID player){
        return notifications.getOrDefault(player, null);
    }

    public void addNotificaiton(Notification notification){
        UUID player = notification.getPlayer();
        if(notifications.containsKey(player)){
            ConcurrentLinkedQueue<Notification> queue = notifications.get(player);
            queue.add(notification);
            notifications.replace(player, queue);
            return;
        }
        ConcurrentLinkedQueue<Notification> queue = new ConcurrentLinkedQueue<>();
        queue.add(notification);
        notifications.put(player, queue);
        save();
    }

    @SneakyThrows
    public void reload(){
        LoggerUtils.info("Reloading notifications...").send();
        if(!file.exists()){
            file.createNewFile();
            return;
        }
        FileReader reader = new FileReader(file);
        HashMap<String, ConcurrentHashMap<String, ConcurrentLinkedQueue<Notification>>> notificationMap = gson.fromJson(reader, HashMap.class);
        if(notificationMap == null) return;
        ConcurrentHashMap<String, ConcurrentLinkedQueue<Notification>> rawNotifications = notificationMap.getOrDefault("notifications", null);
        if(rawNotifications == null) return;
        this.notifications.clear();
        rawNotifications.forEach((key, value) -> this.notifications.put(UUID.fromString(key), value));
        reader.close();
        LoggerUtils.debug("Reloaded " + this.notifications.size() + " notifications")
                .append(LoggerUtils.Level.DEBUG, this.notifications.toString())
                .send();
    }

    public ConcurrentMap<UUID, ConcurrentLinkedQueue<Notification>> getNotifications() {
        return notifications;
    }

    public void removeNotification(Notification notification){
        notifications.remove(notification.getPlayer());
        save();
    }

    public void clearNotifications(UUID player){
        notifications.remove(player);
        save();
    }

    public void clearNotifications(){
        notifications.clear();
        save();
    }

    @SneakyThrows
    @Override
    public void load() {
        LoggerUtils.info("Loading notifications...").send();
        if(!file.exists()){
            file.createNewFile();
            return;
        }
        FileReader reader = new FileReader(file);
        HashMap<String, ConcurrentHashMap<String, ConcurrentLinkedQueue<Notification>>> notificationMap = gson.fromJson(reader, HashMap.class);
        if(notificationMap == null) return;
        ConcurrentHashMap<String, ConcurrentLinkedQueue<Notification>> rawNotifications = notificationMap.getOrDefault("notifications", null);
        if(rawNotifications == null) return;
        this.notifications.clear();
        rawNotifications.forEach((key, value) -> this.notifications.put(UUID.fromString(key), value));
        reader.close();
        LoggerUtils.debug("Loaded " + this.notifications.size() + " notifications")
                .append(LoggerUtils.Level.DEBUG, this.notifications.toString())
                .send();
    }

    @SneakyThrows
    public void save(){
        LoggerUtils.debug("Saving notifications...").send();
        if(!file.exists()) {
            file.createNewFile();
        }
        FileWriter writer = new FileWriter(file, false);
        ConcurrentHashMap<String, ConcurrentLinkedQueue<Notification>> nots = new ConcurrentHashMap<>();
        notifications.forEach((key, value) -> nots.put(key.toString(), value));
        HashMap<String, ConcurrentHashMap<String, ConcurrentLinkedQueue<Notification>>> notifications = new HashMap<String, ConcurrentHashMap<String, ConcurrentLinkedQueue<Notification>>>(){{put("notifications", nots);}};
        writer.write(gson.toJson(notifications, HashMap.class));
        writer.flush();
        writer.close();
        LoggerUtils.debug("Saved " + this.notifications.size() + " notifications").send();
    }
}
