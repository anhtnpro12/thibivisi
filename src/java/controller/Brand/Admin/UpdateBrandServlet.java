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
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.Date;
import java.util.logging.Level;
import java.util.logging.Logger;
import model.Brand;

/**
 *
 * @author M.S.I
 */
@WebServlet(name = "UpdateBrandServlet", value = "/update-brand")
public class UpdateBrandServlet extends HttpServlet {

    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        try {
            String brand_id = req.getParameter("brandID");
            String code = req.getParameter("code");
            String name = req.getParameter("name");
            String logo = req.getParameter("logo");
            String status = "true";
            String createdAt = req.getParameter("createdAt");
            String createdBy = req.getParameter("createdBy");
            int modifiedBy = 1;
            
            SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
            
            Brand b = new Brand(Integer.parseInt(brand_id),code, name, logo, Boolean.parseBoolean(status), sdf.parse(createdAt)
                    , Integer.parseInt(createdBy), new Date(), modifiedBy);
            BrandDAO.updateBrand(b);
            resp.sendRedirect("admin-view-brand");
        } catch (ParseException ex) {
            Logger.getLogger(UpdateBrandServlet.class.getName()).log(Level.SEVERE, null, ex);
        }
    }

    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        String brandID = req.getParameter("brandID");
        Brand c = BrandDAO.getBrandbyId(Integer.parseInt(brandID));
        req.setAttribute("brand", c);
        req.getRequestDispatcher("AdminPage/JSP/brand-update.jsp").forward(req, resp);
    }

}
