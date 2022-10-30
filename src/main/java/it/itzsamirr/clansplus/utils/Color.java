package it.itzsamirr.clansplus.utils;

import it.itzsamirr.clansplus.ClansAPI;
import it.itzsamirr.clansplus.managers.configuration.lang.LangManager;
import lombok.experimental.UtilityClass;
import org.bukkit.ChatColor;

import java.util.List;
import java.util.stream.Collectors;

@UtilityClass
public class Color {
    public String color(String s){
        return ChatColor.translateAlternateColorCodes('&', s);
    }

    public String colorP(String s){
        return ChatColor.translateAlternateColorCodes('&', s.replace("{prefix}",
                ClansAPI.getInstance().getManager(LangManager.class)
                        .getLanguage().getConfig().getString("prefix")));
    }

    public List<String> color(List<String> list){
        return list.stream().map(Color::color).collect(Collectors.toList());
    }

    public List<String> colorP(List<String> list){
        return list.stream().map(Color::colorP).collect(Collectors.toList());
    }
}
