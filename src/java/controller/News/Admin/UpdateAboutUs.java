/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package controller.News.Admin;

import dal.NewsDAO;
import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.text.DecimalFormat;
import java.util.ArrayList;
import model.News;

/**
 *
 * @author ADMIN
 */
@WebServlet(name = "UpdateAboutUs", value = "/admin-update-aboutus")
public class UpdateAboutUs extends HttpServlet{

    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        ArrayList<News> list = NewsDAO.getAllAboutUs();
        int page, numPerPage = 5, size = list.size();
        String xpage = req.getParameter("page");
        if (xpage == null) {
            page = 1;
        } else {
            page = Integer.parseInt(xpage);
        }
        int begin = numPerPage * (page - 1);
        int end = Math.min(numPerPage * page, size);
        String pageType = req.getParameter("page-type");
        pageType = pageType == null ? "all" : pageType;

        DecimalFormat df = new DecimalFormat("#");
        df.setMaximumFractionDigits(8);
        req.setAttribute("pageType", pageType);
        req.setAttribute("NewsDAO", new NewsDAO());
        req.setAttribute("news", list);
        req.setAttribute("list", NewsDAO.getListByPage(list, begin, end));
        req.setAttribute("size", size % numPerPage == 0 ? size / numPerPage : (size / numPerPage) + 1);
        req.setAttribute("page", page);
        req.getRequestDispatcher("AdminPage/JSP/news-companyprofile.jsp").forward(req, resp);
    }

    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        
    }

}
