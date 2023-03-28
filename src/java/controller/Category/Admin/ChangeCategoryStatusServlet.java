/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package controller.Category.Admin;

/**
 *
 * @author M.S.I
 */
import dal.CategoryDAO;
import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import java.io.IOException;

/**
 *
 * @author M.S.I
 */
@WebServlet(name = "ChangeCategoryStatusServlet", value = "/admin-change-category-status")
public class ChangeCategoryStatusServlet extends HttpServlet{

    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
    }

    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        String cate_id=req.getParameter("cateID");
        String status=req.getParameter("status");
        CategoryDAO.changeCateStatus(cate_id,status);
        resp.sendRedirect("/SWP/admin-view-category");
    }
    
}
