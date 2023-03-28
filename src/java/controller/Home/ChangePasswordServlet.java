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
@WebServlet(name = "ChangePasswordServlet", urlPatterns = "/changepassword")
public class ChangePasswordServlet extends HttpServlet {

    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        String oldpass = req.getParameter("oldpass");
        String newpass = req.getParameter("newpass");
        String renewpass = req.getParameter("renewpass");
        String customerID = req.getParameter("customerID");
        if (!CustomerDAO.getCustomerbyIdandPass(Integer.parseInt(customerID), oldpass)) {
            req.setAttribute("error", "Old password is invalid!!");
            req.getRequestDispatcher("FrontEnd/changepassword.jsp").forward(req, resp);
        } else {
            if (!newpass.equals(renewpass)) {
                req.setAttribute("error", "Confirm password and new password must be the same !!");
                req.getRequestDispatcher("FrontEnd/changepassword.jsp").forward(req, resp);
            }
            else{
                CustomerDAO.updatePassword(Integer.parseInt(customerID), newpass);
                req.setAttribute("error", "Change password success!!");
                req.getRequestDispatcher("FrontEnd/home.jsp").forward(req, resp);
            }
        }
    }

    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        HttpSession session = req.getSession();
        Customer c = (Customer) session.getAttribute("customer");
        req.setAttribute("customerID", c.getCustomerID());
        req.getRequestDispatcher("FrontEnd/changepassword.jsp").forward(req, resp);
    }

}
