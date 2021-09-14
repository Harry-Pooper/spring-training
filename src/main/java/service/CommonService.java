package main.java.service;

import main.java.Dict;
import main.java.util.StringUtil;
import org.springframework.stereotype.Service;
import org.springframework.ui.Model;

import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

/**
 * Created by Ваня on 21.05.2018.
 */
@Service
public class CommonService {

    public static Model putHeaderInfo(Model model){
        model.addAttribute("rdbo", SecurityService.getLoggedInUser());
        model.addAttribute("userType", Dict.USER);
        model.addAttribute("organType", Dict.ORG);
        return model;
    }

    public static List<String> distinctList(List<String> list){
        List<String> list1 = new ArrayList<>();
        for(String s : list){
            if(!StringUtil.listContainsString(list1, s)){
                list1.add(s);
            }
        }
        return list1;
    }

    public static String getStatusString(Long id){
        if(Objects.equals(id, Dict.SENT)){
            return "На рассмотрении";
        }
        if(Objects.equals(id, Dict.REJECT)){
            return "Отклонено";
        }
        return "Приглашён на собеседование";
    }
}
