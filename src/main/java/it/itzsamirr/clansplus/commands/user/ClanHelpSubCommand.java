package it.itzsamirr.clansplus.commands.user;

import it.itzsamirr.clansplus.ClansPlus;
import it.itzsamirr.clansplus.annotations.command.SubCommandInfo;
import it.itzsamirr.clansplus.model.command.Command;
import it.itzsamirr.clansplus.model.command.SubCommand;
import it.itzsamirr.clansplus.utils.NumberUtils;
import it.itzsamirr.clansplus.utils.PageUtils;
import org.bukkit.command.CommandSender;

@SubCommandInfo(name = "help", aliases = "h")
public class ClanHelpSubCommand extends SubCommand {
    public ClanHelpSubCommand(ClansPlus plugin, Command parentCommand) {
        super(plugin, parentCommand);
    }

    @Override
    public boolean execute(CommandSender sender, String[] args) {
        int page = args.length > 0 ? NumberUtils.fromString(args[0]) : 1;
        PageUtils.pages("clan-help", page).forEach(sender::sendMessage);
        return false;
    }
}
