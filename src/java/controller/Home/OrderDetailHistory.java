/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package controller.Home;

import dal.CategoryDAO;
import dal.OrderDAO;
import dal.ProductDAO;
import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.text.DecimalFormat;

/**
 *
 * @author M.S.I
 */
@WebServlet(name = "OrderDetailHistory", urlPatterns = {"/orderdetailhistory"})
public class OrderDetailHistory extends HttpServlet {

    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        super.doPost(req, resp); // Generated from nbfs://nbhost/SystemFileSystem/Templates/Classes/Code/OverriddenMethodBody
    }

    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        String orderID = req.getParameter("orderID");
        DecimalFormat df = new DecimalFormat("#,###.##");
        df.setMaximumFractionDigits(8);
        req.setAttribute("df", df);
        req.setAttribute("listnotnull", CategoryDAO.getlistCategoryWithNotNullParentID());
        req.setAttribute("listnull", CategoryDAO.getlistCategoryWithNullParentID());
        req.setAttribute("ProductDAO", new ProductDAO());
        req.setAttribute("orderdetail", OrderDAO.getOrderByID(Integer.parseInt(orderID)));
        req.getRequestDispatcher("FrontEnd/orderdetail.jsp").forward(req, resp);
    }

}
