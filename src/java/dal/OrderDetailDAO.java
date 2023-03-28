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
import model.OrderDetail;

/**
 *
 * @author TNA
 */
public class OrderDetailDAO {

    public static void insertOrderDetail(OrderDetail od) {
        try {
            String str = "insert into OrderDetails(order_id,product_id,code,quantity,unitprice,[status]\n"
                    + ",created_at,created_by,modified_at,modified_by)\n"
                    + "values (?,?,?,?,?,?,?,?,?,?)";
            Connection conn = DBConnect.getConnection();
            PreparedStatement pstm = conn.prepareStatement(str);
            pstm.setInt(1, od.getOrderID());
            pstm.setInt(2, od.getProductID());
            pstm.setString(3, od.getCode());
            pstm.setInt(4, od.getQuantity());
            pstm.setDouble(5, od.getUnitPrice());
            pstm.setBoolean(6, od.isStatus());
            pstm.setObject(7, od.getCreatedAt());
            pstm.setInt(8, od.getCreatedBy());
            pstm.setObject(9, od.getModifiedAt());
            pstm.setInt(10, od.getModifiedBy());
            pstm.executeUpdate();
        } catch (SQLException e) {
            System.out.println("insert order detail error: " + e);
        }
    }
        public static void addtOrderDetail(OrderDetail od) {
        try {
            String str = "insert into OrderDetails(order_id,product_id,code,quantity,unitprice,[status])\n"
                    + "values (?,?,?,?,?,?)";
            Connection conn = DBConnect.getConnection();
            PreparedStatement pstm = conn.prepareStatement(str);
            pstm.setInt(1, od.getOrderID());
            pstm.setInt(2, od.getProductID());
            pstm.setString(3, od.getCode());
            pstm.setInt(4, od.getQuantity());
            pstm.setDouble(5, od.getUnitPrice());
            pstm.setBoolean(6, od.isStatus());
            pstm.executeUpdate();
        } catch (SQLException e) {
            System.out.println("insert order detail error: " + e);
        }
    }

    public static ArrayList<OrderDetail> getListActiveByOrderID(int id) {
        ArrayList<OrderDetail> list = new ArrayList<>();
        try {
            String str = "select orderdetail_id,order_id,product_id,code,quantity,unitprice,[status]\n"
                    + ",created_at,created_by,modified_at,modified_by\n"
                    + "from OrderDetails\n"
                    + "where order_id=? and [status]=1";
            Connection conn = DBConnect.getConnection();
            PreparedStatement pstm = conn.prepareStatement(str);
            pstm.setInt(1, id);
            ResultSet rs = pstm.executeQuery();
            while (rs.next()) {
                OrderDetail od = new OrderDetail();
                od.setOrderDetailID(rs.getInt(1));
                od.setOrderID(rs.getInt(2));
                od.setProductID(rs.getInt(3));
                od.setCode(rs.getString(4));
                od.setQuantity(rs.getInt(5));
                od.setUnitPrice(rs.getDouble(6));
                od.setStatus(rs.getBoolean(7));
                od.setCreatedAt(rs.getDate(8));
                od.setCreatedBy(rs.getInt(9));
                od.setModifiedAt(rs.getDate(10));
                od.setModifiedBy(rs.getInt(11));

                list.add(od);
            }
        } catch (SQLException e) {
            System.out.println("get list by order id error: " + e);
        }
        return list;
    }

    public static void deactivateByID(int orderDetailID) {
        try {
            String str = "update OrderDetails\n"
                    + "set [status]=0\n"
                    + "where orderdetail_id=?";
            Connection conn = DBConnect.getConnection();
            PreparedStatement pstm = conn.prepareStatement(str);
            pstm.setInt(1, orderDetailID);
            pstm.executeUpdate();
        } catch (SQLException e) {
            System.out.println("deactive by id error: " + e);
        }
    }

    public static void updateOrderDetail(OrderDetail od) {
        try {
            String str = "update OrderDetails\n"
                    + "set product_id=?,quantity=?,unitprice=?,[status]=?\n"                    
                    + ",modified_at=?,modified_by=?\n"
                    + "where orderdetail_id=?";
            Connection conn = DBConnect.getConnection();
            PreparedStatement pstm = conn.prepareStatement(str);                        
            pstm.setInt(1, od.getProductID());
            pstm.setInt(2, od.getQuantity());
            pstm.setDouble(3, od.getUnitPrice());
            pstm.setBoolean(4, od.isStatus());            
            pstm.setObject(5, od.getModifiedAt());
            pstm.setInt(6, od.getModifiedBy());
            pstm.setInt(7, od.getOrderDetailID());
            pstm.executeUpdate();
        } catch (SQLException e) {
            System.out.println("update order detail error: " + e);
        }
    }

    public static int getQuantityOrderFromDate(String date) {
        int res = 0;
        try {
            String str = "select COUNT(orderdetail_id)\n"
                    + "from OrderDetails\n"
                    + "where created_at >= ?";
            Connection conn = DBConnect.getConnection();
            PreparedStatement pstm = conn.prepareStatement(str);
            pstm.setString(1, date);
            ResultSet rs = pstm.executeQuery();
            while (rs.next()) {
                res = rs.getInt(1);
            }
        } catch (SQLException e) {
            System.out.println("get max orderid error: " + e);
        }
        return res;
    }
}
