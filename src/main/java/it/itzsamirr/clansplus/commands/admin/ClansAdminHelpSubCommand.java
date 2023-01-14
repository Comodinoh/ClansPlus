package it.itzsamirr.clansplus.commands.admin;

import it.itzsamirr.clansplus.ClansPlus;
import it.itzsamirr.clansplus.annotations.command.SubCommandInfo;
import it.itzsamirr.clansplus.model.command.SubCommand;
import it.itzsamirr.clansplus.utils.NumberUtils;
import it.itzsamirr.clansplus.utils.PageUtils;
import org.bukkit.command.CommandSender;

@SubCommandInfo(name = "help", aliases = "h")
public class ClansAdminHelpSubCommand extends SubCommand {
    public ClansAdminHelpSubCommand(ClansPlus plugin) {
        super(plugin);
    }

    @Override
    public boolean run(CommandSender sender, String[] args) {
        if(args.length < 1){
            PageUtils.pages("clan-admin-help", 1).forEach(sender::sendMessage);
            return false;
        }
        Integer page = NumberUtils.fromString(args[0]);
        if(page == null){
            PageUtils.pages("clan-admin-help", 1).forEach(sender::sendMessage);
            return false;
        }
        PageUtils.pages("clan-admin-help", page).forEach(sender::sendMessage);
        return false;
    }
}
