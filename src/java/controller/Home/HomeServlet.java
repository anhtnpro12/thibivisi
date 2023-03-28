///*
// * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
// * Click nbfs://nbhost/SystemFileSystem/Templates/JSP_Servlet/Servlet.java to edit this template
// */
//
package controller.Home;

import dal.CategoryDAO;
import dal.NewsDAO;
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
import model.News;
import model.Product;

/**
 *
 * @author ASUS
 */
@WebServlet(name = "HomeServlet", urlPatterns = {"/home"})
public class HomeServlet extends HttpServlet {

    protected void processRequest(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        response.setContentType("text/html;charset=UTF-8");
        try ( PrintWriter out = response.getWriter()) {
            /* TODO output your page here. You may use following sample code. */
            out.println("<!DOCTYPE html>");
            out.println("<html>");
            out.println("<head>");
            out.println("<title>Servlet HomeServlet</title>");
            out.println("</head>");
            out.println("<body>");
            out.println("<h1>Servlet HomeServlet at " + request.getContextPath() + "</h1>");
            out.println("</body>");
            out.println("</html>");
        }
    }

    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {

        response.setContentType("text/html;charset=UTF-8");
        request.setCharacterEncoding("utf-8");
        ArrayList<News> news1 = NewsDAO.getTop2News();
        request.setAttribute("news1", news1.get(0));
        request.setAttribute("news2", news1.get(1));
        ArrayList<News> news2 = NewsDAO.getTop3ViewNews();
        for (News news : news2) {
            System.out.println(news.getUrl());
        }
        request.setAttribute("news3", news2.get(0));
        request.setAttribute("news4", news2.get(1));
        request.setAttribute("news5", news2.get(2));
        ArrayList<Product> productListTopPrice = ProductDAO.getTop8Product("price");
        ArrayList<Product> productListNew = ProductDAO.getTop8Product("created_at");
        request.setAttribute("productListTopPrice", productListTopPrice);
        request.setAttribute("productListNew", productListNew);
        request.setAttribute("listnotnull", CategoryDAO.getlistCategoryWithNotNullParentID());
        request.setAttribute("listnull", CategoryDAO.getlistCategoryWithNullParentID());
        DecimalFormat df = new DecimalFormat("#,###.##");
        df.setMaximumFractionDigits(8);
        request.setAttribute("df", df);
        ArrayList<News> news = NewsDAO.getAllAboutUs();
        request.setAttribute("news", news);
        request.getRequestDispatcher("FrontEnd/home.jsp").forward(request, response);
    }

    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        ArrayList<Product> productListTopPrice = ProductDAO.getTop8Product("price");
        ArrayList<Product> productListNew = ProductDAO.getTop8Product("created_at");
        request.setAttribute("productListTopPrice", productListTopPrice);
        request.setAttribute("productListNew", productListNew);
        request.setAttribute("listnotnull", CategoryDAO.getlistCategoryWithNotNullParentID());
        request.setAttribute("listnull", CategoryDAO.getlistCategoryWithNullParentID());
        request.getRequestDispatcher("FrontEnd/home.jsp").forward(request, response);
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

    public static void main(String[] args) {
//        DecimalFormat df = new DecimalFormat("#,###.##");
//        df.setMaximumFractionDigits();
//        System.out.println(df.format(1.0E8));
    }
}
