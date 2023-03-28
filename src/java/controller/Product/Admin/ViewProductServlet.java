package controller.Product.Admin;

import dal.ProductDAO;
import jakarta.servlet.*;
import jakarta.servlet.http.*;
import jakarta.servlet.annotation.*;
import java.io.File;

import java.io.IOException;
import java.text.DecimalFormat;
import java.util.ArrayList;
import model.Product;

@WebServlet(name = "ViewProductServlet", value = "/admin-view-product")
public class ViewProductServlet extends HttpServlet {
    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {                                
        
        String pageType = request.getParameter("page-type");
        pageType = pageType == null?"all":pageType;
        String name = request.getParameter("name");
        if (name == null) {
            name = "";
        }
        // Pagination
        ArrayList<Product> list = ProductDAO.getSearchProduct(name);
        if (!pageType.equals("all")) {
            list = ProductDAO.getListProductByStatus(Boolean.parseBoolean(pageType), name);
        } 
        int page, numPerPage = 5, size = list.size();
        String xpage = request.getParameter("page");
        if (xpage == null) {
            page = 1;
        } else {
            page = Integer.parseInt(xpage);
        }
        int begin = numPerPage * (page - 1);
        int end = Math.min(numPerPage * page, size);
             
        
        DecimalFormat df = new DecimalFormat("#,###.##");
        df.setMaximumFractionDigits(8);
        request.setAttribute("df", df);
        request.setAttribute("productDao", new ProductDAO());
        request.setAttribute("pageType", pageType);        
        request.setAttribute("list", ProductDAO.getListByPage(list, begin, end));                                    
        request.setAttribute("size", size%numPerPage==0?size/numPerPage:(size/numPerPage)+1);
        request.setAttribute("page", page);
        request.setAttribute("name", name);


        request.getRequestDispatcher("AdminPage/JSP/product.jsp").forward(request, response);
    }       

    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {

    }
}
