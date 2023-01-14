package it.itzsamirr.clansplus.commands.main;

import it.itzsamirr.clansplus.ClansPlus;
import it.itzsamirr.clansplus.annotations.command.SubCommandInfo;
import it.itzsamirr.clansplus.managers.clan.ClanManager;
import it.itzsamirr.clansplus.managers.configuration.lang.LangManager;
import it.itzsamirr.clansplus.model.clan.Clan;
import it.itzsamirr.clansplus.model.command.SubCommand;
import it.itzsamirr.clansplus.utils.ClanUtils;
import org.bukkit.command.CommandSender;

import java.util.Collections;
import java.util.List;
import java.util.stream.Collectors;

@SubCommandInfo(name = "info", aliases = "i")
public class ClansInfoSubCommand extends SubCommand {
    public ClansInfoSubCommand(ClansPlus plugin) {
        super(plugin);
    }

    @Override
    public boolean run(CommandSender sender, String[] args) {
        LangManager langManager = api.get(LangManager.class);
        ClanManager clanManager = api.get(ClanManager.class);
        if(args.length < 1){
            sender.sendMessage(langManager.getLanguage().getString("clan-info-usage"));
            return false; 
        }
        String clanName = args[0];
        Clan clan = clanManager.getClan(clanName);
        if(clan == null){
            sender.sendMessage(langManager.getLanguage().getString("clan-not-found").replace("{name}", clanName));
            return false;
        }
        ClanUtils.getInfo(clan).forEach(sender::sendMessage);
        return false;
    }

    @Override
    public List<String> tabComplete(CommandSender sender, String[] args) {
        if(args.length != 1) return Collections.emptyList();
        return api.get(ClanManager.class).getClans()
                .stream()
                .map(Clan::getName)
                .sorted()
                .filter(clan -> clan.toLowerCase().startsWith(args[0].toLowerCase()))
                .collect(Collectors.toList());
    }
}
