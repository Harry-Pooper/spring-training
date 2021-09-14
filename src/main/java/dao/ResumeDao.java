package main.java.dao;

import main.Dbc;
import main.java.dbo.RegistrationDbo;
import main.java.dbo.ResumeDbo;

import java.sql.Date;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.List;
import java.util.Locale;
import java.util.Objects;

/**
 * Created by Ваня on 21.05.2018.
 */
public class ResumeDao {

    public static ResumeDbo getUserResumeById(Long cvId){
        Dbc c = new Dbc();
        DateFormat format = new SimpleDateFormat("dd MMMMMMMMM yyyy", Locale.getDefault());
        ResumeDbo rdbo = new ResumeDbo();
        PreparedStatement stmt;
        c.connect();
        try{
            stmt = c.createPreparedStatement("select * from cv where cv_id = ?");
            stmt.setLong(1, cvId);
            ResultSet rs = stmt.executeQuery();
            while(rs.next()){
                rdbo.setProfileId(rs.getLong("profile_id"));
                rdbo.setCvId(rs.getLong("cv_id"));
                rdbo.setPaymentId(rs.getLong("payment_id"));
                rdbo.setCvImage(rs.getString("cv_image"));
                rdbo.setPhone(rs.getString("phone"));
                rdbo.setEmail(rs.getString("email"));
                rdbo.setWatches(rs.getLong("watches"));
                rdbo.setDegree(rs.getLong("degree"));
                rdbo.setCanMove(rs.getLong("can_move"));
                rdbo.setBusinessTrip(rs.getLong("business_trip"));
                rdbo.setExperience(rs.getLong("experience"));
                rdbo.setIsPublic(rs.getBoolean("is_public"));
                rdbo.setDatePublished(format.format(rs.getDate("date_published")));
                rdbo.setAbout(rs.getString("about"));
                rdbo.setTitle(rs.getString("title"));
            }
            c.close();
        } catch (Exception ex){
            ex.printStackTrace();
            System.err.println(ex.getClass().getName() + ": " + ex.getMessage());
        }
        return rdbo;
    }

    public static void incrementWatches(Long cvId){
        Dbc c = new Dbc();
        PreparedStatement stmt;
        c.connect();
        try{
            stmt = c.createPreparedStatement("update cv set watches = watches + 1 where cv_id = ?");
            stmt.setLong(1, cvId);
            stmt.executeUpdate();
            c.commit();
            c.close();
        } catch (Exception ex){
            ex.printStackTrace();
            System.err.println(ex.getClass().getName() + ": " + ex.getMessage());
        }
    }

    public static List<ResumeDbo> getPublicResume(Long profileId){
        Dbc c = new Dbc();
        DateFormat format = new SimpleDateFormat("dd MMMMMMMMM yyyy", Locale.getDefault());
        PreparedStatement stmt;
        List<ResumeDbo> list = new ArrayList<>();
        c.connect();
        try{
            stmt = c.createPreparedStatement("select * from cv where profile_id = ? and is_public = true");
            stmt.setLong(1, profileId);
            ResultSet rs = stmt.executeQuery();
            while(rs.next()){
                ResumeDbo rdbo = new ResumeDbo();
                rdbo.setProfileId(rs.getLong("profile_id"));
                rdbo.setCvId(rs.getLong("cv_id"));
                rdbo.setPaymentId(rs.getLong("payment_id"));
                rdbo.setCvImage(rs.getString("cv_image"));
                rdbo.setPhone(rs.getString("phone"));
                rdbo.setEmail(rs.getString("email"));
                rdbo.setWatches(rs.getLong("watches"));
                rdbo.setDegree(rs.getLong("degree"));
                rdbo.setCanMove(rs.getLong("can_move"));
                rdbo.setBusinessTrip(rs.getLong("business_trip"));
                rdbo.setExperience(rs.getLong("experience"));
                rdbo.setIsPublic(rs.getBoolean("is_public"));
                rdbo.setDatePublished(format.format(rs.getDate("date_published")));
                rdbo.setAbout(rs.getString("about"));
                rdbo.setTitle(rs.getString("title"));
                list.add(rdbo);
            }
            c.close();
        } catch (Exception ex){
            ex.printStackTrace();
            System.err.println(ex.getClass().getName() + ": " + ex.getMessage());
        }
        return list;
    }

    public static List<ResumeDbo> getUserResume(Long profileId){
        Dbc c = new Dbc();
        DateFormat format = new SimpleDateFormat("dd MMMMMMMMM yyyy", Locale.getDefault());
        PreparedStatement stmt;
        List<ResumeDbo> list = new ArrayList<>();
        c.connect();
        try{
            stmt = c.createPreparedStatement("select * from cv where profile_id = ?");
            stmt.setLong(1, profileId);
            ResultSet rs = stmt.executeQuery();
            while(rs.next()){
                ResumeDbo rdbo = new ResumeDbo();
                rdbo.setProfileId(rs.getLong("profile_id"));
                rdbo.setCvId(rs.getLong("cv_id"));
                rdbo.setPaymentId(rs.getLong("payment_id"));
                rdbo.setCvImage(rs.getString("cv_image"));
                rdbo.setPhone(rs.getString("phone"));
                rdbo.setEmail(rs.getString("email"));
                rdbo.setWatches(rs.getLong("watches"));
                rdbo.setDegree(rs.getLong("degree"));
                rdbo.setCanMove(rs.getLong("can_move"));
                rdbo.setBusinessTrip(rs.getLong("business_trip"));
                rdbo.setExperience(rs.getLong("experience"));
                rdbo.setIsPublic(rs.getBoolean("is_public"));
                rdbo.setDatePublished(format.format(rs.getDate("date_published")));
                rdbo.setAbout(rs.getString("about"));
                rdbo.setTitle(rs.getString("title"));
                list.add(rdbo);
            }
            c.close();
        } catch (Exception ex){
            ex.printStackTrace();
            System.err.println(ex.getClass().getName() + ": " + ex.getMessage());
        }
        return list;
    }

    public static List<Long> searchByQuery(String query, String type){
        Dbc c = new Dbc();
        PreparedStatement stmt;
        List<Long> res = new ArrayList<>();
        c.connect();
        try{
            stmt = c.createPreparedStatement(query);
            ResultSet rs = stmt.executeQuery();
            while(rs.next()){
                if(Objects.equals(type, "resume")) {
                    res.add(rs.getLong("cv_id"));
                } else {
                    res.add(rs.getLong("vc_id"));
                }
            }
            c.close();
        } catch (Exception ex){
            ex.printStackTrace();
            System.err.println(ex.getClass().getName() + ": " + ex.getMessage());
        }
        return res;
    }

    public static void publishResume(Long cvId){
        Dbc c = new Dbc();
        PreparedStatement stmt;
        c.connect();
        try{
            stmt = c.createPreparedStatement("update cv set is_public = true where cv_id = ?");
            stmt.setLong(1, cvId);
            stmt.executeUpdate();
            c.commit();
            c.close();
        } catch (Exception ex){
            ex.printStackTrace();
            System.err.println(ex.getClass().getName() + ": " + ex.getMessage());
        }
    }

    public static Long createResume(ResumeDbo rdbo){
        Dbc c = new Dbc();
        PreparedStatement stmt;
        Long lastId = 0L;
        c.connect();
        try{
            stmt = c.createPreparedStatement("insert into cv values(" +
                    "?," + //profile_id 1
                    "generate_id()," + //cv_id
                    "?," + //payment_id 2
                    "null," + //cv_image
                    "?," + //phone 3
                    "?," + //email 4
                    "?," + //watches 5
                    "?," + //degree 6
                    "0," + //can_move
                    "?," + //business_tip 7
                    "?," + //experience 8
                    "?," + //is_public 9
                    "?," + //date_published 10
                    "?," + //about 11
                    "?" + //title 12
                    ")");
            stmt.setLong(1, rdbo.getProfileId());
            stmt.setLong(2, rdbo.getPaymentId());
            stmt.setString(3, rdbo.getPhone());
            stmt.setString(4, rdbo.getEmail());
            stmt.setLong(5, rdbo.getWatches());
            stmt.setLong(6, rdbo.getDegree());
            stmt.setLong(7, rdbo.getBusinessTrip());
            stmt.setLong(8, rdbo.getExperience());
            stmt.setBoolean(9, rdbo.getIsPublic());
            stmt.setDate(10, Date.valueOf(LocalDate.parse(rdbo.getDatePublished(), DateTimeFormatter.ofPattern("dd.MM.yyyy"))));
            stmt.setString(11, rdbo.getAbout());
            stmt.setString(12, rdbo.getTitle());
            stmt.executeUpdate();
            c.commit();
            stmt = c.createPreparedStatement("select cv_id from cv order by cv_id desc limit 1");
            ResultSet rs = stmt.executeQuery();
            while(rs.next()){
                lastId = rs.getLong("cv_id");
            }
            c.close();
        } catch (Exception ex){
            ex.printStackTrace();
            System.err.println(ex.getClass().getName() + ": " + ex.getMessage());
            return 1L;
        }
        return lastId;
    }

    public static void updateResume(ResumeDbo rdbo){
        Dbc c = new Dbc();
        PreparedStatement stmt;
        c.connect();
        try{
            stmt = c.createPreparedStatement("update cv set " +
                    "profile_id = ?," + //profile_id 1
                    "cv_id = ?," + //cv_id 2
                    "payment_id = ?," + //payment_id 3
                    "cv_image = ?," + //cv_image 4
                    "phone = ?," + //phone 5
                    "email = ?," + //email 6
                    "watches = ?," + //watches 7
                    "degree = ?," + //degree 8
                    "can_move = 0," + //can_move
                    "business_trip = ?," + //business_tip 9
                    "experience = ?," + //experience 10
                    "is_public = ?," + //is_public 11
                    "about = ?," + //about 12
                    "title = ? " + //title 13
                    "where cv_id = ?"  //cvId 13
                    );
            stmt.setLong(1, rdbo.getProfileId());
            stmt.setLong(2, rdbo.getCvId());
            stmt.setLong(3, rdbo.getPaymentId());
            stmt.setString(4, rdbo.getCvImage());
            stmt.setString(5, rdbo.getPhone());
            stmt.setString(6, rdbo.getEmail());
            stmt.setLong(7, rdbo.getWatches());
            stmt.setLong(8, rdbo.getDegree());
            stmt.setLong(9, rdbo.getBusinessTrip());
            stmt.setLong(10, rdbo.getExperience());
            stmt.setBoolean(11, rdbo.getIsPublic());
            stmt.setString(12, rdbo.getAbout());
            stmt.setString(13, rdbo.getTitle());
            stmt.setLong(14, rdbo.getCvId());
            stmt.executeUpdate();
            c.commit();
            c.close();
        } catch (Exception ex){
            ex.printStackTrace();
            System.err.println(ex.getClass().getName() + ": " + ex.getMessage());
        }
    }

    public static void updateCvPicUrl(String photoUrl, Long cvId){
        Dbc c = new Dbc();
        PreparedStatement stmt;
        c.connect();
        try{
            stmt = c.createPreparedStatement(
                    "UPDATE CV " +
                            "SET CV_IMAGE = ?" + //1
                            "WHERE CV_ID = ?" //2
            );
            stmt.setString(1, photoUrl);
            stmt.setLong(2, cvId);
            stmt.executeUpdate();
            c.commit();
            c.close();
        } catch (Exception ex){
            ex.printStackTrace();
            System.err.println(ex.getClass().getName() + ": " + ex.getMessage());
            System.exit(0);
        }
    }

    public static void deleteUserCv(Long cvId){
        Dbc c = new Dbc();
        PreparedStatement stmt;
        c.connect();
        try{
            stmt = c.createPreparedStatement(
                    "DELETE FROM VACANSY_REPLY WHERE CV_ID = ?"
            );
            stmt.setLong(1, cvId);
            stmt.executeUpdate();
            stmt = c.createPreparedStatement(
                    "delete from position_cv where cv_id=?"
            );
            stmt.setLong(1, cvId);
            stmt.executeUpdate();
            stmt = c.createPreparedStatement(
                    "delete from tags_cv where cv_id=?"
            );
            stmt.setLong(1, cvId);
            stmt.executeUpdate();
            stmt = c.createPreparedStatement(
                    "select payment_id from cv where cv_id=?"
            );
            stmt.setLong(1, cvId);
            ResultSet rs = stmt.executeQuery();
            Long paymentId = 0L;
            while(rs.next()){
                paymentId = rs.getLong("payment_id");
            }
            stmt = c.createPreparedStatement(
                    "delete from cv where cv_id=?"
            );
            stmt.setLong(1, cvId);
            stmt.executeUpdate();
            stmt = c.createPreparedStatement(
                    "delete from payment where payment_id = ?"
            );
            stmt.setLong(1, paymentId);
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
