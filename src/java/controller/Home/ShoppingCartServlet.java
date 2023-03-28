/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package controller.Home;

import dal.CategoryDAO;
import dal.ProductDAO;
import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.Cookie;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.text.DecimalFormat;
import java.util.List;
import model.Cart;
import model.Product;

/**
 *
 * @author M.S.I
 */
@WebServlet(name = "ShoppingCartServlet", urlPatterns = "/shopping-cart")
public class ShoppingCartServlet extends HttpServlet{

    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        super.doPost(req, resp); // Generated from nbfs://nbhost/SystemFileSystem/Templates/Classes/Code/OverriddenMethodBody
    }

    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        req.setAttribute("listnotnull", CategoryDAO.getlistCategoryWithNotNullParentID());
        req.setAttribute("listnull", CategoryDAO.getlistCategoryWithNullParentID());
        
        List<Product> list = ProductDAO.getAllProduct();
        Cookie[] cookieList = req.getCookies();
        String txt = "";
        if (cookieList != null) {
            for (Cookie c : cookieList) {
                if (c.getName().equals("cart")) {
                    txt += c.getValue();
                }
            }
        }

        Cart cart = new Cart(txt,list);
        
        DecimalFormat df = new DecimalFormat("#,###.##");
        df.setMaximumFractionDigits(8);
        
        req.setAttribute("cart", cart);
        req.setAttribute("df", df);
        req.getRequestDispatcher("FrontEnd/shopping-cart.jsp").forward(req, resp);
    }
    
}
