package ekyss.model;

import javax.mail.*;
import javax.mail.internet.InternetAddress;
import javax.mail.internet.MimeMessage;
import java.util.Properties;

/**
 *  Denna klass sköter vidaresändning av mail.
 */
public class MailHandler {
    /**
     * Skickar ett lösenord för en användare till en specifik e-post adress.
     * @param recipient E-post adressen mailet ska skickas till.
     * @param payload Lösenordet.
     */
    public static void sendPassword(String recipient, String payload) {
        MailHandler.sendMail("puspekyss@gmail.com", "etsf202017", recipient, payload);
    }

    /**
     * Skickar ett mail till en användare.
     * @param username E-post adress som mailet ska skickas ifrån.
     * @param password Lösenord till e-post adressen.
     * @param to E-post adress som mailet ska skickats till.
     * @param payload Meddelande.
     */
    public static void sendMail(final String username, final String password, String to, String payload) {
        String from = "puspekyss@gmail.com";
        String host = "smtp.gmail.com";
        Properties props = new Properties();

        props.put("mail.smtp.auth", "true");
        props.put("mail.smtp.starttls.enable", "true");
        props.put("mail.smtp.host", host);
        props.put("mail.smtp.port", "587");
        Session session = Session.getInstance(props,
                new javax.mail.Authenticator() {
                    protected PasswordAuthentication getPasswordAuthentication() {
                        return new PasswordAuthentication(username, password);
                    }
                });

        try {
            Message message = new MimeMessage(session);
            // Set From: header field of the header.
            message.setFrom(new InternetAddress(from));
            message.setRecipients(Message.RecipientType.TO,
                    InternetAddress.parse(to));
            message.setSubject("Lösenord");
            message.setText("Nedan följer lösenordet skapat för ditt användarkonto. \n" + payload);
            // Send message
            Transport.send(message);
            System.out.println("Emailsändning lyckades");
        } catch (MessagingException e) {
            throw new RuntimeException(e);
        }
    }
}
