package main.java.dao;

import main.Dbc;
import main.java.dbo.PaymentDbo;
import main.java.dbo.ResumeDbo;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.util.ArrayList;
import java.util.List;

/**
 * Created by Ваня on 22.05.2018.
 */
public class PaymentDao {

    public static Long createPayment(Long from, Long to, Long type){
        Dbc c = new Dbc();
        PreparedStatement stmt;
        Long id = 0L;
        c.connect();
        try{
            stmt = c.createPreparedStatement("insert into payment values (" +
                    "generate_id()," +
                    "?," + //from 1
                    "?," + //to 2
                    "?)" //type 3
            );
            stmt.setLong(1, from);
            stmt.setLong(2, to);
            stmt.setLong(3, type);
            stmt.executeUpdate();
            c.commit();
            stmt = c.createPreparedStatement("select payment_id from payment order by payment_id desc limit 1");
            ResultSet rs = stmt.executeQuery();
            while(rs.next()){
                id = rs.getLong("payment_id");
            }
            c.close();
        } catch (Exception ex){
            ex.printStackTrace();
            System.err.println(ex.getClass().getName() + ": " + ex.getMessage());
            return 1L;
        }
        return id;
    }

    public static void updatePayment(PaymentDbo paymentDbo){
        Dbc c = new Dbc();
        PreparedStatement stmt;
        c.connect();
        try{
            stmt = c.createPreparedStatement("update payment set " +
                    "payment_from = ?," +
                    "payment_to = ?," +
                    "type = ?" +
                    "where payment_id = ?"
            );
            stmt.setLong(1, paymentDbo.getPaymentFrom());
            stmt.setLong(2, paymentDbo.getPaymentTo());
            stmt.setLong(3, paymentDbo.getPaymentType());
            stmt.executeUpdate();
            c.commit();
            c.close();
        } catch (Exception ex){
            ex.printStackTrace();
            System.err.println(ex.getClass().getName() + ": " + ex.getMessage());
        }
    }

    public static void deletePaymentById(Long paymentId){
        Dbc c = new Dbc();
        PreparedStatement stmt;
        c.connect();
        try{
            stmt = c.createPreparedStatement("delete from payment where payment_id = ?");
            stmt.setLong(1, paymentId);
            stmt.executeUpdate();
            c.commit();
            c.close();
        } catch (Exception ex){
            ex.printStackTrace();
            System.err.println(ex.getClass().getName() + ": " + ex.getMessage());
        }
    }


    public static PaymentDbo getPaymentById(Long paymentId){
        Dbc c = new Dbc();
        PreparedStatement stmt;
        PaymentDbo paymentDbo = new PaymentDbo();
        c.connect();
        try{
            stmt = c.createPreparedStatement("select * from payment where payment_id = ?");
            stmt.setLong(1, paymentId);
            ResultSet rs = stmt.executeQuery();
            while(rs.next()){
                paymentDbo.setPaymentId(paymentId);
                paymentDbo.setPaymentFrom(rs.getLong("payment_from"));
                paymentDbo.setPaymentTo(rs.getLong("payment_to"));
                paymentDbo.setPaymentType(rs.getLong("type"));
            }
            c.close();
        } catch (Exception ex){
            ex.printStackTrace();
            System.err.println(ex.getClass().getName() + ": " + ex.getMessage());
        }
        return paymentDbo;
    }


}
