package it.itzsamirr.clansplus.commands.user;

import it.itzsamirr.clansplus.ClansPlus;
import it.itzsamirr.clansplus.annotations.command.CommandInfo;
import it.itzsamirr.clansplus.model.command.Command;
import it.itzsamirr.clansplus.utils.PageUtils;
import org.bukkit.command.CommandSender;

@CommandInfo(name = "clan"
        ,description = "Main Command for ClansPlus"
        ,aliases = {"clanp", "cp", "c"}
,subCommands = {ClanHelpSubCommand.class})
public class ClanCommand extends Command {
    public ClanCommand(ClansPlus plugin) {
        super(plugin);
    }

    @Override
    public void execute(CommandSender sender, String[] args) {
        PageUtils.pages("clan-help", 1).forEach(sender::sendMessage);;
    }
}
