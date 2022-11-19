package it.itzsamirr.clansplus.utils;

import it.itzsamirr.clansplus.ClansAPI;
import it.itzsamirr.clansplus.managers.configuration.lang.LangManager;
import it.itzsamirr.clansplus.model.clan.Clan;
import lombok.experimental.UtilityClass;
import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.OfflinePlayer;

import java.util.List;
import java.util.stream.Collectors;

@UtilityClass
public class ClanUtils {
    public List<String> getInfo(Clan clan){
        return ClansAPI.getInstance().getManager(LangManager.class)
                .getLanguage().getList("clan-info-format")
                .stream()
                .map(s -> s.replace("{name}", clan.getName())
                        .replace("{leader}",
                                Color.color("&" + (Bukkit.getOfflinePlayer(clan.getLeader()).isOnline() ? "a" : "c")) + Bukkit.getOfflinePlayer(clan.getLeader()).getName())
                        .replace("{coleader}", clan.hasCoLeader() ?
                                Color.color("&" + (Bukkit.getOfflinePlayer(clan.getCoLeader()).isOnline() ? "a" : "c")) + Bukkit.getOfflinePlayer(clan.getCoLeader()).getName() :
                                ClansAPI.getInstance().getManager(LangManager.class)
                                        .getLanguage().getString("no-co-leader"))
                        .replace("{members}", clan.getMembers()
                                .stream().map(Bukkit::getOfflinePlayer)
                                .map(p -> Color.color("&" + (p.isOnline() ? "a" : "c")) + p.getName())
                                .collect(Collectors.joining(", "))
                        )
                        .replace("{balance}", String.valueOf(clan.getBalance())))
                .collect(Collectors.toList());
    }
}
