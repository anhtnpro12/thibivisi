/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/JSP_Servlet/Servlet.java to edit this template
 */
package controller.UserManagement;

import dal.UserDAO;
import java.io.IOException;
import java.io.PrintWriter;
import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.MultipartConfig;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.servlet.http.HttpSession;
import jakarta.servlet.http.Part;
import java.io.File;
import model.User;

/**
 *
 * @author ASUS
 */
@WebServlet(name = "UpdateUserServlet", urlPatterns = {"/admin-update-user"})
@MultipartConfig(fileSizeThreshold = 1024 * 1024 * 2, //2MB
        maxFileSize = 1024 * 1024 * 50, // 50MB
        maxRequestSize = 1024 * 1024 * 50) // 50MB
public class UpdateUserServlet extends HttpServlet {

    /**
     * Processes requests for both HTTP <code>GET</code> and <code>POST</code>
     * methods.
     *
     * @param request servlet request
     * @param response servlet response
     * @throws ServletException if a servlet-specific error occurs
     * @throws IOException if an I/O error occurs
     */
    protected void processRequest(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        response.setContentType("text/html;charset=UTF-8");
        try ( PrintWriter out = response.getWriter()) {
            /* TODO output your page here. You may use following sample code. */
            out.println("<!DOCTYPE html>");
            out.println("<html>");
            out.println("<head>");
            out.println("<title>Servlet UpdateUserServlet</title>");
            out.println("</head>");
            out.println("<body>");
            out.println("<h1>Servlet UpdateUserServlet at " + request.getContextPath() + "</h1>");
            out.println("</body>");
            out.println("</html>");
        }
    }

    // <editor-fold defaultstate="collapsed" desc="HttpServlet methods. Click on the + sign on the left to edit the code.">
    /**
     * Handles the HTTP <code>GET</code> method.
     *
     * @param request servlet request
     * @param response servlet response
     * @throws ServletException if a servlet-specific error occurs
     * @throws IOException if an I/O error occurs
     */
    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        String userID = request.getParameter("userID");
        User user = UserDAO.getUserByID(Integer.parseInt(userID));
        request.setAttribute("user", user);
//        response.getWriter().print(user.getDob());

        request.getRequestDispatcher("AdminPage/JSP/user-update.jsp").forward(request, response);
    }

    /**
     * Handles the HTTP <code>POST</code> method.
     *
     * @param request servlet request
     * @param response servlet response
     * @throws ServletException if a servlet-specific error occurs
     * @throws IOException if an I/O error occurs
     */
    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        String userID = request.getParameter("userID");
        String userCode = request.getParameter("userCode");
        String userName = request.getParameter("userName");
        String userDob = request.getParameter("userDob");
        String userAddress = request.getParameter("userAddress");
        String userEmail = request.getParameter("userEmail");
        String userPassword = request.getParameter("userPassword");
        String userIDCard = request.getParameter("userIDCard");
        String userGender = request.getParameter("userGender");
        String createdAt = request.getParameter("createdAt");
        String createdBy = request.getParameter("createdBy");

        HttpSession session = request.getSession();
        User currentUser = (User) session.getAttribute("user");
        User u = new User();
        u.setUser_id(Integer.parseInt(userID));
        u.setCode(userCode);
        u.setAddress(userAddress);
        u.setFullname(userName);
        u.setDob(userDob);
        u.setEmail(userEmail);
        u.setPassword(userPassword);
        u.setId_card(userIDCard);
        u.setGender(userGender);
        u.setCreated_at(java.sql.Date.valueOf(createdAt));
        u.setCreated_by(Integer.parseInt(createdBy));
        u.setRole(2);
        u.setStatus(true);
        u.setAvatar("");
        u.setModified_by(currentUser.getUser_id());
        u.setModified_at(new java.sql.Date(System.currentTimeMillis()));
//
//        response.getWriter().print(u);        
        
        // upload file to server 
        File myFolder = new File(this.getFolderUpload(userID+"").getAbsolutePath());
        String[]entries = myFolder.list();
        for (String s : entries) {
            File currentFile = new File(myFolder.getPath(),s);
            currentFile.delete(); 
        }
        for (Part part : request.getParts()) {            
            if (part.getName().contains("file-")) {                
                String fileName = extractFileName(part);                 
                if (fileName.isEmpty()) {
                    continue;
                }                         
                fileName = new File(fileName).getName();                
                System.out.println(fileName);                                
                u.setAvatar(fileName);
                System.out.println(this.getFolderUpload(userID+"").getAbsolutePath() + File.separator + fileName);
                System.out.println(this.getFolderUpload2(userID+"").getAbsolutePath() + File.separator + fileName);                
                part.write(this.getFolderUpload2(userID+"").getAbsolutePath() + File.separator + fileName);                
                part.write(this.getFolderUpload(userID+"").getAbsolutePath() + File.separator + fileName);                                                 
            }
        }
        UserDAO.updateUser(u);
        response.sendRedirect("admin-view-user");
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
        String basePath = getServletContext().getRealPath(File.separator) + "\\..\\..\\web\\img\\UploadImgs\\UserImgs\\" + code;
//        System.out.println(basePath);
        File folderUpload = new File(basePath);
        if (!folderUpload.exists()) {
            folderUpload.mkdirs();
        }
        return folderUpload;
    }
    
    private File getFolderUpload2(String code) {        
        String basePath = getServletContext().getRealPath(File.separator) + "\\img\\UploadImgs\\UserImgs\\" + code;
//        System.out.println(basePath);
        File folderUpload = new File(basePath);
        if (!folderUpload.exists()) {
            folderUpload.mkdirs();
        }
        return folderUpload;
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

}
