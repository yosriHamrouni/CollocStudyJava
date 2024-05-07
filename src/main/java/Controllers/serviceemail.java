package Controllers;

import entities.Offres;
import javax.mail.*;
import javax.mail.internet.InternetAddress;
import javax.mail.internet.MimeMessage;
import java.util.Properties;

public class serviceemail {

    public static void sendEmail(String recipientEmail, Offres offres) {
        final String username = "skandernacheb@gmail.com";
        final String password = "feqg angt voek kdeh";

        // Setup mail server properties
        Properties props = new Properties();
        props.put("mail.smtp.auth", "true");
        props.put("mail.smtp.starttls.enable", "true");
        props.put("mail.smtp.host", "smtp.gmail.com");
        props.put("mail.smtp.port", "587");

        // Create a session with authentication
        Session session = Session.getInstance(props, new javax.mail.Authenticator() {
            protected PasswordAuthentication getPasswordAuthentication() {
                return new PasswordAuthentication(username, password);
            }
        });

        try {
            // Create a default MimeMessage object
            Message message = new MimeMessage(session);

            // Set From: header field of the header
            message.setFrom(new InternetAddress(username));

            // Set To: header field of the header
            message.setRecipients(Message.RecipientType.TO, InternetAddress.parse(recipientEmail));

            // Construct email content with offer details
            StringBuilder emailContent = new StringBuilder();
            emailContent.append("<html><head><style>");
            emailContent.append("body { font-family: Arial, sans-serif; background-color: #f5f5f5; margin: 0; padding: 0; }");
            emailContent.append(".container { max-width: 600px; margin: auto; padding: 20px; }");
            emailContent.append(".header { background-color: #007bff; color: #fff; padding: 10px; text-align: center; }");
            emailContent.append(".content { padding: 20px; background-color: #fff; }");
            emailContent.append("</style></head><body>");
            emailContent.append("<div class='container'>");
            emailContent.append("<div class='header'><img src='https://i.ibb.co/nL8W7jS/logo-v2.png' alt='Logo' style='width: 100px; height: 100px;'><h1>ColocStudy</h1></div>");
            emailContent.append("<div class='content'>");
            emailContent.append("<p>Cher ColocStudent,</p>");
            emailContent.append("<p>Voici les détails de l'offre :</p>");
            emailContent.append("<p><strong>Description:</strong> ").append(offres.getDescrip()).append("</p>");
            emailContent.append("<p><strong>Salaire:</strong> ").append(offres.getSalaire()).append(" DT</p>");
            emailContent.append("<p><strong>Lieu:</strong> ").append(offres.getLieu()).append("</p>");
            emailContent.append("<p><strong>Horaire début:</strong> ").append(offres.getHorairedeb()).append("</p>");
            emailContent.append("<p><strong>Horaire fin:</strong> ").append(offres.getHoraireter()).append("</p>");
            emailContent.append("<p><strong>Numéro de tel:</strong> ").append(offres.getNum_tel()).append("</p>");
            emailContent.append("</div></div></body></html>");

            // Set email content
            message.setContent(emailContent.toString(), "text/html");

            // Set email subject
            message.setSubject("Nouvelle Offre d'Emploi");

            // Send message
            Transport.send(message);

            System.out.println("Email sent successfully...");

        } catch (MessagingException e) {
            throw new RuntimeException(e);
        }
    }
}
