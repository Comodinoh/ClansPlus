package it.itzsamirr.clansplus.commands.main;

import it.itzsamirr.clansplus.ClansPlus;
import it.itzsamirr.clansplus.model.command.SubCommand;
import it.itzsamirr.clansplus.utils.NumberUtils;
import it.itzsamirr.clansplus.utils.Pageinator;
import org.bukkit.command.CommandSender;

public class ClanHelpSubCommand extends SubCommand {
    public ClanHelpSubCommand(ClansPlus plugin) {
        super(plugin, "help");
    }

    @Override
    public boolean run(CommandSender sender, String[] args) {
        if(args.length < 1){
            Pageinator.pages("clan-help", 1).forEach(sender::sendMessage);
            return false;
        }
        Integer page = NumberUtils.fromString(args[0]);
        if(page == null){
            Pageinator.pages("clan-help", 1).forEach(sender::sendMessage);
            return false;
        }
        Pageinator.pages("clan-help", page).forEach(sender::sendMessage);
        return false;
    }
}
