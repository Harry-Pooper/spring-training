package main.java.dao;

import main.Dbc;
import main.java.dbo.RegistrationDbo;
import main.java.dbo.OrgDbo;
import main.java.service.OrgRegistrationService;
import main.java.service.UserRegistrationService;

import java.sql.Date;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;

/**
 * Created by mrlz on 02.04.2018.
 */
public class OrgDao {


    public static void createOrgProfile(OrgDbo orgDbo, Long accountId){
        Dbc c = new Dbc();
        PreparedStatement stmt;
        OrgRegistrationService orgRegistrationService = new OrgRegistrationService();
        c.connect();
        try{
            stmt = c.createPreparedStatement(
                    "INSERT INTO org_profile VALUES(GENERATE_ID(), " +
                            "?, " + // 1 accid
                            "?, " + // 2 orgName
                            "?, " + // 3 adress
                            "?, " + // 4 link
                            "?, " +  //5 aboutOrg
                            "?, " + //6 countmem

                            "?, " + // 7 conName
                            "?, " + //8 conSecName
                            "?, " + // 9 conEmail
                            "?, " + //10 conTel
                            "?, " + // 11 conDopTel
                            "?, " + // 12 orgtype
                            "?, " + // 13 tr
                            "?, " + // 14 ca
                            "?, " + // 15 sa
                            "?, " + // 16 sp
                            "?, " + // 17 pr
                            "?," + //18 city-id
                            "?)" //19 imageurl
            );
            stmt.setLong(1, accountId);
            stmt.setString(2, orgDbo.getorgName());
            stmt.setString(3, orgDbo.getAdress());
            stmt.setString(4, orgDbo.getLink());
            stmt.setString(5, orgDbo.getAboutOrg());
            stmt.setLong(6, orgDbo.getcountMem());
            stmt.setLong(7, orgDbo.getOrgType());
            stmt.setLong(8, orgDbo.getCityId());

            stmt.setString(9, orgDbo.getcontactName());
            stmt.setString(10, orgDbo.getcontactSecName());
            stmt.setString(11, orgDbo.getcontactEmail());
            stmt.setString(12, orgDbo.getcontactTeleph());
            stmt.setString(13, orgDbo.getcontactDopTeleph());

            stmt.setLong(14, orgDbo.gettransport());
            stmt.setLong(15, orgDbo.getsalary());
            stmt.setLong(16, orgDbo.getspirit());
            stmt.setLong(17, orgDbo.getprojects());
            stmt.setLong(18, orgDbo.getcareer());

            stmt.setString(19, orgDbo.getImageUrl());

            stmt.executeUpdate();
            c.commit();
            c.close();



        } catch (Exception ex){
            ex.printStackTrace();
            System.err.println(ex.getClass().getName() + ": " + ex.getMessage());
            System.exit(0);
        }
    }

    public static void addConsForOrg(Long orgId, String param){
        Dbc c = new Dbc();
        PreparedStatement stmt;
        c.connect();
        try{
            stmt = c.createPreparedStatement(
                    "UPDATE ORG_PROFILE " +
                            "SET " + param + " = " + param + " + 1 WHERE PROFILEORG_ID = ?"
            );
            stmt.setLong(1, orgId);
            stmt.executeUpdate();
            c.commit();
            c.close();
        } catch (Exception ex){
            ex.printStackTrace();
            System.err.println(ex.getClass().getName() + ": " + ex.getMessage());
            System.exit(0);
        }
    }

    public static void updateOrgProfileInfo(OrgDbo orgDbo, Long profileorgId){
        Dbc c = new Dbc();
        PreparedStatement stmt;
        OrgRegistrationService orgRegistrationService = new OrgRegistrationService();
        c.connect();
        try{
            stmt = c.createPreparedStatement(
                    "UPDATE ORG_PROFILE " +
                            "SET ORG_NAME = ?, " + //1
                            "ADRESS = ?, " + //2
                            "LINK = ?, " + //3
                            "ABOUT_ORG = ?, " + //4
                           "COUNT_MEM = ?, " + //5
                            "ORG_TYPE = ?, " + //6
                            "CITY_ID = ?, " + //9
                            "CONTACTNAME = ?, " + //8
                            "CONTACTSECNAME = ?, " + //8
                            "CONTACTEMAIL = ?, " + //8
                            "CONTACTTELEPH = ?, " + //8
                            "CONTACTDOPTELEPH = ?" + //8

                            "WHERE PROFILEORG_ID = ?" //9
            );
            stmt.setString(1, orgDbo.getorgName());
            stmt.setString(2, orgDbo.getAdress());
            stmt.setString(3, orgDbo.getLink());
            stmt.setString(4, orgDbo.getAboutOrg());
            stmt.setLong(5, orgDbo.getcountMem());
            stmt.setLong(6, orgDbo.getOrgType());
            stmt.setLong(7, orgDbo.getCityId());

            stmt.setString(8, orgDbo.getcontactName());
            stmt.setString(9, orgDbo.getcontactSecName());
            stmt.setString(10, orgDbo.getcontactEmail());
            stmt.setString(11, orgDbo.getcontactTeleph());
            stmt.setString(12, orgDbo.getcontactDopTeleph());

            stmt.setLong(13, profileorgId);
            stmt.executeUpdate();
            c.commit();
            c.close();
        } catch (Exception ex){
            ex.printStackTrace();
            System.err.println(ex.getClass().getName() + ": " + ex.getMessage());
            System.exit(0);
        }
    }

    public static OrgDbo findOrgByProfileId(Long profileorgId){
        Dbc c = new Dbc();
        PreparedStatement stmt;
        OrgDbo orgDbo = new OrgDbo();
        c.connect();
        try{
            stmt = c.createPreparedStatement("SELECT * FROM ORG_PROFILE WHERE PROFILEORG_ID = ?");
            stmt.setLong(1, profileorgId);
            ResultSet rs = stmt.executeQuery();
            orgDbo = fillOrgDbo(rs);
            c.close();
        } catch (Exception ex){
            ex.printStackTrace();
            System.err.println(ex.getClass().getName() + ": " + ex.getMessage());
            System.exit(0);
        }
        return orgDbo;
    }

    public static OrgDbo findOrgByAccId(RegistrationDbo rdbo){
        Dbc c = new Dbc();
        PreparedStatement stmt;
        OrgDbo orgDbo = new OrgDbo();
        c.connect();
        try{
            stmt = c.createPreparedStatement("SELECT * FROM ORG_PROFILE WHERE ACCOUNT_ID = ?");
            stmt.setLong(1, rdbo.getAccountId());
            ResultSet rs = stmt.executeQuery();
            orgDbo = fillOrgDbo(rs);
            c.close();
        } catch (Exception ex){
            ex.printStackTrace();
            System.err.println(ex.getClass().getName() + ": " + ex.getMessage());
            System.exit(0);
        }
        return orgDbo;
    }

    private static OrgDbo fillOrgDbo(ResultSet rs){
        OrgDbo orgDbo = new OrgDbo();
        try{
            while(rs.next()){
                orgDbo.setProfileorgId(rs.getLong("PROFILEORG_ID"));
                orgDbo.setAccountId(rs.getLong("ACCOUNT_ID"));
                orgDbo.setorgName(rs.getString("ORG_NAME"));
                orgDbo.setAdress(rs.getString("ADRESS"));
                orgDbo.setLink(rs.getString("LINK"));
                orgDbo.setAboutOrg(rs.getString("ABOUT_ORG"));
                orgDbo.setcountMem(rs.getLong("COUNT_MEM"));
                orgDbo.setOrgType(rs.getLong("ORG_TYPE"));
                orgDbo.setCityId(rs.getLong("CITY_ID"));
                orgDbo.setcontactName(rs.getString("CONTACTNAME"));
                orgDbo.setcontactSecName(rs.getString("CONTACTSECNAME"));
                orgDbo.setcontactEmail(rs.getString("CONTACTEMAIL"));
                orgDbo.setcontactTeleph(rs.getString("CONTACTTELEPH"));
                orgDbo.setcontactDopTeleph(rs.getString("CONTACTDOPTELEPH"));
                orgDbo.setImageUrl(rs.getString("IMAGE"));
                orgDbo.settransport(rs.getLong("TRANSPORT"));
                orgDbo.setcareer(rs.getLong("CAREER"));
                orgDbo.setspirit(rs.getLong("SPIRIT"));
                orgDbo.setsalary(rs.getLong("SALARY"));
                orgDbo.setprojects(rs.getLong("PROJECTS"));
            }
        } catch (Exception ex){
            ex.printStackTrace();
            System.err.println(ex.getClass().getName() + ": " + ex.getMessage());
            System.exit(0);
        }
        return orgDbo;
    }

    public static void updateOrgProfilePicUrl(String photoUrl, Long profileId){
        Dbc c = new Dbc();
        PreparedStatement stmt;
        c.connect();
        try{
            stmt = c.createPreparedStatement(
                    "UPDATE ORG_PROFILE " +
                            "SET IMAGE = ?" + //1
                            "WHERE PROFILEORG_ID = ?" //2
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
}
