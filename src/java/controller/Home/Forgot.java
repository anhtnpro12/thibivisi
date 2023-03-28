/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package controller.Home;

import dal.CustomerDAO;
import dal.UserDAO;
import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.Random;

/**
 *
 * @author M.S.I
 */
@WebServlet(name = "Forgot", urlPatterns = "/forgot")
public class Forgot extends HttpServlet{

    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        String email = req.getParameter("email");
        if (!CustomerDAO.IfEmailExist(email)) {
            req.setAttribute("error", "Email not exist. Please try again!");
            req.getRequestDispatcher("FrontEnd/forgot.jsp").forward(req, resp);
        } else {
            Random r = new Random();
            String code = String.format("%08d", r.nextInt(99999999));
            UserDAO.sendMail(email, "Your code confirm to reset password", "Your code is : " + code + ". Please do not share this to anyone!!", "trinhminh2907@gmail.com", "ovnaeftdphlggdpi");
            req.setAttribute("email", email);
            req.setAttribute("code", code);
            req.getRequestDispatcher("FrontEnd/code-confirm.jsp").forward(req, resp);
        }
    }

    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        req.getRequestDispatcher("FrontEnd/forgot.jsp").forward(req, resp);
    }
    
}
