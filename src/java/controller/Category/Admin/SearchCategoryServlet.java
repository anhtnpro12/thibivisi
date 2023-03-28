/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package controller.Category.Admin;

import dal.CategoryDAO;
import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.text.DecimalFormat;
import java.util.ArrayList;
import model.Category;

/**
 *
 * @author M.S.I
 */
@WebServlet(name = "SearchCategoryServlet", value = "/admin-search-category")
public class SearchCategoryServlet extends HttpServlet {

    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        super.doPost(req, resp); // Generated from nbfs://nbhost/SystemFileSystem/Templates/Classes/Code/OverriddenMethodBody
    }

    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        String name = req.getParameter("name")==null?"":req.getParameter("name");
        String pageType = req.getParameter("page-type");
        pageType = pageType == null ? "all" : pageType;
        String status = pageType.equals("all")?"":(pageType.equals("deactive")?"0":"1");
        ArrayList<Category> list = CategoryDAO.getListCategoryContains("cate", name, "status", status, "modified_at");
        int page, numPerPage = 5, size = list.size();
        String xpage = req.getParameter("page");
        if (xpage == null) {
            page = 1;
        } else {
            page = Integer.parseInt(xpage);
        }
        int begin = numPerPage * (page - 1);
        int end = Math.min(numPerPage * page, size);
//        for (Category category : list1) {
//            System.out.println(category);
//        }

        DecimalFormat df = new DecimalFormat("#");
        df.setMaximumFractionDigits(8);
        req.setAttribute("pageType", pageType);
        req.setAttribute("categoryDao", new CategoryDAO());
        req.setAttribute("df", df);
        req.setAttribute("list", CategoryDAO.getListByPage(list, begin, end));
        req.setAttribute("size", size % numPerPage == 0 ? size / numPerPage : (size / numPerPage) + 1);
        req.setAttribute("page", page);
        req.setAttribute("name", name);
        req.setAttribute("status", status);

        req.getRequestDispatcher("AdminPage/JSP/category-search.jsp").forward(req, resp);
    }

}
