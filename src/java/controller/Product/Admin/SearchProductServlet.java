/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package controller.Product.Admin;

import dal.ProductDAO;
import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.text.DecimalFormat;
import java.util.ArrayList;
import model.Product;

/**
 *
 * @author ADMIN
 */
@WebServlet(name = "SearchProductServlet", value = "/admin-search-product")
public class SearchProductServlet extends HttpServlet{

    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        // Pagination
        String name = req.getParameter("name")==null?"":req.getParameter("name");
        String pageType = req.getParameter("page-type");
        pageType = pageType == null?"all":pageType;
        String status = pageType.equals("all")?"":(pageType.equals("deactive")?"0":"1");
        ArrayList<Product> list = ProductDAO.getListProductContains("name", name, "status", status, "modified_at");
        int page, numPerPage = 5, size = list.size();
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
        
        DecimalFormat df = new DecimalFormat("#");
        df.setMaximumFractionDigits(8);
        req.setAttribute("pageType", pageType);
        req.setAttribute("productDao", new ProductDAO());
        req.setAttribute("df", df);
        req.setAttribute("list", ProductDAO.getListByPage(list, begin, end));                
        req.setAttribute("size", size%numPerPage==0?size/numPerPage:(size/numPerPage)+1);
        req.setAttribute("page", page);

        req.setAttribute("name", name);
        req.setAttribute("status", status);

        req.getRequestDispatcher("AdminPage/JSP/product-search.jsp").forward(req, resp);
    }

    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        
    }
    
    
}
