/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package controller.Order.Admin;

import static controller.Payment.Mail.sendMail;
import dal.CustomerDAO;
import dal.OrderDAO;
import dal.OrderDetailDAO;
import dal.ProductDAO;
import dal.UserDAO;
import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.servlet.http.HttpSession;
import java.io.IOException;
import java.text.DecimalFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.logging.Level;
import java.util.logging.Logger;
import model.Customer;
import model.Order;
import model.OrderDetail;
import model.Product;
import model.User;

/**
 *
 * @author TNA
 */
@WebServlet(name = "CreateOrderServlet", value = "/admin-create-order")
public class CreateOrderServlet extends HttpServlet {

    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        try {
            SimpleDateFormat sdf = new SimpleDateFormat("ddMMyy");
            SimpleDateFormat sdf2 = new SimpleDateFormat("yyyy-MM-dd");

            String code = sdf.format(new Date()) + "CU" + CustomerDAO.getQuantityOrderFromDate(sdf2.format(new Date()));
            String name = req.getParameter("name");
            String gender = req.getParameter("gender");
            String email = req.getParameter("email");
            String phoneNumber = req.getParameter("phoneNumber");
            String status = "true";
            String pass = "ThiBiViSiCamOn";
            HttpSession session = req.getSession();
            User u = (User) session.getAttribute("user");

            Customer c = new Customer(
                    code,
                    name,
                    Boolean.parseBoolean(gender),
                    email,
                    phoneNumber,
                    CustomerDAO.encryptPassword(pass),
                    Boolean.parseBoolean(status),
                    new Date(),
                    u.getUser_id(),
                    new Date(),
                    u.getUser_id()
            );
            int customerID = CustomerDAO.findCustomer(c); 
            Boolean newCustomer = false;
            if (!CustomerDAO.IfEmailExist(email)) {
                CustomerDAO.addCustomer(c);
                customerID = CustomerDAO.getMaxCustomerID();
                newCustomer = true;
            }
            
            code = sdf.format(new Date()) + "OD" + (OrderDAO.getQuantityOrderFromDate(sdf2.format(new Date()))+1);
            String orderCode = code;
            String address = req.getParameter("address");
            String total = req.getParameter("total").replace(",", "");
            String state = req.getParameter("state");
            status = "true";
            String payment = req.getParameter("payment");

            Order oder = new Order(customerID, u.getUser_id(), code, address,
                    Double.parseDouble(total), Integer.parseInt(state), Boolean.parseBoolean(status),
                    new Date(), u.getUser_id(),
                    new Date(), u.getUser_id(),
                    Boolean.parseBoolean(payment));

            OrderDAO.insertOrder(oder);

            int orderID = OrderDAO.getMaxOrderID();
            String[] productIDs = req.getParameterValues("productID");
            String[] quantitys = req.getParameterValues("quantity");
            String[] unitPrices = req.getParameterValues("unitPrice");
            status = "true";

            for (int i = 0; i < unitPrices.length; i++) {
                code = sdf.format(new Date()) + "ODT" + OrderDetailDAO.getQuantityOrderFromDate(sdf2.format(new Date()));
                OrderDetail od = new OrderDetail(
                        orderID,
                        Integer.parseInt(productIDs[i]),
                        code,
                        Integer.parseInt(quantitys[i]),
                        Double.parseDouble(unitPrices[i].replace(",", "")),
                        Boolean.parseBoolean(status),
                        new Date(),
                        u.getUser_id(),
                        new Date(),
                        u.getUser_id()
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
                    + "                                    <th scope=\"col\">Unit price</th>\n"
                    + "                                    <th scope=\"col\">Total</th>\n"
                    + "                                </tr>\n"
                    + "                            </thead>\n"
                    + "                            <tbody>\n"
                    + pro
                    + "                            </tbody>\n"
                    + "                        </table>\n"
                    + "                    </div>\n"
                    + "                    <a href=\"http://localhost:9999/SWP/vnpayajax?amount=" + df.format(sum) + "&bankCode=&language=vn&orderCode=" + orderCode + "\">\n"
                    + (Boolean.parseBoolean(payment)==false?"                        <input class=\"go-home\" type=\"button\" value=\"Pay Now\" />                        \n":"")
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
                    + "                            Your Password: " + pass + "\n"
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

            req.setAttribute("mess", "Add order successful");
            doGet(req, resp);
        } catch (Exception ex) {
            Logger.getLogger(CreateOrderServlet.class.getName()).log(Level.SEVERE, null, ex);
        }
    }

    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {

        String preSite = req.getParameter("preSite");

        ArrayList<Product> plist = ProductDAO.getAllProduct();
        int index = 0;
        while (index < plist.size() && !plist.get(index).isStatus()) {
            index++;
        }
        DecimalFormat df = new DecimalFormat("#,###.##");
        df.setMaximumFractionDigits(8);
        req.setAttribute("df", df);
        req.setAttribute("productList", plist);
        req.setAttribute("orderList", OrderDAO.getAllOrder());
        req.setAttribute("fprice", plist.get(index).getPrice());
        req.setAttribute("preSite", Boolean.parseBoolean(preSite));

        req.setAttribute("cusList", CustomerDAO.getListCustomer());
        req.getRequestDispatcher("AdminPage/JSP/order-add.jsp").forward(req, resp);
    }

}
