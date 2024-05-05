package gui;

import javafx.fxml.FXML;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.scene.control.TextArea;
import javafx.scene.control.TextField;
import javafx.stage.Stage;

import java.util.Random;

public class ChatBot {

    @FXML
    private TextField userInputField;
    @FXML
    private TextArea chatArea;
    @FXML
    private Button sendButton;

    // Define predefined questions and responses
    private final String[] predefinedQuestions = {
            "Quels sont les documents nécessaires pour louer un logement ?",
            "Quel est le mode de paiement accepté pour la location ?",
            "Est-ce que les animaux de compagnie sont autorisés dans le logement ?",
            "Y a-t-il des places de parking disponibles avec le logement ?",
            "Quels sont les équipements inclus dans le logement ?"
    };

    private final String[] predefinedResponses = {
            "Les documents nécessaires pour louer un logement sont la pièce d'identité, le justificatif de revenu et un dossier de garant.",
            "Le mode de paiement accepté pour la location est le virement bancaire.",
            "Oui, les animaux de compagnie sont autorisés dans le logement, mais un dépôt de garantie supplémentaire peut être requis.",
            "Oui, des places de parking sont disponibles avec le logement, avec un nombre limité de places par locataire.",
            "Les équipements inclus dans le logement comprennent une cuisine équipée, une connexion Internet haut débit et une machine à laver."
    };

    private final Random random = new Random();

    @FXML
    void initialize() {
        // Display the introductory message from the chatbot
        chatArea.appendText("Bonjour! Je suis Chatbot. Comment puis-je vous aider aujourd'hui?\n");
    }

    @FXML
    void sendMessage() {
        // Get user input
        String userMessage = userInputField.getText().trim();
        // Display user input in the chat area
        chatArea.appendText("Vous: " + userMessage + "\n");

        // Check if the user's message matches any predefined question
        boolean matched = false;
        for (int i = 0; i < predefinedQuestions.length; i++) {
            if (userMessage.equalsIgnoreCase(predefinedQuestions[i])) {
                // Display predefined response
                chatArea.appendText("Chatbot: " + predefinedResponses[i] + "\n");
                matched = true;
                break;
            }
        }

        // If the user's message doesn't match any predefined question, respond randomly
        if (!matched) {
            int randomIndex = random.nextInt(predefinedQuestions.length);
            chatArea.appendText("Chatbot: " + predefinedResponses[randomIndex] + "\n");
        }

        // Clear user input field
        userInputField.clear();
    }

    @FXML
    void onClose() {
        // Create and show an alert message
        Alert alert = new Alert(Alert.AlertType.INFORMATION);
        alert.setTitle("Chatbot");
        alert.setHeaderText(null);
        alert.setContentText("Merci d'avoir utilisé Chatbot!");
        alert.showAndWait();
        // Close the popup window
        Stage stage = (Stage) sendButton.getScene().getWindow();
        stage.close();
    }
}
