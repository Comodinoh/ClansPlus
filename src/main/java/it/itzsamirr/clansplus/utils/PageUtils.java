package it.itzsamirr.clansplus.utils;

import it.itzsamirr.clansplus.ClansAPI;
import it.itzsamirr.clansplus.managers.configuration.lang.LangManager;
import lombok.experimental.UtilityClass;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.List;

@UtilityClass
public class PageUtils {

    private final int PAGE_SIZE = 5;
    private LangManager langManager;

    static{
        langManager = ClansAPI.getInstance().getManager(LangManager.class);
    }

    public static List<String> pages(List<String> inputList, int selectedPage, int pageSize, String bottomLine, String topLine){
        if(inputList == null || inputList.isEmpty()) return Arrays.asList(topLine);
        List<String> list = new ArrayList<>(Collections.singletonList(topLine));
        int size = inputList.size();
        int pages = size/pageSize;
        selectedPage = Math.max(1, Math.min(pages, selectedPage));
        if(pages == 0){
            pages = 1;
            pageSize = size;
        }
        list.addAll(inputList.subList((selectedPage*pageSize)-pageSize, selectedPage*pageSize));
        if(selectedPage != pages){
            list.add(bottomLine);
        }
        return list;
    }

    public List<String> pages(List<String> inputList, int selectedPage, String bottomLine, String topLine){
        return pages(inputList, selectedPage, PAGE_SIZE, bottomLine, topLine);
    }

    public List<String> pages(String listPath, int selectedPage, int pageSize){
        List<String> inputList = langManager.getLanguage().getList(listPath);
        if(inputList == null || inputList.isEmpty()) return pages(null, 1, null, langManager.getLanguage().getString(listPath + "-top"));
        selectedPage = Math.max(1, Math.min(inputList.size()/pageSize, selectedPage));
        return pages(inputList, selectedPage, pageSize,
                langManager.getLanguage().getString(listPath + "-bottom").replace("{number}", String.valueOf(selectedPage+1)),
                langManager.getLanguage().getString(listPath + "-top"));
    }

    public List<String> pages(String listPath, int selectedPage){
        return pages(listPath, selectedPage, PAGE_SIZE);
    }
}
