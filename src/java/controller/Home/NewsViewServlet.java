/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package controller.Home;

import dal.CategoryDAO;
import dal.NewsDAO;
import dal.UserDAO;
import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import java.io.IOException;
import model.News;

/**
 *
 * @author M.S.I
 */
@WebServlet(name = "NewsViewServlet", urlPatterns = {"/news"})
public class NewsViewServlet extends HttpServlet {

    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        super.doPost(req, resp); // Generated from nbfs://nbhost/SystemFileSystem/Templates/Classes/Code/OverriddenMethodBody
    }

    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        String code = req.getParameter("code");

        req.setAttribute("listnotnull", CategoryDAO.getlistCategoryWithNotNullParentID());
        req.setAttribute("listnull", CategoryDAO.getlistCategoryWithNullParentID());
        req.setAttribute("UserDAO", new UserDAO());
        News n = new News();
        n = NewsDAO.getNewsByCode(code);
        req.setAttribute("newss", n);
        System.out.println(NewsDAO.getNewsByCode(code));
        NewsDAO.onemoreview(NewsDAO.getNewsByCode(code));
        req.getRequestDispatcher("FrontEnd/blog-details.jsp").forward(req, resp);
    }

}
