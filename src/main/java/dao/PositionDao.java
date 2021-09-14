package main.java.dao;

import main.Dbc;
import main.java.dbo.PaymentDbo;
import main.java.dbo.PositionDbo;
import main.java.dbo.ResumeDbo;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.List;
import java.util.Locale;

/**
 * Created by Ваня on 26.05.2018.
 */
public class PositionDao {

    public static List<PositionDbo> getPositions(){
        Dbc c = new Dbc();
        PreparedStatement stmt;
        List<PositionDbo> list = new ArrayList<>();
        c.connect();
        try{
            stmt = c.createPreparedStatement("select * from position");
            ResultSet rs = stmt.executeQuery();
            while(rs.next()){
                PositionDbo positionDbo = new PositionDbo();
                positionDbo.setPositionId(rs.getLong("position_id"));
                positionDbo.setPositionName(rs.getString("position_name"));
                list.add(positionDbo);
            }
            c.close();
        } catch (Exception ex){
            ex.printStackTrace();
            System.err.println(ex.getClass().getName() + ": " + ex.getMessage());
        }
        return list;
    }

    public static PositionDbo getPositionForCv(Long cvId){
        Dbc c = new Dbc();
        PreparedStatement stmt;
        PositionDbo pd = new PositionDbo();
        c.connect();
        try{
            stmt = c.createPreparedStatement("select * from position p, position_cv pc where pc.cv_id = ? and pc.position_id = p.position_id");
            stmt.setLong(1, cvId);
            ResultSet rs = stmt.executeQuery();
            while(rs.next()){
                pd.setPositionId(rs.getLong("position_id"));
                pd.setPositionName(rs.getString("position_name"));
            }
            c.close();
        } catch (Exception ex){
            ex.printStackTrace();
            System.err.println(ex.getClass().getName() + ": " + ex.getMessage());
        }
        return pd;
    }

    public static List<PositionDbo> getUserPositions(Long profileId){
        Dbc c = new Dbc();
        PreparedStatement stmt;
        List<PositionDbo> list = new ArrayList<>();
        c.connect();
        try{
            stmt = c.createPreparedStatement("select p.position_id, p.position_name from position p, position_user pu where p.position_id = pu.position_id and pu.profile_id = ?");
            stmt.setLong(1, profileId);
            ResultSet rs = stmt.executeQuery();
            while(rs.next()){
                PositionDbo positionDbo = new PositionDbo();
                positionDbo.setPositionId(rs.getLong("position_id"));
                positionDbo.setPositionName(rs.getString("position_name"));
                list.add(positionDbo);
            }
            c.close();
        } catch (Exception ex){
            ex.printStackTrace();
            System.err.println(ex.getClass().getName() + ": " + ex.getMessage());
        }
        return list;
    }

    public static void deletePositionsForUser(Long profileId){
        Dbc c = new Dbc();
        PreparedStatement stmt;
        c.connect();
        try{
            stmt = c.createPreparedStatement("delete from position_user where profile_id = ?");
            stmt.setLong(1, profileId);
            stmt.executeUpdate();
            c.commit();
            c.close();
        } catch (Exception ex){
            ex.printStackTrace();
            System.err.println(ex.getClass().getName() + ": " + ex.getMessage());
        }
    }

    public static void deletePositionsForCv(Long cvId){
        Dbc c = new Dbc();
        PreparedStatement stmt;
        c.connect();
        try{
            stmt = c.createPreparedStatement("delete from position_cv where cv_id = ?");
            stmt.setLong(1, cvId);
            stmt.executeUpdate();
            c.commit();
            c.close();
        } catch (Exception ex){
            ex.printStackTrace();
            System.err.println(ex.getClass().getName() + ": " + ex.getMessage());
        }
    }

    public static void createPositionsForCv(Long cvId, Long positionId){
        Dbc c = new Dbc();
        PreparedStatement stmt;
        c.connect();
        try{
            stmt = c.createPreparedStatement("insert into position_cv values (?, ?)");
            stmt.setLong(1, positionId);
            stmt.setLong(2, cvId);
            stmt.executeUpdate();
            c.commit();
            c.close();
        } catch (Exception ex){
            ex.printStackTrace();
            System.err.println(ex.getClass().getName() + ": " + ex.getMessage());
        }
    }

    public static void createPositionsForUser(Long profileId, List<String> positions){
        Dbc c = new Dbc();
        PreparedStatement stmt;
        c.connect();
        try{
            for(String s : positions){
                stmt = c.createPreparedStatement("insert into position_user values (?, ?)");
                stmt.setLong(1, Long.valueOf(s));
                stmt.setLong(2, profileId);
                stmt.executeUpdate();
            }
            c.commit();
            c.close();
        } catch (Exception ex){
            ex.printStackTrace();
            System.err.println(ex.getClass().getName() + ": " + ex.getMessage());
        }
    }
}
