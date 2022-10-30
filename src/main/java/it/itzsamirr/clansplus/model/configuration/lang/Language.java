package it.itzsamirr.clansplus.model.configuration.lang;

import it.itzsamirr.clansplus.ClansPlus;
import it.itzsamirr.clansplus.model.configuration.Configuration;
import it.itzsamirr.clansplus.utils.Color;

import java.util.List;

public final class Language extends Configuration {
    public Language(ClansPlus plugin, String name) {
        super(plugin, name, "lang", true);
    }

    public String getString(String path){
        return Color.colorP(getConfig().getString(path));
    }

    public List<String> getList(String path){
        return Color.colorP(getConfig().getStringList(path));
    }
}
