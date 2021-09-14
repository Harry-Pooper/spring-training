package main.java.dao;

import main.Dbc;
import main.java.service.CommonService;
import main.java.service.UserRegistrationService;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.util.ArrayList;
import java.util.List;

/**
 * Created by Ваня on 17.05.2018.
 */
public class TagsDao {

    public static void createTag(List<String> tags){
        Dbc c = new Dbc();
        PreparedStatement stmt;
        c.connect();
        try{
            for(String tag : tags) {
                stmt = c.createPreparedStatement(
                        "INSERT INTO TAGS VALUES(" +
                                "GENERATE_ID()," +
                                "?) " +
                           " ON CONFLICT DO NOTHING"
                );
                stmt.setString(1, tag);
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

    public static void createTagsForVc(List<String> tags, Long vcId){
        Dbc c = new Dbc();
        PreparedStatement stmt;
        c.connect();
        try{
            CommonService.distinctList(tags);
            for(String tag : tags) {
                stmt = c.createPreparedStatement(
                        "INSERT INTO TAGS_VC SELECT TAG_ID, ? FROM TAGS WHERE TAG_INFO = ?"
                );
                stmt.setLong(1, vcId);
                stmt.setString(2, tag);
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

    public static void createTagsForCv(List<String> tags, Long cvId){
        Dbc c = new Dbc();
        PreparedStatement stmt;
        c.connect();
        try{
            CommonService.distinctList(tags);
            for(String tag : tags) {
                stmt = c.createPreparedStatement(
                        "INSERT INTO TAGS_CV SELECT TAG_ID, ? FROM TAGS WHERE TAG_INFO = ?"
                );
                stmt.setLong(1, cvId);
                stmt.setString(2, tag);
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

    public static void createTagsForUser(List<String> tags, Long profileId){
        Dbc c = new Dbc();
        PreparedStatement stmt;
        c.connect();
        try{
            CommonService.distinctList(tags);
            for(String tag : tags) {
                stmt = c.createPreparedStatement(
                        "INSERT INTO TAGS_USER SELECT ?, TAG_ID FROM TAGS WHERE TAG_INFO = ?"
                );
                stmt.setLong(1, profileId);
                stmt.setString(2, tag);
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

    public static void deleteTagsForVc(Long vcId){
        Dbc c = new Dbc();
        PreparedStatement stmt;
        c.connect();
        try{
            stmt = c.createPreparedStatement(
                    "DELETE FROM TAGS_VC WHERE VC_ID = ?"
            );
            stmt.setLong(1, vcId);
            stmt.executeUpdate();
            c.commit();
            c.close();
        } catch (Exception ex){
            ex.printStackTrace();
            System.err.println(ex.getClass().getName() + ": " + ex.getMessage());
            System.exit(0);
        }
    }

    public static void deleteTagsForCv(Long cvId){
        Dbc c = new Dbc();
        PreparedStatement stmt;
        c.connect();
        try{
            stmt = c.createPreparedStatement(
                    "DELETE FROM TAGS_CV WHERE CV_ID = ?"
            );
            stmt.setLong(1, cvId);
            stmt.executeUpdate();
            c.commit();
            c.close();
        } catch (Exception ex){
            ex.printStackTrace();
            System.err.println(ex.getClass().getName() + ": " + ex.getMessage());
            System.exit(0);
        }
    }

    public static void deleteTagsForUser(Long profileId){
        Dbc c = new Dbc();
        PreparedStatement stmt;
        c.connect();
        try{
            stmt = c.createPreparedStatement(
                    "DELETE FROM TAGS_USER WHERE PROFILE_ID = ?"
            );
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

    public static List<String> getCvTags(Long cvId){
        Dbc c = new Dbc();
        PreparedStatement stmt;
        List<String> tags = new ArrayList<>();
        c.connect();
        try{
            stmt = c.createPreparedStatement(
                    "SELECT TAG_INFO FROM TAGS, TAGS_CV WHERE TAGS.TAG_ID = TAGS_CV.TAG_ID AND TAGS_CV.CV_ID = ?"
            );
            stmt.setLong(1, cvId);
            ResultSet rs = stmt.executeQuery();
            while(rs.next()){
                tags.add(rs.getString("TAG_INFO"));
            }
            c.close();
        } catch (Exception ex){
            ex.printStackTrace();
            System.err.println(ex.getClass().getName() + ": " + ex.getMessage());
            System.exit(0);
        }
        return tags;
    }

    public static List<String> getVcTags(Long vcId){
        Dbc c = new Dbc();
        PreparedStatement stmt;
        List<String> tags = new ArrayList<>();
        c.connect();
        try{
            stmt = c.createPreparedStatement(
                    "SELECT TAG_INFO FROM TAGS, TAGS_VC WHERE TAGS.TAG_ID = TAGS_VC.TAG_ID AND TAGS_VC.VC_ID = ?"
            );
            stmt.setLong(1, vcId);
            ResultSet rs = stmt.executeQuery();
            while(rs.next()){
                tags.add(rs.getString("TAG_INFO"));
            }
            c.close();
        } catch (Exception ex){
            ex.printStackTrace();
            System.err.println(ex.getClass().getName() + ": " + ex.getMessage());
            System.exit(0);
        }
        return tags;
    }


    public static List<String> getUserTags(Long profileId){
        Dbc c = new Dbc();
        PreparedStatement stmt;
        List<String> tags = new ArrayList<>();
        c.connect();
        try{
            stmt = c.createPreparedStatement(
                    "SELECT TAG_INFO FROM TAGS, TAGS_USER WHERE TAGS.TAG_ID = TAGS_USER.TAG_ID AND TAGS_USER.PROFILE_ID = ?"
            );
            stmt.setLong(1, profileId);
            ResultSet rs = stmt.executeQuery();
            while(rs.next()){
                tags.add(rs.getString("TAG_INFO"));
            }
            c.close();
        } catch (Exception ex){
            ex.printStackTrace();
            System.err.println(ex.getClass().getName() + ": " + ex.getMessage());
            System.exit(0);
        }
        return tags;
    }
}
