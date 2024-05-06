package controllers;

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
            emailContent.append("<html><body style='font-family: Arial, sans-serif;'>");
            emailContent.append("<div style='text-align: center;'>");
            emailContent.append("<img src='https://i.ibb.co/nL8W7jS/logo-v2.png' alt='Logo' style='width: 100px; height: 100px;'>");
            emailContent.append("<h1 style='color: #007bff;'>ColocStudy</h1>");
            emailContent.append("<p style='font-size: 16px;'>Service Offres d'emplois</p>");
            emailContent.append("</div>");
            emailContent.append("<p>Cher Client,</p>");
            emailContent.append("<p>Voici les détails de l'offre :</p>");
            emailContent.append("<p><strong>Description:</strong> ").append(offres.getDescrip()).append("</p>");
            emailContent.append("<p><strong>Salaire:</strong> ").append(offres.getSalaire()).append(" DT</p>");
            emailContent.append("<p><strong>Lieu:</strong> ").append(offres.getLieu()).append("</p>");
            emailContent.append("<p><strong>Horaire début:</strong> ").append(offres.getHorairedeb()).append("</p>");
            emailContent.append("<p><strong>Horaire fin:</strong> ").append(offres.getHoraireter()).append("</p>");
            emailContent.append("<p><strong>Numéro de tel:</strong> ").append(offres.getNum_tel()).append("</p>");
            emailContent.append("</body></html>");

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
