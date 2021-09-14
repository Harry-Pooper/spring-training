package main.java.service;

import main.Dbc;
import main.java.dao.MainPageDao;
import org.springframework.stereotype.Service;

/**
 * Created by mrlz on 27.03.2018.
 */
@Service
public class MainPageService {

    public static String getLastId(){
        return MainPageDao.getLastId();
    }

}
