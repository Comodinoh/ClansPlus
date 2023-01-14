package it.itzsamirr.clansplus.commands.admin;

import it.itzsamirr.clansplus.ClansAPI;
import it.itzsamirr.clansplus.ClansPlus;
import it.itzsamirr.clansplus.annotations.command.SubCommandInfo;
import it.itzsamirr.clansplus.managers.configuration.lang.LangManager;
import it.itzsamirr.clansplus.model.command.SubCommand;
import org.bukkit.command.CommandSender;

@SubCommandInfo(name = "reload", aliases = "rl", permission = "clansplus.admin.reload")
public class ClansAdminReloadSubCommand extends SubCommand {
    public ClansAdminReloadSubCommand(ClansPlus plugin) {
        super(plugin);
    }

    @Override
    public boolean run(CommandSender sender, String[] args) {
        sender.sendMessage(api.get(LangManager.class).getLanguage().getString("clan-admin-reload").replace("{time}", String.valueOf(ClansAPI.getInstance().reload())));
        return false;
    }
}
