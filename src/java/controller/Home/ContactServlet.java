/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package controller.Home;

import dal.CategoryDAO;
import dal.ContactDAO;
import dal.ProductDAO;
import dal.UserDAO;
import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.text.DecimalFormat;
import java.util.ArrayList;
import java.util.Date;
import model.Contact;
import model.Product;

/**
 *
 * @author M.S.I
 */
@WebServlet(name = "ContactServlet", urlPatterns = "/contact")
public class ContactServlet extends HttpServlet {

    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        String name = req.getParameter("name");
        String email = req.getParameter("email");
        String message = req.getParameter("message");
//            UserDAO.sendMail("minhtche164004@fpt.edu.vn", "Message from " + name + " with email " + email, message, "trinhminh2907@gmail.com", "ovnaeftdphlggdpi");
        Contact c = new Contact(0, null, name, email, message, false, new Date(), 1, new Date(), 1);
        ContactDAO.sendContact(c);
        UserDAO.sendMail(email, "Thanks you for your contact!", "We have received you contact message. We will response you as soon as possible!!", "trinhminh2907@gmail.com", "ovnaeftdphlggdpi");
//        req.setAttribute("error", "Send message complete. We will response you as soon as possible!!");
//        ArrayList<Product> productListTopPrice = ProductDAO.getTop8Product("price");
//        ArrayList<Product> productListNew = ProductDAO.getTop8Product("created_at");
//        req.setAttribute("productListTopPrice", productListTopPrice);
//        req.setAttribute("productListNew", productListNew);
//        req.setAttribute("listnotnull", CategoryDAO.getlistCategoryWithNotNullParentID());
//        req.setAttribute("listnull", CategoryDAO.getlistCategoryWithNullParentID());
//        DecimalFormat df = new DecimalFormat("#,###.##");
//        df.setMaximumFractionDigits(8);
//        req.setAttribute("df", df);
        resp.sendRedirect("home");
    }

    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        if (!req.getMethod().equalsIgnoreCase("GET")) {
            // Đây là một request mới, dữ liệu trong HttpServletRequest sẽ không còn tồn tại
            req.getRequestDispatcher("FrontEnd/contact.jsp").forward(req, resp);
        } else {
            req.setAttribute("listnotnull", CategoryDAO.getlistCategoryWithNotNullParentID());
            req.setAttribute("listnull", CategoryDAO.getlistCategoryWithNullParentID());
            req.getRequestDispatcher("FrontEnd/contact.jsp").include(req, resp);
        }

    }

}
