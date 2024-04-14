package it.itzsamirr.clansplus.model.command;

import it.itzsamirr.clansplus.ClansAPI;
import it.itzsamirr.clansplus.ClansPlus;
import it.itzsamirr.clansplus.annotations.command.SubCommandInfo;
import lombok.Getter;
import lombok.SneakyThrows;
import org.apache.commons.lang.Validate;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.List;
import java.util.stream.Collectors;

public abstract class SubCommand {
    protected final ClansAPI api;
    protected final Command parentCommand;
    protected final ClansPlus plugin;
    protected final List<SubCommand> subCommands = new ArrayList<>();
    @Getter  private String name;
    @Getter private boolean onlyPlayers;
    @Getter private String permission;
    @Getter private String[] aliases;

    @SneakyThrows
    public SubCommand(ClansPlus plugin, Command parentCommand) {
        this.plugin = plugin;
        this.api = ClansAPI.getInstance();
        SubCommandInfo info = getClass().getDeclaredAnnotation(SubCommandInfo.class);
        Validate.notNull(info, "SubCommandInfo annotation not found in class " + getClass());
        this.name = info.name();
        this.aliases = info.aliases();
        this.onlyPlayers = info.onlyPlayers();
        this.permission = info.permission();
        this.parentCommand = parentCommand;
        for(Class<? extends SubCommand> subCommandClass : info.subCommands()){
            subCommands.add(subCommandClass.getDeclaredConstructor(ClansPlus.class, Command.class).newInstance(plugin, parentCommand));
        }
    }

    public boolean execute(CommandSender sender, String[] args){
        return false;
    }

    public boolean execute(Player player, String[] args){
        return false;
    }

    public SubCommand getSubCommand(String name){
        return subCommands.stream().filter(sc -> sc.getName().equalsIgnoreCase(name) || Arrays.stream(sc.getAliases()).anyMatch(s -> s.equalsIgnoreCase(name)))
                .findFirst()
                .orElse(null);
    }

    public boolean run(CommandSender sender, String[] args){
        if(args.length == 0 || subCommands.isEmpty()) {
            return execute(sender, args);
        }
        String subCommandName = args[0];
        SubCommand subCommand = getSubCommand(subCommandName);
        if(subCommand == null) return true;
        if (!subCommand.getPermission().isEmpty() && ((!parentCommand.getSubCommandGeneralPermission().isEmpty() && sender.hasPermission(parentCommand.getSubCommandGeneralPermission())) || !sender.hasPermission(subCommand.getPermission()))){
            return true;
        }
        final String[] subArgs = Arrays.copyOfRange(args, 1, args.length);
        return subCommand.run(sender, subArgs);
    }

    public boolean run(Player player, String[] args){
        if(args.length == 0 || subCommands.isEmpty()) {
            return execute(player, args);
        }
        String subCommandName = args[0];
        SubCommand subCommand = getSubCommand(subCommandName);
        if(subCommand == null) return true;
        if (!subCommand.getPermission().isEmpty() && ((!parentCommand.getSubCommandGeneralPermission().isEmpty() && player.hasPermission(parentCommand.getSubCommandGeneralPermission())) || !player.hasPermission(subCommand.getPermission()))){
            return true;
        }
        final String[] subArgs = Arrays.copyOfRange(args, 1, args.length);
        return subCommand.run(player, subArgs);
    }

    public List<String> tabComplete(CommandSender sender, String[] args){
        return Collections.emptyList();
    }

    public List<String> runTab(CommandSender sender, String[] args){
        if(!subCommands.isEmpty()){
            if(args.length == 1){
                List<String> list = new ArrayList<>();
                subCommands.stream().filter(cmd -> cmd.getPermission().isEmpty() || sender.hasPermission(cmd.getPermission()) || (!parentCommand.getSubCommandGeneralPermission().isEmpty() && sender.hasPermission(parentCommand.getSubCommandGeneralPermission()))).forEach(cmd -> {
                    list.add(cmd.getName());
                    list.addAll(Arrays.asList(cmd.getAliases()));
                });
                return list.stream()
                        .filter(sc -> sc.toLowerCase().startsWith(args[0].toLowerCase()))
                        .sorted()
                        .collect(Collectors.toList());
            }
            if(args.length > 1){
                SubCommand subCommand = getSubCommand(args[0]);
                if(subCommand == null) return Collections.emptyList();
                return subCommand.runTab(sender, Arrays.copyOfRange(args, 1, args.length));
            }
        }
        return tabComplete(sender, args);
    }
}