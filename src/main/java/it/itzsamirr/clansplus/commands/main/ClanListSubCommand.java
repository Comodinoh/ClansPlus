package it.itzsamirr.clansplus.commands.main;

import it.itzsamirr.clansplus.ClansPlus;
import it.itzsamirr.clansplus.annotations.command.SubCommandInfo;
import it.itzsamirr.clansplus.managers.configuration.lang.LangManager;
import it.itzsamirr.clansplus.model.command.SubCommand;
import it.itzsamirr.clansplus.utils.ClanUtils;
import it.itzsamirr.clansplus.utils.NumberUtils;
import org.bukkit.command.CommandSender;

import java.util.Arrays;

@SubCommandInfo(name = "list", aliases = "l")
public class ClanListSubCommand extends SubCommand {
    public ClanListSubCommand(ClansPlus plugin) {
        super(plugin);
    }

    @Override
    public boolean run(CommandSender sender, String[] args) {
        if(args.length < 1){
            ClanUtils.getList(1, plugin.getConfig().getInt("clans.clan-list-max-pages")).forEach(sender::sendMessage);
            return false;
        }
        Integer page = NumberUtils.fromString(args[0]);
        if(page == null){
            ClanUtils.getList(1, plugin.getConfig().getInt("clans.clan-list-max-pages")).forEach(sender::sendMessage);
            return false;
        }
        ClanUtils.getList(page, plugin.getConfig().getInt("clans.clan-list-max-pages")).forEach(sender::sendMessage);
        return false;
    }
}
