package it.itzsamirr.clansplus;

import it.itzsamirr.clansplus.commands.user.ClanCommand;
import it.itzsamirr.clansplus.managers.command.CommandManager;
import it.itzsamirr.clansplus.model.clan.YamlClan;
import it.itzsamirr.clansplus.model.invite.YamlInvite;
import it.itzsamirr.clansplus.utils.LoggerUtils;
import org.bukkit.Bukkit;
import org.bukkit.configuration.serialization.ConfigurationSerialization;
import org.bukkit.plugin.java.JavaPlugin;

public final class ClansPlus extends JavaPlugin {

    static{
        ConfigurationSerialization.registerClass(YamlClan.class, "YamlClan");
        ConfigurationSerialization.registerClass(YamlInvite.class, "YamlInvite");
//        SQLUtils.register(SQLClan.class, "SQLClan");
    }

    @Override
    public void onEnable() {
        long time = System.currentTimeMillis();
        saveDefaultConfig();
        LoggerUtils.init(this);
        LoggerUtils.separators(LoggerUtils.Level.INFO).send();
        ClansAPI.init(this);
        ClansAPI.getInstance().get(CommandManager.class).register(ClanCommand.class);
        long dt = System.currentTimeMillis()-time;
        LoggerUtils.info("Enabled in " + dt + " ms (" + dt/1000.0 + "s)")
                .appendSeparators(LoggerUtils.Level.INFO)
                .send();
    }

    public long reload(){
        long time = System.currentTimeMillis();
        reloadConfig();
        saveConfig();
        long dt = System.currentTimeMillis()-time;
        return dt;
    }

    @Override
    public void onDisable() {
        Bukkit.getServer().getScheduler().cancelTasks(this);
    }
}
