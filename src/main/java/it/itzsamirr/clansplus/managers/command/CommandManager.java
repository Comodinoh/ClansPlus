package it.itzsamirr.clansplus.managers.command;

import it.itzsamirr.clansplus.ClansPlus;
import it.itzsamirr.clansplus.managers.Manager;
import it.itzsamirr.clansplus.model.command.Command;
import it.itzsamirr.clansplus.model.command.InternalCommand;
import lombok.SneakyThrows;
import org.bukkit.command.CommandMap;

import java.lang.reflect.Field;
import java.util.HashMap;

public class CommandManager implements Manager {
    private HashMap<Class<? extends Command>, Command> commands = new HashMap<>();
    private final ClansPlus plugin;

    public CommandManager(ClansPlus plugin) {
        this.plugin = plugin;
    }

    @SneakyThrows
    public <T extends Command> T register(Class<T> commandClass){
        T command = commandClass.getDeclaredConstructor(ClansPlus.class)
                .newInstance(plugin);
        Field field = plugin.getServer().getClass().getDeclaredField("commandMap");
        field.setAccessible(true);
        CommandMap map = (CommandMap)field.get(plugin.getServer());
        map.register(command.getName(), new InternalCommand(command));
        commands.put(commandClass, command);
        return command;
    }

    public void unregister(Class<? extends Command> commandClass){
        if(!commands.containsKey(commandClass)) return;
        Command command = commands.get(commandClass);
        plugin.getCommand(command.getName()).setExecutor(null);
        commands.remove(commandClass);
    }

    public void unregisterAll(){
        commands.keySet().forEach(this::unregister);
    }

    @Override
    public void load() {
        // empty
    }
}
