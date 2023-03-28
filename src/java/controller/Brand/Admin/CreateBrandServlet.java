package controller.Brand.Admin;

import dal.BrandDAO;
import jakarta.servlet.*;
import jakarta.servlet.http.*;
import jakarta.servlet.annotation.*;
import model.Brand;

import java.io.IOException;
import java.io.PrintWriter;
import java.time.format.DateTimeFormatter;
import java.util.Date;
import model.User;

@WebServlet(name = "CreateBrandServlet", value = "/admin-create-brand")
public class CreateBrandServlet extends HttpServlet {
    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        String name = request.getParameter("brandName");
        String logo = request.getParameter("logo"); 
        HttpSession session = request.getSession();
        User user = (User)session.getAttribute("user");
        int createdBy = user.getUser_id();
        int modifiedBy = user.getUser_id();

        DateTimeFormatter dtf = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss");

        Brand b = new Brand(null, name, logo, true, new Date()
                            , createdBy, new Date(), modifiedBy);

        BrandDAO.insertBrand(b);

        response.sendRedirect("admin-view-brand");

    }

    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        request.getRequestDispatcher("AdminPage/JSP/brand-add.jsp").forward(request, response);
    }
}

// test
// http://localhost:9999/create-brand?code=codetest&name=nametest&logo=logotest&status=true&createdBy=1&modifiedBy=1