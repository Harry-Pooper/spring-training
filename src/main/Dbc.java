package main;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.Statement;

/**
 * Created by mrlz on 27.03.2018.
 */
public class Dbc {

    private Connection c;

    public void connect() {
        try{
            Class.forName("org.postgresql.Driver");
            c = DriverManager.getConnection("jdbc:postgresql://localhost:5432/JobToad", "postgres", "admin");
            c.setAutoCommit(false);
        } catch (Exception ex) {
            ex.printStackTrace();
            System.err.println(ex.getClass().getName() + ": " + ex.getMessage());
            System.exit(0);
        }
    }

    public PreparedStatement createPreparedStatement(String s){
        PreparedStatement stmt = null;
        try {
            stmt = this.c.prepareStatement(s);
        } catch (Exception ex) {
            ex.printStackTrace();
            System.err.println(ex.getClass().getName() + ": " + ex.getMessage());
            System.exit(0);
        }
        return stmt;
    }

    public void commit(){
        try {
            this.c.commit();
        } catch (Exception ex) {
            ex.printStackTrace();
            System.err.println(ex.getClass().getName() + ": " + ex.getMessage());
            System.exit(0);
        }
    }

    public Statement createStatement(){
        Statement stmt = null;
        try {
            stmt = this.c.createStatement();
        } catch (Exception ex) {
            ex.printStackTrace();
            System.err.println(ex.getClass().getName() + ": " + ex.getMessage());
            System.exit(0);
        }
        return stmt;
    }

    public void close(){
        try {
            this.c.close();
        } catch (Exception ex){
            ex.printStackTrace();
            System.err.println(ex.getClass().getName() + ": " + ex.getMessage());
            System.exit(0);
        }
    }

}
