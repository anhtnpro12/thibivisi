package dal;

import static dal.UserDAO.sendMail;
import model.Brand;

import java.sql.Connection;
import java.util.Date;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import model.Category;

public class BrandDAO {

    static Connection cnn; // kết nối
    static PreparedStatement stm; // thực hiên các cáu lệnh sql
    static ResultSet rs; // lưu trữ và xử lí dữ liệu

    public static void insertBrand(Brand b) {
        try {
            String str = "insert into Brands(code,[name],logo,[status],created_at,created_by,modified_at,modified_by)\n"
                    + "values (?, ?, ?, ?, ?, ?, ?, ?)";
            Connection conn = DBConnect.getConnection();
            PreparedStatement pstm = conn.prepareStatement(str);
            pstm.setString(1, genCode());
            pstm.setString(2, b.getName());
            pstm.setString(3, b.getLogo());
            pstm.setBoolean(4, b.isStatus());
            pstm.setObject(5, b.getCreatedAt());
            pstm.setInt(6, b.getCreatedBy());
            pstm.setObject(7, b.getModifiedAt());
            pstm.setInt(8, b.getModifiedBy());
            pstm.executeUpdate();
        } catch (SQLException e) {
            System.out.println("Insert brand error: " + e);
        }
    }

    public static void updateBrand(Brand b) {
        try {
            String sql = "update [Brands]"
                    + " Set [code]=?"
                    + ", [name]=?"
                    + ",[logo]=?"
                    + ",[status]=?"
                    + ",[created_at]=?"
                    + ",[created_by]=?"
                    + ",[modified_at]=?"
                    + ",[modified_by]=?"
                    + " Where [brand_id]=?";
            Connection conn = DBConnect.getConnection();
            PreparedStatement pstm = conn.prepareStatement(sql);
            pstm.setString(1, b.getCode());
            pstm.setString(2, b.getName());
            pstm.setString(3, b.getLogo());
            pstm.setBoolean(4, b.isStatus());
            pstm.setObject(5, b.getCreatedAt());
            pstm.setInt(6, b.getCreatedBy());
            pstm.setObject(7, b.getModifiedAt());
            pstm.setInt(8, b.getModifiedBy());
            pstm.setInt(9, b.getBrandID());
            pstm.executeUpdate();
        } catch (Exception e) {
            System.out.println("updateBrand error: " + e.getMessage());
        }
    }

    public static void deleteBrandbyId(String id) {
        try {
            String sql = "delete from [Brands] where id=" + id + "";
            cnn = DBConnect.getConnection();
            stm = cnn.prepareStatement(sql);
            stm.execute();
        } catch (Exception e) {
            System.out.println("deleteBrandbyid Error:" + e.getMessage());
        }
    }

    public static ArrayList<Brand> getListBrand() {
        ArrayList<Brand> list = new ArrayList<>();
        try {
            String str = "select brand_id,code,[name],logo,[status],created_at,created_by,modified_at,modified_by\n"
                    + "from Brands";
            Connection conn = DBConnect.getConnection();
            PreparedStatement pstm = conn.prepareStatement(str);
            ResultSet rs = pstm.executeQuery();
            while (rs.next()) {
                Brand b = new Brand();
                b.setBrandID(rs.getInt(1));
                b.setCode(rs.getString(2));
                b.setName(rs.getString(3));
                b.setLogo(rs.getString(4));
                b.setStatus(rs.getBoolean(5));
                b.setCreatedAt(rs.getDate(6));
                b.setCreatedBy(rs.getInt(7));
                b.setModifiedAt(rs.getDate(8));
                b.setModifiedBy(rs.getInt(9));

                list.add(b);
            }
        } catch (SQLException e) {
            System.out.println("get list brand error: " + e);
        }

        return list;
    }

    public static void changeBrandStatus(String id, String status) {
        try {
            String sql;
            if (status.equalsIgnoreCase("true")) {
                sql = "Update [Brands] Set [status] = 'false' "
                        + "Where [brand_id]=" + id + "";
            } else {
                sql = "Update [Brands] Set [status] = 'true' "
                        + "Where [brand_id]=" + id + "";
            }
            cnn = DBConnect.getConnection();
            stm = cnn.prepareStatement(sql);
            stm.execute();
        } catch (Exception e) {
            System.out.println("setBrandStatus Error:" + e.getMessage());
        }
    }

    public static ArrayList<Brand> getListByPage(ArrayList<Brand> list, int begin, int end) {
        ArrayList<Brand> myList = new ArrayList<>();
        int myEnd = Math.min(end, list.size());
        for (int i = begin; i < myEnd; i++) {
            myList.add(list.get(i));
        }
        return myList;
    }

    public static String genCode() {
        String count = null;
        try {
            String sql = "select COUNT(brand_id) from Brands";
            cnn = DBConnect.getConnection();
            stm = cnn.prepareStatement(sql);
            rs = stm.executeQuery();
            while (rs.next()) {
                count = rs.getString(1);
            }
        } catch (Exception e) {
            System.out.println("gencode error: " + e.getMessage());
        }
        int counts=Integer.parseInt(count)+1;
        return "brand"+counts;
    }

    public static Brand getBrandbyId(int brand_id) {
        Brand b = new Brand();
        try {
            String sql = "select brand_id,code,[name],logo,[status],created_at,created_by,modified_at,modified_by\n"
                    + "from Brands where brand_id = ?";
            cnn = DBConnect.getConnection();
            stm = cnn.prepareStatement(sql);
            stm.setInt(1, brand_id);
            rs = stm.executeQuery();
            while (rs.next()) {
                b.setBrandID(rs.getInt(1));
                b.setCode(rs.getString(2));
                b.setName(rs.getString(3));
                b.setLogo(rs.getString(4));
                b.setStatus(rs.getBoolean(5));
                b.setCreatedAt(rs.getDate(6));
                b.setCreatedBy(rs.getInt(7));
                b.setModifiedAt(rs.getDate(8));
                b.setModifiedBy(rs.getInt(9));
            }
        } catch (Exception e) {
            System.out.println("getBrandbyId error: " + e.getMessage());
        }
        return b;
    }

    public static void main(String[] args) {
        sendMail("trinhminh2907@gmail.com", "Message from customer", "Hello", "minhtche164004@fpt.edu.vn", "hhhsfvcbugpcukrg");
    }
    public static ArrayList<Brand> getListBrandContains(String name, String name0, String status, String status0, String target) {
        ArrayList<Brand> myList = new ArrayList<>();
        try {
            String sql = "select brand_id,code,[name],logo,[status],created_at,created_by,modified_at,modified_by\n"
                    + "from Brands where " + name + " like '%" + name0 + "%'\n"
                    + "and " + status + " like '%" + status0 + "%'\n"
                    + "order by " + target + " desc, brand_id desc";
            cnn = DBConnect.getConnection();
            stm = cnn.prepareStatement(sql);
            rs = stm.executeQuery();
            while (rs.next()) {
                Brand b = new Brand();
                b.setBrandID(rs.getInt(1));
                b.setCode(rs.getString(2));
                b.setName(rs.getString(3));
                b.setLogo(rs.getString(4));
                b.setStatus(rs.getBoolean(5));
                b.setCreatedAt(rs.getDate(6));
                b.setCreatedBy(rs.getInt(7));
                b.setModifiedAt(rs.getDate(8));
                b.setModifiedBy(rs.getInt(9));

                myList.add(b);
            }
        } catch (Exception e) {
            System.out.println("getListBrandContains error: " + e.getMessage());
        }
        return myList;
    }

    public static int getBrandIDMax() {
        int res = -1;
        try {
            String str = "select max(brand_id) as 'max'\n"
                    + "from Brands";
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
}
