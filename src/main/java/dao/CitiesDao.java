package main.java.dao;

import main.Dbc;
import main.java.dbo.CitiesDbo;
import main.java.dbo.CountryDbo;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * Created by mrlz on 16.04.2018.
 */
public class CitiesDao {

    public static List<CitiesDbo> getCitiesList(){
        List<CitiesDbo> cities = new ArrayList<>();
        Dbc c = new Dbc();
        PreparedStatement stmt;
        c.connect();
        try{
            stmt = c.createPreparedStatement(
                    "SELECT * FROM CITIES"
            );
            ResultSet rs = stmt.executeQuery();
            while(rs.next()){
                CitiesDbo city = new CitiesDbo();
                city.setCityId(rs.getLong("CITY_ID"));
                city.setCountryId(rs.getLong("COUNTRY_ID"));
                city.setCityName(rs.getString("CITY_NAME"));
                cities.add(city);
            }
            c.close();
        } catch (Exception ex){
            ex.printStackTrace();
            System.err.println(ex.getClass().getName() + ": " + ex.getMessage());
            System.exit(0);
        }
        return cities;
    }

    public static Long getCityIdByName(String name){
        Long cityId = 0L;
        Dbc c = new Dbc();
        PreparedStatement stmt;
        c.connect();
        try{
            stmt = c.createPreparedStatement(
                    "SELECT * FROM CITIES WHERE CITY_NAME = ?"
            );
            stmt.setString(1, name);
            ResultSet rs = stmt.executeQuery();
            while(rs.next()){
                cityId = rs.getLong("CITY_ID");
            }
            c.close();
        } catch (Exception ex){
            ex.printStackTrace();
            System.err.println(ex.getClass().getName() + ": " + ex.getMessage());
        }
        return cityId;
    }


    public static Map<Long, String> getCitiesMap(){
        Map<Long, String> cities = new HashMap<>();
        Dbc c = new Dbc();
        PreparedStatement stmt;
        c.connect();
        try{
            stmt = c.createPreparedStatement(
                    "SELECT * FROM CITIES"
            );
            ResultSet rs = stmt.executeQuery();
            while(rs.next()){
                cities.put(rs.getLong("CITY_ID"), rs.getString("CITY_NAME"));
            }
            c.close();
        } catch (Exception ex){
            ex.printStackTrace();
            System.err.println(ex.getClass().getName() + ": " + ex.getMessage());
            System.exit(0);
        }
        return cities;
    }

    public static Map<Long, String> getCityById(Long cityId){
        Map<Long, String> cities = new HashMap<>();
        Dbc c = new Dbc();
        PreparedStatement stmt;
        c.connect();
        try{
            stmt = c.createPreparedStatement(
                    "SELECT * FROM CITIES WHERE CITY_ID = ?"
            );
            stmt.setLong(1, cityId);
            ResultSet rs = stmt.executeQuery();
            while(rs.next()){
                cities.put(rs.getLong("CITY_ID"), rs.getString("CITY_NAME"));
            }
            c.close();
        } catch (Exception ex){
            ex.printStackTrace();
            System.err.println(ex.getClass().getName() + ": " + ex.getMessage());
            System.exit(0);
        }
        return cities;
    }

    public static String getCityNameById(Long cityId){
        String city = "";
        Dbc c = new Dbc();
        PreparedStatement stmt;
        c.connect();
        try{
            stmt = c.createPreparedStatement(
                    "SELECT * FROM CITIES WHERE CITY_ID = ?"
            );
            stmt.setLong(1, cityId);
            ResultSet rs = stmt.executeQuery();
            while(rs.next()){
                city = rs.getString("CITY_NAME");
            }
            c.close();
        } catch (Exception ex){
            ex.printStackTrace();
            System.err.println(ex.getClass().getName() + ": " + ex.getMessage());
        }
        return city;
    }

    public static List<CountryDbo> getCountriesList(){
        List<CountryDbo> countries = new ArrayList<>();
        Dbc c = new Dbc();
        PreparedStatement stmt;
        c.connect();
        try{
            CountryDbo country = new CountryDbo();
            stmt = c.createPreparedStatement(
                    "SELECT * FROM COUNTRIES"
            );
            ResultSet rs = stmt.executeQuery();
            while(rs.next()){
                country.setCountryId(rs.getLong("COUNTRY_ID"));
                country.setCountryName(rs.getString("COUNTRY_NAME"));
                countries.add(country);
            }
            c.close();
        } catch (Exception ex){
            ex.printStackTrace();
            System.err.println(ex.getClass().getName() + ": " + ex.getMessage());
            System.exit(0);
        }
        return countries;
    }
}
