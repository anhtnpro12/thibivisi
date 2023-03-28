/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package controller.Home;

import dal.CategoryDAO;
import dal.NewsDAO;
import dal.NewsGroupDAO;
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
@WebServlet(name = "BlogServlet", urlPatterns = "/blog")
public class BlogServlet extends HttpServlet {

    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        super.doPost(req, resp); // Generated from nbfs://nbhost/SystemFileSystem/Templates/Classes/Code/OverriddenMethodBody
    }

    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        String code = req.getParameter("code");
        if (code != null) {
            req.setAttribute("news", NewsDAO.getAllNewsbyID(NewsGroupDAO.getIDbyCode(code)));
        } else {
            req.setAttribute("news", NewsDAO.getAllNotAboutUs());
        }
        req.setAttribute("code", code);
        req.setAttribute("listnewgroupnotnull", NewsGroupDAO.getlistNotNullNewsGroup());
        req.setAttribute("listnotnull", CategoryDAO.getlistCategoryWithNotNullParentID());
        req.setAttribute("listnull", CategoryDAO.getlistCategoryWithNullParentID());
        req.getRequestDispatcher("FrontEnd/blog.jsp").forward(req, resp);
    }

}
