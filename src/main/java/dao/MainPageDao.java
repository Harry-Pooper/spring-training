package main.java.dao;

import main.Dbc;
import main.java.dbo.MainPageDbo;

import java.sql.ResultSet;
import java.sql.Statement;

/**
 * Created by mrlz on 27.03.2018.
 */
public class MainPageDao {

    public static String getLastId(){
        Dbc c = new Dbc();
        Statement stmt = null;
        MainPageDbo mpd = new MainPageDbo();
        c.connect();

        try {
            stmt = c.createStatement();
            ResultSet rs = stmt.executeQuery("select id from ids");
            while(rs.next()){
                mpd.setId(rs.getLong("id"));
            }
            c.close();
        } catch (Exception ex){
            ex.printStackTrace();
            System.err.println(ex.getClass().getName() + ": " + ex.getMessage());
            System.exit(0);
        }
        return Long.toString(mpd.getId());
    }
}
