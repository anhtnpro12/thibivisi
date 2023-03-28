/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package controller.Payment;

import static controller.Payment.Mail.sendMail;

import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import java.io.IOException;

/**
 *
 * @author TNA
 */
@WebServlet(name = "SendEmail", value = "/send-email")
public class SendEmail extends HttpServlet {

    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {

        String id, sender, receiver, password, subject, message;

        // check if directed url
        String action = "add";
        String url = "/index.html";
        if (action == null) {
            // directed to email interface
            action = "join";
        }
        if (action.equals("join")) {
            url = "/index.html";
        }
        if (action.equals("add")) {
            // retrieve the entered credentials
            id = "ThiBiViSi";
            sender = "anhyc88@gmail.com";
            receiver = "anhyc12@gmail.com";
            password = "jkbetjophxeigafd";
            subject = "Your order has been successfully placed";
            message = "";
            // get and set String value of email status
            request.setAttribute(
                    "message",
                    sendMail(id, receiver, sender, subject,
                            message, true, password));
            // directed to page showing the status of email
            url = "/ErrorPage.jsp";
        }

        getServletContext()
                .getRequestDispatcher(url)
                .forward(request, response);
    }

}
