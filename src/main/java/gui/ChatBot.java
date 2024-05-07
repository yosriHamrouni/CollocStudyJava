package gui;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.*;
import javafx.stage.Stage;

public class ChatBot {

    @FXML
    private TextField userInputField;
    @FXML
    private ComboBox<?> questionComboBox;
    @FXML
    private TextArea chatArea;
    @FXML
    private Button sendButton;

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
        chatArea.appendText("Vous: ");
        chatArea.appendText(userMessage + "\n");
        chatArea.getStyleClass().add("user-message");

        // Respond based on the user's message
        String response = getResponse(userMessage);
        // Display the response in the chat area
        chatArea.appendText("Chatbot: ");
        chatArea.appendText(response + "\n");
        chatArea.getStyleClass().add("chatbot-response");

        // Clear user input field
        userInputField.clear();
    }



    private String getResponse(String userMessage) {
        // Normalize user message
        userMessage = userMessage.toLowerCase();

        // Determine the response based on the user's message
        if (userMessage.contains("bonjour") || userMessage.contains("salut") || userMessage.contains("hi") || userMessage.contains("bj") || userMessage.contains("coucou") || userMessage.contains("merci")) {
            return "Bonjour! Comment puis-je vous aider aujourd'hui?";
        } else if (userMessage.contains("merci")) {
            return "De rien! N'hésitez pas si vous avez d'autres questions.";
        } else {
            // If the message does not match any specific cases, treat it as a question
            return getResponseToQuestion(userMessage);
        }
    }

    private String getResponseToQuestion(String question) {
        // Determine the response based on the user's question
        switch (question.toLowerCase()) {
            case "quels sont les documents nécessaires pour louer un logement ?":
                return "Les documents nécessaires pour louer un logement sont la pièce d'identité, le justificatif de revenu et un dossier de garant.";
            case "quel est le mode de paiement accepté pour la location ?":
                return "Le mode de paiement accepté pour la location est le virement bancaire.";
            case "est-ce que les animaux de compagnie sont autorisés dans le logement ?":
                return "Oui, les animaux de compagnie sont autorisés dans le logement, mais un dépôt de garantie supplémentaire peut être requis.";
            case "y a-t-il des places de parking disponibles avec le logement ?":
                return "Oui, des places de parking sont disponibles avec le logement, avec un nombre limité de places par locataire.";
            case "quels sont les équipements inclus dans le logement ?":
                return "Les équipements inclus dans le logement comprennent une cuisine équipée, une connexion Internet haut débit et une machine à laver.";
            case "quelle est la durée minimale du bail ?":
                return "La durée minimale du bail est de 12 mois.";
            case "est-il possible de sous-louer le logement ?":
                return "La sous-location du logement n'est pas autorisée sans consentement préalable du propriétaire.";
            default:
                // Respond with a generic message for unknown questions
                return "Je suis désolé, je ne comprends pas votre question.";
        }
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

    public void selectQuestion(ActionEvent actionEvent) {

            // Get the selected question from the ComboBox
            String selectedQuestion = (String) questionComboBox.getValue();

            // Set the selected question as the user input
            userInputField.setText(selectedQuestion);

            // Call sendMessage() to process the selected question and get the response
            sendMessage();
        }

    }

