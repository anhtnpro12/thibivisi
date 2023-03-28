/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package controller.OrderDetail.Admin;

import dal.OrderDAO;
import dal.OrderDetailDAO;
import dal.ProductDAO;
import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.servlet.http.HttpSession;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Date;
import model.OrderDetail;
import model.Product;
import model.User;

/**
 *
 * @author TNA
 */
@WebServlet(name = "UpdateOrderDetailServlet", value = "/admin-update-order-detail")
public class UpdateOrderDetailServlet extends HttpServlet {

    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        String preTotal = req.getParameter("preTotal");
        String orderDetailID = req.getParameter("orderDetailID");
        String orderID = req.getParameter("orderID"); 
        String productID = req.getParameter("productID");
        String quantity = req.getParameter("quantity");
        String unitPrice = req.getParameter("unitPrice").replace(",", "");
        String status = "true";
        HttpSession session = req.getSession();
        User u = (User) session.getAttribute("user");

        OrderDetail od = new OrderDetail(                
                Integer.parseInt(orderDetailID),
                Integer.parseInt(productID),
                Integer.parseInt(quantity),
                Double.parseDouble(unitPrice),
                Boolean.parseBoolean(status),               
                new Date(),
                u.getUser_id()
        );
        
//        System.out.println(preTotal);
        OrderDetailDAO.updateOrderDetail(od);
        OrderDAO.updatePlusTotalOrder(Integer.parseInt(orderID)
                , Integer.parseInt(quantity)*Double.parseDouble(unitPrice)-Double.parseDouble(preTotal));
                       
        String mess = "Product Saved";
        resp.sendRedirect("admin-edit-order?orderID="+orderID+"&mess="+mess);        
    }

    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        
    }    
}
