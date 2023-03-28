/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/JSP_Servlet/Servlet.java to edit this template
 */
package controller.Home;

import static controller.Payment.Mail.sendMail;

import controller.Order.Admin.CreateOrderServlet;
import dal.CustomerDAO;
import dal.OrderDAO;
import dal.OrderDetailDAO;
import dal.ProductDAO;
import java.io.IOException;
import java.io.PrintWriter;
import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.Cookie;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.servlet.http.HttpSession;
import java.text.DecimalFormat;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;
import model.Cart;
import model.Customer;
import model.Order;
import model.OrderDetail;
import model.Product;
import model.User;

/**
 *
 * @author ASUS
 */
@WebServlet(name = "CheckOutServlet", urlPatterns = {"/checkout"})
public class CheckOutServlet extends HttpServlet {

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
            out.println("<title>Servlet CheckOutServlet</title>");
            out.println("</head>");
            out.println("<body>");
            out.println("<h1>Servlet CheckOutServlet at " + request.getContextPath() + "</h1>");
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
        DecimalFormat df = new DecimalFormat("#,###.##");
        df.setMaximumFractionDigits(8);
        request.setAttribute("df", df);
        List<Product> list = ProductDAO.getAllProduct();
        Cookie[] cookieList = request.getCookies();
        String txt = "";
        if (cookieList != null) {
            for (Cookie c : cookieList) {
                if (c.getName().equals("cart")) {
                    txt += c.getValue();
                }
            }
        }
        Cart cart = new Cart(txt, list);
        request.setAttribute("cart", cart);
        request.getRequestDispatcher("FrontEnd/checkout.jsp").forward(request, response);
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
    protected void doPost(HttpServletRequest req, HttpServletResponse resp)
            throws ServletException, IOException {
        try {
            req.setCharacterEncoding("UTF-8");
            resp.setContentType("text/html; charset=UTF-8");

            SimpleDateFormat sdf = new SimpleDateFormat("ddMMyy");
            SimpleDateFormat sdf2 = new SimpleDateFormat("yyyy-MM-dd");

            HttpSession session = req.getSession();
            String code = sdf.format(new Date()) + "CU" + CustomerDAO.getQuantityOrderFromDate(sdf2.format(new Date()));
            String name = req.getParameter("name");
            String gender = req.getParameter("gender");
            String email = req.getParameter("email");
            String phoneNumber = req.getParameter("phoneNumber");
            Boolean status = true;
            String customerPassword = "ThiBiViSiCamOn";
            Boolean newCustomer = false;

            Customer c = new Customer(
                    code,
                    name,
                    Boolean.parseBoolean(gender),
                    email,
                    phoneNumber,
                    status,
                    new Date(),
                    0,
                    new Date(),
                    0
            );
            c.setPassword(CustomerDAO.encryptPassword(customerPassword));

            int customerID = CustomerDAO.findCustomer(c);
            if (!CustomerDAO.IfEmailExist(email)) {
                CustomerDAO.addCustomer(c);
                customerID = CustomerDAO.getMaxCustomerID();
                newCustomer = true;
            }
            code = sdf.format(new Date()) + "OD" + (OrderDAO.getQuantityOrderFromDate(sdf2.format(new Date())) + 1);
            String orderCode = code;
            String address = req.getParameter("address");
            String total = req.getParameter("total").replace(",", "");
            int state = 1;
            status = true;
            Boolean payment = false;

            Order order = new Order(customerID, 0, code, address,
                    Double.parseDouble(total), state, status,
                    new Date(), 0,
                    new Date(), 0,
                    payment);

            OrderDAO.insertOrder(order);
            int orderID = OrderDAO.getMaxOrderID();
            String[] productIDs = req.getParameterValues("productID");
            String[] quantitys = req.getParameterValues("quantity");
            String[] unitPrices = req.getParameterValues("unitPrice");
            status = true;

            for (int i = 0; i < unitPrices.length; i++) {
                code = sdf.format(new Date()) + "ODT" + OrderDetailDAO.getQuantityOrderFromDate(sdf2.format(new Date()));
                OrderDetail od = new OrderDetail(
                        orderID,
                        Integer.parseInt(productIDs[i]),
                        code,
                        Integer.parseInt(quantitys[i]),
                        Double.parseDouble(unitPrices[i].replace(",", "")),
                        status,
                        new Date(),
                        0,
                        new Date(),
                        0
                );

                OrderDetailDAO.insertOrderDetail(od);
            }

            // send mail payment
            String pro = "";
            double sum = 0;
            DecimalFormat df = new DecimalFormat("#,###.##");
            df.setMaximumFractionDigits(8);
            for (int i = 0; i < unitPrices.length; i++) {
                Product p = ProductDAO.getProductByID(Integer.parseInt(productIDs[i]));
                double temp = Double.parseDouble(unitPrices[i].replace(",", "")) * Integer.parseInt(quantitys[i]);
                pro += "<tr>\n"
                        + "    <td scope=\"row\">" + (i + 1) + "</th>\n"
                        + "    <td>" + p.getName() + "</td>\n"
                        + "    <td>" + quantitys[i] + "</td>\n"
                        + "    <td>" + unitPrices[i] + "</td>\n"
                        + "    <td>" + df.format(temp) + "</td>\n"
                        + "</tr>\n";
                sum += temp;
            }            
            pro += "<tr>\n"
                    + "    <td scope=\"row\"></th>\n"
                    + "    <td></td>\n"
                    + "    <td></td>\n"
                    + "    <td></td>\n"
                    + "    <td>" + df.format(sum) + "</td>\n"
                    + "</tr>\n";
            df = new DecimalFormat("#");
            df.setMaximumFractionDigits(8);
            String id = "ThiBiViSi";
            String sender = "anhyc88@gmail.com";
            String receiver = email;
            String password = "jkbetjophxeigafd";
            String subject = "Your order has been successfully placed";
            String message = "<!DOCTYPE html>\n"
                    + "<html>\n"
                    + "    <head>\n"
                    + "        <title></title>\n"
                    + "        <meta http-equiv=\"Content-Type\" content=\"text/html; charset=utf-8\" />\n"
                    + "        <meta name=\"viewport\" content=\"width=device-width, initial-scale=1\">\n"
                    + "        <meta http-equiv=\"X-UA-Compatible\" content=\"IE=edge\" />       \n"
                    + "\n"
                    + "        <style type=\"text/css\">\n"
                    + "            *{\n"
                    + "                box-sizing:border-box;\n"
                    + "                /* outline:1px solid ;*/\n"
                    + "            }\n"
                    + "            body{\n"
                    + "                background: #ffffff;\n"
                    + "                background: linear-gradient(to bottom, #ffffff 0%,#e1e8ed 100%);\n"
                    + "                filter: progid:DXImageTransform.Microsoft.gradient( startColorstr='#ffffff', endColorstr='#e1e8ed',GradientType=0 );\n"
                    + "                height: 100%;\n"
                    + "                margin: 0;\n"
                    + "                background-repeat: no-repeat;\n"
                    + "                background-attachment: fixed;\n"
                    + "\n"
                    + "            }\n"
                    + "\n"
                    + "            .wrapper-1{\n"
                    + "                width:100%;\n"
                    + "                height:100vh;\n"
                    + "                display: flex;\n"
                    + "                flex-direction: column;\n"
                    + "            }\n"
                    + "            .wrapper-2{\n"
                    + "                padding :30px;\n"
                    + "                text-align:center;\n"
                    + "            }\n"
                    + "            h1{\n"
                    + "                font-family: 'Kaushan Script', cursive;\n"
                    + "                font-size:4em;\n"
                    + "                letter-spacing:3px;\n"
                    + "                color:#5892FF ;\n"
                    + "                margin:0;\n"
                    + "                margin-bottom:20px;\n"
                    + "            }\n"
                    + "            .wrapper-2 h2 {\n"
                    + "                margin:0;\n"
                    + "                font-size:2em;\n"
                    + "                color:#aaa;\n"
                    + "                font-family: 'Source Sans Pro', sans-serif;\n"
                    + "                letter-spacing:1px;\n"
                    + "            }\n"
                    + "            .wrapper-2 p{\n"
                    + "                margin:0;\n"
                    + "                font-size:1.3em;\n"
                    + "                color:#aaa;\n"
                    + "                font-family: 'Source Sans Pro', sans-serif;\n"
                    + "                letter-spacing:1px;\n"
                    + "            }\n"
                    + "            .go-home{\n"
                    + "                color:#fff;\n"
                    + "                background:#5892FF;\n"
                    + "                border:none;\n"
                    + "                padding:10px 50px;\n"
                    + "                margin:30px 0;\n"
                    + "                border-radius:30px;\n"
                    + "                text-transform:capitalize;\n"
                    + "                box-shadow: 0 10px 16px 1px rgba(174, 199, 251, 1);\n"
                    + "            }\n"
                    + "            .go-home:hover {\n"
                    + "                cursor: pointer;\n"
                    + "                opacity: 0.8;\n"
                    + "            }\n"
                    + "            .footer-like{\n"
                    + "                margin-top: auto;\n"
                    + "                background:#D7E6FE;\n"
                    + "                padding:6px;\n"
                    + "                text-align:center;\n"
                    + "            }\n"
                    + "            .footer-like p{\n"
                    + "                margin:0;\n"
                    + "                padding:4px;\n"
                    + "                color:#5892FF;\n"
                    + "                font-family: 'Source Sans Pro', sans-serif;\n"
                    + "                letter-spacing:1px;\n"
                    + "            }\n"
                    + "            .footer-like p a{\n"
                    + "                text-decoration:none;\n"
                    + "                color:#5892FF;\n"
                    + "                font-weight:600;\n"
                    + "            }\n"
                    + "            \n"
                    + "            table th {\n"
                    + "                border-bottom: 1px solid #ccc;\n"
                    + "                height: 50px;\n"
                    + "            }\n"
                    + "            \n"
                    + "            table td {\n"
                    + "                border-top: 1px solid #ccc;\n"
                    + "                height: 50px;\n"
                    + "            }\n"
                    + "\n"
                    + "            @media (min-width:360px){\n"
                    + "                h1{\n"
                    + "                    font-size:4.5em;\n"
                    + "                }\n"
                    + "                .go-home{\n"
                    + "                    margin-bottom:20px;\n"
                    + "                }\n"
                    + "            }\n"
                    + "\n"
                    + "            @media (min-width:600px){\n"
                    + "                .content{\n"
                    + "                    max-width:1000px;\n"
                    + "                    margin:0 auto;\n"
                    + "                }\n"
                    + "                .wrapper-1{\n"
                    + "                    height: initial;\n"
                    + "                    max-width:620px;\n"
                    + "                    margin:0 auto;\n"
                    + "                    margin-top:50px;\n"
                    + "                    box-shadow: 4px 8px 40px 8px rgba(88, 146, 255, 0.2);\n"
                    + "                }\n"
                    + "\n"
                    + "            }\n"
                    + "        </style>\n"
                    + "    </head>\n"
                    + "\n"
                    + "    <body style=\"background-color: #f4f4f4; margin: 0 !important; padding: 0 !important;\">\n"
                    + "        <div class=content>\n"
                    + "            <div class=\"wrapper-1\">\n"
                    + "                <div class=\"wrapper-2\">\n"
                    + "                    <h1>Thank you !</h1>\n"
                    + "                    <h2><strong>You have successfully placed your order</strong></h2>\n"
                    + "                    <div>\n"
                    + "                        <p>Your order here</p>                    \n"
                    + "                        <table width=\"100%\" style=\"border-collapse: collapse\">\n"
                    + "                            <thead>\n"
                    + "                                <tr>\n"
                    + "                                    <th scope=\"col\">No</th>\n"
                    + "                                    <th scope=\"col\">Name</th>\n"
                    + "                                    <th scope=\"col\">Quantity</th>\n"
                    + "                                    <th scope=\"col\">Unit Price</th>\n"
                    + "                                    <th scope=\"col\">Total</th>\n"
                    + "                                </tr>\n"
                    + "                            </thead>\n"
                    + "                            <tbody>\n"
                    + pro
                    + "                            </tbody>\n"
                    + "                        </table>\n"
                    + "                    </div>\n"
                    + "                    <a href=\"http://localhost:9999/SWP/vnpayajax?amount=" + df.format(sum) + "&bankCode=&language=vn&orderCode=" + orderCode + "\">\n"
                    + "                        <input class=\"go-home\" type=\"button\" value=\"Pay Now\" />                        \n"
                    + "                    </a>\n"
                    + "                </div>\n"
                    + "            </div>\n"
                    + "        </div>\n"
                    + "\n"
                    + "\n"
                    + "\n"
                    + "        <link href=\"https://fonts.googleapis.com/css?family=Kaushan+Script|Source+Sans+Pro\" rel=\"stylesheet\">\n"
                    + "        \n"
                    + "    </body>\n"
                    + "\n"
                    + "</html>";
            req.setAttribute("message",
                    sendMail(id, receiver, sender, subject,
                            message, true, password));

            String customerMessage = "<!DOCTYPE html>\n"
                    + "<html>\n"
                    + "    <head>\n"
                    + "        <title></title>\n"
                    + "        <meta http-equiv=\"Content-Type\" content=\"text/html; charset=utf-8\" />\n"
                    + "        <meta name=\"viewport\" content=\"width=device-width, initial-scale=1\">\n"
                    + "        <meta http-equiv=\"X-UA-Compatible\" content=\"IE=edge\" />       \n"
                    + "\n"
                    + "        <style type=\"text/css\">\n"
                    + "            *{\n"
                    + "                box-sizing:border-box;\n"
                    + "                /* outline:1px solid ;*/\n"
                    + "            }\n"
                    + "            body{\n"
                    + "                background: #ffffff;\n"
                    + "                background: linear-gradient(to bottom, #ffffff 0%,#e1e8ed 100%);\n"
                    + "                filter: progid:DXImageTransform.Microsoft.gradient( startColorstr='#ffffff', endColorstr='#e1e8ed',GradientType=0 );\n"
                    + "                height: 100%;\n"
                    + "                margin: 0;\n"
                    + "                background-repeat: no-repeat;\n"
                    + "                background-attachment: fixed;\n"
                    + "\n"
                    + "            }\n"
                    + "\n"
                    + "            .wrapper-1{\n"
                    + "                width:100%;\n"
                    + "                height:100vh;\n"
                    + "                display: flex;\n"
                    + "                flex-direction: column;\n"
                    + "            }\n"
                    + "            .wrapper-2{\n"
                    + "                padding :30px;\n"
                    + "                text-align:center;\n"
                    + "            }\n"
                    + "            h1{\n"
                    + "                font-family: 'Kaushan Script', cursive;\n"
                    + "                font-size:4em;\n"
                    + "                letter-spacing:3px;\n"
                    + "                color:#5892FF ;\n"
                    + "                margin:0;\n"
                    + "                margin-bottom:20px;\n"
                    + "            }\n"
                    + "            .wrapper-2 h2 {\n"
                    + "                margin:0;\n"
                    + "                font-size:2em;\n"
                    + "                color:#aaa;\n"
                    + "                font-family: 'Source Sans Pro', sans-serif;\n"
                    + "                letter-spacing:1px;\n"
                    + "            }\n"
                    + "            .wrapper-2 p{\n"
                    + "                margin:0;\n"
                    + "                color:#aaa;\n"
                    + "                font-family: 'Source Sans Pro', sans-serif;\n"
                    + "                letter-spacing:1px;\n"
                    + "            }\n"
                    + "            .go-home{\n"
                    + "                color:#fff;\n"
                    + "                background:#5892FF;\n"
                    + "                border:none;\n"
                    + "                padding:10px 50px;\n"
                    + "                margin:30px 0;\n"
                    + "                border-radius:30px;\n"
                    + "                text-transform:capitalize;\n"
                    + "                box-shadow: 0 10px 16px 1px rgba(174, 199, 251, 1);\n"
                    + "            }\n"
                    + "            .go-home:hover {\n"
                    + "                cursor: pointer;\n"
                    + "                opacity: 0.8;\n"
                    + "            }\n"
                    + "            .footer-like{\n"
                    + "                margin-top: auto;\n"
                    + "                background:#D7E6FE;\n"
                    + "                padding:6px;\n"
                    + "                text-align:center;\n"
                    + "            }\n"
                    + "            .footer-like p{\n"
                    + "                margin:0;\n"
                    + "                padding:4px;\n"
                    + "                color:#5892FF;\n"
                    + "                font-family: 'Source Sans Pro', sans-serif;\n"
                    + "                letter-spacing:1px;\n"
                    + "            }\n"
                    + "            .footer-like p a{\n"
                    + "                text-decoration:none;\n"
                    + "                color:#5892FF;\n"
                    + "                font-weight:600;\n"
                    + "            }\n"
                    + "            \n"
                    + "\n"
                    + "\n"
                    + "            @media (min-width:360px){\n"
                    + "                h1{\n"
                    + "                    font-size:4.5em;\n"
                    + "                }\n"
                    + "                .go-home{\n"
                    + "                    margin-bottom:20px;\n"
                    + "                }\n"
                    + "            }\n"
                    + "\n"
                    + "            @media (min-width:600px){\n"
                    + "                .content{\n"
                    + "                    max-width:1000px;\n"
                    + "                    margin:0 auto;\n"
                    + "                }\n"
                    + "                .wrapper-1{\n"
                    + "                    height: initial;\n"
                    + "                    max-width:620px;\n"
                    + "                    margin:0 auto;\n"
                    + "                    margin-top:50px;\n"
                    + "                    box-shadow: 4px 8px 40px 8px rgba(88, 146, 255, 0.2);\n"
                    + "                }\n"
                    + "\n"
                    + "            }\n"
                    + "        </style>\n"
                    + "    </head>\n"
                    + "\n"
                    + "    <body style=\"background-color: #f4f4f4; margin: 0 !important; padding: 0 !important;\">\n"
                    + "        <div class=content>\n"
                    + "            <div class=\"wrapper-1\">\n"
                    + "                <div class=\"wrapper-2\">\n"
                    + "                    <h1>Thank you !</h1>\n"
                    + "                    <h2><strong>Please use this account to log in!!!</strong></h2>\n"
                    + "                    <div>\n"
                    + "                        <p>\n"
                    + "                            Your Account: " + email + "\n"
                    + "                            <br>\n"
                    + "                            Your Password: " + customerPassword + "\n"
                    + "                        </p>                   \n"
                    + "                    </div>\n"
                    + "                    <h3><strong>Rememeber to log in and change your password!!!</strong></h3>\n"
                    + "                    <a href=\"http://localhost:9999/SWP/customerlogin\">\n"
                    + "                        <input class=\"go-home\" type=\"button\" value=\"LOG IN\" />                        \n"
                    + "                    </a>\n"
                    + "                </div>\n"
                    + "            </div>\n"
                    + "        </div>\n"
                    + "\n"
                    + "\n"
                    + "\n"
                    + "        <link href=\"https://fonts.googleapis.com/css?family=Kaushan+Script|Source+Sans+Pro\" rel=\"stylesheet\">\n"
                    + "        \n"
                    + "    </body>\n"
                    + "\n"
                    + "</html>";
            if (newCustomer) {
                sendMail(id, receiver, sender, subject,
                        customerMessage, true, password);
            }
            Cookie cookie = new Cookie("cart", "");
            cookie.setMaxAge(0);
            resp.addCookie(cookie);
            cookie = new Cookie("total", "");
            cookie.setMaxAge(0);
            resp.addCookie(cookie);

            resp.sendRedirect("home");
        } catch (Exception ex) {
            Logger.getLogger(CreateOrderServlet.class.getName()).log(Level.SEVERE, null, ex);
        }
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

    public static void main(String[] args) {

    }
}
