/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package controller.Contact.Admin;

import dal.ContactDAO;
import dal.UserDAO;
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
import model.Contact;
import model.RepContact;
import model.User;

/**
 *
 * @author M.S.I
 */
@WebServlet(name = "ContactDetailServlet", value = "/admin-detail-contact")
public class ContactDetailServlet extends HttpServlet {

    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        String contactID = req.getParameter("contactID");
        String title = req.getParameter("title");
        String message = req.getParameter("message");
        HttpSession session = req.getSession();
        User user = (User) session.getAttribute("user");
        int createdBy = user.getUser_id();
        int modifiedBy = user.getUser_id();
        RepContact rp = new RepContact(0, null, Integer.parseInt(contactID), title, message, true, new Date(), createdBy, new Date(), modifiedBy);
        try {
            UserDAO.sendMail(ContactDAO.getContactByID(Integer.parseInt(contactID)).getEmail(), title, message, "trinhminh2907@gmail.com", "ovnaeftdphlggdpi");

        } catch (Exception e) {
            req.setAttribute("error", "Send message error please check your connection!!");
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
            return;
        }
        ContactDAO.SaveRepContact(rp);
        req.setAttribute("mess", "Send message success!!");
        ContactDAO.ChangeContactStatus(contactID);
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

    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        String contactID = req.getParameter("contactID");
        Contact c = ContactDAO.getContactByID(Integer.parseInt(contactID));
        ArrayList<RepContact> rc = ContactDAO.getRepContactListByContactID(Integer.parseInt(contactID));
        req.setAttribute("UserDAO", new UserDAO());
        req.setAttribute("repcontact", rc);
        req.setAttribute("contact", c);
        req.getRequestDispatcher("AdminPage/JSP/contact-detail.jsp").forward(req, resp);
    }

}
