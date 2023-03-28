/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package controller.Brand.Admin;

import dal.BrandDAO;
import dal.ProductDAO;
import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.text.DecimalFormat;
import java.util.ArrayList;
import model.Brand;
import model.Product;

/**
 *
 * @author M.S.I
 */
@WebServlet(name = "ViewBrandServlet", value = "/admin-view-brand")
public class ViewBrandServlet extends HttpServlet {

    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
    }

    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        // Pagination
        ArrayList<Brand> list = BrandDAO.getListBrand();
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
        req.setAttribute("brandDao", new BrandDAO());
        req.setAttribute("df", df);
        req.setAttribute("list", BrandDAO.getListByPage(list, begin, end));                
        req.setAttribute("size", size%numPerPage==0?size/numPerPage:(size/numPerPage)+1);
        req.setAttribute("page", page);


        req.getRequestDispatcher("AdminPage/JSP/brand.jsp").forward(req, resp);
//        response.sendRedirect("./AdminPage/JSP/product.jsp");
    }

}
