package main.java.dao;

import main.Dbc;
import main.java.dbo.VacansyDbo;

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

/**
 * Created by on 21.05.2018.
 */
public class VacansyDao {

    public static VacansyDbo getOrgVacansyById(Long vcId){
        Dbc c = new Dbc();
        DateFormat format = new SimpleDateFormat("dd MMMMMMMMM yyyy", Locale.getDefault());
        VacansyDbo rdbo = new VacansyDbo();
        PreparedStatement stmt;
        c.connect();
        try{
            stmt = c.createPreparedStatement("select * from org_vacansys where vc_id = ?");
            stmt.setLong(1, vcId);
            ResultSet rs = stmt.executeQuery();
            while(rs.next()){
                rdbo.setProfileorgId(rs.getLong("profileorg_id"));
                rdbo.setAboutvc(rs.getString("about"));
                rdbo.setTitlevc(rs.getString("name_vacansys"));
                rdbo.setDemands(rs.getString("demands"));
                rdbo.setSfrom(rs.getLong("salary_from"));
                rdbo.setSto(rs.getLong("salary_to"));
                rdbo.setStype(rs.getLong("salary_type"));
                rdbo.setIsPublicvc(rs.getBoolean("ispublic"));
                rdbo.setVcId(rs.getLong("vc_id"));
                rdbo.setVcName(rs.getString("contactname"));
                rdbo.setVcSecName(rs.getString("contactsecname"));
                rdbo.setVcEmail(rs.getString("contactemail"));
                rdbo.setVcTeleph(rs.getString("contactteleph"));
                rdbo.setAdress(rs.getString("adress"));
                rdbo.setDatePublishedvc(format.format(rs.getDate("date")));
            }
            c.close();
        } catch (Exception ex){
            ex.printStackTrace();
            System.err.println(ex.getClass().getName() + ": " + ex.getMessage());
        }
        return rdbo;
    }

    public static void incrementWatches(Long vcId){
        Dbc c = new Dbc();
        PreparedStatement stmt;
        c.connect();
        try{
            stmt = c.createPreparedStatement("update org_vacansys set watches = watches + 1 where vc_id = ?");
            stmt.setLong(1, vcId);
            stmt.executeUpdate();
            c.commit();
            c.close();
        } catch (Exception ex){
            ex.printStackTrace();
            System.err.println(ex.getClass().getName() + ": " + ex.getMessage());
        }
    }

    public static List<VacansyDbo> getOrgVacansy(Long profileorgId){
        Dbc c = new Dbc();
        DateFormat format = new SimpleDateFormat("dd MMMMMMMMM yyyy", Locale.getDefault());
        PreparedStatement stmt;
        List<VacansyDbo> list = new ArrayList<>();
        c.connect();
        try{
            stmt = c.createPreparedStatement("select * from org_vacansys where profileorg_id = ?");
            stmt.setLong(1, profileorgId);
            ResultSet rs = stmt.executeQuery();
            while(rs.next()){
                VacansyDbo rdbo = new VacansyDbo();
                rdbo.setProfileorgId(rs.getLong("profileorg_id"));
                rdbo.setAboutvc(rs.getString("about"));
                rdbo.setTitlevc(rs.getString("name_vacansys"));
                rdbo.setDemands(rs.getString("demands"));
                rdbo.setSfrom(rs.getLong("salary_from"));
                rdbo.setSto(rs.getLong("salary_to"));
                rdbo.setStype(rs.getLong("salary_type"));
                rdbo.setIsPublicvc(rs.getBoolean("ispublic"));
                rdbo.setVcId(rs.getLong("vc_id"));
                rdbo.setVcName(rs.getString("contactname"));
                rdbo.setVcSecName(rs.getString("contactsecname"));
                rdbo.setVcEmail(rs.getString("contactemail"));
                rdbo.setVcTeleph(rs.getString("contactteleph"));
                rdbo.setAdress(rs.getString("adress"));
                rdbo.setDatePublishedvc(format.format(rs.getDate("date")));
                rdbo.setWatches(rs.getLong("watches"));
                list.add(rdbo);
            }
            c.close();
        } catch (Exception ex){
            ex.printStackTrace();
            System.err.println(ex.getClass().getName() + ": " + ex.getMessage());
        }
        return list;
    }

    public static List<VacansyDbo> getLastVacansies(){
        Dbc c = new Dbc();
        DateFormat format = new SimpleDateFormat("dd MMMMMMMMM yyyy", Locale.getDefault());
        PreparedStatement stmt;
        List<VacansyDbo> list = new ArrayList<>();
        c.connect();
        try{
            stmt = c.createPreparedStatement("select * from org_vacansys where ispublic order by date limit 10");;
            ResultSet rs = stmt.executeQuery();
            while(rs.next()){
                VacansyDbo rdbo = new VacansyDbo();
                rdbo.setProfileorgId(rs.getLong("profileorg_id"));
                rdbo.setAboutvc(rs.getString("about"));
                rdbo.setTitlevc(rs.getString("name_vacansys"));
                rdbo.setDemands(rs.getString("demands"));
                rdbo.setSfrom(rs.getLong("salary_from"));
                rdbo.setSto(rs.getLong("salary_to"));
                rdbo.setStype(rs.getLong("salary_type"));
                rdbo.setIsPublicvc(rs.getBoolean("ispublic"));
                rdbo.setVcId(rs.getLong("vc_id"));
                rdbo.setVcName(rs.getString("contactname"));
                rdbo.setVcSecName(rs.getString("contactsecname"));
                rdbo.setVcEmail(rs.getString("contactemail"));
                rdbo.setVcTeleph(rs.getString("contactteleph"));
                rdbo.setAdress(rs.getString("adress"));
                rdbo.setDatePublishedvc(format.format(rs.getDate("date")));
                rdbo.setWatches(rs.getLong("watches"));
                list.add(rdbo);
            }
            c.close();
        } catch (Exception ex){
            ex.printStackTrace();
            System.err.println(ex.getClass().getName() + ": " + ex.getMessage());
        }
        return list;
    }

    public static void publishVacansy(Long vcId){
        Dbc c = new Dbc();
        PreparedStatement stmt;
        c.connect();
        try{
            stmt = c.createPreparedStatement("update org_vacansys set ispublic = true where vc_id = ?");
            stmt.setLong(1, vcId);
            stmt.executeUpdate();
            c.commit();
            c.close();
        } catch (Exception ex){
            ex.printStackTrace();
            System.err.println(ex.getClass().getName() + ": " + ex.getMessage());
        }
    }

    public static Long createVacansy(VacansyDbo rdbo){
        Dbc c = new Dbc();
        PreparedStatement stmt;
        Long lastId = 0L;
        c.connect();
        try{
            stmt = c.createPreparedStatement("insert into org_vacansys values(" +
                    "?," + //profileorg_id 1
                    "?," + //about 2
                    "?," + //title 3
                    "?," + //demands 4
                    "?," + //sfrom 5
                    "?," + //sto 6
                    "?," + //stype 7
                    "?," + //is_public 8
                    "generate_id()," + //cv_id
                    "?," + //cname 9
                    "?," + //csn 10
                    "?," + //cemail 11
                    "?," + //ctel 12
                    "?," + //adr 13
                    "?," + //date 14
                    "false," +
                    "0" +

                    ")");
            stmt.setLong(1, rdbo.getProfileorgId());
            stmt.setString(2, rdbo.getAboutvc());
            stmt.setString(3, rdbo.getTitlevc());
            stmt.setString(4, rdbo.getDemands());
            stmt.setLong(5, rdbo.getSfrom());
            stmt.setLong(6, rdbo.getSto());
            stmt.setLong(7, rdbo.getStype());
            stmt.setBoolean(8, rdbo.getIsPublicvc());

            stmt.setString(9, rdbo.getVcName());
            stmt.setString(10, rdbo.getVcSecName());
            stmt.setString(11, rdbo.getVcEmail());
            stmt.setString(12, rdbo.getVcTeleph());
            stmt.setString(13, rdbo.getAdress());
            stmt.setDate(14, Date.valueOf(LocalDate.parse(rdbo.getDatePublishedvc(), DateTimeFormatter.ofPattern("dd.MM.yyyy"))));

            stmt.executeUpdate();
            c.commit();
            stmt = c.createPreparedStatement("select vc_id from org_vacansys order by vc_id desc limit 1");
            ResultSet rs = stmt.executeQuery();
            while(rs.next()){
                lastId = rs.getLong("vc_id");
            }
            c.close();
        } catch (Exception ex){
            ex.printStackTrace();
            System.err.println(ex.getClass().getName() + ": " + ex.getMessage());
            return 1L;
        }
        return lastId;
    }

    public static void updateVacansy(VacansyDbo rdbo){
        Dbc c = new Dbc();
        PreparedStatement stmt;
        c.connect();
        try{
            stmt = c.createPreparedStatement("update org_vacansys set " +
                    "profileorg_id = ?," + //profile_id 1
                    "about = ?," + //about 2
                    "name_vacansys = ?," + //title 3
                    "demands = ?," + //demands 4
                    "salary_from = ?," + //sfrom 5
                    "salary_to = ?," + //sto 6
                    "salary_type = ?," + //stype 7
                    "ispublic = ?," + //ispubl 8
                    "vc_id = ?," + //vc_id 9
                    "contactname = ?," + //cname 10
                    "contactsecname = ?," + //csecname 11
                    "contactemail = ?," + //cemail 12
                    "contactteleph = ?," + // cteleph 13
                    "adress = ?" + //adress 14

                    "where vc_id = ?"  //cvId 15
                    );

            stmt.setLong(1, rdbo.getProfileorgId());
            stmt.setString(2, rdbo.getAboutvc());
            stmt.setString(3, rdbo.getTitlevc());
            stmt.setString(4, rdbo.getDemands());
            stmt.setLong(5, rdbo.getSfrom());
            stmt.setLong(6, rdbo.getSto());
            stmt.setLong(7, rdbo.getStype());
            stmt.setBoolean(8, rdbo.getIsPublicvc());
            stmt.setLong(9, rdbo.getVcId());
            stmt.setString(10, rdbo.getVcName());
            stmt.setString(11, rdbo.getVcSecName());
            stmt.setString(12, rdbo.getVcEmail());
            stmt.setString(13, rdbo.getVcTeleph());
            stmt.setString(14, rdbo.getAdress());
            stmt.setLong(15, rdbo.getVcId());
            stmt.executeUpdate();
            c.commit();
            c.close();
        } catch (Exception ex){
            ex.printStackTrace();
            System.err.println(ex.getClass().getName() + ": " + ex.getMessage());
        }
    }

    public static void deleteOrgVc(Long vcId){
        Dbc c = new Dbc();
        PreparedStatement stmt;
        c.connect();
        try{
            stmt = c.createPreparedStatement(
                    "DELETE FROM VACANSY_REPLY WHERE VC_ID = ?"
            );
            stmt.setLong(1, vcId);
            stmt.executeUpdate();
            
            stmt = c.createPreparedStatement(
                    "delete from tags_vc where vc_id=?"
            );
            stmt.setLong(1, vcId);
            stmt.executeUpdate();
            stmt = c.createPreparedStatement(
                    "delete from org_vacansys where vc_id=?"
            );
            stmt.setLong(1, vcId);
            stmt.executeUpdate();
            c.commit();
            c.close();
        } catch (Exception ex){
            ex.printStackTrace();
            System.err.println(ex.getClass().getName() + ": " + ex.getMessage());
        }

    }

}
