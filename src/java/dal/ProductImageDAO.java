/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package dal;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.SQLException;
import model.ProductImage;

/**
 *
 * @author TNA
 */
public class ProductImageDAO {

    public static void insertProductImage(ProductImage pimg) {
        try {
            String str = "insert into ProductImages(product_id,code,[image],[level],[status],created_at\n"
                    + ",created_by,modified_at,modified_by)\n"
                    + "values (?,?,?,?,?,?,?,?,?)";
            Connection conn = DBConnect.getConnection();
            PreparedStatement pstm = conn.prepareStatement(str);
            pstm.setInt(1, pimg.getProductID());
            pstm.setString(2, pimg.getCode());
            pstm.setString(3, pimg.getImage());
            pstm.setInt(4, pimg.getLevel());
            pstm.setBoolean(5, pimg.isStatus());
            pstm.setObject(6, pimg.getCreatedAt());
            pstm.setInt(7, pimg.getCreatedBy());
            pstm.setObject(8, pimg.getModifiedAt());
            pstm.setInt(9, pimg.getModifiedBy());
            pstm.executeUpdate();
        } catch (SQLException e) {
            System.out.println("insert product image error: " + e);
        }
    }

    public static void deleteByProductID(int pid) {
        try {
            String str = "delete from ProductImages\n"
                    + "where product_id=?";
            Connection conn = DBConnect.getConnection();
            PreparedStatement pstm = conn.prepareStatement(str);
            pstm.setInt(1, pid);
            pstm.executeUpdate();
        } catch (SQLException e) {
            System.out.println("delete product image by product id error: " + e);
        }
    }

    public static void main(String[] args) {
        System.out.println("123412342341234234");
        System.out.println("123412341234");
    }
}
