/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package controller.Payment;

import static controller.Payment.Mail.sendMail;
import dal.CustomerDAO;
import dal.OrderDAO;
import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.io.PrintWriter;
import java.net.URLEncoder;
import java.nio.charset.StandardCharsets;
import java.util.Enumeration;
import java.util.HashMap;
import java.util.Map;
import model.Customer;
import model.Order;

/**
 *
 * @author TNA
 */
@WebServlet(name = "vnpayReturn", value = "/vnpay-return")
public class vnpayReturn extends HttpServlet {

    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        PrintWriter out = resp.getWriter();
        try {
            /*  IPN URL: Record payment results from VNPAY
                        Implementation steps:
                        Check checksum
                        Find transactions (vnp_TxnRef) in the database (checkOrderId)
                        Check the payment status of transactions before updating (checkOrderStatus)
                        Check the amount (vnp_Amount) of transactions before updating (checkAmount)
                        Update results to Database
                        Return recorded results to VNPAY
             */
            // ex:  	PaymnentStatus = 0; pending 
            //              PaymnentStatus = 1; success 
            //              PaymnentStatus = 2; Faile 
            //Begin process return from VNPAY
            Map fields = new HashMap();
            for (Enumeration params = req.getParameterNames(); params.hasMoreElements();) {
                String fieldName = URLEncoder.encode((String) params.nextElement(), StandardCharsets.US_ASCII.toString());
                String fieldValue = URLEncoder.encode(req.getParameter(fieldName), StandardCharsets.US_ASCII.toString());
                if ((fieldValue != null) && (fieldValue.length() > 0)) {
                    fields.put(fieldName, fieldValue);
                }
            }

            String vnp_SecureHash = req.getParameter("vnp_SecureHash");
            if (fields.containsKey("vnp_SecureHashType")) {
                fields.remove("vnp_SecureHashType");
            }
            if (fields.containsKey("vnp_SecureHash")) {
                fields.remove("vnp_SecureHash");
            }

            //check sum
            String signValue = Config.hashAllFields(fields);
            if (signValue.equals(vnp_SecureHash)) {

                String orderCode = req.getParameter("vnp_TxnRef");
                Order o = OrderDAO.getOrderByCode(orderCode);
                double amount = Double.parseDouble(req.getParameter("vnp_Amount"));

                boolean checkOrderId = (o != null); // vnp_TxnRef exists in your database
                if (checkOrderId) {
                    boolean checkAmount = (amount == o.getTotal() * 100); // vnp_Amount is valid (Check vnp_Amount VNPAY returns compared to the amount of the code (vnp_TxnRef) in the Your database).
                    if (checkAmount) {
                        boolean checkOrderStatus = (!o.isPayment()); // PaymnentStatus = 0 (pending)
                        if (checkOrderStatus) {
                            if ("00".equals(req.getParameter("vnp_ResponseCode"))) {
                                o.setPayment(true);
                                OrderDAO.updateOrder(o);
                                req.setAttribute("mess", "Payment is successful, the product will be delivered to you in the nearest time!!");
                                //Here Code update PaymnentStatus = 1 into your Database
                            } else {
                                req.setAttribute("mess", "Payment failed, please pay again!!");
                                // Here Code update PaymnentStatus = 2 into your Database
                            }
                            out.print("{\"RspCode\":\"00\",\"Message\":\"Confirm Success\"}");
                        } else {
                            req.setAttribute("mess", "Order already confirmed!!");
                            out.print("{\"RspCode\":\"02\",\"Message\":\"Order already confirmed\"}");
                        }
                    } else {
                        req.setAttribute("mess", "Invalid Amount!!");
                        out.print("{\"RspCode\":\"04\",\"Message\":\"Invalid Amount\"}");
                    }
                } else {
                    req.setAttribute("mess", "Order not Found!!");
                    out.print("{\"RspCode\":\"01\",\"Message\":\"Order not Found\"}");
                }
            } else {
                req.setAttribute("mess", "Invalid Checksum!!");
                out.print("{\"RspCode\":\"97\",\"Message\":\"Invalid Checksum\"}");
            }
        } catch (Exception e) {
            req.setAttribute("mess", "Unknow error!!");
            out.print("{\"RspCode\":\"99\",\"Message\":\"Unknow error\"}");
        } finally {
            String orderCode = req.getParameter("vnp_TxnRef");
            Order o = OrderDAO.getOrderByCode(orderCode);
            Customer c = CustomerDAO.getCustomerbyID(o.getCustomerID());

            String id = "ThiBiViSi";
            String sender = "anhyc88@gmail.com";
            String receiver = c.getEmail();
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
                    + "                    <h2 style=\"color: #5eff00;\"><strong>You have successfully paid for your order.</strong></h2>                    \n"
                    + "                </div>                \n"
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

            req.getRequestDispatcher("FrontEnd/vnpay_return.jsp").forward(req, resp);
        }
    }

}
