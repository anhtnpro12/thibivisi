/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package controller.UserManagement;

import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.Random;
import dal.UserDAO;

/**
 *
 * @author M.S.I
 */
@WebServlet(name = "CodeConfirm", urlPatterns = "/code-confirm")
public class CodeConfirm extends HttpServlet {

    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        String code = req.getParameter("code");
        String email = req.getParameter("email");
        String codeenter = req.getParameter("codeenter");
        if (!codeenter.equals(code)) {
            req.setAttribute("error", "The code you entered is incorrect");
            req.setAttribute("email", email);
            req.setAttribute("code", code);
            req.getRequestDispatcher("AdminPage/JSP/code-confirm.jsp").forward(req, resp);
        } else {
            req.setAttribute("error", "Reset password success! Please check your email to get new password and remember to change your password if login successful");
            Random r = new Random();
            String newpass = String.format("%08d", r.nextInt(99999999));
            UserDAO.sendMail(email, "Your new password", "Your new password is: " + newpass + ". Please do not share this to anyone!", "trinhminh2907@gmail.com", "ovnaeftdphlggdpi");
            UserDAO.updatePassbyEmail(email, newpass);
            req.getRequestDispatcher("AdminPage/JSP/login.jsp").forward(req, resp);
        }

    }

    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        super.doGet(req, resp); // Generated from nbfs://nbhost/SystemFileSystem/Templates/Classes/Code/OverriddenMethodBody
    }

}
