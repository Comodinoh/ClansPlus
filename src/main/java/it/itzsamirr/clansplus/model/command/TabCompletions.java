package it.itzsamirr.clansplus.model.command;

import lombok.experimental.UtilityClass;
import org.bukkit.Bukkit;
import org.bukkit.entity.Player;

import java.util.List;
import java.util.stream.Collectors;

@UtilityClass
public class TabCompletions {
    public static List<String> onlinePlayers(String arg){
        return Bukkit.getOnlinePlayers().stream().map(Player::getName)
                .filter(name -> name.toLowerCase().startsWith(arg.toLowerCase()))
                .collect(Collectors.toList());
    }
}
