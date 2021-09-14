package main.java.util;

import java.util.List;
import java.util.Objects;

/**
 * Created by Ваня on 02.06.2018.
 */
public class StringUtil {

    public static boolean isNullOrEmpty(String str){
        return str == null || str.length() == 0;
    }

    public static boolean listContainsString(List<String> list, String s){
        for(String str : list){
            if(Objects.equals(s, str)){
                return true;
            }
        }
        return false;
    }
}
