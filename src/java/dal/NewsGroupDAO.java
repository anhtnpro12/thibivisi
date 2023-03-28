/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package dal;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.text.Normalizer;
import java.util.ArrayList;
import model.NewsGroup;

/**
 *
 * @author ADMIN
 */
public class NewsGroupDAO {

    static Connection cnn; // kết nối
    static PreparedStatement stm; // thực hiên các cáu lệnh sql
    static ResultSet rs; // lưu trữ và xử lí dữ liệu

    public NewsGroupDAO() {
    }

    public static NewsGroup getNewsGroupbyID(int newsgroupID) {
        NewsGroup c = new NewsGroup();
        try {
            String sql = "select [newsgroup_id]\n"
                    + "      ,[parent_id]\n"
                    + "      ,[code]\n"
                    + "      ,[newsgroup_name]\n"
                    + "      ,[url]\n"
                    + "      ,[status]\n"
                    + "      ,[created_at]\n"
                    + "      ,[created_by]\n"
                    + "      ,[modified_at]\n"
                    + "      ,[modified_by]\n"
                    + "from [NewsGroups] where newsgroup_id=" + newsgroupID + "";
            cnn = DBConnect.getConnection();
            stm = cnn.prepareStatement(sql);
            rs = stm.executeQuery();
            while (rs.next()) {
                c.setNewsgroup_id(rs.getInt(1));
                c.setParent_id(rs.getInt(2));
                c.setCode(rs.getString(3));
                c.setNewsgroup_name(rs.getString(4));
                c.setUrl(rs.getString(5));
                c.setStatus(rs.getBoolean(6));
                c.setCreated_at(rs.getDate(7));
                c.setCreated_by(rs.getInt(8));
                c.setModified_at(rs.getDate(9));
                c.setModified_by(rs.getInt(10));
            }
        } catch (Exception e) {
            System.out.println("getNewsGroupbyID error: " + e.getMessage());
        }
        return c;
    }

    public static ArrayList<NewsGroup> getlistNotNullNewsGroup() {
        ArrayList<NewsGroup> ng = new ArrayList<>();
        try {
            String sql = "select [newsgroup_id]\n"
                    + "      ,[parent_id]\n"
                    + "      ,[code]\n"
                    + "      ,[newsgroup_name]\n"
                    + "      ,[url]\n"
                    + "      ,[status]\n"
                    + "      ,[created_at]\n"
                    + "      ,[created_by]\n"
                    + "      ,[modified_at]\n"
                    + "      ,[modified_by]\n"
                    + "from [NewsGroups] where parent_id is not null";
            cnn = DBConnect.getConnection();
            stm = cnn.prepareStatement(sql);
            rs = stm.executeQuery();
            while (rs.next()) {
                NewsGroup c = new NewsGroup();
                c.setNewsgroup_id(rs.getInt(1));
                c.setParent_id(rs.getInt(2));
                c.setCode(rs.getString(3));
                c.setNewsgroup_name(rs.getString(4));
                c.setUrl(rs.getString(5));
                c.setStatus(rs.getBoolean(6));
                c.setCreated_at(rs.getDate(7));
                c.setCreated_by(rs.getInt(8));
                c.setModified_at(rs.getDate(9));
                c.setModified_by(rs.getInt(10));
                ng.add(c);
            }
        } catch (Exception e) {
            System.out.println("getlistNotNullNewsGroup error: " + e.getMessage());
        }
        return ng;
    }

    public static ArrayList<NewsGroup> getAllNewsGroup() {
        ArrayList<NewsGroup> ng = new ArrayList<>();
        try {
            String sql = "select [newsgroup_id]\n"
                    + "      ,[parent_id]\n"
                    + "      ,[code]\n"
                    + "      ,[newsgroup_name]\n"
                    + "      ,[url]\n"
                    + "      ,[status]\n"
                    + "      ,[created_at]\n"
                    + "      ,[created_by]\n"
                    + "      ,[modified_at]\n"
                    + "      ,[modified_by] from [NewsGroups]\n";
            cnn = DBConnect.getConnection();
            stm = cnn.prepareStatement(sql);
            rs = stm.executeQuery();
            while (rs.next()) {
                NewsGroup c = new NewsGroup();
                c.setNewsgroup_id(rs.getInt(1));
                c.setParent_id(rs.getInt(2));
                c.setCode(rs.getString(3));
                c.setNewsgroup_name(rs.getString(4));
                c.setUrl(rs.getString(5));
                c.setStatus(rs.getBoolean(6));
                c.setCreated_at(rs.getDate(7));
                c.setCreated_by(rs.getInt(8));
                c.setModified_at(rs.getDate(9));
                c.setModified_by(rs.getInt(10));
                ng.add(c);
            }
        } catch (Exception e) {
            System.out.println("getAllNewsGroup error: " + e.getMessage());
        }
        return ng;
    }

    public static int getIDbyURL(String url) {
        try {
            String sql = "select newsgroup_id from NewsGroups where url='" + url + "'";
            cnn = DBConnect.getConnection();
            stm = cnn.prepareStatement(sql);
            rs = stm.executeQuery();
            while (rs.next()) {
                return rs.getInt(1);
            }
        } catch (Exception e) {
            System.out.println("getIDbyURL error: " + e.getMessage());
        }
        return 0;
    }

//    public static void main(String[] args) {
//        System.out.println(getIDbyURL("Tin-khuyen-mai"));
//    }
    public static ArrayList<NewsGroup> getListByPage(ArrayList<NewsGroup> list, int begin, int end) {
        ArrayList<NewsGroup> myList = new ArrayList<>();
        int myEnd = Math.min(end, list.size());
        for (int i = begin; i < myEnd; i++) {
            myList.add(list.get(i));
        }
        return myList;
    }

    public static void updateNewsGroup(String name, int id) {
        try {
            String sql = "UPDATE [NewsGroups]\n"
                    + "   SET\n"
                    + "      [newsgroup_name] = '" + name + "'\n"
                    + "      ,[url] = '" + getURL(name) + "'\n"
                    + " WHERE newsgroup_id=" + id + "";
            Connection conn = DBConnect.getConnection();
            PreparedStatement pstm = conn.prepareStatement(sql);
            pstm.executeUpdate();
        } catch (Exception e) {
            System.out.println("updateNewsGroup error: " + e.getMessage());
        }
    }

    public static String getURL(String title) {
        String url = Normalizer.normalize(title, Normalizer.Form.NFD)
                .replaceAll("\\p{M}", "").trim().replaceAll("\\s+", "-");
        return url;
    }

    public static int getIDbyCode(String code) {
        try {
            String sql = "select newsgroup_id from NewsGroups where code='" + code + "'";
            cnn = DBConnect.getConnection();
            stm = cnn.prepareStatement(sql);
            rs = stm.executeQuery();
            while (rs.next()) {
                return rs.getInt(1);
            }
        } catch (Exception e) {
            System.out.println("getIDbyURL error: " + e.getMessage());
        }
        return 0;
    }

    public static String genCode() {
        String count = null;
        try {
            String sql = "select COUNT(newsgroup_id) from NewsGroups";
            cnn = DBConnect.getConnection();
            stm = cnn.prepareStatement(sql);
            rs = stm.executeQuery();
            while (rs.next()) {
                count = rs.getString(1);
            }
        } catch (Exception e) {
            System.out.println("gencode error: " + e.getMessage());
        }
        int counts = Integer.parseInt(count) + 1;
        return "newgr" + counts;
    }

    public static void inserNewsGroup(NewsGroup ng) {
        try {
            String sql = "INSERT INTO [NewsGroups]\n"
                    + "           ([parent_id]\n"
                    + "           ,[code]\n"
                    + "           ,[newsgroup_name]\n"
                    + "           ,[url]\n"
                    + "           ,[status]\n"
                    + "           ,[created_at]\n"
                    + "           ,[created_by]\n"
                    + "           ,[modified_at]\n"
                    + "           ,[modified_by])\n"
                    + "     VALUES\n"
                    + "           (" + ng.getParent_id() + "\n"
                    + "           ,'" + genCode() + "'\n"
                    + "           ,N'" + ng.getNewsgroup_name() + "'\n"
                    + "           ,'" + getURL(ng.getNewsgroup_name()) + "'\n"
                    + "           ,'" + ng.isStatus() + "'\n"
                    + "           ,?\n"
                    + "           ,?\n"
                    + "           ,?\n"
                    + "           ,?)";
            Connection conn = DBConnect.getConnection();
            PreparedStatement pstm = conn.prepareStatement(sql);
            pstm.setObject(1, ng.getCreated_at());
            pstm.setInt(2, ng.getCreated_by());
            pstm.setObject(3, ng.getModified_at());
            pstm.setInt(4, ng.getModified_by());
            pstm.executeUpdate();
        } catch (Exception e) {
            System.out.println("inserNewsGroup error: " + e.getMessage());
        }
    }

    public static void changeNewsStatus(int newsID, String status) {
        try {
            status = status.equalsIgnoreCase("true") ? "false" : "true";
            String sql = "Update [NewsGroups] Set [status] = '" + status + "' "
                    + "Where [newsgroup_id]=" + newsID + "";

            cnn = DBConnect.getConnection();
            stm = cnn.prepareStatement(sql);
            stm.execute();
        } catch (Exception e) {
            System.out.println("changeNewsStatus Error:" + e.getMessage());
        }
    }
}
