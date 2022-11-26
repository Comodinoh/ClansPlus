package it.itzsamirr.clansplus.commands.admin;

import it.itzsamirr.clansplus.ClansAPI;
import it.itzsamirr.clansplus.ClansPlus;
import it.itzsamirr.clansplus.managers.configuration.lang.LangManager;
import it.itzsamirr.clansplus.model.command.SubCommand;
import org.bukkit.command.CommandSender;

public class ClansAdminReloadSubCommand extends SubCommand {
    public ClansAdminReloadSubCommand(ClansPlus plugin) {
        super(plugin, "reload", "clansplus.admin.reload", false, "rl");
    }

    @Override
    public boolean run(CommandSender sender, String[] args) {
        sender.sendMessage(api.getManager(LangManager.class).getLanguage().getString("clan-admin-reload").replace("{time}", String.valueOf(ClansAPI.reload())));
        return false;
    }
}
