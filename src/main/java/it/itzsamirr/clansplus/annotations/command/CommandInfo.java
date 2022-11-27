package it.itzsamirr.clansplus.annotations.command;

import it.itzsamirr.clansplus.model.command.SubCommand;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

@Retention(RetentionPolicy.RUNTIME)
@Target(ElementType.TYPE)
public @interface CommandInfo {
    String name();
    String permission() default "";
    String description() default "";
    String[] aliases() default {};
    boolean onlyPlayers() default false;
    Class<? extends SubCommand>[] subCommands() default {};
    String subCommandGeneralPermission() default "";

}
