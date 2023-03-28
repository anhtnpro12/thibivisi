/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/JSP_Servlet/Servlet.java to edit this template
 */
package controller.Home;

import dal.ProductDAO;
import java.io.IOException;
import java.io.PrintWriter;
import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.Cookie;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import java.text.DecimalFormat;
import java.util.List;
import model.Cart;
import model.Item;
import model.Product;

/**
 *
 * @author ASUS
 */
@WebServlet(name = "ProcessServlet", urlPatterns = {"/process"})
public class ProcessServlet extends HttpServlet {

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
            out.println("<title>Servlet ProcessServlet</title>");
            out.println("</head>");
            out.println("<body>");
            out.println("<h1>Servlet ProcessServlet at " + request.getContextPath() + "</h1>");
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
        List<Product> list = ProductDAO.getAllProduct();
        Cookie[] cookieList = request.getCookies();
        String txt = "";
        if (cookieList != null) {
            for (Cookie c : cookieList) {
                if (c.getName().equals("cart")) {
                    txt += c.getValue();
                    c.setMaxAge(0);
                    response.addCookie(c);
                }
            }
        }
        Cart cart = new Cart(txt, list);
        String amount_raw = request.getParameter("amount");
        String productID_raw = request.getParameter("productID");
        int amount, productID;
        txt = "";
        try {
            productID = Integer.parseInt(productID_raw);
            Product p = ProductDAO.getProductByID(productID);
            amount = Integer.parseInt(amount_raw);
            if (amount == -1 && (cart.getQuantityById(productID) <= 1)) {
                cart.removeItem(productID);
            } else if (amount == 0) {
                cart.removeProduct(productID);
            } else {
                double price = p.getPrice();
                Item t = new Item(p, amount, price);
                cart.addItem(t);
            }
        } catch (NumberFormatException e) {

        }
        List<Item> items = cart.getItems();
        if (items.size() > 0) {
            txt = items.get(0).getProduct().getProductID() + ":"
                    + items.get(0).getQuantity();
            for (int i = 1; i < items.size(); i++) {
                txt = txt + "-" + items.get(i).getProduct().getProductID() + ":"
                        + items.get(i).getQuantity();
            }
        }
        Cookie c = new Cookie("cart", txt);
        c.setMaxAge(2 * 24 * 60 * 60);
        response.addCookie(c);
        request.setAttribute("cart", cart);
        Cookie ck = new Cookie("total", cart.getTotalMoney() + "");
        ck.setMaxAge(2 * 24 * 60 * 60);
        response.addCookie(ck);
        DecimalFormat df = new DecimalFormat("#,###.##");
        df.setMaximumFractionDigits(8);
        PrintWriter out = response.getWriter();
        String output = "";
        output += "<div class=\"col-lg-8\">\n"
                + "                        <div class=\"shopping__cart__table\">\n"
                + "                            <table>\n"
                + "                                <thead>\n"
                + "                                    <tr>\n"
                + "                                        <th>Product</th>\n"
                + "                                        <th>Quantity</th>\n"
                + "                                        <th>Total</th>\n"
                + "                                        <th></th>\n"
                + "                                    </tr>\n"
                + "                                </thead>\n"
                + "                                <tbody id=\"body\">";
        for (int i = 0; i < items.size(); i++) {
            output += "<tr>\n"
                    + "                                            <td class=\"product__cart__item\">\n"
                    + "                                                <div class=\"product__cart__item__pic\">\n"
                    + "                                                    <img style=\"object-fit: contain; height: 100px\" class=\"img-fluid img-thumbnail\" src=\"" + ((items.get(i).getProduct().getImages().isEmpty()) ? ".img/General/no-image.png" : "./img/UploadImgs/ProductImgs/" + items.get(i).getProduct().getProductID() + "/" + items.get(i).getProduct().getImages().get(0).getImage())+"\"" + " alt=\"\">\n"
                    + "                                                </div>\n"
                    + "                                                <div class=\"product__cart__item__text\">\n"
                    + "                                                    <h6>" + items.get(i).getProduct().getName() + "</h6>\n"
                    + "                                                    <h5>" + df.format(items.get(i).getPrice()) + "</h5>\n"
                    + "                                                    </div>\n"
                    + "                                                </td>\n"
                    + "                                                <td class=\"quantity__item\">\n"
                    + "                                                    <div class=\"quantity\">\n"
                    + "                                                        <button onclick=\"process(" + items.get(i).getProduct().getProductID() + "," + "1" + ")\" class=\"btn btn-outline-dark\"><i class=\"fa fa-plus-circle\"></i></button>\n"
                    + "                                                    <span>" + items.get(i).getQuantity() + "</span>\n"
                    + "                                                    <button onclick=\"process(" + items.get(i).getProduct().getProductID() + "," + "-1" + ")\" class=\"btn btn-outline-dark\"><i class=\"fa fa-minus-circle\"></i></button>\n"
                    + "                                                </div>\n"
                    + "                                            </td>\n"
                    + "                                            <td class=\"cart__price\">" + df.format(items.get(i).getPrice() * items.get(i).getQuantity()) + "</td>\n"
                    + "                                            <td class=\"cart__close\"> <button onclick=\"process(" + items.get(i).getProduct().getProductID() + "," + "0" + ")\" class=\"btn btn-outline-light\"><i class=\"fa fa-close\"></i></button></td>\n"
                    + "                                        </tr>";
        }
        output += "</tbody>\n"
                + "                            </table>\n"
                + "                        </div>\n"
                + "                        <div class=\"row\">\n"
                + "                            <div class=\"col-lg-6 col-md-6 col-sm-6\">\n"
                + "                                <div class=\"continue__btn\">\n"
                + "                                    <a href=\"shop\">Continue Shopping</a>\n"
                + "                                </div>\n"
                + "                            </div>\n"
                + "                        </div>\n"
                + "                    </div>\n"
                + "                    <div class=\"col-lg-4\">\n"
                + "                        <div class=\"cart__total\">\n"
                + "                            <h6>Cart total</h6>\n"
                + "                            <ul>\n"
                + "                                <li>Total <span id=\"total\">"+df.format(cart.getTotalMoney())+"</span></li>\n"
                + "                                </ul>\n"
                + "                                <a href=\"checkout\" class=\"primary-btn\" id=\"checkout\">Proceed to checkout</a>\n"
                + "                            </div>\n"
                + "                        </div>";
        
        out.println(output);
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
        processRequest(request, response);
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
