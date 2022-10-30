package it.itzsamirr.clansplus.model.configuration;

import it.itzsamirr.clansplus.ClansPlus;
import lombok.SneakyThrows;
import org.bukkit.configuration.file.FileConfiguration;
import org.bukkit.configuration.file.YamlConfiguration;

import java.io.File;

public class Configuration {
    private FileConfiguration config;
    private File file;
    private String name;
    private final ClansPlus plugin;
    private boolean loadFromDefault;

    public Configuration(ClansPlus plugin, String name, boolean loadFromDefault){
        this.plugin = plugin;
        StringBuilder sb = new StringBuilder();
        sb.append(!name.endsWith(".yml") ? name + ".yml" : name);
        name = sb.toString();
        this.name = name;
        this.file = new File(plugin.getDataFolder(), name);
        this.loadFromDefault = loadFromDefault;
    }

    public Configuration(ClansPlus plugin, String name, String folderName, boolean loadFromDefault){
        this.plugin = plugin;
        StringBuilder sb = new StringBuilder();
        sb.append(!name.endsWith(".yml") ? name + ".yml" : name);
        name = sb.toString();
        this.name = folderName + File.separator + name;
        File folder = new File(plugin.getDataFolder(), folderName + File.separator);
        if(!folder.exists()){
            folder.mkdirs();
        }
        this.file = new File(folder, name);
        this.loadFromDefault = loadFromDefault;
    }

    public boolean hasLoadFromDefault() {
        return loadFromDefault;
    }

    public File getFile() {
        return file;
    }

    public FileConfiguration getConfig() {
        return config;
    }

    @SneakyThrows
    public synchronized void load(){
        if(!file.exists()){
            if(loadFromDefault) plugin.saveResource(this.name, true);
            else{
                file.createNewFile();
            }
        }
        config = YamlConfiguration.loadConfiguration(file);
    }

    @SneakyThrows
    public synchronized void save(){
        config.save(file);
    }
}
