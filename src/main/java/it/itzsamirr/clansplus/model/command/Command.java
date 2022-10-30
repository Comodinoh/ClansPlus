package it.itzsamirr.clansplus.model.command;


import it.itzsamirr.clansplus.ClansAPI;
import it.itzsamirr.clansplus.ClansPlus;
import it.itzsamirr.clansplus.managers.configuration.lang.LangManager;
import lombok.Getter;
import lombok.SneakyThrows;
import org.apache.commons.lang.Validate;
import org.bukkit.command.CommandSender;
import org.bukkit.command.TabExecutor;
import org.bukkit.entity.Player;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.List;

public abstract class Command implements TabExecutor {
    @Getter private String name;
    @Getter private String permission;
    @Getter private boolean onlyPlayers;
    @Getter private String description;
    @Getter private List<String> aliases;
    private List<SubCommand> subCommands = new ArrayList<>();
    protected final ClansPlus plugin;

    @SneakyThrows
    public Command(ClansPlus plugin){
        this.plugin = plugin;
        CommandInfo info = getClass().getDeclaredAnnotation(CommandInfo.class);
        Validate.notNull(info, "CommandInfo annotation not found in class " + getClass());
        this.name = info.name();
        this.permission = info.permission();
        this.description = info.description();
        this.aliases = Arrays.asList(info.aliases());
        this.onlyPlayers = info.onlyPlayers();
        for(Class<? extends SubCommand> subCommandClass : info.subCommands()){
            subCommands.add(subCommandClass.getDeclaredConstructor(ClansPlus.class).newInstance(plugin));
        }
    }

    public void execute(CommandSender sender, String[] args){}

    public void execute(Player player, String[] args){}

    private SubCommand getSubCommand(String name){
        return subCommands.stream().filter(sc -> sc.getName().equalsIgnoreCase(name))
                .findFirst()
                .orElse(null);
    }

    @Override
    public boolean onCommand(CommandSender sender, org.bukkit.command.Command command, String label, String[] args) {
        if(!permission.isEmpty()){
            if(!sender.hasPermission(permission)){
                sender.sendMessage(
                        ClansAPI.getInstance().getManager(LangManager.class)
                                .getLanguage().getString("no-permission")
                );
                return false;
            }
        }
        a1:
        {
            if (args.length > 0 && !subCommands.isEmpty()) {
                SubCommand subCommand = getSubCommand(args[0]);
                if(subCommand == null) break a1;
                if (!subCommand.getPermission().isEmpty() && !sender.hasPermission(subCommand.getPermission())){
                    break a1;
                }
                if(subCommand.isOnlyPlayers()){
                    if(!(sender instanceof Player)){
                        sender.sendMessage(ClansAPI.getInstance().getManager(LangManager.class)
                                .getLanguage().getString("only-players"));
                        return false;
                    }
                    if(subCommand.run((Player)sender, args)) break a1;
                    return false;
                }
                if(subCommand.run(sender, args)) break a1;
                return false;
            }
        }
        if(onlyPlayers){
            if(!(sender instanceof Player)){
                sender.sendMessage(ClansAPI.getInstance().getManager(LangManager.class)
                        .getLanguage().getString("only-players"));
                return false;
            }
            execute((Player)sender, args);
            return false;
        }
        execute(sender, args);
        return false;
    }

    public List<String> tabComplete(CommandSender sender, String[] args){
        return null;
    }

    public List<String> tabComplete(Player player, String[] args){
        return null;
    }

    @Override
    public List<String> onTabComplete(CommandSender sender, org.bukkit.command.Command command, String alias, String[] args) {
        if(!permission.isEmpty()){
            if(!sender.hasPermission(permission)){
                return Collections.emptyList();
            }
        }
        if(onlyPlayers){
            if(!(sender instanceof Player)){
                return null;
            }
            return tabComplete((Player)sender, args);
        }
        return tabComplete(sender, args);
    }
}
