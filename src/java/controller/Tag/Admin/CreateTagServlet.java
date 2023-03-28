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
import java.util.Date;
import model.Tag;
import model.User;

/**
 *
 * @author M.S.I
 */
@WebServlet(name = "CreateTagServlet", value = "/admin-create-tag")
public class CreateTagServlet extends HttpServlet {

    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        String tag = req.getParameter("tag");
        HttpSession session = req.getSession();
        User user = (User) session.getAttribute("user");
        int createdBy = user.getUser_id();
        int modifiedBy = user.getUser_id();
        Tag t = new Tag(0, null, tag, true, new Date(),
                createdBy, new Date(), modifiedBy);
        TagDAO.createTag(t);
        resp.sendRedirect("admin-view-tag");
    }

    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        req.getRequestDispatcher("AdminPage/JSP/tag-add.jsp").forward(req, resp);
    }

}
