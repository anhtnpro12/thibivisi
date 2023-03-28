/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package controller.Brand.Admin;

import dal.BrandDAO;
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
@WebServlet(name = "ChangeBrandStatusServlet", value = "/admin-change-brand-status")
public class ChangeBrandStatusServlet extends HttpServlet{

    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
    }

    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        String brand_id=req.getParameter("brandID");
        String status=req.getParameter("status");
        BrandDAO.changeBrandStatus(brand_id,status);
        resp.sendRedirect("/SWP/admin-view-brand");
    }
    
}
