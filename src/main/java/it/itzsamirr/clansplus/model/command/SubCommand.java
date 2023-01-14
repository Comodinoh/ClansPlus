package it.itzsamirr.clansplus.model.command;

import it.itzsamirr.clansplus.ClansAPI;
import it.itzsamirr.clansplus.ClansPlus;
import it.itzsamirr.clansplus.annotations.command.SubCommandInfo;
import lombok.Getter;
import org.apache.commons.lang.Validate;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

import java.util.Collections;
import java.util.List;

public abstract class SubCommand {
    protected final ClansAPI api;
    protected final ClansPlus plugin;
    @Getter  private String name;
    @Getter private boolean onlyPlayers;
    @Getter private String permission;
    @Getter private String[] aliases;

    public SubCommand(ClansPlus plugin) {
        this.plugin = plugin;
        this.api = ClansAPI.getInstance();
        SubCommandInfo info = getClass().getDeclaredAnnotation(SubCommandInfo.class);
        Validate.notNull(info, "SubCommandInfo annotation not found in class " + getClass());
        this.name = info.name();
        this.aliases = info.aliases();
        this.onlyPlayers = info.onlyPlayers();
        this.permission = info.permission();
    }

    public boolean run(CommandSender sender, String[] args){
        return false;
    }

    public boolean run(Player player, String[] args){
        return false;
    }

    public List<String> tabComplete(CommandSender sender, String[] args){
        return Collections.emptyList();
    }
}