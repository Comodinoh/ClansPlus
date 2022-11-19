package it.itzsamirr.clansplus.model.command;

import lombok.experimental.UtilityClass;
import org.bukkit.Bukkit;
import org.bukkit.entity.Player;

import java.util.Arrays;
import java.util.List;
import java.util.stream.Collectors;
import java.util.stream.Stream;

@UtilityClass
public class TabCompletions {
    public static List<String> onlinePlayers(String arg){
        return Bukkit.getOnlinePlayers().stream().map(Player::getName)
                .filter(name -> name.toLowerCase().startsWith(arg.toLowerCase()))
                .sorted()
                .collect(Collectors.toList());
    }

    public static List<String> tab(String arg, String... arr){
        Arrays.sort(arr);
        return Stream.of(arr)
                .filter(s -> s.toLowerCase().startsWith(arg.toLowerCase()))
                .sorted()
                .collect(Collectors.toList());
    }
}
