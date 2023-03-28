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
import java.io.File;
import java.io.IOException;
import java.text.SimpleDateFormat;
import model.News;
import model.User;
import jakarta.servlet.*;
import jakarta.servlet.http.*;
import jakarta.servlet.annotation.*;
import java.text.ParseException;
import java.util.Date;
import java.util.logging.Level;
import java.util.logging.Logger;
import model.NewsImage;

/**
 *
 * @author M.S.I
 */
@WebServlet(name = "UpdateNews", value = "/admin-update-news")
@MultipartConfig(fileSizeThreshold = 1024 * 1024 * 2, //2MB
        maxFileSize = 1024 * 1024 * 50, // 50MB
        maxRequestSize = 1024 * 1024 * 50) // 50MB
public class UpdateNews extends HttpServlet {

    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        try {
            SimpleDateFormat sdf = new SimpleDateFormat("ddMMyy");

            String newsID = request.getParameter("newsID");
            String code = request.getParameter("Code");
            String title = request.getParameter("title");
            String shortdescript = request.getParameter("shortdescript");
            String description = request.getParameter("description");
            String status = "true";
            String createdBy = request.getParameter("createdBy");
            String createdAt = request.getParameter("createdAt");

            HttpSession session = request.getSession();
            User u = (User) session.getAttribute("user");

            SimpleDateFormat dtf = new SimpleDateFormat("yyyy-MM-dd");

            News n = new News(Integer.parseInt(newsID), NewsGroupDAO.getNewsGroupbyID(1), code, title, shortdescript, description, 0, Boolean.parseBoolean(status), dtf.parse(createdAt), Integer.parseInt(createdBy),
                     new Date(), u.getUser_id());
            NewsDAO.updateNews(n);

            // upload file to server  
            File myFolder = new File(this.getFolderUpload(code+"").getAbsolutePath());
            String[] entries = myFolder.list();
            NewsDAO.deleteImageByProductID(n.getNewsID());
            int temp = 0, level = 1;
            for (Part part : request.getParts()) {
                if (part.getName().contains("file-")) {
                    String fileName = extractFileName(part);
                    if (fileName.isEmpty()) {
                        continue;
                    }
                    fileName = new File(fileName).getName();
                    System.out.println(fileName);

                    NewsImage pimg = new NewsImage();
                    pimg.setNews_id(Integer.parseInt(newsID));
                    pimg.setCode(sdf.format(new Date()) + "NI" + (++temp));
                    pimg.setImage(fileName);
                    pimg.setLevel(level);
                    level = 2;
                    pimg.setStatus(true);
                    pimg.setCreated_at(new Date());
                    pimg.setCreated_by(u.getUser_id());
                    pimg.setModified_at(new Date());
                    pimg.setModified_by(u.getUser_id());
                    NewsDAO.insertNewsImage(pimg);

//                    System.out.println(this.getFolderUpload(code).getAbsolutePath() + File.separator + fileName);
//                    System.out.println(this.getFolderUpload2(code).getAbsolutePath() + File.separator + fileName);
                    boolean isExist = false;
                    for (String s : entries) {
                        if (s.equals(fileName)) {
                            isExist = true;
                            break;
                        }
                    }
                    if (!isExist) {
                        part.write(this.getFolderUpload2(code+"").getAbsolutePath() + File.separator + fileName);
                        part.write(this.getFolderUpload(code+"").getAbsolutePath() + File.separator + fileName);
                    }
                }
            }
            for (String s : entries) {
                boolean isSaved = false;
                for (Part part : request.getParts()) {
                    if (part.getName().contains("file-")) {
                        String fileName = extractFileName(part);
                        fileName = new File(fileName).getName();
                        if (s.equals(fileName)) {
                            isSaved = true;
                            break;
                        }
                    }
                }
                if (!isSaved) {
                    System.out.println(s);
                    File currentFile = new File(myFolder.getPath(), s);
                    currentFile.delete();
                }
            }

            // update product tags          
            request.setAttribute("mess", "News Saved");
            doGet(request, response);
        } catch (ParseException ex) {
            Logger.getLogger(UpdateNews.class.getName()).log(Level.SEVERE, null, ex);
        }
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
        String newsID = req.getParameter("newsID");
        News News = NewsDAO.getNewsByNewsID(Integer.parseInt(newsID));
        req.setAttribute("news", News);
        req.getRequestDispatcher("AdminPage/JSP/news-update.jsp").forward(req, resp);
    }

}
