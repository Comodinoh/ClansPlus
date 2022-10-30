package it.itzsamirr.clansplus.model.command;

import org.bukkit.command.Command;
import org.bukkit.command.CommandSender;

import java.util.List;

public class InternalCommand extends Command {
    private it.itzsamirr.clansplus.model.command.Command command;


    public InternalCommand(it.itzsamirr.clansplus.model.command.Command command) {
        super(command.getName(), command.getDescription(), "", command.getAliases());
        this.command = command;
    }

    @Override
    public boolean execute(CommandSender sender, String commandLabel, String[] args) {
        return command.onCommand(sender, this, commandLabel, args);
    }

    @Override
    public List<String> tabComplete(CommandSender sender, String alias, String[] args) throws IllegalArgumentException {
        return command.onTabComplete(sender, this, alias, args);
    }
}
