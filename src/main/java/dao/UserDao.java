package main.java.dao;

import main.Dbc;
import main.java.dbo.RegistrationDbo;
import main.java.dbo.UserDbo;
import main.java.service.UserRegistrationService;

import java.sql.Date;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.Statement;
import java.text.SimpleDateFormat;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;

/**
 * Created by mrlz on 02.04.2018.
 */
public class UserDao {

    public static void createUserProfile(UserDbo userDbo, Long accountId){
        Dbc c = new Dbc();
        PreparedStatement stmt;
        UserRegistrationService userRegistrationService = new UserRegistrationService();
        c.connect();
        try{
            stmt = c.createPreparedStatement(
                    "INSERT INTO user_profile VALUES(GENERATE_ID(), " +
                            "?, " + // 1 accid
                            "?, " + // 2 first name
                            "?, " + // 3 secondname
                            "?, " + // 4 lastname
                            "?, " + // 5 date of birth
                            "false, " + // hide or show date of birth
                            "0, " +  // age
                            "?, " + // 6 aboutuser
                            "?, " + // 7 degree
                            "null, " + //payment id
                            "null," + //photo url
                            " null, " + //can move
                            "?, " + // 8 business trip
                            "?)"  // 9 city_id
            );
            stmt.setLong(1, accountId);
            stmt.setString(2, userDbo.getFirstName());
            stmt.setString(3, userDbo.getSecondName());
            stmt.setString(4, userDbo.getLastName());
            stmt.setDate(5, Date.valueOf(LocalDate.parse(userDbo.getBirthDate(), DateTimeFormatter.ofPattern("dd.MM.yyyy"))));
            stmt.setString(6, userDbo.getAboutUser());
            stmt.setLong(7, userDbo.getDegree());
            stmt.setLong(8, userDbo.getBusinessTrips());
            stmt.setLong(9, userDbo.getCityId());
            stmt.executeUpdate();
            c.commit();
            c.close();
        } catch (Exception ex){
            ex.printStackTrace();
            System.err.println(ex.getClass().getName() + ": " + ex.getMessage());
        }
    }

    public static void updateUserProfileInfo(UserDbo userDbo, Long profileId){
        Dbc c = new Dbc();
        PreparedStatement stmt;
        UserRegistrationService userRegistrationService = new UserRegistrationService();
        c.connect();
        try{
            stmt = c.createPreparedStatement(
                    "UPDATE USER_PROFILE " +
                            "SET FIRST_NAME = ?, " + //1
                            "SECOND_NAME = ?, " + //2
                            "LAST_NAME = ?, " + //3
                            "BIRTH_DATE = ?, " + //4
                            "HIDE_BIRTH_DATE = ?, " + //5
                            "ABOUT_USER = ?, " + //6
                            "DEGREE = ?, " + //7
                            "BUSINESS_TRIP = ?, " + //8
                            "CITY_ID = ?" + //9
                            "WHERE PROFILE_ID = ?" //10
            );
            stmt.setString(1, userDbo.getFirstName());
            stmt.setString(2, userDbo.getSecondName());
            stmt.setString(3, userDbo.getLastName());
            stmt.setDate(4, Date.valueOf(LocalDate.parse(userDbo.getBirthDate(), DateTimeFormatter.ofPattern("dd.MM.yyyy"))));
            stmt.setBoolean(5, userDbo.isHideBirthDate());
            stmt.setString(6, userDbo.getAboutUser());
            stmt.setLong(7, userDbo.getDegree());
            stmt.setLong(8, userDbo.getBusinessTrips());
            stmt.setLong(9, userDbo.getCityId());
            stmt.setLong(10, profileId);
            stmt.executeUpdate();
            c.commit();
            c.close();
        } catch (Exception ex){
            ex.printStackTrace();
            System.err.println(ex.getClass().getName() + ": " + ex.getMessage());
        }
    }

    public static void updateUserProfilePicUrl(String photoUrl, Long profileId){
        Dbc c = new Dbc();
        PreparedStatement stmt;
        UserRegistrationService userRegistrationService = new UserRegistrationService();
        c.connect();
        try{
            stmt = c.createPreparedStatement(
                    "UPDATE USER_PROFILE " +
                            "SET PHOTO_URL = ?" + //1
                            "WHERE PROFILE_ID = ?" //2
            );
            stmt.setString(1, photoUrl);
            stmt.setLong(2, profileId);
            stmt.executeUpdate();
            c.commit();
            c.close();
        } catch (Exception ex){
            ex.printStackTrace();
            System.err.println(ex.getClass().getName() + ": " + ex.getMessage());
        }
    }

    public static UserDbo findUserByProfileId(Long profileId){
        Dbc c = new Dbc();
        PreparedStatement stmt;
        UserDbo userDbo = new UserDbo();
        c.connect();
        try{
            stmt = c.createPreparedStatement("SELECT * FROM USER_PROFILE WHERE PROFILE_ID = ?");
            stmt.setLong(1, profileId);
            ResultSet rs = stmt.executeQuery();
            userDbo = fillUserDbo(rs);
            c.close();
        } catch (Exception ex){
            ex.printStackTrace();
            System.err.println(ex.getClass().getName() + ": " + ex.getMessage());
        }
        return userDbo;
    }

    public static UserDbo findUserByAccId(RegistrationDbo rdbo){
        Dbc c = new Dbc();
        PreparedStatement stmt;
        UserDbo userDbo = new UserDbo();
        c.connect();
        try{
            stmt = c.createPreparedStatement("SELECT * FROM USER_PROFILE WHERE ACCOUNT_ID = ?");
            stmt.setLong(1, rdbo.getAccountId());
            ResultSet rs = stmt.executeQuery();
            userDbo = fillUserDbo(rs);
            c.close();
        } catch (Exception ex){
            ex.printStackTrace();
            System.err.println(ex.getClass().getName() + ": " + ex.getMessage());
        }
        return userDbo;
    }

    private static UserDbo fillUserDbo(ResultSet rs){
        UserDbo userDbo = new UserDbo();
        SimpleDateFormat dateFormat = new SimpleDateFormat("dd.MM.yyyy");
        try{
            while(rs.next()){
                userDbo.setProfileId(rs.getLong("PROFILE_ID"));
                userDbo.setAccountId(rs.getLong("ACCOUNT_ID"));
                userDbo.setFirstName(rs.getString("FIRST_NAME"));
                userDbo.setSecondName(rs.getString("SECOND_NAME"));
                userDbo.setLastName(rs.getString("LAST_NAME"));
                userDbo.setBirthDate(dateFormat.format(rs.getDate("BIRTH_DATE")));
                userDbo.setHideBirthDate(rs.getBoolean("HIDE_BIRTH_DATE"));
                userDbo.setAge(rs.getLong("AGE"));
                userDbo.setAboutUser(rs.getString("ABOUT_USER"));
                userDbo.setDegree(rs.getLong("DEGREE"));
                userDbo.setPaymentId(rs.getLong("PAYMENT_ID"));
                userDbo.setPhotoUrl(rs.getString("PHOTO_URL"));
                userDbo.setCanMove(rs.getLong("CAN_MOVE"));
                userDbo.setBusinessTrips(rs.getLong("BUSINESS_TRIP"));
                userDbo.setCityId(rs.getLong("CITY_ID"));
            }
        } catch (Exception ex){
            ex.printStackTrace();
            System.err.println(ex.getClass().getName() + ": " + ex.getMessage());
        }
        return userDbo;
    }
}
