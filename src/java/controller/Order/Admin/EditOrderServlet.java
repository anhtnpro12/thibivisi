/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package controller.Order.Admin;

import dal.CustomerDAO;
import dal.OrderDAO;
import dal.OrderDetailDAO;
import dal.ProductDAO;
import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.servlet.http.HttpSession;
import java.io.IOException;
import java.text.DecimalFormat;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.logging.Level;
import java.util.logging.Logger;
import model.Order;
import model.User;

/**
 *
 * @author TNA
 */
@WebServlet(name = "EditOrderServlet", value = "/admin-edit-order")
public class EditOrderServlet extends HttpServlet {

    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        try {
            String orderID = req.getParameter("orderID");

            String customerID = req.getParameter("customerID");
            String code = req.getParameter("code");
            String address = req.getParameter("address");
            String total = req.getParameter("total").replace(",", "");
            String state = req.getParameter("state");
            String status = req.getParameter("status");
            String createdAt = req.getParameter("createdAt");
            String createdBy = req.getParameter("createdBy");
            HttpSession session = req.getSession();
            User u = (User) session.getAttribute("user");
            String payment = req.getParameter("payment");

            SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");            

            Order oder = new Order(Integer.parseInt(orderID), Integer.parseInt(customerID), u.getUser_id(), code, address,
                    Double.parseDouble(total), Integer.parseInt(state), Boolean.parseBoolean(status),
                    sdf.parse(createdAt), Integer.parseInt(createdBy),
                    new Date(), u.getUser_id(),
                    Boolean.parseBoolean(payment));

            OrderDAO.updateOrder(oder);

            DecimalFormat df = new DecimalFormat("#,###.##");
            df.setMaximumFractionDigits(8);
            req.setAttribute("df", df);
            req.setAttribute("order", OrderDAO.getOrderByID(Integer.parseInt(orderID)));
            req.setAttribute("cusList", CustomerDAO.getListCustomerByStatus(true));
            req.setAttribute("odList", OrderDetailDAO.getListActiveByOrderID(Integer.parseInt(orderID)));
            req.setAttribute("orderList", OrderDAO.getAllOrder());
            req.setAttribute("productList", ProductDAO.getAllProduct());
            req.setAttribute("mess", "Order Saved");
            req.getRequestDispatcher("AdminPage/JSP/order-edit.jsp").forward(req, resp);

        } catch (Exception ex) {
            Logger.getLogger(CreateOrderServlet.class.getName()).log(Level.SEVERE, null, ex);
        }
    }

    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        String orderID = req.getParameter("orderID");
        String mess = req.getParameter("mess");

        DecimalFormat df = new DecimalFormat("#,###.##");
        df.setMaximumFractionDigits(8);
        req.setAttribute("df", df);
        req.setAttribute("order", OrderDAO.getOrderByID(Integer.parseInt(orderID)));
        req.setAttribute("cusList", CustomerDAO.getListCustomer());
        req.setAttribute("odList", OrderDetailDAO.getListActiveByOrderID(Integer.parseInt(orderID)));
        req.setAttribute("orderList", OrderDAO.getAllOrder());
        req.setAttribute("productList", ProductDAO.getAllProduct());
        req.setAttribute("mess", mess);
        req.getRequestDispatcher("AdminPage/JSP/order-edit.jsp").forward(req, resp);
    }

}