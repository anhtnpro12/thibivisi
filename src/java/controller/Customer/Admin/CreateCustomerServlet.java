/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package controller.Customer.Admin;

import dal.CustomerDAO;
import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.servlet.http.HttpSession;
import java.io.IOException;
import java.util.Date;
import model.Customer;
import model.User;

/**
 *
 * @author TNA
 */
@WebServlet(name = "CreateCustomerServlet", value = "/admin-create-customer")
public class CreateCustomerServlet extends HttpServlet {

    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        String code = "code test";
        String name = req.getParameter("name");
        String gender = req.getParameter("gender");
        String email = req.getParameter("email");
        String phoneNumber = req.getParameter("phoneNumber");
        String password = req.getParameter("password");
        String status = req.getParameter("status");
        HttpSession session = req.getSession();
        User u = (User) session.getAttribute("user");

        Customer c = new Customer(
                code,
                name,
                Boolean.parseBoolean(gender),
                email,
                phoneNumber,
                password,
                Boolean.parseBoolean(status),
                new Date(),
                u.getUser_id(),
                new Date(),
                u.getUser_id()
        );

        CustomerDAO.insertCustomer(c);

        req.setAttribute("mess", "Add customer successful!");
        req.getRequestDispatcher("AdminPage/JSP/customer-add.jsp").forward(req, resp);
    }

    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        req.getRequestDispatcher("AdminPage/JSP/customer-add.jsp").forward(req, resp);
    }

}
