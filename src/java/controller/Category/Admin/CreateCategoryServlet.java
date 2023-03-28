package controller.Category.Admin;

import dal.CategoryDAO;
import jakarta.servlet.*;
import jakarta.servlet.http.*;
import jakarta.servlet.annotation.*;
import model.Category;

import java.io.IOException;
import java.util.Date;
import model.User;

@WebServlet(name = "CreateCategoryServlet", value = "/admin-create-category")
public class CreateCategoryServlet extends HttpServlet {
    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        request.setAttribute("listnull", CategoryDAO.getlistCategoryWithNullParentID());
        request.getRequestDispatcher("AdminPage/JSP/category-add.jsp").forward(request, response);
    }

    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        String parentID = request.getParameter("parentID");
        String cate = request.getParameter("cate"); 
        String code = "cate" + (CategoryDAO.getCateIDMax()+1);
        HttpSession session = request.getSession();
        User user = (User)session.getAttribute("user");
        int createdBy = user.getUser_id();        
        int modifiedBy = user.getUser_id();    

        Category c = new Category(Integer.parseInt(parentID), null, cate, true, new Date()
                                , createdBy, new Date(), modifiedBy);

        CategoryDAO.insertCategory(c);

        response.sendRedirect("admin-view-category");
    }
}
