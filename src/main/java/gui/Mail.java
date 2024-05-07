package gui;

import entities.logement;
import javax.mail.*;
import javax.mail.internet.InternetAddress;
import javax.mail.internet.MimeMessage;
import java.util.Properties;

public class Mail {

    public static void sendEmail(String recipientEmail, logement logement) {
        final String username = "nourchehida3@gmail.com";
        final String password = "Skandoura13102015";

        // Setup mail server properties
        Properties props = new Properties();
        props.put("mail.smtp.auth", "true");
        props.put("mail.smtp.starttls.enable", "true");
        props.put("mail.smtp.host", "smtp.gmail.com");
        props.put("mail.smtp.port", "587");

        // Create a session with authentication
        Session session = Session.getInstance(props, new javax.mail.Authenticator() {
            protected PasswordAuthentication getMailPasswordAuthentication() {
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

            // Construct email content with logement details
            StringBuilder emailContent = new StringBuilder();
            emailContent.append("<html><body style='font-family: Arial, sans-serif;'>");
            emailContent.append("<div style='text-align: center;'>");
            emailContent.append("<img src='https://i.ibb.co/nL8W7jS/logo-v2.png' alt='Logo' style='width: 100px; height: 100px;'>");
            emailContent.append("<h1 style='color: #007bff;'>ColocStudy</h1>");
            emailContent.append("<p style='font-size: 16px;'>Service Logements</p>");
            emailContent.append("</div>");
            emailContent.append("<p>Cher Client,</p>");
            emailContent.append("<p>Voici les détails du logement :</p>");
            emailContent.append("<p><strong>Description:</strong> ").append(logement.getDescription()).append("</p>");
            emailContent.append("<p><strong>Adresse:</strong> ").append(logement.getAdresse()).append("</p>");
            emailContent.append("<p><strong>Équipement:</strong> ").append(logement.getEquipement()).append("</p>");
            emailContent.append("<p><strong>Image:</strong> ").append(logement.getImage()).append("</p>");
            emailContent.append("<p><strong>Disponibilité:</strong> ").append(logement.getDispo()).append("</p>");
            emailContent.append("<p><strong>Tarifs:</strong> ").append(logement.getTarifs()).append(" DT</p>");
            emailContent.append("</body></html>");

            // Set email content
            message.setContent(emailContent.toString(), "text/html");

            // Set email subject
            message.setSubject("Nouveau Logement Disponible");

            // Send message
            Transport.send(message);

            System.out.println("Email sent successfully...");

        } catch (MessagingException e) {
            throw new RuntimeException(e);
        }
    }
}