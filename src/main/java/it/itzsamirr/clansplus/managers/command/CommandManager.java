package it.itzsamirr.clansplus.managers.command;

import it.itzsamirr.clansplus.ClansPlus;
import it.itzsamirr.clansplus.managers.Manager;
import it.itzsamirr.clansplus.model.command.Command;
import it.itzsamirr.clansplus.model.command.InternalCommand;
import lombok.SneakyThrows;
import org.bukkit.Bukkit;
import org.bukkit.command.CommandMap;

import java.lang.reflect.Field;
import java.util.HashMap;

public class CommandManager implements Manager {
    private static Field field;

    static {
        try {
            field = Bukkit.getServer().getClass().getDeclaredField("commandMap");
        } catch (NoSuchFieldException e) {
            e.printStackTrace();
        }
    }

    private HashMap<Class<? extends Command>, Command> commands = new HashMap<>();
    private final ClansPlus plugin;

    public CommandManager(ClansPlus plugin) {
        this.plugin = plugin;
    }

    @SneakyThrows
    public <T extends Command> T register(Class<T> commandClass){
        T command = commandClass.getDeclaredConstructor(ClansPlus.class)
                .newInstance(plugin);
        field.setAccessible(true);
        CommandMap map = (CommandMap)field.get(plugin.getServer());
        map.register(command.getName(), "clansplus", new InternalCommand(command));
        field.setAccessible(false);
        commands.put(commandClass, command);
        return command;
    }

    @SneakyThrows
    public void unregister(Class<? extends Command> commandClass){
        if(!commands.containsKey(commandClass)) return;
        Command command = commands.get(commandClass);
        field.setAccessible(true);
        CommandMap map = (CommandMap)field.get(plugin.getServer());
        map.getCommand(command.getName()).unregister(map);
        field.setAccessible(false);
    }

    public void unregisterAll(){
        commands.keySet().forEach(this::unregister);
        commands.clear();
    }

    @Override
    public void load() {
        // empty
    }
}
