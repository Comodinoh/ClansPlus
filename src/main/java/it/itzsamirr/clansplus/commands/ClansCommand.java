package it.itzsamirr.clansplus.commands;

import it.itzsamirr.clansplus.ClansPlus;
import it.itzsamirr.clansplus.model.command.Command;
import it.itzsamirr.clansplus.model.command.CommandInfo;
import it.itzsamirr.clansplus.model.command.SubCommand;
import org.bukkit.command.CommandSender;

@CommandInfo(name = "clans", aliases = "clan", description = "Main command of ClansPlus", subCommands = {ClansCreateSubCommand.class})
public class ClansCommand extends Command {
    public ClansCommand(ClansPlus plugin) {
        super(plugin);
    }

    @Override
    public void execute(CommandSender sender, String[] args) {
        sender.sendMessage("a");
    }
}
