package it.itzsamirr.clansplus.utils;

import it.itzsamirr.clansplus.ClansAPI;
import it.itzsamirr.clansplus.managers.clan.ClanManager;
import it.itzsamirr.clansplus.managers.configuration.lang.LangManager;
import it.itzsamirr.clansplus.model.clan.Clan;
import lombok.experimental.UtilityClass;
import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.OfflinePlayer;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.stream.Collectors;

@UtilityClass
public class ClanUtils {
    private ClanManager clanManager;
    private LangManager langManager;

    static{
        clanManager = ClansAPI.getInstance().getManager(ClanManager.class);
        langManager = ClansAPI.getInstance().getManager(LangManager.class);
    }

    public List<String> getInfo(Clan clan){
        return langManager
                .getLanguage().getList("clan-info-format")
                .stream()
                .map(s -> replaceInfos(s, clan))
                .collect(Collectors.toList());
    }

    public String replaceInfos(String s, Clan clan){
        return s.replace("{name}", clan.getName())
                .replace("{leader}",
                        Color.color("&" + (Bukkit.getOfflinePlayer(clan.getLeader()).isOnline() ? "a" : "4")) + Bukkit.getOfflinePlayer(clan.getLeader()).getName())
                .replace("{coleader}", clan.hasCoLeader() ?
                        Color.color("&" + (Bukkit.getOfflinePlayer(clan.getCoLeader()).isOnline() ? "a" : "4")) + Bukkit.getOfflinePlayer(clan.getCoLeader()).getName() :
                        langManager
                                .getLanguage().getString("no-co-leader"))
                .replace("{members}", clan.hasMembers() ? clan.getMembers()
                        .stream().map(Bukkit::getOfflinePlayer)
                        .map(p -> Color.color("&" + (p.isOnline() ? "a" : "4")) + p.getName())
                        .collect(Collectors.joining(", ")) : langManager.getLanguage().getString("no-member")
                )
                .replace("{balance}", String.valueOf(clan.getBalance()))
                .replace("{size}", String.valueOf(clan.getMembers().size()+(clan.hasCoLeader() ? 1 : 0)))
                .replace("{size_total}", String.valueOf(clan.getMembers().size()+((clan.hasLeader() ? 1 : 0) + (clan.hasCoLeader() ? 1 : 0))));
    }

    public List<String> getList(int page, int pageSize){
        List<String> list = getList();
        return PageUtils.pages(list, page, pageSize, langManager.getLanguage().getString("clan-list-bottom"), langManager.getLanguage().getString("clan-list-top"));
    }

    private List<String> getList(){
        List<Clan> clans = clanManager.getClans();
        final List<String> list = clans.stream().map(clan -> {
            return replaceInfos(langManager.getLanguage().getString("clan-list-format"), clan);
        }).sorted().collect(Collectors.toList());
        return list;
    }
}
