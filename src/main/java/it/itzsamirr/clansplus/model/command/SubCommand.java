package it.itzsamirr.clansplus.model.command;

import it.itzsamirr.clansplus.ClansAPI;
import it.itzsamirr.clansplus.ClansPlus;
import lombok.Getter;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

public abstract class SubCommand {
    protected final ClansAPI api;
    protected final ClansPlus plugin;
    @Getter  private String name;
    @Getter private boolean onlyPlayers;
    @Getter private String permission;
    @Getter private String[] aliases;

    public SubCommand(ClansPlus plugin, String name, String permission, boolean onlyPlayers, String... aliases) {
        this.plugin = plugin;
        this.api = ClansAPI.getInstance();
        this.name = name;
        this.onlyPlayers = onlyPlayers;
        this.permission = permission;
        this.aliases = aliases;
    }

    public SubCommand(ClansPlus plugin, String name, String permission, String... aliases){
        this(plugin, name, permission, false, aliases);
    }

    public SubCommand(ClansPlus plugin, String name, String... aliases){
        this(plugin, name, "", aliases);
    }

    public boolean run(CommandSender sender, String[] args){
        return false;
    }

    public boolean run(Player player, String[] args){
        return false;
    }
}