/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package controller.News.Admin;

import dal.NewsDAO;
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

@WebServlet(name = "ChangeNewsStatus", value = "/admin-change-news-status")
public class ChangeNewsStatus extends HttpServlet{

    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
    }

    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        String newsID=req.getParameter("newsID");
        String status=req.getParameter("status");
        NewsDAO.changeNewsStatus(newsID,status);
        if(NewsDAO.getNewsByNewsID(Integer.parseInt(newsID)).getNewsGroup().getNewsgroup_id()==1){
            resp.sendRedirect("/SWP/admin-update-aboutus");
        }
        else{
            resp.sendRedirect("/SWP/admin-view-blogs");
        }
        
    }
    
}
