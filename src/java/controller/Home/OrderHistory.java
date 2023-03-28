/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package controller.Home;

import dal.CategoryDAO;
import dal.OrderDAO;
import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.servlet.http.HttpSession;
import java.io.IOException;
import java.text.DecimalFormat;
import java.util.ArrayList;
import model.Customer;
import model.Order;

/**
 *
 * @author M.S.I
 */
@WebServlet(name = "OrderHistory", urlPatterns = "/orderhistory")
public class OrderHistory extends HttpServlet {

    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        super.doPost(req, resp); // Generated from nbfs://nbhost/SystemFileSystem/Templates/Classes/Code/OverriddenMethodBody
    }

    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        HttpSession session = req.getSession();
        if (session.getAttribute("customer") != null) {
            Customer c = (Customer) session.getAttribute("customer");
            ArrayList<Order> o = OrderDAO.getOrderListByCustomer(c);
            req.setAttribute("order", o);
            DecimalFormat df = new DecimalFormat("#,###.##");
            df.setMaximumFractionDigits(8);
            req.setAttribute("df", df);
            req.setAttribute("listnotnull", CategoryDAO.getlistCategoryWithNotNullParentID());
            req.setAttribute("listnull", CategoryDAO.getlistCategoryWithNullParentID());
            req.getRequestDispatcher("FrontEnd/orderhistory.jsp").forward(req, resp);
        }
    }

}
