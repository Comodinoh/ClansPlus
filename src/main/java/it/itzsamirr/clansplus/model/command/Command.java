package it.itzsamirr.clansplus.model.command;


import it.itzsamirr.clansplus.ClansAPI;
import it.itzsamirr.clansplus.ClansPlus;
import it.itzsamirr.clansplus.annotations.command.CommandInfo;
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
import java.util.stream.Collectors;

public abstract class Command implements TabExecutor {
    @Getter private String name;
    @Getter private String permission;
    @Getter private boolean onlyPlayers;
    @Getter private String description;
    @Getter private List<String> aliases;
    @Getter private String subCommandGeneralPermission;
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
        this.subCommandGeneralPermission = info.subCommandGeneralPermission();
        for(Class<? extends SubCommand> subCommandClass : info.subCommands()){
            subCommands.add(subCommandClass.getDeclaredConstructor(ClansPlus.class, Command.class).newInstance(plugin, this));
        }
    }

    public void execute(CommandSender sender, String[] args){}

    public void execute(Player player, String[] args){}

    private SubCommand getSubCommand(String name){
        return subCommands.stream().filter(sc -> sc.getName().equalsIgnoreCase(name) || Arrays.stream(sc.getAliases()).anyMatch(s -> s.equalsIgnoreCase(name)))
                .findFirst()
                .orElse(null);
    }

    @Override
    public boolean onCommand(CommandSender sender, org.bukkit.command.Command command, String label, String[] args) {
        if(!permission.isEmpty()){
            if(!sender.hasPermission(permission)){
                sender.sendMessage(
                        ClansAPI.getInstance().get(LangManager.class)
                                .getLanguage().getString("no-permission")
                );
                return false;
            }
        }
        argsCheck:
        {
            if (args.length > 0 && !subCommands.isEmpty()) {
                SubCommand subCommand = getSubCommand(args[0]);
                if(subCommand == null) break argsCheck;
                if (!subCommand.getPermission().isEmpty() && ((!subCommandGeneralPermission.isEmpty() && sender.hasPermission(subCommandGeneralPermission)) || !sender.hasPermission(subCommand.getPermission()))){
                    break argsCheck;
                }
                final String[] subArgs = Arrays.copyOfRange(args, 1, args.length);
                if(subCommand.isOnlyPlayers()){
                    if(!(sender instanceof Player)){
                        sender.sendMessage(ClansAPI.getInstance().get(LangManager.class)
                                .getLanguage().getString("only-players"));
                        return false;
                    }
                    if(subCommand.run((Player)sender, subArgs)) break argsCheck;
                    return false;
                }
                if(subCommand.run(sender, subArgs)) break argsCheck;
                return false;
            }
        }
        if(onlyPlayers){
            if(!(sender instanceof Player)){
                sender.sendMessage(ClansAPI.getInstance().get(LangManager.class)
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
        return Collections.emptyList();
    }

    @Override
    public List<String> onTabComplete(CommandSender sender, org.bukkit.command.Command command, String alias, String[] args) {
        if(!permission.isEmpty()){
            if(!sender.hasPermission(permission)){
                return null;
            }
        }
        if(!subCommands.isEmpty()) {
            if (args.length == 1) {
                List<String> list = new ArrayList<>();
                subCommands.stream().filter(cmd -> cmd.getPermission().isEmpty() || sender.hasPermission(cmd.getPermission()) || (!subCommandGeneralPermission.isEmpty() && sender.hasPermission(subCommandGeneralPermission))).forEach(cmd -> {
                    list.add(cmd.getName());
                    list.addAll(Arrays.asList(cmd.getAliases()));
                });
                return list.stream()
                        .filter(sc -> sc.toLowerCase().startsWith(args[0].toLowerCase()))
                        .sorted()
                        .collect(Collectors.toList());
            }
            if (args.length > 1){
                SubCommand subCommand = getSubCommand(args[0]);
                if(subCommand == null) return Collections.emptyList();
                return subCommand.runTab(sender, Arrays.copyOfRange(args, 1, args.length));
            }
        }
        return tabComplete(sender, args);
    }
}
