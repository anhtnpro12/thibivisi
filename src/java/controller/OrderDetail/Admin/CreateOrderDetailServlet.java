/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package controller.OrderDetail.Admin;

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
import java.util.ArrayList;
import java.util.Date;
import model.OrderDetail;
import model.Product;
import model.User;

/**
 *
 * @author TNA
 */
@WebServlet(name = "CreateOrderDetailServlet", value = "/admin-create-order-detail")
public class CreateOrderDetailServlet extends HttpServlet {

    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
                
        SimpleDateFormat sdf = new SimpleDateFormat("ddMMyy");
        SimpleDateFormat sdf2 = new SimpleDateFormat("yyyy-MM-dd");
        
        String orderID = req.getParameter("orderID");
        String productID = req.getParameter("productID");
        String code = sdf.format(new Date()) + "ODT" + OrderDetailDAO.getQuantityOrderFromDate(sdf2.format(new Date()));
        String quantity = req.getParameter("quantity");
        String unitPrice = req.getParameter("unitPrice").replace(",", "");
        String status = "true";
        HttpSession session = req.getSession();
        User u = (User) session.getAttribute("user");

        OrderDetail od = new OrderDetail(
                Integer.parseInt(orderID),
                Integer.parseInt(productID),
                code,
                Integer.parseInt(quantity),
                Double.parseDouble(unitPrice),
                Boolean.parseBoolean(status),
                new Date(),
                u.getUser_id(),
                new Date(),
                u.getUser_id()
        );

        OrderDetailDAO.insertOrderDetail(od);
        OrderDAO.updatePlusTotalOrder(Integer.parseInt(orderID), Integer.parseInt(quantity)*Double.parseDouble(unitPrice));

        resp.sendRedirect("admin-edit-order?orderID="+orderID);
    }

    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        String preSite = req.getParameter("preSite");
        String orderID = req.getParameter("orderID");
        
        ArrayList<Product> plist = ProductDAO.getAllProduct();
        int index = 0;
        while (index < plist.size() && !plist.get(index).isStatus()) {
            index++;
        }
        DecimalFormat df = new DecimalFormat("#,###.##");
        df.setMaximumFractionDigits(8);
        req.setAttribute("df", df);
        req.setAttribute("productList", plist);
        req.setAttribute("orderList", OrderDAO.getAllOrder());
        req.setAttribute("fprice", plist.get(index).getPrice());
        req.setAttribute("preSite", Boolean.parseBoolean(preSite));
        req.setAttribute("orderID", orderID);
        req.getRequestDispatcher("AdminPage/JSP/order-detail-add.jsp").forward(req, resp);
    }

}
