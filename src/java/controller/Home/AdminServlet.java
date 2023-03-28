///*
// * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
// * Click nbfs://nbhost/SystemFileSystem/Templates/JSP_Servlet/Servlet.java to edit this template
// */
//
package controller.Home;

import dal.OrderDAO;
import java.io.IOException;
import java.io.PrintWriter;
import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import java.text.DecimalFormat;
import java.text.SimpleDateFormat;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.Date;

/**
 *
 * @author ASUS
 */
@WebServlet(name="AdminServlet", urlPatterns={"/admin"})
public class AdminServlet extends HttpServlet {
   
    /** 
     * Processes requests for both HTTP <code>GET</code> and <code>POST</code> methods.
     * @param request servlet request
     * @param response servlet response
     * @throws ServletException if a servlet-specific error occurs
     * @throws IOException if an I/O error occurs
     */
    protected void processRequest(HttpServletRequest request, HttpServletResponse response)
    throws ServletException, IOException {
        response.setContentType("text/html;charset=UTF-8");
        try (PrintWriter out = response.getWriter()) {
            /* TODO output your page here. You may use following sample code. */
            out.println("<!DOCTYPE html>");
            out.println("<html>");
            out.println("<head>");
            out.println("<title>Servlet AdminServlet</title>");  
            out.println("</head>");
            out.println("<body>");
            out.println("<h1>Servlet AdminServlet at " + request.getContextPath () + "</h1>");
            out.println("</body>");
            out.println("</html>");
        }
    } 

    // <editor-fold defaultstate="collapsed" desc="HttpServlet methods. Click on the + sign on the left to edit the code.">

    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response)
    throws ServletException, IOException {      
        SimpleDateFormat sdf = new SimpleDateFormat("ddMMyy");
        SimpleDateFormat sdf2 = new SimpleDateFormat("yyyy-MM-dd");
        LocalDate todaydate = LocalDate.now().withDayOfMonth(1);
        LocalDate predate = todaydate.minusMonths(1);
        LocalDate postdate = todaydate.plusMonths(1);
        DecimalFormat df = new DecimalFormat("#,###.##");
        df.setMaximumFractionDigits(8);
        
        int nowOrder = OrderDAO.getQuantityOrderBetweenDate(todaydate.toString(), postdate.toString());        
        int preOrder = OrderDAO.getQuantityOrderBetweenDate(predate.toString(), todaydate.toString());
        request.setAttribute("nowOrder", df.format(nowOrder));        
        request.setAttribute("orderDifference",   df.format(nowOrder-preOrder));
        
        double nowTotal = OrderDAO.getTotalBetweenDate(todaydate.toString(), postdate.toString());
        double preTotal = OrderDAO.getTotalBetweenDate(predate.toString(), todaydate.toString());
        request.setAttribute("nowTotal",  df.format(nowTotal));
        request.setAttribute("totalDifference", df.format(nowTotal-preTotal));
        
        double nowPayment = OrderDAO.getPaymentBetweenDate(todaydate.toString(), postdate.toString());
        double prePayment = OrderDAO.getPaymentBetweenDate(predate.toString(), todaydate.toString());
        request.setAttribute("nowPayment",  df.format(nowPayment));
        request.setAttribute("paymentDifference", df.format(nowPayment-prePayment));
        
        String totals = "";
        for (int i = 1; i <= 12; i++) {
            todaydate = todaydate.withMonth(i);
            postdate = todaydate.plusMonths(1);
            totals += OrderDAO.getTotalBetweenDate(todaydate.toString(), postdate.toString()) + (i!=12?",":"");
        }
        request.setAttribute("totals", totals);
        
        String payments = "";
        for (int i = 1; i <= 12; i++) {
            todaydate = todaydate.withMonth(i);
            postdate = todaydate.plusMonths(1);
            payments += OrderDAO.getPaymentBetweenDate(todaydate.toString(), postdate.toString()) + (i!=12?",":"");
        }
        request.setAttribute("payments", payments);
        
        request.getRequestDispatcher("AdminPage/JSP/admin.jsp").forward(request,response);
    } 

    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response)
    throws ServletException, IOException {
        processRequest(request, response);
    }

    /** 
     * Returns a short description of the servlet.
     * @return a String containing servlet description
     */
    @Override
    public String getServletInfo() {
        return "Short description";
    }// </editor-fold>

}
