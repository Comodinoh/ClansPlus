package it.itzsamirr.clansplus.utils;

import lombok.experimental.UtilityClass;
import org.w3c.dom.stylesheets.LinkStyle;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collection;
import java.util.List;

@UtilityClass
public class ArrayUtils {
    @SuppressWarnings("unchecked cast")
    public <T> T[] fromList(List<T> list){
        Object[] arr1 = new Object[list.size()];
        for (int i = 0; i < list.size(); i++) {
            arr1[i] = list.get(i);
        }
        return (T[]) arr1;
    }

    public <T> List<T> toList(T[] arr){
        if(arr == null) return null;
        List<T> list = new ArrayList<>(Arrays.asList(arr));
        return list;
    }
}
