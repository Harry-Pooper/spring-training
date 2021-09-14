package main.java.dao;

import main.Dbc;
import main.java.dbo.UserContactsDbo;
import main.java.service.UserRegistrationService;

import java.sql.Date;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.List;

/**
 * Created by mrlz on 08.04.2018.
 */
public class UserContactsDao {

    public static void createUserContacts(List<UserContactsDbo> userContactsDboList){
        Dbc c = new Dbc();
        PreparedStatement stmt;
        c.connect();
        try{
            for (UserContactsDbo anUserContactsDboList : userContactsDboList) {
                stmt = c.createPreparedStatement(
                        "INSERT INTO user_contacts VALUES(?, ?, ?, ?)"
                );
                stmt.setLong(1, anUserContactsDboList.getProfileId());
                stmt.setString(2, anUserContactsDboList.getContact());
                stmt.setLong(3, anUserContactsDboList.getType());
                stmt.setBoolean(4, anUserContactsDboList.isPrefered());
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

    public static void deleteContactsForUser(Long profileId){
        Dbc c = new Dbc();
        PreparedStatement stmt;
        c.connect();
        try{
            stmt = c.createPreparedStatement("DELETE FROM USER_CONTACTS WHERE PROFILE_ID = ?");
            stmt.setLong(1, profileId);
            stmt.executeUpdate();
            c.commit();
            c.close();
        } catch (Exception ex){
            ex.printStackTrace();
            System.err.println(ex.getClass().getName() + ": " + ex.getMessage());
            System.exit(0);
        }
    }

    public static List<String> getPhonesForUser(Long profileId){
        Dbc c = new Dbc();
        PreparedStatement stmt;
        List<String> phones = new ArrayList<>();
        c.connect();
        try{
            stmt = c.createPreparedStatement(
                    "SELECT * FROM USER_CONTACTS WHERE PROFILE_ID = ?"
            );
            stmt.setLong(1, profileId);
            ResultSet rs = stmt.executeQuery();
            while(rs.next()){
                if(rs.getLong("type") == 1681L){
                    phones.add(rs.getString("contact"));
                }
            }
            c.close();
        } catch (Exception ex){
            ex.printStackTrace();
            System.err.println(ex.getClass().getName() + ": " + ex.getMessage());
            System.exit(0);
        }
        return phones;
    }

    public static List<String> getEmailsForUser(Long profileId){
        Dbc c = new Dbc();
        PreparedStatement stmt;
        List<String> emails = new ArrayList<>();
        c.connect();
        try{
            stmt = c.createPreparedStatement(
                    "SELECT * FROM USER_CONTACTS WHERE PROFILE_ID = ?"
            );
            stmt.setLong(1, profileId);
            ResultSet rs = stmt.executeQuery();
            while(rs.next()){
                if(rs.getLong("type") == 1651L){
                    emails.add(rs.getString("contact"));
                }
            }
            c.close();
        } catch (Exception ex){
            ex.printStackTrace();
            System.err.println(ex.getClass().getName() + ": " + ex.getMessage());
            System.exit(0);
        }
        return emails;
    }

    public static List<String> getFaxesForUser(Long profileId){
        Dbc c = new Dbc();
        PreparedStatement stmt;
        List<String> faxes = new ArrayList<>();
        c.connect();
        try{
            stmt = c.createPreparedStatement(
                    "SELECT * FROM USER_CONTACTS WHERE PROFILE_ID = ?"
            );
            stmt.setLong(1, profileId);
            ResultSet rs = stmt.executeQuery();
            while(rs.next()){
                if(rs.getLong("type") == 1711L){
                    faxes.add(rs.getString("contact"));
                }
            }
            c.close();
        } catch (Exception ex){
            ex.printStackTrace();
            System.err.println(ex.getClass().getName() + ": " + ex.getMessage());
            System.exit(0);
        }
        return faxes;
    }
}
