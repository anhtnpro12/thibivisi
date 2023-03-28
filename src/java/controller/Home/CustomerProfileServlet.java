/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package controller.Home;

import dal.CategoryDAO;
import dal.CustomerDAO;
import dal.ProductDAO;
import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.servlet.http.HttpSession;
import java.io.IOException;
import java.text.DecimalFormat;
import java.util.ArrayList;
import model.Product;

/**
 *
 * @author M.S.I
 */
@WebServlet(name = "CustomerProfileServlet", urlPatterns = {"/customerprofile"})
public class CustomerProfileServlet extends HttpServlet{

    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        String customerID = req.getParameter("customerID");
        String name = req.getParameter("name");
        String phoneNumber = req.getParameter("phoneNumber");
        CustomerDAO.updateCustomer(customerID,name,phoneNumber);
        HttpSession session = req.getSession();
        req.setAttribute("error", "Change profile success!!");
        ArrayList<Product> productListTopPrice = ProductDAO.getTop8Product("price");
        ArrayList<Product> productListNew = ProductDAO.getTop8Product("created_at");
        req.setAttribute("productListTopPrice", productListTopPrice);
        req.setAttribute("productListNew", productListNew);
        req.setAttribute("listnotnull", CategoryDAO.getlistCategoryWithNotNullParentID());
        req.setAttribute("listnull", CategoryDAO.getlistCategoryWithNullParentID());
        DecimalFormat df = new DecimalFormat("#,###.##");
        df.setMaximumFractionDigits(8);
        req.setAttribute("df", df);
        session.setAttribute("customer", CustomerDAO.getCustomerbyID(Integer.parseInt(customerID)));
        req.getRequestDispatcher("FrontEnd/home.jsp").forward(req, resp);
    }

    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        req.setAttribute("listnotnull", CategoryDAO.getlistCategoryWithNotNullParentID());
        req.setAttribute("listnull", CategoryDAO.getlistCategoryWithNullParentID());
        req.getRequestDispatcher("FrontEnd/customerprofile.jsp").forward(req, resp);
    }
    
}
