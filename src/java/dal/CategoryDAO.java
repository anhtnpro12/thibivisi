package dal;

import model.Category;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.time.LocalDateTime;
import java.time.ZoneId;
import java.util.ArrayList;
import java.util.Date;

public class CategoryDAO {

    static Connection cnn; // kết nối
    static PreparedStatement stm; // thực hiên các cáu lệnh sql
    static ResultSet rs; // lưu trữ và xử lí dữ liệu

    public static void insertCategory(Category c) {
        try {
            String str;
            if (c.getParentID() == 0) {
                str = "insert into \n"
                        + "Categories(code,cate,[status],created_at,created_by,modified_at,modified_by)\n"
                        + "values (?, ?, ?, ?, ?, ?, ?);";
                Connection connection = DBConnect.getConnection();
                PreparedStatement pstm = connection.prepareStatement(str);
                pstm.setString(1, genCode());
                pstm.setString(2, c.getCate());
                pstm.setBoolean(3, c.isStatus());
                pstm.setObject(4, c.getCreatedAt());
                pstm.setInt(5, c.getCreatedBy());
                pstm.setObject(6, c.getModifiedAt());
                pstm.setInt(7, c.getModifiedBy());
                pstm.executeUpdate();
            } else {
                str = "insert into \n"
                        + "Categories(parent_id,code,cate,[status],created_at,created_by,modified_at,modified_by)\n"
                        + "values (?, ?, ?, ?, ?, ?, ?, ?);";
                Connection connection = DBConnect.getConnection();
                PreparedStatement pstm = connection.prepareStatement(str);
                pstm.setInt(1, c.getParentID());
                pstm.setString(2, genCode());
                pstm.setString(3, c.getCate());
                pstm.setBoolean(4, c.isStatus());
                pstm.setObject(5, c.getCreatedAt());
                pstm.setInt(6, c.getCreatedBy());
                pstm.setObject(7, c.getModifiedAt());
                pstm.setInt(8, c.getModifiedBy());
                pstm.executeUpdate();
            }
        } catch (SQLException e) {
            System.out.println("Insert category error: " + e);
        }
    }

    public static ArrayList<Category> getListCategory() {
        ArrayList<Category> list = new ArrayList<>();
        try {
            String str = "select cate_id,parent_id,code,cate,[status],created_at,created_by,modified_at,modified_by\n"
                    + "from Categories\n";
            Connection conn = DBConnect.getConnection();
            PreparedStatement pstm = conn.prepareStatement(str);
            ResultSet rs = pstm.executeQuery();
            while (rs.next()) {
                Category c = new Category();
                c.setCateID(rs.getInt(1));
                c.setParentID(rs.getInt(2));
                c.setCode(rs.getString(3));
                c.setCate(rs.getString(4));
                c.setStatus(rs.getBoolean(5));
                c.setCreatedAt(rs.getDate(6));
                c.setCreatedBy(rs.getInt(7));
                c.setModifiedAt(rs.getDate(8));
                c.setModifiedBy(rs.getInt(9));

                list.add(c);
            }
        } catch (Exception e) {
            System.out.println("Get list category error: " + e.getMessage());
        }

        return list;
    }

    public static String genCode() {
        String count = null;
        try {
            String sql = "select COUNT(cate_id) from Categories";
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
        return "cate" + counts;
    }

    public static Category getCategorybyID(int cate_id) {
        Category c = new Category();
        try {
            String sql = "select cate_id,parent_id,code,cate,[status],created_at,created_by,modified_at,modified_by,url\n"
                    + "from Categories where cate_id=" + cate_id + "";
            cnn = DBConnect.getConnection();
            stm = cnn.prepareStatement(sql);
            rs = stm.executeQuery();
            while (rs.next()) {
                c.setCateID(rs.getInt(1));
                c.setParentID(rs.getInt(2));
                c.setCode(rs.getString(3));
                c.setCate(rs.getString(4));
                c.setStatus(rs.getBoolean(5));
                c.setCreatedAt(rs.getDate(6));
                c.setCreatedBy(rs.getInt(7));
                c.setModifiedAt(rs.getDate(8));
                c.setModifiedBy(rs.getInt(9));
                c.setUrl(rs.getString(10));
            }
        } catch (Exception e) {
            System.out.println("getCategorybyId error: " + e.getMessage());
        }
        return c;
    }

    public static void changeCateStatus(String id, String status) {
        try {
            status = status.equalsIgnoreCase("true") ? "false" : "true";
            String sql = "Update [Categories] Set [status] = '" + status + "' "
                    + "Where [cate_id]=" + id + "";

            cnn = DBConnect.getConnection();
            stm = cnn.prepareStatement(sql);
            stm.execute();
        } catch (Exception e) {
            System.out.println("setBrandStatus Error:" + e.getMessage());
        }
    }

    public static void updateCategory(Category c) {
        try {
            String sql;
            if (c.getParentID() == 0) {
                sql = "update Categories set "
                        + "parent_id = null,"
                        + "code = ?,"
                        + "cate = ?,"
                        + "status = ?,"
                        + "created_at = ?,"
                        + "created_by = ?,"
                        + "modified_at = ?,"
                        + "modified_by = ? "
                        + "where cate_id = ?";
                cnn = DBConnect.getConnection();
                stm = cnn.prepareStatement(sql);
                stm.setString(1, c.getCode());
                stm.setString(2, c.getCate());
                stm.setBoolean(3, c.isStatus());
                stm.setObject(4, c.getCreatedAt());
                stm.setInt(5, c.getCreatedBy());
                stm.setObject(6, c.getModifiedAt());
                stm.setInt(7, c.getModifiedBy());
                stm.setInt(8, c.getCateID());
                stm.execute();
            } else {
                sql = "update Categories set "
                        + "parent_id = ?,"
                        + "code = ?,"
                        + "cate = ?,"
                        + "status = ?,"
                        + "created_at = ?,"
                        + "created_by = ?,"
                        + "modified_at = ?,"
                        + "modified_by = ? "
                        + "where cate_id = ?";
                cnn = DBConnect.getConnection();
                stm = cnn.prepareStatement(sql);
                stm.setInt(1, c.getParentID());
                stm.setString(2, c.getCode());
                stm.setString(3, c.getCate());
                stm.setBoolean(4, c.isStatus());
                stm.setObject(5, c.getCreatedAt());
                stm.setInt(6, c.getCreatedBy());
                stm.setObject(7, c.getModifiedAt());
                stm.setInt(8, c.getModifiedBy());
                stm.setInt(9, c.getCateID());
                stm.execute();
            }
        } catch (Exception e) {
            System.out.println("updateCategory error: " + e.getMessage());
        }
    }

    public static ArrayList<Category> getListByPage(ArrayList<Category> list, int begin, int end) {
        ArrayList<Category> myList = new ArrayList<>();
        int myEnd = Math.min(end, list.size());
        for (int i = begin; i < myEnd; i++) {
            myList.add(list.get(i));
        }
        return myList;
    }

    public static ArrayList<Category> getlistCategoryWithNullParentID() {
        try {
            ArrayList<Category> list = new ArrayList<>();
            String sql = "select cate_id,parent_id,code,cate,[status],created_at,created_by,modified_at,modified_by, [url]\n"
                    + "from Categories where parent_id IS NULL ";
            Connection conn = DBConnect.getConnection();
            PreparedStatement pstm = conn.prepareStatement(sql);
            ResultSet rs = pstm.executeQuery();
            while (rs.next()) {
                Category c = new Category();
                c.setCateID(rs.getInt(1));
                c.setParentID(rs.getInt(2));
                c.setCode(rs.getString(3));
                c.setCate(rs.getString(4));
                c.setStatus(rs.getBoolean(5));
                c.setCreatedAt(rs.getDate(6));
                c.setCreatedBy(rs.getInt(7));
                c.setModifiedAt(rs.getDate(8));
                c.setModifiedBy(rs.getInt(9));
                c.setUrl(rs.getString(10));
                list.add(c);
            }
            return list;
        } catch (Exception e) {
            System.out.println("getlistCategoryWithNullParentID error: " + e.getMessage());
        }
        return null;
    }

    public static ArrayList<Category> getlistCategoryWithNotNullParentID() {
        try {
            ArrayList<Category> list = new ArrayList<>();
            String sql = "select cate_id,parent_id,code,cate,[status],created_at,created_by,modified_at,modified_by, [url]\n"
                    + "from Categories where parent_id IS NOT NULL ";
            Connection conn = DBConnect.getConnection();
            PreparedStatement pstm = conn.prepareStatement(sql);
            ResultSet rs = pstm.executeQuery();
            while (rs.next()) {
                Category c = new Category();
                c.setCateID(rs.getInt(1));
                c.setParentID(rs.getInt(2));
                c.setCode(rs.getString(3));
                c.setCate(rs.getString(4));
                c.setStatus(rs.getBoolean(5));
                c.setCreatedAt(rs.getDate(6));
                c.setCreatedBy(rs.getInt(7));
                c.setModifiedAt(rs.getDate(8));
                c.setModifiedBy(rs.getInt(9));
                c.setUrl(rs.getString(10));
                list.add(c);
            }
            return list;
        } catch (Exception e) {
            System.out.println("getlistCategoryWithNotNullParentID error: " + e.getMessage());
        }
        return null;
    }

    public static int getCateIDMax() {
        int res = -1;
        try {
            String str = "select max(cate_id) as 'max'\n"
                    + "from Categories";
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

    public static ArrayList<Category> getListCategoryContains(String name, String name0, String status, String status0, String target) {
        ArrayList<Category> list = new ArrayList<>();
        try {
            String str = "select cate_id,parent_id,code,cate,[status],created_at,created_by,modified_at,modified_by\n"
                    + "from Categories where " + name + " like '%" + name0 + "%'\n"
                    + "and " + status + " like '%" + status0 + "%'\n"
                    + "order by " + target + " desc, cate_id desc";
            Connection conn = DBConnect.getConnection();
            PreparedStatement pstm = conn.prepareStatement(str);
            ResultSet rs = pstm.executeQuery();
            while (rs.next()) {
                Category c = new Category();
                c.setCateID(rs.getInt(1));
                c.setParentID(rs.getInt(2));
                c.setCode(rs.getString(3));
                c.setCate(rs.getString(4));
                c.setStatus(rs.getBoolean(5));
                c.setCreatedAt(rs.getDate(6));
                c.setCreatedBy(rs.getInt(7));
                c.setModifiedAt(rs.getDate(8));
                c.setModifiedBy(rs.getInt(9));

                list.add(c);
            }
        } catch (Exception e) {
            System.out.println("getListCategoryContains error: " + e.getMessage());
        }

        return list;
    }
//    public static void main(String[] args) {
//        System.out.println(getCategorybyID(0));
//    }

    public static int getParentIDByCateID(int cateID) {
        try {
            String sql = "Select parent_id from Categories where cate_id=" + cateID + "";
            Connection conn = DBConnect.getConnection();
            PreparedStatement pstm = conn.prepareStatement(sql);
            ResultSet rs = pstm.executeQuery();
            while (rs.next()) {
                return rs.getInt(1);
            }
        } catch (Exception e) {
            System.out.println("getListCategoryContains error: " + e.getMessage());
        }
        return 0;
    }

    public static ArrayList<Category> getHierarchicalCategoryList() {
        ArrayList<Category> list = new ArrayList<>();
        try {
            String str = "select par.cate_id, par.parent_id, par.code,par.cate, par.[status], par.created_at, par.created_by, par.modified_at, par.modified_by \n"
                    + ", chil.cate_id, chil.parent_id, chil.code,chil.cate, chil.[status], chil.created_at, chil.created_by, chil.modified_at, chil.modified_by \n"
                    + "from Categories par\n"
                    + "left join Categories chil on par.cate_id=chil.parent_id\n"
                    + "where par.parent_id is null\n"
                    + "order by par.cate_id";
            Connection conn = DBConnect.getConnection();
            PreparedStatement pstm = conn.prepareStatement(str);
            ResultSet rs = pstm.executeQuery();
            int preID = -1;
            while (rs.next()) {
                Category chil = new Category();
                chil.setCateID(rs.getInt(10));
                chil.setParentID(rs.getInt(11));
                chil.setCode(rs.getString(12));
                chil.setCate(rs.getString(13));
                chil.setStatus(rs.getBoolean(14));
                chil.setCreatedAt(rs.getDate(15));
                chil.setCreatedBy(rs.getInt(16));
                chil.setModifiedAt(rs.getDate(17));
                chil.setModifiedBy(rs.getInt(18));
                if (preID != rs.getInt(1)) {
                    Category par = new Category();
                    par.setCateID(rs.getInt(1));
                    par.setParentID(rs.getInt(2));
                    par.setCode(rs.getString(3));
                    par.setCate(rs.getString(4));
                    par.setStatus(rs.getBoolean(5));
                    par.setCreatedAt(rs.getDate(6));
                    par.setCreatedBy(rs.getInt(7));
                    par.setModifiedAt(rs.getDate(8));
                    par.setModifiedBy(rs.getInt(9));
                    if (chil.getCateID() != 0) {
                        par.getChildren().add(chil);                        
                    }
                    
                    preID = par.getCateID();
                    list.add(par);
                } else {                                            
                    list.get(list.size()-1).getChildren().add(chil);                                                                
                }
            }
        } catch (SQLException e) {
            System.out.println("Get hierarchical list category error: " + e.getMessage());
        }

        return list;
    }        
}
