package it.itzsamirr.clansplus.utils;

import lombok.experimental.UtilityClass;

@UtilityClass
public class NumberUtils {
    public Integer fromString(String s){
        Integer integer = null;
        try{
            integer = Integer.valueOf(s);
        }catch(NumberFormatException e){
            //empty
        }
        return integer;
    }
}
