package it.itzsamirr.clansplus.commands.main;

import it.itzsamirr.clansplus.ClansPlus;
import it.itzsamirr.clansplus.model.command.Command;
import it.itzsamirr.clansplus.annotations.command.CommandInfo;
import it.itzsamirr.clansplus.utils.PageUtils;
import org.bukkit.command.CommandSender;

@CommandInfo(name = "clans", aliases = {"clan"}, description = "Main command of ClansPlus", subCommands = {ClansCreateSubCommand.class, ClansInfoSubCommand.class, ClanHelpSubCommand.class, ClanListSubCommand.class})
public class ClansCommand extends Command {
    public ClansCommand(ClansPlus plugin) {
        super(plugin);
    }

    @Override
    public void execute(CommandSender sender, String[] args) {
        PageUtils.pages("clan-help", 1).forEach(sender::sendMessage);
    }
}
