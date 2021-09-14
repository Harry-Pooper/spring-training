package main.java.service;

import main.java.Dict;
import main.java.dao.OrgDao;
import main.java.dao.TagsDao;
import main.java.dbo.OrgDbo;
import org.springframework.stereotype.Service;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * Created by mrlz on 05.04.2018.
 */
@Service
public class OrgRegistrationService {


    public Map<Long,String> getOrgTypeList(){
        Map<Long, String> orgType = new HashMap<Long, String>();
        orgType.put(Dict.OOO_TYPE, "ООО");
        orgType.put(Dict.IP_TYPE, "ИП");
        orgType.put(Dict.ODO_TYPE, "ОДО");
        orgType.put(Dict.PT_TYPE, "Полное товарищество");
        orgType.put(Dict.TV_TYPE, "Товарищество на вере");
        orgType.put(Dict.PK_TYPE, "Производственный кооператив");

        return orgType;
    }

    public String getOrgType(Long id){
        Map<Long, String> orgType = getOrgTypeList();
        return orgType.get(id);
    }


    public Map<Long,String> getcountMemList(){
        Map<Long, String> countMem = new HashMap<Long, String>();
        countMem.put(Dict.min50_TYPE, "Менее 50 сотрудников");
        countMem.put(Dict.min100_TYPE, "от 50 до 100 сотрудников");
        countMem.put(Dict.min500_TYPE, "от 100 до 500 сотрудников");
        countMem.put(Dict.min1000_TYPE, "от 500 до 1000 сотрудников");
        countMem.put(Dict.min10000_TYPE, "от 1000 до 1000000 сотрудников");

        return countMem;
    }

    public String getcountMem(Long id){
        Map<Long, String> countMem = getcountMemList();
        return countMem.get(id);
    }



    public void fillOrgProfile(OrgDbo orgDbo, Long accountId){
        OrgDao.createOrgProfile(orgDbo, accountId);
    }

}
