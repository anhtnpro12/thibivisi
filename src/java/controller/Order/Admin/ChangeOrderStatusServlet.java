/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package controller.Order.Admin;

import dal.OrderDAO;
import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.servlet.http.HttpSession;
import java.io.IOException;
import java.io.PrintWriter;
import java.util.Date;
import model.User;

/**
 *
 * @author TNA
 */
@WebServlet(name = "ChangeOrderStatusServlet", value = "/admin-change-order-status")
public class ChangeOrderStatusServlet extends HttpServlet {

    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
    }

    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        String orderID = req.getParameter("orderID");
        String status = req.getParameter("status");
        String page = req.getParameter("page");       
        
        HttpSession session = req.getSession();
        User u = (User)session.getAttribute("user");  
        
        OrderDAO.changeOrderStatus(
            Integer.parseInt(orderID), 
            Boolean.parseBoolean(status),
            u.getUser_id(),
            new Date()
        );
        
        resp.sendRedirect("admin-view-order?page="+page);
    }
    
}
