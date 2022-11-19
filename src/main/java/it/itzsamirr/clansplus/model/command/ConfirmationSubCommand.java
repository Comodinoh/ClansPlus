package it.itzsamirr.clansplus.model.command;

import it.itzsamirr.clansplus.ClansPlus;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

import java.util.*;

public abstract class ConfirmationSubCommand extends SubCommand{
    protected Map<CommandSender, Long> confirming = new HashMap<>();
    public ConfirmationSubCommand(ClansPlus plugin, String name, String permission, boolean onlyPlayers, String... aliases) {
        super(plugin, name, permission, onlyPlayers, aliases);
    }

    public ConfirmationSubCommand(ClansPlus plugin, String name, String permission, String... aliases) {
        super(plugin, name, permission, aliases);
    }

    public ConfirmationSubCommand(ClansPlus plugin, String name, String... aliases) {
        super(plugin, name, aliases);
    }

    protected boolean isConfirming(CommandSender sender){
        return confirming.containsKey(sender);
    }

    protected boolean isConfirmingExpired(CommandSender sender, double time){
        return (time <= .0 || ((System.currentTimeMillis()-confirming.get(sender))/1000.0) >= time);
    }

    protected void setConfirming(CommandSender sender, long now){
        if(isConfirming(sender)){
            confirming.replace(sender, now);
            return;
        }
        confirming.put(sender, now);
    }

    protected void setConfirming(CommandSender sender){
        setConfirming(sender, -1);
    }

    protected void removeConfirming(CommandSender sender){
        confirming.remove(sender);
    }
}
