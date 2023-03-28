/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package controller.Tag.Admin;

import dal.TagDAO;
import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.servlet.http.HttpSession;
import java.io.IOException;
import java.text.DecimalFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.logging.Level;
import java.util.logging.Logger;
import model.Tag;
import model.User;

/**
 *
 * @author M.S.I
 */
@WebServlet(name = "UpdateTagServlet", value = "/admin-update-tag")
public class UpdateTagServlet extends HttpServlet {

    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        String tag = req.getParameter("tag");
        String tagID = req.getParameter("tagID");
        String code = req.getParameter("code");
        String status = "true";
        String createdAt = req.getParameter("createdAt");
        String createdBy = req.getParameter("createdBy");
        HttpSession session = req.getSession();
        User u = (User) session.getAttribute("user");
        int modifiedBy = 1;
        SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
        Tag t = null;

        try {
            t = new Tag(Integer.parseInt(tagID), code, tag, Boolean.parseBoolean(status), sdf.parse(createdAt),
                    Integer.parseInt(createdBy), new Date(), modifiedBy);
        } catch (ParseException ex) {
            Logger.getLogger(UpdateTagServlet.class.getName()).log(Level.SEVERE, null, ex);
        }
        TagDAO.updateTag(t);
        req.setAttribute("mess", "Update success!");
        ArrayList<Tag> list = TagDAO.getListTag();
        int page, numPerPage = 5, size = list.size();
        String xpage = req.getParameter("page");
        if (xpage == null) {
            page = 1;
        } else {
            page = Integer.parseInt(xpage);
        }
        int begin = numPerPage * (page - 1);
        int end = Math.min(numPerPage * page, size);
//        ArrayList<Tag> list1 = TagDAO.getListByPage(list, begin, end);
//        for (Category category : list1) {
//            System.out.println(category);
//        }
        String pageType = req.getParameter("page-type");
        pageType = pageType == null ? "all" : pageType;

        DecimalFormat df = new DecimalFormat("#");
        df.setMaximumFractionDigits(8);
        req.setAttribute("pageType", pageType);
        req.setAttribute("TagDAO", new TagDAO());
        req.setAttribute("df", df);
        req.setAttribute("list", TagDAO.getListByPage(list, begin, end));
        req.setAttribute("size", size % numPerPage == 0 ? size / numPerPage : (size / numPerPage) + 1);
        req.setAttribute("page", page);

        req.getRequestDispatcher("AdminPage/JSP/tag.jsp").forward(req, resp);
    }

    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        String tagID = req.getParameter("tagID");
        Tag t = TagDAO.getTagbyID(Integer.parseInt(tagID));
        req.setAttribute("tag", t);
        req.getRequestDispatcher("AdminPage/JSP/tag-update.jsp").forward(req, resp);
    }

}
