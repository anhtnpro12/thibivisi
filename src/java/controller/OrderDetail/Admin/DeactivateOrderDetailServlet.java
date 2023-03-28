/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package controller.OrderDetail.Admin;

import dal.OrderDAO;
import dal.OrderDetailDAO;
import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import java.io.IOException;

/**
 *
 * @author TNA
 */
@WebServlet(name = "DeactivateOrderDetailServlet", value = "/admin-deactivate-order-detail")
public class DeactivateOrderDetailServlet extends HttpServlet {

    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
    }

    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        String orderDetailID = req.getParameter("orderDetailID");
        String orderID = req.getParameter("orderID");
        String price = req.getParameter("price");
        
        OrderDetailDAO.deactivateByID(Integer.parseInt(orderDetailID));
        OrderDAO.updatePlusTotalOrder(Integer.parseInt(orderID)
                , -1*Double.parseDouble(price));
        
        resp.sendRedirect("admin-edit-order?orderID="+orderID);
    }
    
}
