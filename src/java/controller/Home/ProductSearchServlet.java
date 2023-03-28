/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package controller.Home;

import dal.BrandDAO;
import dal.CategoryDAO;
import dal.ProductDAO;
import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.ArrayList;
import model.Product;

/**
 *
 * @author M.S.I
 */
@WebServlet(name="ProductSearchServlet", urlPatterns={"/search"})
public class ProductSearchServlet extends HttpServlet{

    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        super.doPost(req, resp); // Generated from nbfs://nbhost/SystemFileSystem/Templates/Classes/Code/OverriddenMethodBody
    }

    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        String sortType = request.getParameter("sortType");
        String search = request.getParameter("search");
        if(sortType==null) sortType = "desc";
        ArrayList<Product> list = new ArrayList<>();
        if (sortType.equals("desc")) {
            list = ProductDAO.getAllProductBySearch("price",search);
        } else {
            list = ProductDAO.getAllProductAscBySearch("price",search);
        }
        int page, numPerPage = 12, size = list.size();
        String xpage = request.getParameter("page");
        if (xpage == null) {
            page = 1;
        } else {
            page = Integer.parseInt(xpage);
        }
        int begin = numPerPage * (page - 1);
        int end = Math.min(numPerPage * page, size);
        request.setAttribute("search", search);
        request.setAttribute("sortType", sortType);
        request.setAttribute("productDAO", new ProductDAO());
        request.setAttribute("categoryList", CategoryDAO.getListCategory());
        request.setAttribute("brandList", BrandDAO.getListBrand());
        request.setAttribute("numOfProduct", ProductDAO.getAllProductBySearch("price",search).size());
        request.setAttribute("listnotnull", CategoryDAO.getlistCategoryWithNotNullParentID());
        request.setAttribute("listnull", CategoryDAO.getlistCategoryWithNullParentID());
        request.setAttribute("list", ProductDAO.getListByPage(list, begin, end));
        request.setAttribute("size", size % numPerPage == 0 ? size / numPerPage : (size / numPerPage) + 1);
        request.setAttribute("page", page);
        request.getRequestDispatcher("FrontEnd/productsearch.jsp").forward(request, response);
    }
    
}
