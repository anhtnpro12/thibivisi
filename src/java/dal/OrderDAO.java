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
import java.util.ArrayList;
import java.util.Date;
import java.util.logging.Level;
import java.util.logging.Logger;
import model.Customer;
import model.Order;
import model.OrderDetail;

/**
 *
 * @author TNA
 */
public class OrderDAO {

    public static void insertOrder(Order o) {
        try {
            String str = "insert into Orders(customer_id,[user_id],code,[address],total,[state],[status]\n"
                    + ",created_at,created_by,modified_at,modified_by,[payment])\n"
                    + "values (?,?,?,?,?,?,?,?,?,?,?,?)";
            Connection conn = DBConnect.getConnection();
            PreparedStatement pstm = conn.prepareStatement(str);
            pstm.setInt(1, o.getCustomerID());
            pstm.setInt(2, o.getUserID());
            pstm.setString(3, o.getCode());
            pstm.setString(4, o.getAddress());
            pstm.setDouble(5, o.getTotal());
            pstm.setInt(6, o.getState());
            pstm.setBoolean(7, o.isStatus());
            pstm.setObject(8, o.getCreatedAt());
            pstm.setInt(9, o.getCreatedBy());
            pstm.setObject(10, o.getModifiedAt());
            pstm.setInt(11, o.getModifiedBy());
            pstm.setBoolean(12, o.isPayment());
            pstm.executeUpdate();
        } catch (SQLException e) {
            System.out.println("insert order error: " + e);
        }
    }

    public static void addOrder(Order o) {
        try {
            String str = "insert into Orders(customer_id,code,[address],total,[state],[status],[payment])\n"
                    + "values (?,?,?,?,?,?,?)";
            Connection conn = DBConnect.getConnection();
            PreparedStatement pstm = conn.prepareStatement(str);
            pstm.setInt(1, o.getCustomerID());
            pstm.setString(2, o.getCode());
            pstm.setString(3, o.getAddress());
            pstm.setDouble(4, o.getTotal());
            pstm.setInt(5, o.getState());
            pstm.setBoolean(6, o.isStatus());
            pstm.setBoolean(7, o.isPayment());
            pstm.executeUpdate();
        } catch (SQLException e) {
            System.out.println("insert order error: " + e);
        }
    }

    public static void changeOrderStatus(int orderID, boolean status, int modifiedBy, Date modifiedAt) {
        try {
            String str = "update Orders\n"
                    + "set [status]=?, modified_by=?, modified_at=?\n"
                    + "where order_id=?";
            Connection conn = DBConnect.getConnection();
            PreparedStatement pstm = conn.prepareStatement(str);
            pstm.setBoolean(1, status);
            pstm.setInt(2, modifiedBy);
            pstm.setObject(3, modifiedAt);
            pstm.setInt(4, orderID);
            pstm.executeUpdate();
        } catch (SQLException e) {
            System.out.println("change order status error: " + e);
        }
    }

    public static ArrayList<Order> getAllOrder() {
        ArrayList<Order> list = new ArrayList<>();
        try {
            String str = "select o.order_id,o.customer_id,o.[user_id],o.code,o.[address],o.total,o.[state],o.[status],o.created_at,o.created_by\n"
                    + ",o.modified_at,o.modified_by,o.payment\n"
                    + ",od.orderdetail_id,od.order_id,od.product_id,od.code,od.quantity,od.unitprice,od.[status],od.created_at,od.created_by,\n"
                    + "od.modified_at,od.modified_by\n"
                    + "from Orders o\n"
                    + "left join OrderDetails od on o.order_id=od.order_id\n"
                    + "order by o.order_id desc, od.orderdetail_id asc";
            Connection conn = DBConnect.getConnection();
            PreparedStatement pstm = conn.prepareStatement(str);
            ResultSet rs = pstm.executeQuery();
            int preID = -1;
            while (rs.next()) {
                OrderDetail od = new OrderDetail();
                od.setOrderDetailID(rs.getInt(14));
                od.setOrderID(rs.getInt(15));
                od.setProductID(rs.getInt(16));
                od.setCode(rs.getString(17));
                od.setQuantity(rs.getInt(18));
                od.setUnitPrice(rs.getDouble(19));
                od.setStatus(rs.getBoolean(20));
                od.setCreatedAt(rs.getDate(21));
                od.setCreatedBy(rs.getInt(22));
                od.setModifiedAt(rs.getDate(23));
                od.setModifiedBy(rs.getInt(24));
                if (preID != rs.getInt(1)) {
                    Order o = new Order();
                    o.setOrderID(rs.getInt(1));
                    o.setCustomerID(rs.getInt(2));
                    o.setUserID(rs.getInt(3));
                    o.setCode(rs.getString(4));
                    o.setAddress(rs.getString(5));
                    o.setTotal(rs.getDouble(6));
                    o.setState(rs.getInt(7));
                    o.setStatus(rs.getBoolean(8));
                    o.setCreatedAt(rs.getDate(9));
                    o.setCreatedBy(rs.getInt(10));
                    o.setModifiedAt(rs.getDate(11));
                    o.setModifiedBy(rs.getInt(12));
                    o.setPayment(rs.getBoolean(13));
                    o.getOrderDetail().add(od);
                    preID = o.getOrderID();

                    list.add(o);
                } else {
                    list.get(list.size() - 1).getOrderDetail().add(od);
                }
            }
        } catch (SQLException e) {
            System.out.println("get all order error: " + e);
        }
        return list;
    }

    public static ArrayList<Order> getListOrderByStatus(boolean status) {
        ArrayList<Order> list = new ArrayList<>();
        try {
            String str = "-- get order list\n"
                    + "select o.order_id,o.customer_id,o.[user_id],o.code,o.[address],o.total,o.[state],o.[status],o.created_at,o.created_by\n"
                    + ",o.modified_at,o.modified_by,o.payment\n"
                    + ",od.orderdetail_id,od.order_id,od.product_id,od.code,od.quantity,od.unitprice,od.[status],od.created_at,od.created_by,\n"
                    + "od.modified_at,od.modified_by\n"
                    + "from Orders o\n"
                    + "left join OrderDetails od on o.order_id=od.order_id\n"
                    + "where o.[status]=?\n"
                    + "order by o.order_id desc, od.orderdetail_id asc";
            Connection conn = DBConnect.getConnection();
            PreparedStatement pstm = conn.prepareStatement(str);
            pstm.setBoolean(1, status);
            ResultSet rs = pstm.executeQuery();
            int preID = -1;
            while (rs.next()) {
                OrderDetail od = new OrderDetail();
                od.setOrderDetailID(rs.getInt(14));
                od.setOrderID(rs.getInt(15));
                od.setProductID(rs.getInt(16));
                od.setCode(rs.getString(17));
                od.setQuantity(rs.getInt(18));
                od.setUnitPrice(rs.getDouble(19));
                od.setStatus(rs.getBoolean(20));
                od.setCreatedAt(rs.getDate(21));
                od.setCreatedBy(rs.getInt(22));
                od.setModifiedAt(rs.getDate(23));
                od.setModifiedBy(rs.getInt(24));
                if (preID != rs.getInt(1)) {
                    Order o = new Order();
                    o.setOrderID(rs.getInt(1));
                    o.setCustomerID(rs.getInt(2));
                    o.setUserID(rs.getInt(3));
                    o.setCode(rs.getString(4));
                    o.setAddress(rs.getString(5));
                    o.setTotal(rs.getDouble(6));
                    o.setState(rs.getInt(7));
                    o.setStatus(rs.getBoolean(8));
                    o.setCreatedAt(rs.getDate(9));
                    o.setCreatedBy(rs.getInt(10));
                    o.setModifiedAt(rs.getDate(11));
                    o.setModifiedBy(rs.getInt(12));
                    o.setPayment(rs.getBoolean(13));
                    o.getOrderDetail().add(od);
                    preID = o.getOrderID();

                    list.add(o);
                } else {
                    list.get(list.size() - 1).getOrderDetail().add(od);
                }
            }
        } catch (SQLException e) {
            System.out.println("get all order error: " + e);
        }
        return list;
    }

    public static ArrayList<Order> getListByPage(ArrayList<Order> list, int begin, int end) {
        ArrayList<Order> myList = new ArrayList<>();
        int myEnd = Math.min(end, list.size());
        for (int i = begin; i < myEnd; i++) {
            myList.add(list.get(i));
        }
        return myList;
    }

    public static int getQuantityOrder() {
        int res = 0;
        try {
            String str = "select COUNT(o.order_id)\n"
                    + "from Orders o";
            Connection conn = DBConnect.getConnection();
            PreparedStatement pstm = conn.prepareStatement(str);
            ResultSet rs = pstm.executeQuery();
            while (rs.next()) {
                res = rs.getInt(1);
            }
        } catch (SQLException e) {
            System.out.println("get quantity order error: " + e);
        }
        return res;
    }

    public static void updatePlusTotalOrder(int orderID, double value) {
        try {
            String str = "update Orders\n"
                    + "set total=total+?\n"
                    + "where order_id=?";
            Connection conn = DBConnect.getConnection();
            PreparedStatement pstm = conn.prepareStatement(str);
            pstm.setDouble(1, value);
            pstm.setInt(2, orderID);
            pstm.executeUpdate();
        } catch (SQLException e) {
            System.out.println("update plus total order error: " + e);
        }
    }

    public static Order getOrderByID(int id) {
        Order o = null;
        try {
            String str = "select o.order_id,o.customer_id,o.[user_id],o.code,o.[address],o.total,o.[state],o.[status],o.created_at,o.created_by\n"
                    + ",o.modified_at,o.modified_by,o.payment\n"
                    + ",od.orderdetail_id,od.order_id,od.product_id,od.code,od.quantity,od.unitprice,od.[status],od.created_at,od.created_by,\n"
                    + "od.modified_at,od.modified_by\n"
                    + "from Orders o\n"
                    + "left join OrderDetails od on o.order_id=od.order_id\n"
                    + "where o.order_id=?";
            Connection conn = DBConnect.getConnection();
            PreparedStatement pstm = conn.prepareStatement(str);
            pstm.setInt(1, id);
            ResultSet rs = pstm.executeQuery();
            int preID = -1;
            while (rs.next()) {
                OrderDetail od = new OrderDetail();
                od.setOrderDetailID(rs.getInt(14));
                od.setOrderID(rs.getInt(15));
                od.setProductID(rs.getInt(16));
                od.setCode(rs.getString(17));
                od.setQuantity(rs.getInt(18));
                od.setUnitPrice(rs.getDouble(19));
                od.setStatus(rs.getBoolean(20));
                od.setCreatedAt(rs.getDate(21));
                od.setCreatedBy(rs.getInt(22));
                od.setModifiedAt(rs.getDate(23));
                od.setModifiedBy(rs.getInt(24));
                if (preID != rs.getInt(1)) {
                    o = new Order();
                    o.setOrderID(rs.getInt(1));
                    o.setCustomerID(rs.getInt(2));
                    o.setUserID(rs.getInt(3));
                    o.setCode(rs.getString(4));
                    o.setAddress(rs.getString(5));
                    o.setTotal(rs.getDouble(6));
                    o.setState(rs.getInt(7));
                    o.setStatus(rs.getBoolean(8));
                    o.setCreatedAt(rs.getTimestamp(9));
                    o.setCreatedBy(rs.getInt(10));
                    o.setModifiedAt(rs.getTimestamp(11));
                    o.setModifiedBy(rs.getInt(12));
                    o.setPayment(rs.getBoolean(13));
                    o.getOrderDetail().add(od);
                    preID = o.getOrderID();
                } else {
                    o.getOrderDetail().add(od);
                }
            }
        } catch (Exception e) {
            System.out.println("get order by id error: " + e);
        }
        return o;
    }

    public static Order getOrderByCode(String code) {
        Order o = null;
        try {
            String str = "select o.order_id,o.customer_id,o.[user_id],o.code,o.[address],o.total,o.[state],o.[status],o.created_at,o.created_by\n"
                    + ",o.modified_at,o.modified_by,o.payment\n"
                    + ",od.orderdetail_id,od.order_id,od.product_id,od.code,od.quantity,od.unitprice,od.[status],od.created_at,od.created_by,\n"
                    + "od.modified_at,od.modified_by\n"
                    + "from Orders o\n"
                    + "left join OrderDetails od on o.order_id=od.order_id\n"
                    + "where o.code=?";
            Connection conn = DBConnect.getConnection();
            PreparedStatement pstm = conn.prepareStatement(str);
            pstm.setString(1, code);
            ResultSet rs = pstm.executeQuery();
            int preID = -1;
            while (rs.next()) {
                OrderDetail od = new OrderDetail();
                od.setOrderDetailID(rs.getInt(14));
                od.setOrderID(rs.getInt(15));
                od.setProductID(rs.getInt(16));
                od.setCode(rs.getString(17));
                od.setQuantity(rs.getInt(18));
                od.setUnitPrice(rs.getDouble(19));
                od.setStatus(rs.getBoolean(20));
                od.setCreatedAt(rs.getDate(21));
                od.setCreatedBy(rs.getInt(22));
                od.setModifiedAt(rs.getDate(23));
                od.setModifiedBy(rs.getInt(24));
                if (preID != rs.getInt(1)) {
                    o = new Order();
                    o.setOrderID(rs.getInt(1));
                    o.setCustomerID(rs.getInt(2));
                    o.setUserID(rs.getInt(3));
                    o.setCode(rs.getString(4));
                    o.setAddress(rs.getString(5));
                    o.setTotal(rs.getDouble(6));
                    o.setState(rs.getInt(7));
                    o.setStatus(rs.getBoolean(8));
                    o.setCreatedAt(rs.getTimestamp(9));
                    o.setCreatedBy(rs.getInt(10));
                    o.setModifiedAt(rs.getTimestamp(11));
                    o.setModifiedBy(rs.getInt(12));
                    o.setPayment(rs.getBoolean(13));
                    o.getOrderDetail().add(od);
                    preID = o.getOrderID();
                } else {
                    o.getOrderDetail().add(od);
                }
            }
        } catch (Exception e) {
            System.out.println("get order by code error: " + e);
        }
        return o;
    }

    public static void updateOrder(Order o) {
        try {
            String str = "update Orders\n"
                    + "set customer_id=?,[user_id]=?,code=?,[address]=?,total=?,[state]=?,[status]=?\n"
                    + ",created_at=?,created_by=?,modified_at=?,modified_by=?,payment=?\n"
                    + "where order_id=?";
            Connection conn = DBConnect.getConnection();
            PreparedStatement pstm = conn.prepareStatement(str);
            pstm.setInt(1, o.getCustomerID());
            pstm.setInt(2, o.getUserID());
            pstm.setString(3, o.getCode());
            pstm.setString(4, o.getAddress());
            pstm.setDouble(5, o.getTotal());
            pstm.setInt(6, o.getState());
            pstm.setBoolean(7, o.isStatus());
            pstm.setObject(8, o.getCreatedAt());
            pstm.setInt(9, o.getCreatedBy());
            pstm.setObject(10, o.getModifiedAt());
            pstm.setInt(11, o.getModifiedBy());
            pstm.setBoolean(12, o.isPayment());
            pstm.setInt(13, o.getOrderID());
            pstm.executeUpdate();
        } catch (SQLException e) {
            System.out.println("update order error: " + e);
        }
    }

    public static int getMaxOrderID() {
        int res = 0;
        try {
            String str = "select MAX(o.order_id)\n"
                    + "from Orders o";
            Connection conn = DBConnect.getConnection();
            PreparedStatement pstm = conn.prepareStatement(str);
            ResultSet rs = pstm.executeQuery();
            while (rs.next()) {
                res = rs.getInt(1);
            }
        } catch (SQLException e) {
            System.out.println("get max orderid error: " + e);
        }
        return res;
    }

    public static int getQuantityOrderFromDate(String date) {
        int res = 0;
        try {
            String str = "select COUNT(order_id)\n"
                    + "from Orders\n"
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

    public static void main(String[] args) {
        SimpleDateFormat sdf2 = new SimpleDateFormat("yyyy-MM-dd");
        System.out.println(OrderDAO.getQuantityOrderFromDate(sdf2.format(new Date())));
    }

    public static ArrayList<Order> getOrderListByCustomer(Customer c) {
        ArrayList<Order> list = new ArrayList<>();
        try {
            String str = ""
                    + "select o.order_id,o.customer_id,o.[user_id],o.code,o.[address],o.total,o.[state],o.[status],o.created_at,o.created_by\n"
                    + ",o.modified_at,o.modified_by,o.payment\n"
                    + ",od.orderdetail_id,od.order_id,od.product_id,od.code,od.quantity,od.unitprice,od.[status],od.created_at,od.created_by,\n"
                    + "od.modified_at,od.modified_by\n"
                    + "from Orders o\n"
                    + "left join OrderDetails od on o.order_id=od.order_id\n"
                    + "where o.customer_id=?";
            Connection conn = DBConnect.getConnection();
            PreparedStatement pstm = conn.prepareStatement(str);
            pstm.setInt(1, c.getCustomerID());
            ResultSet rs = pstm.executeQuery();
            int preID = -1;
            while (rs.next()) {
                OrderDetail od = new OrderDetail();
                od.setOrderDetailID(rs.getInt(14));
                od.setOrderID(rs.getInt(15));
                od.setProductID(rs.getInt(16));
                od.setCode(rs.getString(17));
                od.setQuantity(rs.getInt(18));
                od.setUnitPrice(rs.getDouble(19));
                od.setStatus(rs.getBoolean(20));
                od.setCreatedAt(rs.getDate(21));
                od.setCreatedBy(rs.getInt(22));
                od.setModifiedAt(rs.getDate(23));
                od.setModifiedBy(rs.getInt(24));
                if (preID != rs.getInt(1)) {
                    Order o = new Order();
                    o.setOrderID(rs.getInt(1));
                    o.setCustomerID(rs.getInt(2));
                    o.setUserID(rs.getInt(3));
                    o.setCode(rs.getString(4));
                    o.setAddress(rs.getString(5));
                    o.setTotal(rs.getDouble(6));
                    o.setState(rs.getInt(7));
                    o.setStatus(rs.getBoolean(8));
                    o.setCreatedAt(rs.getDate(9));
                    o.setCreatedBy(rs.getInt(10));
                    o.setModifiedAt(rs.getDate(11));
                    o.setModifiedBy(rs.getInt(12));
                    o.setPayment(rs.getBoolean(13));
                    o.getOrderDetail().add(od);
                    preID = o.getOrderID();

                    list.add(o);
                } else {
                    list.get(list.size() - 1).getOrderDetail().add(od);
                }
            }
        } catch (SQLException e) {
            System.out.println("getOrderListByCustomer error: " + e);
        }
        return list;
    }

    public static int getQuantityOrderBetweenDate(String from, String to) {
        int res = 0;
        try {
            String str = "select count(order_id)\n"
                    + "from Orders\n"
                    + "where modified_at >= ? and modified_at < ?";
            Connection conn = DBConnect.getConnection();
            PreparedStatement pstm = conn.prepareStatement(str);
            pstm.setString(1, from);
            pstm.setString(2, to);
            ResultSet rs = pstm.executeQuery();
            while (rs.next()) {
                res = rs.getInt(1);
            }
        } catch (SQLException e) {
            System.out.println("get max orderid error: " + e);
        }
        return res;
    }

    public static double getTotalBetweenDate(String from, String to) {
        double res = 0;
        try {
            String str = "select SUM(total)\n"
                    + "from Orders\n"
                    + "where modified_at >= ? and modified_at < ?";
            Connection conn = DBConnect.getConnection();
            PreparedStatement pstm = conn.prepareStatement(str);
            pstm.setString(1, from);
            pstm.setString(2, to);
            ResultSet rs = pstm.executeQuery();
            while (rs.next()) {
                res = rs.getDouble(1);
            }
        } catch (SQLException e) {
            System.out.println("getTotalBetweenDate error: " + e);
        }
        return res;
    }

    public static double getPaymentBetweenDate(String from, String to) {
        double res = 0;
        try {
            String str = "select SUM(total)\n"
                    + "from Orders\n"
                    + "where payment=1 and modified_at >= ? and modified_at < ?";
            Connection conn = DBConnect.getConnection();
            PreparedStatement pstm = conn.prepareStatement(str);
            pstm.setString(1, from);
            pstm.setString(2, to);
            ResultSet rs = pstm.executeQuery();
            while (rs.next()) {
                res = rs.getDouble(1);
            }
        } catch (SQLException e) {
            System.out.println("getPaymentBetweenDate error: " + e);
        }
        return res;
    }
}
