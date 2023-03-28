/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/JSP_Servlet/Servlet.java to edit this template
 */
package controller.Home;

import dal.BrandDAO;
import dal.CategoryDAO;
import dal.ProductDAO;
import java.io.IOException;
import java.io.PrintWriter;
import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import java.text.DecimalFormat;
import java.util.ArrayList;
import model.Category;
import model.Product;

/**
 *
 * @author ASUS
 */
@WebServlet(name = "ShopByCateIDServlet", urlPatterns = {"/shopbycateID"})
public class ShopByCateIDServlet extends HttpServlet {

    /**
     * Processes requests for both HTTP <code>GET</code> and <code>POST</code>
     * methods.
     *
     * @param request servlet request
     * @param response servlet response
     * @throws ServletException if a servlet-specific error occurs
     * @throws IOException if an I/O error occurs
     */
    protected void processRequest(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        response.setContentType("text/html;charset=UTF-8");
        try ( PrintWriter out = response.getWriter()) {
            /* TODO output your page here. You may use following sample code. */
            out.println("<!DOCTYPE html>");
            out.println("<html>");
            out.println("<head>");
            out.println("<title>Servlet ShopServlet</title>");
            out.println("</head>");
            out.println("<body>");
            out.println("<h1>Servlet ShopServlet at " + request.getContextPath() + "</h1>");
            out.println("</body>");
            out.println("</html>");
        }
    }

    // <editor-fold defaultstate="collapsed" desc="HttpServlet methods. Click on the + sign on the left to edit the code.">
    /**
     * Handles the HTTP <code>GET</code> method.
     *
     * @param request servlet request
     * @param response servlet response
     * @throws ServletException if a servlet-specific error occurs
     * @throws IOException if an I/O error occurs
     */
    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        String sortType = request.getParameter("sortType");
        String cateID = request.getParameter("cateID");
        if (sortType == null) {
            sortType = "desc";
        }
        ArrayList<Product> list = new ArrayList<>();
        if (CategoryDAO.getParentIDByCateID(Integer.parseInt(cateID)) != 0) {
            if (sortType.equals("desc")) {
                list = ProductDAO.getAllProductByCateID("price", Integer.parseInt(cateID));
            } else {
                list = ProductDAO.getAllProductAscByCateID("price", Integer.parseInt(cateID));
            }
        } else {
            if (sortType.equals("desc")) {
                list = ProductDAO.getAllProductByParentID("price", Integer.parseInt(cateID));
            } else {
                list = ProductDAO.getAllProductAscByParentID("price", Integer.parseInt(cateID));
            }
        }

        int page, numPerPage = 12, size = list.size();
        String xpage = request.getParameter("page");
        if (xpage == null) {
            page = 1;
        } else {
            page = Integer.parseInt(xpage);
        }
        ArrayList<Category> c = new ArrayList<>();
        if (CategoryDAO.getParentIDByCateID(Integer.parseInt(cateID)) != 0) {
            c.add(CategoryDAO.getCategorybyID(CategoryDAO.getParentIDByCateID(Integer.parseInt(cateID))));
        }
        c.add(CategoryDAO.getCategorybyID(Integer.parseInt(cateID)));
        String url = CategoryDAO.getCategorybyID(Integer.parseInt(cateID)).getUrl();
        System.out.println(url);
        request.setAttribute("c", c);
        int begin = numPerPage * (page - 1);
        int end = Math.min(numPerPage * page, size);

        request.setAttribute("cateID", cateID);
        request.setAttribute("sortType", sortType);
        request.setAttribute("productList", (sortType.equals("desc")) ? (ProductDAO.getAllProductByCateID("price", Integer.parseInt(cateID))) : (ProductDAO.getAllProductAscByCateID("price", Integer.parseInt(cateID))));
        request.setAttribute("productDAO", new ProductDAO());
        request.setAttribute("categoryList", CategoryDAO.getListCategory());
        request.setAttribute("brandList", BrandDAO.getListBrand());
        if (CategoryDAO.getParentIDByCateID(Integer.parseInt(cateID)) != 0) {
            request.setAttribute("numOfProduct", ProductDAO.getAllProductByCateID(Integer.parseInt(cateID)).size());
        } else {
            request.setAttribute("numOfProduct", ProductDAO.getAllProductByParentID("price", Integer.parseInt(cateID)).size());
        }
        request.setAttribute("listnotnull", CategoryDAO.getlistCategoryWithNotNullParentID());
        request.setAttribute("listnull", CategoryDAO.getlistCategoryWithNullParentID());
        request.setAttribute("list", ProductDAO.getListByPage(list, begin, end));
        request.setAttribute("size", size % numPerPage == 0 ? size / numPerPage : (size / numPerPage) + 1);
        request.setAttribute("page", page);
        DecimalFormat df = new DecimalFormat("#,###.##");
        df.setMaximumFractionDigits(8);
        request.setAttribute("df", df);
        request.setAttribute("url", url);
        request.getRequestDispatcher("FrontEnd/shopbycateID.jsp").forward(request, response);
    }

    /**
     * Handles the HTTP <code>POST</code> method.
     *
     * @param request servlet request
     * @param response servlet response
     * @throws ServletException if a servlet-specific error occurs
     * @throws IOException if an I/O error occurs
     */
    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        String sortType = request.getParameter("sortType");
        String search = request.getParameter("search");
        if (sortType == null) {
            sortType = "desc";
        }
        ArrayList<Product> list = new ArrayList<>();
        if (sortType.equals("desc")) {
            list = ProductDAO.getAllProductBySearch("price", search);
        } else {
            list = ProductDAO.getAllProductAscBySearch("price", search);
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
        request.setAttribute("sortType", sortType);
        request.setAttribute("productDAO", new ProductDAO());
        request.setAttribute("categoryList", CategoryDAO.getListCategory());
        request.setAttribute("brandList", BrandDAO.getListBrand());
        request.setAttribute("numOfProduct", ProductDAO.getAllProductBySearch("price", search));
        request.setAttribute("listnotnull", CategoryDAO.getlistCategoryWithNotNullParentID());
        request.setAttribute("listnull", CategoryDAO.getlistCategoryWithNullParentID());
        request.setAttribute("list", ProductDAO.getListByPage(list, begin, end));
        request.setAttribute("size", size % numPerPage == 0 ? size / numPerPage : (size / numPerPage) + 1);
        request.setAttribute("page", page);
        request.getRequestDispatcher("FrontEnd/shopbycateID.jsp").forward(request, response);
    }

    /**
     * Returns a short description of the servlet.
     *
     * @return a String containing servlet description
     */
    @Override
    public String getServletInfo() {
        return "Short description";
    }// </editor-fold>

}
