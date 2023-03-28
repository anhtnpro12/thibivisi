/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package controller.News.Admin;

import dal.NewsDAO;
import dal.NewsGroupDAO;
import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.servlet.http.HttpSession;
import java.io.IOException;
import java.text.DecimalFormat;
import java.util.ArrayList;
import java.util.Date;
import model.News;
import model.NewsGroup;
import model.User;

/**
 *
 * @author M.S.I
 */
@WebServlet(name = "NewsGroupManagement", value = "/admin-news-group-management")
public class NewsGroupManagement extends HttpServlet {

    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        String name = req.getParameter("name");
        String newsgroupID = req.getParameter("newsgroupID");
        if (!newsgroupID.equals("")) {
            NewsGroupDAO.updateNewsGroup(name, Integer.parseInt(newsgroupID));
            req.setAttribute("mess", "News Saved");
        } else {
            HttpSession session = req.getSession();
            User u = (User) session.getAttribute("user");
            int createdBy = u.getUser_id();
            int modifiedBy = u.getUser_id();
            NewsGroup ng = new NewsGroup(0, 2, null, name, null, true, new Date(), createdBy, new Date(), modifiedBy);
            NewsGroupDAO.inserNewsGroup(ng);
            req.setAttribute("mess", "News Added");
        }
        ArrayList<NewsGroup> list = NewsGroupDAO.getlistNotNullNewsGroup();
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
        req.setAttribute("NewsGroupDAO", new NewsGroupDAO());
        req.setAttribute("newsgroup", list);
        req.setAttribute("list", NewsGroupDAO.getListByPage(list, begin, end));
        req.setAttribute("size", size % numPerPage == 0 ? size / numPerPage : (size / numPerPage) + 1);
        req.setAttribute("page", page);
        req.getRequestDispatcher("AdminPage/JSP/news-group-management.jsp").forward(req, resp);
    }

    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        ArrayList<NewsGroup> list = NewsGroupDAO.getlistNotNullNewsGroup();
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
        req.setAttribute("NewsGroupDAO", new NewsGroupDAO());
        req.setAttribute("newsgroup", list);
        req.setAttribute("list", NewsGroupDAO.getListByPage(list, begin, end));
        req.setAttribute("size", size % numPerPage == 0 ? size / numPerPage : (size / numPerPage) + 1);
        req.setAttribute("page", page);
        req.getRequestDispatcher("AdminPage/JSP/news-group-management.jsp").forward(req, resp);
    }

}
