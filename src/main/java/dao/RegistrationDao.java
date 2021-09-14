package main.java.dao;

import main.Dbc;
import main.java.dbo.MainPageDbo;
import main.java.dbo.RegistrationDbo;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.Statement;

/**
 * Created by mrlz on 31.03.2018.
 */
public class RegistrationDao {

    public int getLogin(String login){
        Dbc c = new Dbc();
        Statement stmt = null;
        int count = 0;
        c.connect();

        try {
            stmt = c.createStatement();
            ResultSet rs = stmt.executeQuery("select count(*) as a from auth where login = " + login);
            count = rs.getInt("a");
            c.close();
        } catch (Exception ex){
            ex.printStackTrace();
            System.err.println(ex.getClass().getName() + ": " + ex.getMessage());
            System.exit(0);
        }
        return count;
    }

    public static RegistrationDbo getRegistrationInfoByLogin(String login){
        Dbc c = new Dbc();
        Statement stmt;
        RegistrationDbo rdbo = new RegistrationDbo();
        c.connect();
        try {
            stmt = c.createStatement();
            ResultSet rs = stmt.executeQuery("select *, count(*) as a from auth where login = '" + login + "' group by auth.account_id");
            while(rs.next()){
                rdbo.setAccountId(rs.getLong("account_id"));
                rdbo.setLogin(rs.getString("login"));
                rdbo.setPassword(rs.getString("password"));
                rdbo.setEmail(rs.getString("email"));
                rdbo.setSecretQuestion(rs.getString("secret_question"));
                rdbo.setSecretAnswer(rs.getString("secret_answer"));
                rdbo.setAccType(rs.getLong("type"));
            }
            c.close();
        } catch (Exception ex){
            ex.printStackTrace();
            System.err.println(ex.getClass().getName() + ": " + ex.getMessage());
            System.exit(0);
        }

        return rdbo;
    }

    public static void registerUser(RegistrationDbo rdbo){
        Dbc c = new Dbc();
        PreparedStatement stmt;
        c.connect();
        try{
            stmt = c.createPreparedStatement("INSERT INTO auth VALUES( GENERATE_ID(), ?, ?, ?, ?, ?, ?)");
            stmt.setString(1, rdbo.getLogin());
            stmt.setString(2, rdbo.getPassword());
            stmt.setString(3, rdbo.getEmail());
            stmt.setString(4, rdbo.getSecretQuestion());
            stmt.setString(5, rdbo.getSecretAnswer());
            stmt.setLong(6, rdbo.getAccType());
            stmt.executeUpdate();
            stmt.close();
            c.commit();
            c.close();
        } catch (Exception ex){
            ex.printStackTrace();
            System.err.println(ex.getClass().getName() + ": " + ex.getMessage());
            System.exit(0);
        }
    }

    public static Long searchEmail(String email){
        Dbc c = new Dbc();
        Statement stmt;
        Long count = 0L;
        c.connect();
        try{
            stmt = c.createStatement();
            ResultSet rs = stmt.executeQuery("SELECT count(*) as a from auth where email = '" + email + "'");
            while(rs.next()){
                count = rs.getLong("a");
            }
            c.close();
        } catch(Exception ex){
            ex.printStackTrace();
            System.err.println(ex.getClass().getName() + ": " + ex.getMessage());
            System.exit(0);
        }
        return count;
    }

    public static void deleteUser(RegistrationDbo rdbo){
        Dbc c = new Dbc();
        PreparedStatement stmt;
        c.connect();
        try{
            stmt = c.createPreparedStatement("DELETE FROM auth WHERE login = ?");
            stmt.setString(1, rdbo.getLogin());
            stmt.executeUpdate();
            c.commit();
            c.close();
        } catch (Exception ex){
            ex.printStackTrace();
            System.err.println(ex.getClass().getName() + ": " + ex.getMessage());
            System.exit(0);
        }
    }
}
