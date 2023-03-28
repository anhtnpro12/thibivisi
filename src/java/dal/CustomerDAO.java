/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package dal;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.Date;
import model.Customer;
import org.apache.commons.codec.digest.DigestUtils;

/**
 *
 * @author TNA
 */
public class CustomerDAO {

    public static void insertCustomer(Customer c) {
        try {
            String str = "insert into Customers(code,[name],gender,email,phone_number,password,[status],created_at\n"
                    + ",created_by,modified_at,modified_by)\n"
                    + "values (?,?,?,?,?,?,?,?,?,?,?)";
            Connection conn = DBConnect.getConnection();
            PreparedStatement pstm = conn.prepareStatement(str);
            pstm.setString(1, c.getCode());
            pstm.setString(2, c.getName());
            pstm.setBoolean(3, c.isGender());
            pstm.setString(4, c.getEmail());
            pstm.setString(5, c.getPhoneNumber());
            pstm.setString(6, encryptPassword(c.getPassword()));
            pstm.setBoolean(7, c.isStatus());
            pstm.setObject(8, c.getCreatedAt());
            pstm.setInt(9, c.getCreatedBy());
            pstm.setObject(10, c.getModifiedAt());
            pstm.setInt(11, c.getModifiedBy());
            pstm.executeUpdate();
        } catch (SQLException e) {
            System.out.println("insert customer error: " + e);
        }
    }

    public static void addCustomer(Customer c) {
        try {
            String str = "insert into Customers(code,[name],gender,email,phone_number,[status],password)\n"
                    + "values (?,?,?,?,?,?,?)";
            Connection conn = DBConnect.getConnection();
            PreparedStatement pstm = conn.prepareStatement(str);
            pstm.setString(1, c.getCode());
            pstm.setString(2, c.getName());
            pstm.setBoolean(3, c.isGender());
            pstm.setString(4, c.getEmail());
            pstm.setString(5, c.getPhoneNumber());
            pstm.setBoolean(6, c.isStatus());
            pstm.setString(7, c.getPassword());
            pstm.executeUpdate();
        } catch (SQLException e) {
            System.out.println("insert customer error: " + e);
        }
    }

    public static ArrayList<Customer> getListCustomerByStatus(boolean status) {
        ArrayList<Customer> list = new ArrayList<>();
        try {
            String str = "select customer_id,code,[name],[gender],[email],phone_number,[status]\n"
                    + ",created_at,created_by,modified_at,modified_by\n"
                    + "from Customers\n"
                    + "where [status]=?";
            Connection conn = DBConnect.getConnection();
            PreparedStatement pstm = conn.prepareStatement(str);
            pstm.setBoolean(1, status);
            ResultSet rs = pstm.executeQuery();
            while (rs.next()) {
                Customer c = new Customer();
                c.setCustomerID(rs.getInt(1));
                c.setCode(rs.getString(2));
                c.setName(rs.getString(3));
                c.setGender(rs.getBoolean(4));
                c.setEmail(rs.getString(5));
                c.setPhoneNumber(rs.getString(6));
                c.setStatus(rs.getBoolean(7));
                c.setCreatedAt(rs.getDate(8));
                c.setCreatedBy(rs.getInt(9));
                c.setModifiedAt(rs.getDate(10));
                c.setModifiedBy(rs.getInt(11));

                list.add(c);
            }
        } catch (SQLException e) {
            System.out.println("get list customer active error: " + e);
        }
        return list;
    }

    public static ArrayList<Customer> getListCustomer() {
        ArrayList<Customer> list = new ArrayList<>();
        try {
            String str = "select customer_id,code,[name],[gender],[email],phone_number,[status]\n"
                    + ",created_at,created_by,modified_at,modified_by\n"
                    + "from Customers\n";
            Connection conn = DBConnect.getConnection();
            PreparedStatement pstm = conn.prepareStatement(str);
            ResultSet rs = pstm.executeQuery();
            while (rs.next()) {
                Customer c = new Customer();
                c.setCustomerID(rs.getInt(1));
                c.setCode(rs.getString(2));
                c.setName(rs.getString(3));
                c.setGender(rs.getBoolean(4));
                c.setEmail(rs.getString(5));
                c.setPhoneNumber(rs.getString(6));
                c.setStatus(rs.getBoolean(7));
                c.setCreatedAt(rs.getDate(8));
                c.setCreatedBy(rs.getInt(9));
                c.setModifiedAt(rs.getDate(10));
                c.setModifiedBy(rs.getInt(11));

                list.add(c);
            }
        } catch (SQLException e) {
            System.out.println("get list customer error: " + e);
        }
        return list;
    }

    public static int getQuantityOrderFromDate(String date) {
        int res = 0;
        try {
            String str = "select COUNT(customer_id)\n"
                    + "from Customers\n"
                    + "where created_at >= ?";
            Connection conn = DBConnect.getConnection();
            PreparedStatement pstm = conn.prepareStatement(str);
            pstm.setString(1, date);
            ResultSet rs = pstm.executeQuery();
            while (rs.next()) {
                res = rs.getInt(1);
            }
        } catch (SQLException e) {
            System.out.println("get quantity order from date error: " + e);
        }
        return res;
    }

    public static int findCustomer(Customer c) {
        int res = -1;
        try {
            String str = "select c.customer_id\n"
                    + "from Customers c\n"
                    + "where c.email=?";
            Connection conn = DBConnect.getConnection();
            PreparedStatement pstm = conn.prepareStatement(str);
            pstm.setString(1, c.getEmail());
            ResultSet rs = pstm.executeQuery();
            while (rs.next()) {
                return rs.getInt(1);
            }
        } catch (SQLException e) {
            System.out.println("find customer error: " + e);
        }
        return res;
    }
    
    public static int getMaxCustomerID() {
        int res = 0;
        try {
            String str = "select MAX(customer_id)\n"
                    + "from Customers";
            Connection conn = DBConnect.getConnection();
            PreparedStatement pstm = conn.prepareStatement(str);
            ResultSet rs = pstm.executeQuery();
            while (rs.next()) {
                res = rs.getInt(1);
            }
        } catch (SQLException e) {
            System.out.println("get max customer id error: " + e);
        }
        return res;
    }

    public static String encryptPassword(String pass) {
        return DigestUtils.md5Hex(pass).toUpperCase();
    }

    public static Customer getCustomer(String email, String password) {
        try {
            String sql = "select customer_id,code,[name],[gender],[email],phone_number,[status]\n"
                    + ",created_at,created_by,modified_at,modified_by,password\n"
                    + "from Customers\n"
                    + "where email=? AND password=?";
            Connection conn = DBConnect.getConnection();
            PreparedStatement pstm = conn.prepareStatement(sql);
            pstm.setString(1, email);
            pstm.setString(2, encryptPassword(password));
            ResultSet rs = pstm.executeQuery();
            while (rs.next()) {
                Customer c = new Customer();
                c.setCustomerID(rs.getInt(1));
                c.setCode(rs.getString(2));
                c.setName(rs.getString(3));
                c.setGender(rs.getBoolean(4));
                c.setEmail(rs.getString(5));
                c.setPhoneNumber(rs.getString(6));
                c.setStatus(rs.getBoolean(7));
                c.setCreatedAt(rs.getDate(8));
                c.setCreatedBy(rs.getInt(9));
                c.setModifiedAt(rs.getDate(10));
                c.setModifiedBy(rs.getInt(11));
                c.setPassword(rs.getString(12));
                return c;
            }
        } catch (Exception e) {
            System.out.println("getCustomer error: " + e.getMessage());
        }
        return null;
    }

    public static void main(String[] args) {
        Customer c = new Customer("110323CUS01", "Trịnh Công Minh", true, "minhtche164004@fpt.edu.vn", "0366780698", "adebayor9", true, new Date(), 0, new Date(), 0);
        insertCustomer(c);
    }

    public static void updateCustomer(String customerID, String name, String phoneNumber) {
        try {
            String sql = "Update Customers set name=?, phone_number=? where customer_id=?";
            Connection conn = DBConnect.getConnection();
            PreparedStatement pstm = conn.prepareStatement(sql);
            pstm.setString(1, name);
            pstm.setString(2, phoneNumber);
            pstm.setString(3, customerID);
            pstm.executeUpdate();
        } catch (Exception e) {
            System.out.println("updateCustomer error: " + e.getMessage());
        }
    }

    public static Customer getCustomerbyID(int customerID) {
        try {
            String sql = "select customer_id,code,[name],[gender],[email],phone_number,[status]\n"
                    + ",created_at,created_by,modified_at,modified_by,password\n"
                    + "from Customers\n"
                    + "where customer_id=?";
            Connection conn = DBConnect.getConnection();
            PreparedStatement pstm = conn.prepareStatement(sql);
            pstm.setInt(1, customerID);
            ResultSet rs = pstm.executeQuery();
            while (rs.next()) {
                Customer c = new Customer();
                c.setCustomerID(rs.getInt(1));
                c.setCode(rs.getString(2));
                c.setName(rs.getString(3));
                c.setGender(rs.getBoolean(4));
                c.setEmail(rs.getString(5));
                c.setPhoneNumber(rs.getString(6));
                c.setStatus(rs.getBoolean(7));
                c.setCreatedAt(rs.getDate(8));
                c.setCreatedBy(rs.getInt(9));
                c.setModifiedAt(rs.getDate(10));
                c.setModifiedBy(rs.getInt(11));
                c.setPassword(rs.getString(12));
                return c;
            }
        } catch (Exception e) {
            System.out.println("getCustomer error: " + e.getMessage());
        }
        return null;
    }

    public static boolean getCustomerbyIdandPass(int customerID, String oldpass) {
        try {
            String sql = "select customer_id from Customers where customer_id = ? AND password = ?";
            Connection conn = DBConnect.getConnection();
            PreparedStatement pstm = conn.prepareStatement(sql);
            pstm.setInt(1, customerID);
            pstm.setString(2, encryptPassword(oldpass));
            ResultSet rs = pstm.executeQuery();
            while (rs.next()) {
                return true;
            }
        } catch (Exception e) {
            System.out.println("getCustomerbyIdandPass error: " + e.getMessage());
        }
        return false;
    }

    public static void updatePassword(int customerID, String newpass) {
        try {
            String sql = "Update Customers set password=? where customer_id=?";
            Connection conn = DBConnect.getConnection();
            PreparedStatement pstm = conn.prepareStatement(sql);
            pstm.setString(1, encryptPassword(newpass));
            pstm.setInt(2, customerID);
            pstm.executeUpdate();
        } catch (Exception e) {
            System.out.println("updatePassword error: " + e.getMessage());
        }
    }

    public static boolean IfEmailExist(String email) {
        try {
            String sql = "select email from Customers where email=?";
            Connection conn = DBConnect.getConnection();
            PreparedStatement pstm = conn.prepareStatement(sql);
            pstm.setString(1, email);
            ResultSet rs = pstm.executeQuery();
            while (rs.next()) {
                return true;
            }
        } catch (Exception e) {
            System.out.println("IfEmailExist error:" + e.getMessage());
        }
        return false;
    }

    public static void updatePassbyEmail(String email, String newpass) {
        try {
            String sql = "update Customers set password = '" + encryptPassword(newpass) + "'\n"
                    + "where email='" + email + "'";
            Connection conn = DBConnect.getConnection();
            PreparedStatement pstm = conn.prepareStatement(sql);
            pstm.executeUpdate();
        } catch (Exception e) {
            System.out.println("updatePassbyEmail error: " + e.getMessage());
        }
    }
}
