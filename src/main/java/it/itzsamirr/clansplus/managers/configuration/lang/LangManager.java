package it.itzsamirr.clansplus.managers.configuration.lang;

import it.itzsamirr.clansplus.ClansPlus;
import it.itzsamirr.clansplus.managers.Manager;
import it.itzsamirr.clansplus.model.configuration.Configuration;
import it.itzsamirr.clansplus.model.configuration.lang.Language;
import it.itzsamirr.clansplus.utils.LoggerUtils;

import java.io.File;
import java.util.HashMap;

public final class LangManager implements Manager {
    private final ClansPlus plugin;
    private HashMap<String, Language> loadedLanguages = new HashMap<>();
    private String selectedLocale;

    public LangManager(ClansPlus plugin) {
        this.plugin = plugin;
    }

    public Language load(String locale){
        Language lang = new Language(plugin, locale.toLowerCase());
        lang.load();
        loadedLanguages.put(locale.toLowerCase(), lang);
        LoggerUtils.info("Loaded lang " + locale.toLowerCase()).send();
        return lang;
    }

    public HashMap<String, Language> getLoadedLanguages() {
        return loadedLanguages;
    }

    public Language getLanguage(String locale){
        return loadedLanguages.getOrDefault(locale, null);
    }

    public Language getLanguage(){
        return getLanguage(selectedLocale);
    }

    public void load(){
        LoggerUtils.info("Loading langs...").send();
        File folder = new File(plugin.getDataFolder(), "lang");
        this.selectedLocale = plugin.getConfig().getString("lang");
        if(!folder.exists()){
            load(selectedLocale);
            return;
        }
        File[] files = folder.listFiles(File::isFile);
        if(files == null){
            load(selectedLocale);
            return;
        }
        for(File file : files){
            String n = file.getName().substring(0, file.getName().length()-4);
            if(n.equalsIgnoreCase(selectedLocale)) continue;
            load(n);
        }
        load(selectedLocale);
    }

    public void reload(){
        loadedLanguages.values().forEach(Language::load);
        save();
    }

    public void save(){
        loadedLanguages.values().forEach(Configuration::save);
    }
}
