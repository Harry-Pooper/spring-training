package main.java.dao;

import main.Dbc;
import main.java.dbo.ResponseDbo;
import main.java.dbo.UserDbo;
import main.java.service.UserRegistrationService;

import java.sql.Date;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.List;

/**
 * Created by Ваня on 07.06.2018.
 */
public class ResponseDao {

    public static boolean createResponse(ResponseDbo responseDbo){
        Dbc c = new Dbc();
        PreparedStatement stmt;
        c.connect();
        try{
            stmt = c.createPreparedStatement(
                    "INSERT INTO vacansy_reply VALUES(" +
                            "?," + //1 vcId
                            "?," + //2 cvId
                            "?," + //3 letter
                            "?," + //4 status
                            "?," + //5 date_ent
                            "GENERATE_ID())"
            );
            stmt.setLong(1, responseDbo.getVcId());
            stmt.setLong(2, responseDbo.getCvId());
            stmt.setString(3, responseDbo.getLetter());
            stmt.setLong(4, responseDbo.getStatus());
            stmt.setDate(5, Date.valueOf(LocalDate.parse(responseDbo.getDateSent(), DateTimeFormatter.ofPattern("dd.MM.yyyy"))));
            stmt.executeUpdate();
            c.commit();
            c.close();
        } catch (Exception ex){
            ex.printStackTrace();
            System.err.println(ex.getClass().getName() + ": " + ex.getMessage());
            return false;
        }
        return true;
    }

    public static void changeStatusForResponse(Long status, Long vrId){
        Dbc c = new Dbc();
        PreparedStatement stmt;
        c.connect();
        try{
            stmt = c.createPreparedStatement(
                    "UPDATE VACANSY_REPLY SET STATUS = ? WHERE VR_ID = ?"
            );
            stmt.setLong(1, status);
            stmt.setLong(2, vrId);
            stmt.executeUpdate();
            c.commit();
            c.close();
        } catch (Exception ex){
            ex.printStackTrace();
            System.err.println(ex.getClass().getName() + ": " + ex.getMessage());
        }
    }

    public static void deleteResponseWithVrId(Long vrId){
        Dbc c = new Dbc();
        PreparedStatement stmt;
        c.connect();
        try{
            stmt = c.createPreparedStatement(
                            "DELETE FROM VACANSY_REPLY WHERE VR_ID = ?"
            );
            stmt.setLong(1, vrId);
            stmt.executeUpdate();
            c.commit();
            c.close();
        } catch (Exception ex){
            ex.printStackTrace();
            System.err.println(ex.getClass().getName() + ": " + ex.getMessage());
        }
    }

    public static List<ResponseDbo> getResponsesForUser(Long profileId){
        Dbc c = new Dbc();
        PreparedStatement stmt;
        List<ResponseDbo> list = new ArrayList<>();
        c.connect();
        try{
            stmt = c.createPreparedStatement(
                    "select * from vacansy_reply vp, cv where vp.cv_id = cv.cv_id and cv.profile_id = ?"
            );
            stmt.setLong(1, profileId);
            ResultSet rs = stmt.executeQuery();
            while(rs.next()){
                ResponseDbo responseDbo = new ResponseDbo();
                responseDbo.setVrId(rs.getLong("vr_id"));
                responseDbo.setVcId(rs.getLong("vc_id"));
                responseDbo.setCvId(rs.getLong("cv_id"));
                responseDbo.setLetter(rs.getString("letter"));
                responseDbo.setStatus(rs.getLong("status"));
                responseDbo.setDateSent(rs.getString("date_ent"));
                list.add(responseDbo);
            }
            c.close();
        } catch (Exception ex){
            ex.printStackTrace();
            System.err.println(ex.getClass().getName() + ": " + ex.getMessage());
        }
        return list;
    }

    public static List<ResponseDbo> getResponsesForOrg(Long profileorgId){
        Dbc c = new Dbc();
        PreparedStatement stmt;
        List<ResponseDbo> list = new ArrayList<>();
        c.connect();
        try{
            stmt = c.createPreparedStatement(
                    "select * from vacansy_reply vp, org_vacansys ov where ov.vc_id = vp.vc_id and ov.profileorg_id = ?"
            );
            stmt.setLong(1, profileorgId);
            ResultSet rs = stmt.executeQuery();
            while(rs.next()){
                ResponseDbo responseDbo = new ResponseDbo();
                responseDbo.setVrId(rs.getLong("vr_id"));
                responseDbo.setVcId(rs.getLong("vc_id"));
                responseDbo.setCvId(rs.getLong("cv_id"));
                responseDbo.setLetter(rs.getString("letter"));
                responseDbo.setStatus(rs.getLong("status"));
                responseDbo.setDateSent(rs.getString("date_ent"));
                list.add(responseDbo);
            }
            c.close();
        } catch (Exception ex){
            ex.printStackTrace();
            System.err.println(ex.getClass().getName() + ": " + ex.getMessage());
        }
        return list;
    }

    public static ResponseDbo getResponse(Long vrId){
        Dbc c = new Dbc();
        PreparedStatement stmt;
        ResponseDbo responseDbo = new ResponseDbo();
        c.connect();
        try{
            stmt = c.createPreparedStatement(
                    "select * from vacansy_reply vp where vr_id = ?"
            );
            stmt.setLong(1, vrId);
            ResultSet rs = stmt.executeQuery();
            while(rs.next()){
                responseDbo.setVrId(rs.getLong("vr_id"));
                responseDbo.setVcId(rs.getLong("vc_id"));
                responseDbo.setCvId(rs.getLong("cv_id"));
                responseDbo.setLetter(rs.getString("letter"));
                responseDbo.setStatus(rs.getLong("status"));
                responseDbo.setDateSent(rs.getString("date_ent"));
            }
            c.close();
        } catch (Exception ex){
            ex.printStackTrace();
            System.err.println(ex.getClass().getName() + ": " + ex.getMessage());
        }
        return responseDbo;
    }

}
