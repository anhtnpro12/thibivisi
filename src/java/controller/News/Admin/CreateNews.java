/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package controller.News.Admin;

import dal.NewsDAO;
import dal.NewsGroupDAO;
import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.MultipartConfig;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.servlet.http.HttpSession;
import jakarta.servlet.http.Part;
import java.io.File;
import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import model.News;
import model.NewsGroup;
import model.NewsImage;
import model.User;

/**
 *
 * @author M.S.I
 */
@WebServlet(name = "CreateNews", value = "/admin-create-news")
@MultipartConfig(fileSizeThreshold = 1024 * 1024 * 2, //2MB
        maxFileSize = 1024 * 1024 * 50, // 50MB
        maxRequestSize = 1024 * 1024 * 50) // 50MB
public class CreateNews extends HttpServlet {

    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        SimpleDateFormat sdf = new SimpleDateFormat("ddMMyy");

        String newsgroupID = req.getParameter("newsgroupID");
        String title = req.getParameter("title");
        String shortdescript = req.getParameter("shortdescript");
        String description = req.getParameter("description");
        String status = "true";
        HttpSession session = req.getSession();
        User user = (User) session.getAttribute("user");
        int createdBy = user.getUser_id();
        int modifiedBy = user.getUser_id();
        News n = new News(0, NewsGroupDAO.getNewsGroupbyID(Integer.parseInt(newsgroupID)), null, title, shortdescript, description, 0, Boolean.parseBoolean(status), new Date(), createdBy,
                new Date(), modifiedBy);
        NewsDAO.insertNews(n);
        // upload file to server
        String extension;
        int temp = 0, level = 1;
        for (Part part : req.getParts()) {
            if (part.getName().contains("file-")) {
                String fileName = extractFileName(part);
                if (fileName.isEmpty()) {
                    continue;
                }
                fileName = new File(fileName).getName();
                System.out.println(fileName);

                NewsImage pimg = new NewsImage();
                int myID = NewsDAO.getNewsIDMax();
                pimg.setNews_id(myID);
                pimg.setCode(sdf.format(new Date()) + "NI" + (++temp));
                pimg.setImage(fileName);
                pimg.setLevel(level);
                level = 2;
                pimg.setStatus(true);
                pimg.setCreated_at(new Date());
                pimg.setCreated_by(user.getUser_id());
                pimg.setModified_at(new Date());
                pimg.setModified_by(user.getUser_id());
                NewsDAO.insertNewsImage(pimg);

                part.write(this.getFolderUpload2(NewsDAO.genCode()).getAbsolutePath() + File.separator + fileName);
                part.write(this.getFolderUpload(NewsDAO.genCode()).getAbsolutePath() + File.separator + fileName);
            }
        }
        req.setAttribute("mess", "Add News Successful");
        doGet(req, resp);
    }

    private String extractFileName(Part part) {
        String contentDisp = part.getHeader("content-disposition");
        String[] items = contentDisp.split(";");
        for (String s : items) {
            if (s.trim().startsWith("filename")) {
                return s.substring(s.indexOf("=") + 2, s.length() - 1);
            }
        }
        return "";
    }

    private File getFolderUpload(String code) {
        String basePath = getServletContext().getRealPath(File.separator) + "\\..\\..\\web\\img\\UploadImgs\\NewsImgs\\" + code;
//        System.out.println(basePath);
        File folderUpload = new File(basePath);
        if (!folderUpload.exists()) {
            folderUpload.mkdirs();
        }
        return folderUpload;
    }

    private File getFolderUpload2(String code) {
        String basePath = getServletContext().getRealPath(File.separator) + "\\img\\UploadImgs\\NewsImgs\\" + code;
//        System.out.println(basePath);
        File folderUpload = new File(basePath);
        if (!folderUpload.exists()) {
            folderUpload.mkdirs();
        }
        return folderUpload;
    }

    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        ArrayList<NewsGroup> ng = NewsGroupDAO.getlistNotNullNewsGroup();
        req.setAttribute("newsgroup", ng);
        req.getRequestDispatcher("AdminPage/JSP/news-add.jsp").forward(req, resp);
    }

}
