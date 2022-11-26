package it.itzsamirr.clansplus.commands.admin;

import it.itzsamirr.clansplus.ClansPlus;
import it.itzsamirr.clansplus.model.command.Command;
import it.itzsamirr.clansplus.model.command.CommandInfo;
import it.itzsamirr.clansplus.utils.PageUtils;
import org.bukkit.command.CommandSender;


@CommandInfo(name = "aclan", description = "Command for admins of ClansPlus",
    subCommands = {ClansAdminHelpSubCommand.class, ClansAdminReloadSubCommand.class})
public class ClansAdminCommand extends Command {
    public ClansAdminCommand(ClansPlus plugin) {
        super(plugin);
    }

    @Override
    public void execute(CommandSender sender, String[] args) {
        PageUtils.pages("clan-admin-help", 1).forEach(sender::sendMessage);
    }
}
