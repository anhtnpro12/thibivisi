/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package controller.Home;

import dal.CategoryDAO;
import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import java.io.IOException;


/**
 *
 * @author M.S.I
 */
@WebServlet(name="CategoryServlet", urlPatterns={"/shop-details"})
public class CategoryServlet extends HttpServlet{

    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        super.doPost(req, resp); // Generated from nbfs://nbhost/SystemFileSystem/Templates/Classes/Code/OverriddenMethodBody
    }

    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
//        System.out.println(CategoryDAO.getlistCategoryWithNullParentID().get(0).getCate());
        req.setAttribute("listnotnull", CategoryDAO.getlistCategoryWithNotNullParentID());
        req.setAttribute("listnull", CategoryDAO.getlistCategoryWithNullParentID());
        req.getRequestDispatcher("FrontEnd/shop-details.jsp").forward(req, resp);
    }
    
}
