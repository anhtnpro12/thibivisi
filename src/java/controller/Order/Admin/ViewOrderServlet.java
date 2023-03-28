/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package controller.Order.Admin;

import dal.OrderDAO;
import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.text.DecimalFormat;
import java.util.ArrayList;
import model.Order;

/**
 *
 * @author TNA
 */
@WebServlet(name = "ViewOrderServlet", value = "/admin-view-order")
public class ViewOrderServlet extends HttpServlet {

    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
    }

    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {

        String pageType = req.getParameter("page-type");
        pageType = pageType == null ? "all" : pageType;
        // Pagination        
        ArrayList<Order> list = OrderDAO.getAllOrder();
        if (pageType.equals("active")) {
            list = OrderDAO.getListOrderByStatus(true);
        } else if (pageType.equals("deactivate")) {
            list = OrderDAO.getListOrderByStatus(false);
        }
        int page, numPerPage = 10, size = list.size();
        String xpage = req.getParameter("page");
        if (xpage == null) {
            page = 1;
        } else {
            page = Integer.parseInt(xpage);
        }
        int begin = numPerPage * (page - 1);
        int end = Math.min(numPerPage * page, size);

//        for (Product product : list) {
//            System.out.println(product.getCode());
//        }        
        DecimalFormat df = new DecimalFormat("#,###.##");
        df.setMaximumFractionDigits(8);
        req.setAttribute("df", df);
        req.setAttribute("pageType", pageType);
        req.setAttribute("quantityOrder", OrderDAO.getQuantityOrder());
        req.setAttribute("list", OrderDAO.getListByPage(list, begin, end));
        req.setAttribute("size", size % numPerPage == 0 ? size / numPerPage : (size / numPerPage) + 1);
        req.setAttribute("page", page);
        req.getRequestDispatcher("AdminPage/JSP/order.jsp").forward(req, resp);
    }

}
