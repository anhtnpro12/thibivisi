/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package controller.Home;

import dal.CustomerDAO;
import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.servlet.http.HttpSession;
import java.io.IOException;
import model.Customer;

/**
 *
 * @author M.S.I
 */
@WebServlet(name = "CustomerLoginServlet", urlPatterns = {"/customerlogin"})
public class CustomerLoginServlet extends HttpServlet {

    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        String email = req.getParameter("email");
        String password = req.getParameter("password");
        Customer c = CustomerDAO.getCustomer(email,password);
        HttpSession session = req.getSession();
        if(session.getAttribute("customer")!=null){
            resp.sendRedirect("home");
        }
        if(c==null){
            req.setAttribute("error", "Username or password invalid!!");
            req.getRequestDispatcher("FrontEnd/login.jsp").forward(req, resp);
        }
        else {
            session.setAttribute("customer", CustomerDAO.getCustomer(email,password));
            resp.sendRedirect("home");
        }
    }

    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        HttpSession session = req.getSession();
        if(session.getAttribute("customer")!=null){
            resp.sendRedirect("home");
        }
        req.getRequestDispatcher("FrontEnd/login.jsp").forward(req, resp);
    }

}
