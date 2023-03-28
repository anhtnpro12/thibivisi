/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package dal;

import static dal.CategoryDAO.cnn;
import static dal.CategoryDAO.rs;
import static dal.CategoryDAO.stm;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.text.SimpleDateFormat;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.Date;
import model.Contact;
import model.RepContact;

/**
 *
 * @author M.S.I
 */
public class ContactDAO {

    static Connection cnn; // kết nối
    static PreparedStatement stm; // thực hiên các cáu lệnh sql
    static ResultSet rs; // lưu trữ và xử lí dữ liệu

    public static void sendContact(Contact c) {
        try {
            String sql = "insert into [ContactUs] (code,name,email,message,status,created_at,created_by,modified_at,modified_by)"
                    + "values (?,?,?,?,?,?,?,?,?)";
            Connection connection = DBConnect.getConnection();
            PreparedStatement pstm = connection.prepareStatement(sql);
            pstm.setString(1, genCodeContact());
            pstm.setString(2, c.getName());
            pstm.setString(3, c.getEmail());
            pstm.setString(4, c.getMessage());
            pstm.setBoolean(5, c.isStatus());
            pstm.setObject(6, c.getCreatedAt());
            pstm.setInt(7, c.getCreatedBy());
            pstm.setObject(8, c.getModifiedAt());
            pstm.setInt(9, c.getModifiedBy());
            pstm.executeUpdate();
        } catch (Exception e) {
            System.out.println("sendContact Error: " + e.getMessage());
        }
    }

    private static String genCodeContact() {
        ArrayList<Date> AD = new ArrayList();
        try {
            String sql = "Select created_at from ContactUs where CAST(created_at AS DATE) =?";
            Connection connection = DBConnect.getConnection();
            PreparedStatement pstm = connection.prepareStatement(sql);
            SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy/MM/dd");
            LocalDate currentDate = LocalDate.now();

            // Lấy giá trị ngày, tháng và năm của ngày hôm nay
            int day = currentDate.getDayOfMonth();
            String days, months;
            if (Integer.toString(day).length() == 1) {
                days = "0" + Integer.toString(day);
            } else {
                days = Integer.toString(day);
            }
            int month = currentDate.getMonthValue();
            if (Integer.toString(month).length() == 1) {
                months = "0" + Integer.toString(month);
            } else {
                months = Integer.toString(month);
            }
            int year = currentDate.getYear();
            String years = Integer.toString(year).substring(2, 4);

            pstm.setObject(1, dateFormat.parse(year + "/" + months + "/" + days));
            ResultSet rs = pstm.executeQuery();
            while (rs.next()) {
                AD.add(rs.getDate(1));
            }
            int count = AD.size() + 1;
            String code = days + months + years + "CON" + count;
            if (!checkDupContactCode(code)) {
                while (!checkDupContactCode(code)) {
                    count += 1;
                    code = days + months + years + "CON" + count;
                }
            }
            return code;
        } catch (Exception e) {
            System.out.println("Gencode error: " + e.getMessage());
        }
        return null;
    }

    public static boolean checkDupContactCode(String code) {
        try {
            String sql = "Select code from ContactUs \n"
                    + "where code='" + code + "'";
            cnn = DBConnect.getConnection();
            stm = cnn.prepareStatement(sql);
            rs = stm.executeQuery();
            while (rs.next()) {
                return false;
            }
        } catch (Exception e) {
            System.out.println("dal.ContactDAO.checkDupContactCode() error: " + e.getMessage());
        }
        return true;

    }

    public static ArrayList<Contact> getContactList() {
        ArrayList<Contact> ac = new ArrayList<>();
        try {
            String sql = "select contact_id,code,name,email,message,status,created_at,created_by,modified_at,modified_by\n"
                    + "from ContactUs";
            cnn = DBConnect.getConnection();
            stm = cnn.prepareStatement(sql);
            rs = stm.executeQuery();
            while (rs.next()) {
                Contact c = new Contact();
                c.setContactID(rs.getInt(1));
                c.setCode(rs.getString(2));
                c.setName(rs.getString(3));
                c.setEmail(rs.getString(4));
                c.setMessage(rs.getString(5));
                c.setStatus(rs.getBoolean(6));
                c.setCreatedAt(rs.getDate(7));
                c.setCreatedBy(rs.getInt(8));
                c.setModifiedAt(rs.getDate(9));
                c.setModifiedBy(rs.getInt(10));
                ac.add(c);
            }
        } catch (Exception e) {
            System.out.println("getContactList error: " + e.getMessage());
        }
        return ac;
    }

    public static ArrayList<Contact> getListByPage(ArrayList<Contact> list, int begin, int end) {
        ArrayList<Contact> myList = new ArrayList<>();
        int myEnd = Math.min(end, list.size());
        for (int i = begin; i < myEnd; i++) {
            myList.add(list.get(i));
        }
        return myList;
    }
//    public static void main(String[] args) {
//        System.out.println(getRepContactListByContactID(6));
//    }

    public static void deleteContact(int contactID) {
        try {
            String sql = "delete from ContactUs Where contact_id=" + contactID + "";
            cnn = DBConnect.getConnection();
            stm = cnn.prepareStatement(sql);
            stm.execute();
        } catch (Exception e) {
            System.out.println("deleteContact error: " + e.getMessage());
        }
    }

    public static Contact getContactByID(int contactID) {
        try {
            String sql = "select contact_id,code,name,email,message,status,created_at,created_by,modified_at,modified_by\n"
                    + "from ContactUs where contact_id=" + contactID + "";
            cnn = DBConnect.getConnection();
            stm = cnn.prepareStatement(sql);
            rs = stm.executeQuery();
            while (rs.next()) {
                Contact c = new Contact();
                c.setContactID(rs.getInt(1));
                c.setCode(rs.getString(2));
                c.setName(rs.getString(3));
                c.setEmail(rs.getString(4));
                c.setMessage(rs.getString(5));
                c.setStatus(rs.getBoolean(6));
                c.setCreatedAt(rs.getDate(7));
                c.setCreatedBy(rs.getInt(8));
                c.setModifiedAt(rs.getDate(9));
                c.setModifiedBy(rs.getInt(10));
                return c;
            }
        } catch (Exception e) {
            System.out.println("getContactList error: " + e.getMessage());
        }
        return null;
    }

    public static void SaveRepContact(RepContact rp) {
        RepContact rep = new RepContact();
        try {
            String sql = "insert into [RepContactUs] (code,contact_id,[title],message,status,created_at,created_by,modified_at,modified_by)"
                    + "values (?,?,?,?,?,?,?,?,?)";
            Connection connection = DBConnect.getConnection();
            PreparedStatement pstm = connection.prepareStatement(sql);
            pstm.setString(1, genCodeRepContact());
            pstm.setInt(2, rp.getContactID());
            pstm.setString(3, rp.getTitle());
            pstm.setString(4, rp.getMessage());
            pstm.setBoolean(5, rp.isStatus());
            pstm.setObject(6, rp.getCreatedAt());
            pstm.setInt(7, rp.getCreatedBy());
            pstm.setObject(8, rp.getModifiedAt());
            pstm.setInt(9, rp.getModifiedBy());
            pstm.executeUpdate();
        } catch (Exception e) {
            System.out.println("sendContact Error: " + e.getMessage());
        }
    }

    private static String genCodeRepContact() {
        ArrayList<Date> AD = new ArrayList();
        try {
            String sql = "Select created_at from RepContactUs where CAST(created_at AS DATE) =?";
            Connection connection = DBConnect.getConnection();
            PreparedStatement pstm = connection.prepareStatement(sql);
            SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy/MM/dd");
            LocalDate currentDate = LocalDate.now();

            // Lấy giá trị ngày, tháng và năm của ngày hôm nay
            int day = currentDate.getDayOfMonth();
            String days, months;
            if (Integer.toString(day).length() == 1) {
                days = "0" + Integer.toString(day);
            } else {
                days = Integer.toString(day);
            }
            int month = currentDate.getMonthValue();
            if (Integer.toString(month).length() == 1) {
                months = "0" + Integer.toString(month);
            } else {
                months = Integer.toString(month);
            }
            int year = currentDate.getYear();
            String years = Integer.toString(year).substring(2, 4);

            pstm.setObject(1, dateFormat.parse(year + "/" + months + "/" + days));
            ResultSet rs = pstm.executeQuery();
            while (rs.next()) {
                AD.add(rs.getDate(1));
            }
            int count = AD.size() + 1;
            String code = days + months + years + "REPCON" + count;
            if (!checkDupRepContactCode(code)) {
                while (!checkDupRepContactCode(code)) {
                    count += 1;
                    code = days + months + years + "REPCON" + count;
                }
            }
            return code;
        } catch (Exception e) {
            System.out.println("Gencode error: " + e.getMessage());
        }
        return null;
    }

    public static void ChangeContactStatus(String contactID) {
        try {
            String sql = "UPDATE ContactUs\n"
                    + "   SET status = 'true'\n"
                    + " WHERE contact_id = ?";
            Connection connection = DBConnect.getConnection();
            PreparedStatement pstm = connection.prepareStatement(sql);
            pstm.setString(1, contactID);
            pstm.executeUpdate();
        } catch (Exception e) {
            System.out.println("ChangeContactStatus error: " + e.getMessage());
        }
    }

    public static ArrayList<RepContact> getRepContactListByContactID(int contactid) {
        ArrayList<RepContact> rc = new ArrayList<>();
        try {
            String sql = "select id,code,contact_id,[title],message,status,created_at,created_by,modified_at,modified_by\n"
                    + "from RepContactUs "
                    + "where contact_id=" + contactid + "";
            cnn = DBConnect.getConnection();
            stm = cnn.prepareStatement(sql);
            rs = stm.executeQuery();
            while (rs.next()) {
                RepContact r = new RepContact();
                r.setRepcontactID(rs.getInt(1));
                r.setCode(rs.getString(2));
                r.setContactID(rs.getInt(3));
                r.setTitle(rs.getString(4));
                r.setMessage(rs.getString(5));
                r.setStatus(rs.getBoolean(6));
                r.setCreatedAt(rs.getDate(7));
                r.setCreatedBy(rs.getInt(8));
                r.setModifiedAt(rs.getDate(9));
                r.setModifiedBy(rs.getInt(10));
                rc.add(r);
            }
            return rc;
        } catch (Exception e) {
            System.out.println("getRepContactListByContactID error: " + e.getMessage());
        }
        return null;
    }

    private static boolean checkDupRepContactCode(String code) {
        try {
            String sql = "Select code from RepContactUs \n"
                    + "where code='" + code + "'";
            cnn = DBConnect.getConnection();
            stm = cnn.prepareStatement(sql);
            rs = stm.executeQuery();
            while (rs.next()) {
                return false;
            }
        } catch (Exception e) {
            System.out.println("dal.ContactDAO.checkDupContactCode() error: " + e.getMessage());
        }
        return true;
    }
}
