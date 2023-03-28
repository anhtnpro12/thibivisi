/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package dal;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.Date;
import model.Tag;

/**
 *
 * @author M.S.I
 */
public class TagDAO {

    static Connection cnn; // kết nối
    static PreparedStatement stm; // thực hiên các cáu lệnh sql
    static ResultSet rs; // lưu trữ và xử lí dữ liệu

    public static Object getListByPage(ArrayList<Tag> list, int begin, int end) {
        ArrayList<Tag> myList = new ArrayList<>();
        int myEnd = Math.min(end, list.size());
        for (int i = begin; i < myEnd; i++) {
            myList.add(list.get(i));
        }
        return myList;
    }

    public static ArrayList<Tag> getListTag() {
        ArrayList<Tag> list = new ArrayList<>();
        try {
            String str = "select tag_id,code,tag,[status],created_at,created_by,modified_at,modified_by\n"
                    + "from ProductTags\n";
            Connection conn = DBConnect.getConnection();
            PreparedStatement pstm = conn.prepareStatement(str);
            ResultSet rs = pstm.executeQuery();
            while (rs.next()) {
                Tag t = new Tag();
                t.setTag_id(rs.getInt(1));
                t.setCode(rs.getString(2));
                t.setTag(rs.getString(3));
                t.setStatus(rs.getBoolean(4));
                t.setCreatedAt(rs.getDate(5));
                t.setCreatedBy(rs.getInt(6));
                t.setModifiedAt(rs.getDate(7));
                t.setModifiedBy(rs.getInt(8));
                list.add(t);
            }
        } catch (Exception e) {
            System.out.println("Get list tag error: " + e.getMessage());
        }

        return list;
    }

    public static int getTagIDMax() {
        int res = -1;
        try {
            String str = "select max(tag_id) as 'max'\n"
                    + "from ProductTags";
            Connection conn = DBConnect.getConnection();
            PreparedStatement pstm = conn.prepareStatement(str);
            ResultSet rs = pstm.executeQuery();
            while (rs.next()) {
                res = rs.getInt(1);
            }
        } catch (Exception e) {
        }
        return res;
    }

    public static void main(String[] args) throws ParseException {
        String myUrl = "/SWP/bon-cau-cate1-asc-p1";
        String id = myUrl.substring(myUrl.lastIndexOf("-") + 1);
        System.out.println(myUrl.charAt(myUrl.lastIndexOf("-", myUrl.lastIndexOf("-"))) - 1);
        String sortType = myUrl.substring(myUrl.lastIndexOf("-", myUrl.lastIndexOf("-") - 1) + 1, myUrl.lastIndexOf("-"));
        System.out.println(sortType);
        String ids = myUrl.substring(myUrl.lastIndexOf("-", myUrl.lastIndexOf("-", myUrl.lastIndexOf("-") - 1) - 1) + 1, myUrl.lastIndexOf("-", myUrl.lastIndexOf("-") - 1));
        System.out.println(ids);
    }

    public static void changeTagStatus(String tag_id, String status) {
        try {
            status = status.equalsIgnoreCase("true") ? "false" : "true";
            String sql = "Update [ProductTags] Set [status] = '" + status + "' "
                    + "Where [tag_id]=" + tag_id + "";

            cnn = DBConnect.getConnection();
            stm = cnn.prepareStatement(sql);
            stm.execute();
        } catch (Exception e) {
            System.out.println("setBrandStatus Error:" + e.getMessage());
        }
    }

    public static Tag getTagbyID(int parseInt) {
        try {
            String str = "select tag_id,code,tag,[status],created_at,created_by,modified_at,modified_by\n"
                    + "from ProductTags where tag_id=" + parseInt + " \n";
            Connection conn = DBConnect.getConnection();
            PreparedStatement pstm = conn.prepareStatement(str);
            ResultSet rs = pstm.executeQuery();
            while (rs.next()) {
                Tag t = new Tag();
                t.setTag_id(rs.getInt(1));
                t.setCode(rs.getString(2));
                t.setTag(rs.getString(3));
                t.setStatus(rs.getBoolean(4));
                t.setCreatedAt(rs.getDate(5));
                t.setCreatedBy(rs.getInt(6));
                t.setModifiedAt(rs.getDate(7));
                t.setModifiedBy(rs.getInt(8));
                return t;
            }
        } catch (Exception e) {
            System.out.println("Get tag error: " + e.getMessage());
        }

        return null;
    }

    public static void createTag(Tag t) {
        try {
            String str = "insert into \n"
                    + "ProductTags(code,tag,[status],created_at,created_by,modified_at,modified_by)\n"
                    + "values (?, ?, ?, ?, ?, ?, ?);";
            Connection connection = DBConnect.getConnection();
            PreparedStatement pstm = connection.prepareStatement(str);
            pstm.setString(1, genCode());
            pstm.setString(2, t.getTag());
            pstm.setBoolean(3, t.isStatus());
            pstm.setObject(4, t.getCreatedAt());
            pstm.setInt(5, t.getCreatedBy());
            pstm.setObject(6, t.getModifiedAt());
            pstm.setInt(7, t.getModifiedBy());
            pstm.executeUpdate();
        } catch (SQLException e) {
            System.out.println("Insert category error: " + e);
        }
    }

    private static String genCode() {
        ArrayList<Date> AD = new ArrayList();
        try {
            String sql = "Select created_at from ProductTags where CAST(created_at AS DATE) =?";
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
            return days + months + years + "TA" + count;
        } catch (Exception e) {
            System.out.println("Gencode error: " + e.getMessage());
        }
        return null;
    }

    public static void updateTag(Tag t) {
        try {
            String sql = "update ProductTags set "
                    + "code = ?,"
                    + "tag = ?,"
                    + "status = ?,"
                    + "created_at = ?,"
                    + "created_by = ?,"
                    + "modified_at = ?,"
                    + "modified_by = ? "
                    + "where tag_id = ?";
            cnn = DBConnect.getConnection();
            stm = cnn.prepareStatement(sql);
            stm.setString(1, t.getCode());
            stm.setString(2, t.getTag());
            stm.setBoolean(3, t.isStatus());
            stm.setObject(4, t.getCreatedAt());
            stm.setInt(5, t.getCreatedBy());
            stm.setObject(6, t.getModifiedAt());
            stm.setInt(7, t.getModifiedBy());
            stm.setInt(8, t.getTag_id());
            stm.execute();
        } catch (Exception e) {
            System.out.println("updateTag error: " + e.getMessage());
        }
    }

    public static ArrayList<Tag> getListTagContains(String tag, String name, String status, String status0, String target) {
        ArrayList<Tag> list = new ArrayList<>();
        try {
            String str = "select tag_id,code,tag,[status],created_at,created_by,modified_at,modified_by\n"
                    + "from ProductTags where " + tag + " like '%" + name + "%'\n"
                    + "and " + status + " like '%" + status0 + "%'\n"
                    + "order by " + target + " desc, tag_id desc";
            Connection conn = DBConnect.getConnection();
            PreparedStatement pstm = conn.prepareStatement(str);
            ResultSet rs = pstm.executeQuery();
            while (rs.next()) {
                Tag t = new Tag();
                t.setTag_id(rs.getInt(1));
                t.setCode(rs.getString(2));
                t.setTag(rs.getString(3));
                t.setStatus(rs.getBoolean(4));
                t.setCreatedAt(rs.getDate(5));
                t.setCreatedBy(rs.getInt(6));
                t.setModifiedAt(rs.getDate(7));
                t.setModifiedBy(rs.getInt(8));
                list.add(t);
            }
        } catch (Exception e) {
            System.out.println("getListTagContains error: " + e.getMessage());
        }

        return list;
    }

    public static int getQuantityProductFromDate(String date) {
        int res = 0;
        try {
            String str = "select COUNT(tag_id)\n"
                    + "from ProductTags\n"
                    + "where created_at >= ?";
            Connection conn = DBConnect.getConnection();
            PreparedStatement pstm = conn.prepareStatement(str);
            pstm.setString(1, date);
            ResultSet rs = pstm.executeQuery();
            while (rs.next()) {
                res = rs.getInt(1);
            }
        } catch (SQLException e) {
            System.out.println("get quantity tag from date error: " + e);
        }
        return res;
    }

    public static ArrayList<Tag> getListTagByProductID(int pID) {
        ArrayList<Tag> list = new ArrayList<>();
        try {
            String str = "select t.tag_id, t.code, t.tag, t.[status], t.created_at\n"
                    + ",t.created_by,t.modified_at,t.modified_by\n"
                    + "from Products_ProductTags pt\n"
                    + "inner join ProductTags t on pt.tag_id=t.tag_id\n"
                    + "where pt.product_id=?";
            Connection conn = DBConnect.getConnection();
            PreparedStatement pstm = conn.prepareStatement(str);
            pstm.setInt(1, pID);
            ResultSet rs = pstm.executeQuery();
            while (rs.next()) {
                Tag t = new Tag();
                t.setTag_id(rs.getInt(1));
                t.setCode(rs.getString(2));
                t.setTag(rs.getString(3));
                t.setStatus(rs.getBoolean(4));
                t.setCreatedAt(rs.getDate(5));
                t.setCreatedBy(rs.getInt(6));
                t.setModifiedAt(rs.getDate(7));
                t.setModifiedBy(rs.getInt(8));
                list.add(t);
            }
        } catch (Exception e) {
            System.out.println("Get list tag by product ID error: " + e.getMessage());
        }

        return list;
    }
}
