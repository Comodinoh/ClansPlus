package it.itzsamirr.clansplus.utils;

import it.itzsamirr.clansplus.ClansPlus;
import lombok.experimental.UtilityClass;

import java.util.ArrayList;
import java.util.function.Function;

@UtilityClass
public class LoggerUtils {
    private ClansPlus plugin;
    private final String PREFIX = "[ClansPlus] ";
    private final String SEPARATORS = "-----------------------------";

    private Function<String, LogResult> infoAction, warnAction, errorAction, debugAction;

    static{
        infoAction = s -> new LogResult(Level.INFO, s);
        warnAction = s -> new LogResult(Level.WARNING, s);
        errorAction = s -> new LogResult(Level.ERROR, s);
        debugAction = s -> new LogResult(Level.DEBUG, s);
    }

    public static void init(ClansPlus plugin){
        LoggerUtils.plugin = plugin;
    }

    public LogResult info(String s){
        return infoAction.apply(s);
    }

    public LogResult debug(String s){
        return debugAction.apply(s);
    }

    public static class LogResult{
        private Level level;
        private String msg;
        private ArrayList<LogResult> appended = new ArrayList<>();

        private LogResult(Level level, String msg){
            this.level = level;
            this.msg = msg;
        }

        public LogResult append(Level level, String s){
            appended.add(new LogResult(level, s));
            return this;
        }

        public LogResult appendSeparators(Level level){
            return append(level, SEPARATORS);
        }

        public Level getLevel() {
            return level;
        }

        public String getMsg() {
            return msg;
        }

        public LogResult send(){
            char c1 = 'r', c2 = 'r';
            switch (level){
                case INFO:
                    c1 = 'f';
                    c2 = '7';
                    break;
                case WARNING:
                    c1 = '6';
                    c2 = 'e';
                    break;
                case ERROR:
                    c1 = '4';
                    c2 = 'c';
                    break;
                case DEBUG:
                    if(!plugin.getConfig().getBoolean("debug")){
                        appended.forEach(LogResult::send);
                        return this;
                    }
                    c1 = 'f';
                    c2 = '8';
                    break;
            }
            plugin.getServer().getConsoleSender().sendMessage(Color.color("&" + c1 + PREFIX + "&" +  c2 + msg));
            appended.forEach(LogResult::send);
            return this;
        }
    }

    public enum Level{
        INFO, WARNING, ERROR, DEBUG;
    }

    public LogResult separators(Level level){
        return new LogResult(level, SEPARATORS);
    }

    public LogResult warn(String s){
        return warnAction.apply(s);
    }

    public LogResult error(String s){
        return errorAction.apply(s);
    }
}

