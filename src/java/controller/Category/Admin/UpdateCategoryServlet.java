/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package controller.Category.Admin;

import dal.CategoryDAO;
import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.servlet.http.HttpSession;
import java.io.IOException;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.logging.Level;
import java.util.logging.Logger;
import model.Category;
import model.User;

/**
 *
 * @author M.S.I
 */
@WebServlet(name = "UpdateCategoryServlet", value = "/admin-update-category")
public class UpdateCategoryServlet extends HttpServlet {

    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        try {
            String cate_id = req.getParameter("cateID");
            String parent_id = req.getParameter("parentID");
            String code = req.getParameter("code");
            String cate = req.getParameter("cate");
            String status = "true";
            String createdAt = req.getParameter("createdAt");
            String createdBy = req.getParameter("createdBy");
            HttpSession session = req.getSession();
            User u = (User)session.getAttribute("user"); 
            int modifiedBy = 1;

            SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");

            Category c = new Category(Integer.parseInt(cate_id), Integer.parseInt(parent_id), code, cate, Boolean.parseBoolean(status), sdf.parse(createdAt),
                    Integer.parseInt(createdBy), new Date(), modifiedBy);
            CategoryDAO.updateCategory(c);
//            resp.getWriter().println("Update category success");
            resp.sendRedirect("admin-view-category");
        } catch (ParseException ex) {
            Logger.getLogger(UpdateCategoryServlet.class.getName()).log(Level.SEVERE, null, ex);
        }
    }

    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        String cateID = req.getParameter("cateID");
        Category c = CategoryDAO.getCategorybyID(Integer.parseInt(cateID));
        req.setAttribute("category", c);
        req.setAttribute("listnull", CategoryDAO.getlistCategoryWithNullParentID());
        req.getRequestDispatcher("AdminPage/JSP/category-update.jsp").forward(req, resp);
    }

}
