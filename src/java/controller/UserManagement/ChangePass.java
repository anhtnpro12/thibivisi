/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/JSP_Servlet/Servlet.java to edit this template
 */
package controller.UserManagement;

import com.oracle.wls.shaded.org.apache.bcel.generic.AALOAD;
import dal.UserDAO;
import static dal.UserDAO.checkPassByID;
import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.io.PrintWriter;

/**
 *
 * @author ADMIN
 */
@WebServlet(name = "ChangePass", urlPatterns = {"/admin-change-pass"})
public class ChangePass extends HttpServlet {

    protected void processRequest(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        response.setContentType("text/html;charset=UTF-8");
        try ( PrintWriter out = response.getWriter()) {
            /* TODO output your page here. You may use following sample code. */
            out.println("<!DOCTYPE html>");
            out.println("<html>");
            out.println("<head>");
            out.println("<title>Servlet ChangePass</title>");            
            out.println("</head>");
            out.println("<body>");
            out.println("<h1>Servlet ChangePass at " + request.getContextPath() + "</h1>");
            out.println("</body>");
            out.println("</html>");
        }
    }

    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        request.getRequestDispatcher("AdminPage/JSP/user-changepass.jsp").forward(request, response);
    }

    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        String id = request.getParameter("userID");
        String oldpass = request.getParameter("cur-pass");
        String pass = request.getParameter("new-pass");
        String repass = request.getParameter("confirm-pass");
        
        
        if(UserDAO.checkPassByID(id, oldpass) && repass.equals(pass)){
            request.setAttribute("type", "success");
            request.setAttribute("message", "Success!");
            UserDAO.updateNewPass(id, repass);
            doGet(request, response);
        }else{
            request.setAttribute("type", "danger");
            String message = "";
            if(oldpass.isEmpty() || pass.isEmpty() || repass.isEmpty()){
                message = "Please enter all fields";
            }else{
                message = !UserDAO.checkPassByID(id, oldpass)?"Password is not correct!":"New Password and Confirm Password is not matching!";
            }
            request.setAttribute("message", message);
            doGet(request, response);
        }
        
    }

    @Override
    public String getServletInfo() {
        return "Short description";
    }// </editor-fold>

}
