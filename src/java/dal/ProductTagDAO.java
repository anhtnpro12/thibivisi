/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package dal;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.SQLException;
import model.ProductTag;

/**
 *
 * @author TNA
 */
public class ProductTagDAO {

    public static void insertProductTag(ProductTag pt) {
        try {
            String str = "INSERT INTO [Products_ProductTags]\n"
                    + "           ([product_id]\n"
                    + "           ,[tag_id])\n"
                    + "     VALUES\n"
                    + "           (?\n"
                    + "           ,?)";
            Connection connection = DBConnect.getConnection();
            PreparedStatement pstm = connection.prepareStatement(str);
            pstm.setInt(1, pt.getProductID());
            pstm.setInt(2, pt.getTagID());
            pstm.executeUpdate();
        } catch (SQLException e) {
            System.out.println("Insert product tag error: " + e);
        }
    }

    public static void deletebyProductID(int pID) {
        try {
            String str = "delete from Products_ProductTags\n"
                    + "where product_id = ?";
            Connection connection = DBConnect.getConnection();
            PreparedStatement pstm = connection.prepareStatement(str);
            pstm.setInt(1, pID);            
            pstm.executeUpdate();
        } catch (SQLException e) {
            System.out.println("deletebyProductID error: " + e);
        }
    }
}
