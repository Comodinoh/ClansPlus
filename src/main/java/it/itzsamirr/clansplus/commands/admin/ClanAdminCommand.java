package it.itzsamirr.clansplus.commands.admin;

import it.itzsamirr.clansplus.ClansAPI;
import it.itzsamirr.clansplus.ClansPlus;
import it.itzsamirr.clansplus.managers.configuration.lang.LangManager;
import it.itzsamirr.clansplus.model.command.Command;
import it.itzsamirr.clansplus.model.command.CommandInfo;
import org.bukkit.command.CommandSender;


@CommandInfo(name = "clanadmin", aliases = {"adminclan", "aclan"})
public class ClanAdminCommand extends Command {
    public ClanAdminCommand(ClansPlus plugin) {
        super(plugin);
    }

    @Override
    public void execute(CommandSender sender, String[] args) {
        ClansAPI.getInstance().getManager(LangManager.class).getLanguage().getList("clan-admin-help")
                .forEach(sender::sendMessage);
    }
}
