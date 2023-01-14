package it.itzsamirr.clansplus.annotations.command;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

@Retention(RetentionPolicy.RUNTIME)
@Target(ElementType.TYPE)
public @interface SubCommandInfo {
    String name();
    String[] aliases() default {};
    boolean onlyPlayers() default false;
    String permission() default "";
}
