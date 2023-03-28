/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package controller.Contact.Admin;

import dal.ContactDAO;
import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.text.DecimalFormat;
import java.util.ArrayList;
import model.Contact;

/**
 *
 * @author M.S.I
 */
@WebServlet(name = "ContactViewServlet", value = "/admin-view-contact")
public class ContactViewServlet extends HttpServlet{

    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        super.doPost(req, resp); // Generated from nbfs://nbhost/SystemFileSystem/Templates/Classes/Code/OverriddenMethodBody
    }

    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        ArrayList<Contact> list = ContactDAO.getContactList();
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
        req.setAttribute("ContactDAO", new ContactDAO());
        req.setAttribute("df", df);
        req.setAttribute("list", ContactDAO.getListByPage(list, begin, end));
        req.setAttribute("size", size % numPerPage == 0 ? size / numPerPage : (size / numPerPage) + 1);
        req.setAttribute("page", page);
        req.getRequestDispatcher("AdminPage/JSP/customer-contact-list.jsp").forward(req, resp);
    }
    
}
