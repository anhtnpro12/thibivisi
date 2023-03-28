package controller.Payment;

import java.io.UnsupportedEncodingException;
import java.util.Properties;
import jakarta.mail.Address;
import jakarta.mail.Message;
import jakarta.mail.MessagingException;
import jakarta.mail.Session;
import jakarta.mail.Transport;
import jakarta.mail.internet.InternetAddress;
import jakarta.mail.internet.MimeMessage;

public class Mail {

    public static String sendMail(String id, String to, String from,
                    String subject, String body, boolean content,
                    String password) {
        String status = null;
        try {
            // acquire a secure SMTPs session
            Properties pros = new Properties();
            pros.put("mail.transport.protocol", "smtps");
            pros.put("mail.smtps.host", "smtp.gmail.com");
            pros.put("mail.smtps.port", 465);
            pros.put("mail.smtps.auth", "true");
            pros.put("mail.smtps.quitwait", "false");
            Session session
                    = Session.getDefaultInstance(pros);
            session.setDebug(true);
            // Wrap a message in session
            MimeMessage message = new MimeMessage(session);
            message.setSubject(subject, "UTF-8");

            if (content) {
                message.setContent(body, "text/html; charset=UTF-8");
            } else {
                message.setText(body);
            }
            // specify E-mail address of Sender and Receiver
            Address sender = new InternetAddress(from, id);
            Address receiver = new InternetAddress(to);
            message.setFrom(sender);
            message.setRecipient(Message.RecipientType.TO,
                    receiver);
            // sending an E-mail
            try ( Transport tt = session.getTransport()) {
                // acqruiring a connection to remote server
                tt.connect(from, password);
                tt.sendMessage(message,
                        message.getAllRecipients());
                status = "E-Mail Sent Successfully";
            }
        } catch (MessagingException e) {
            status = e.toString();
        } catch (UnsupportedEncodingException e) {
            status = e.toString();
        }
        // return the status of email
        return status;
    }
}
