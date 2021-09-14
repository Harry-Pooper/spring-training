package main.java.dao;

import main.Dbc;
import main.java.dbo.UserContactsDbo;
import main.java.dbo.UserDbo;
import main.java.dbo.UserWorkHistoryDbo;
import main.java.service.UserRegistrationService;

import java.sql.Date;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.List;

/**
 * Created by mrlz on 16.04.2018.
 */
public class UserWorkHistoryDao {

    public static void addUserWorkHistory(List<UserWorkHistoryDbo> userWorkHistoryDbo, Long profileId){
        Dbc c = new Dbc();
        PreparedStatement stmt;
        UserRegistrationService userRegistrationService = new UserRegistrationService();
        c.connect();
        try{
            for (UserWorkHistoryDbo anUserWorkHistoryDbo : userWorkHistoryDbo) {
                stmt = c.createPreparedStatement(
                        "INSERT INTO USER_WORK_STORY VALUES(GENERATE_ID(), " +
                                "?, " + //1 profileId
                                "?, " + //2 orgName
                                "?, " + //3 emplDate
                                "?, " + //4 unemplDate
                                "?, " + //5 position
                                ")"
                );
                stmt.setLong(1, profileId);
                stmt.setString(2, anUserWorkHistoryDbo.getOrgName());
                stmt.setDate(3, Date.valueOf(LocalDate.parse(anUserWorkHistoryDbo.getEmplDate(), DateTimeFormatter.ofPattern("dd.MM.yyyy"))));
                stmt.setDate(4, Date.valueOf(LocalDate.parse(anUserWorkHistoryDbo.getUnemplDate(), DateTimeFormatter.ofPattern("dd.MM.yyyy"))));
                stmt.setString(5, anUserWorkHistoryDbo.getPosition());
                stmt.executeUpdate();
            }
            c.commit();
            c.close();
        } catch (Exception ex){
            ex.printStackTrace();
            System.err.println(ex.getClass().getName() + ": " + ex.getMessage());
            System.exit(0);
        }
    }

    public static List<UserWorkHistoryDbo> getUserWorkHistoryById(Long profileId){
        Dbc c = new Dbc();
        PreparedStatement stmt;
        List<UserWorkHistoryDbo> userWorkHistoryDboList = new ArrayList<>();
        c.connect();
        try{
            UserWorkHistoryDbo userWorkHistoryDbo = new UserWorkHistoryDbo();
            stmt = c.createPreparedStatement("SELECT * FROM USER_WORK_STORY WHERE PROFILE_ID = ?");
            stmt.setLong(1, profileId);
            ResultSet rs = stmt.executeQuery();
            while(rs.next()){
                userWorkHistoryDbo.setHistoryId(rs.getLong("HISTORY_ID"));
                userWorkHistoryDbo.setProfileId(rs.getLong("PROFILE_ID"));
                userWorkHistoryDbo.setOrgName(rs.getString("ORG_NAME"));
                userWorkHistoryDbo.setEmplDate(rs.getString("UNEMPL_DATE"));
                userWorkHistoryDbo.setPosition(rs.getString("POSITION"));
                userWorkHistoryDboList.add(userWorkHistoryDbo);
            }
            c.commit();
            c.close();
        } catch(Exception ex){
            ex.printStackTrace();
            System.err.println(ex.getClass().getName() + ": " + ex.getMessage());
            System.exit(0);
        }
        return userWorkHistoryDboList;
    }

    public static void clearWorkHistoryById(Long profileId){
        Dbc c = new Dbc();
        PreparedStatement stmt;
        UserRegistrationService userRegistrationService = new UserRegistrationService();
        c.connect();
        try{
            stmt = c.createPreparedStatement("DELETE FROM USER_WORK_STORY WHERE PROFILE_ID = ?");
            stmt.setLong(1, profileId);
            stmt.executeUpdate();
            c.commit();
            c.close();
        } catch(Exception ex){
            ex.printStackTrace();
            System.err.println(ex.getClass().getName() + ": " + ex.getMessage());
            System.exit(0);
        }
    }

}
