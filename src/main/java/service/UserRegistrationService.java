package main.java.service;

import main.java.Dict;
import main.java.dao.UserDao;
import main.java.dbo.UserDbo;
import org.springframework.stereotype.Service;

import java.util.HashMap;
import java.util.Map;

/**
 * Created by mrlz on 05.04.2018.
 */
@Service
public class UserRegistrationService {

    public Map<Long,String> getDegreeList(){
        Map<Long, String> degree = new HashMap<Long, String>();
        degree.put(Dict.UNIVERSITY_DEGREE, "Высшее образование");
        degree.put(Dict.NON_COMPLETE_UNIVERSITY_DEGREE, "Не оконченное высшее образование");
        degree.put(Dict.SCHOOL_DEGREE, "Среднее образование");
        degree.put(Dict.NON_COMPLETE_SCHOOL_DEGREE, "Не оконченное среднее образование");
        degree.put(Dict.NO_DEGREE, "Без образования");

        return degree;
    }

    public Map<Long,String> getBusinessTripsList(){
        Map<Long, String> businessTripsToTrip = new HashMap<Long, String>();
        businessTripsToTrip.put(Dict.SHORT_TERM, "Краткосрочные командировки");
        businessTripsToTrip.put(Dict.LONG_TERM, "Длительные командировки");
        businessTripsToTrip.put(Dict.RARE_BT, "Редкие командировки");
        businessTripsToTrip.put(Dict.OFTEN_BT, "Частые командировки");
        businessTripsToTrip.put(Dict.NO_BT, "Без командировок");

        return businessTripsToTrip;
    }

    public String getDegreeType(Long id){
        Map<Long, String> degree = getDegreeList();
        return degree.get(id);
    }

    public String getBusinessTripType(Long id){
        Map<Long, String> businessTripsToTrip = getBusinessTripsList();
        return businessTripsToTrip.get(id);
    }

    public void fillUserProfile(UserDbo userDbo, Long accountId){
        UserDao.createUserProfile(userDbo, accountId);
    }
}
