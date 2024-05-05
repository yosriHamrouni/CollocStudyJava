package gui;

import com.sun.javafx.collections.ElementObservableListDecorator;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.collections.transformation.SortedList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.ScrollPane;
import javafx.scene.control.TextField;
import javafx.scene.layout.FlowPane;
import javafx.fxml.FXMLLoader;
import javafx.scene.control.Alert;
import javafx.stage.Modality;
import javafx.stage.Stage;
import services.ServiceLogement;
import entities.logement;

import java.io.IOException;
import java.sql.SQLException;
import java.util.List;
import java.util.function.Predicate;
import java.util.stream.Collectors;

public class AfficherLogF {

    @FXML
    private ScrollPane scroll;

    @FXML
    private FlowPane logementsFlowPane;

    private final ServiceLogement serviceLogement = new ServiceLogement();

    private List<logement> allLogements; // Liste de tous les logements
    private static final int pageSize = 2; // Nombre de logements par page
    private int currentPage = 0; // Page actuelle
    @FXML
    private TextField filtrefield;
    private List<logement> listF;

    @FXML
    private void initialize() {
        loadLogement();


        // Charge tous les logements initialement
    }

    private void loadLogement() {
        try {
            allLogements = serviceLogement.afficher();
            updatePage(currentPage);
        } catch (Exception e) {
            e.printStackTrace();
            showAlert("Erreur", "Une erreur s'est produite lors du chargement des logements.");
        }
    }


    private void updatePage(int page) {
        logementsFlowPane.getChildren().clear();
        int startIndex = page * pageSize;
        int endIndex = Math.min(startIndex + pageSize, allLogements.size());

        for (int i = startIndex; i < endIndex; i++) {
            try {
                FXMLLoader loader = new FXMLLoader(getClass().getResource("../CardV.fxml"));
                Node card = loader.load();
                CardV controller = loader.getController();
                controller.setLogement(allLogements.get(i));
                controller.setAfficherLogFController(this);
                logementsFlowPane.getChildren().add(card);
            } catch (IOException e) {
                e.printStackTrace();
                showAlert("Erreur", "Une erreur s'est produite lors de la création de la carte de logement.");
            }
        }
    }

    @FXML
    public void previousPage() {
        if (currentPage > 0) {
            currentPage--;
            updatePage(currentPage);
        }
    }

    @FXML
    public void nextPage() {
        int totalPages = (int) Math.ceil((double) allLogements.size() / pageSize);
        if (currentPage < totalPages - 1) {
            currentPage++;
            updatePage(currentPage);
        }
    }

    private void showAlert(String title, String content) {
        Alert alert = new Alert(Alert.AlertType.ERROR);
        alert.setTitle(title);
        alert.setContentText(content);
        alert.showAndWait();
    }

    public void openChatbotPopup(javafx.event.ActionEvent actionEvent) {
        try {
            // Load the FXML file for the chatbot popup
            FXMLLoader loader = new FXMLLoader(getClass().getResource("../ChatbotPopup.fxml"));
            Parent root = loader.load();

            // Create a new stage for the chatbot popup
            Stage stage = new Stage();
            stage.initModality(Modality.APPLICATION_MODAL);
            stage.setTitle("Chatbot");
            stage.setScene(new Scene(root));

            // Show the chatbot popup
            stage.showAndWait();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
    @FXML
    private void filterTableView() {
        String filterText = filtrefield.getText().toLowerCase().trim(); // Obtenir le texte de filtrage et le convertir en minuscules

        // Filtrer la liste des logements en fonction du texte de filtrage
        listF = allLogements.stream()
                .filter(logement -> logement.getDescription().toLowerCase().contains(filterText) || logement.getAdresse().toLowerCase().contains(filterText))
                .collect(Collectors.toList());

        updatePageFiltered(0); // Mettre à jour la page avec les logements filtrés
    }

    private void updatePageFiltered(int page) {
        logementsFlowPane.getChildren().clear();
        int startIndex = page * pageSize;
        int endIndex = Math.min(startIndex + pageSize, listF.size());

        for (int i = startIndex; i < endIndex; i++) {
            try {
                FXMLLoader loader = new FXMLLoader(getClass().getResource("../CardV.fxml"));
                Node card = loader.load();
                CardV controller = loader.getController();
                controller.setLogement(listF.get(i));
                controller.setAfficherLogFController(this);
                logementsFlowPane.getChildren().add(card);
            } catch (IOException e) {
                e.printStackTrace();
                showAlert("Erreur", "Une erreur s'est produite lors de la création de la carte de logement.");
            }
        }
    }
    public void trierparprix(ActionEvent actionEvent) throws SQLException {
        try {
            // Trier les logements par prix
            List<logement> logementsTries = serviceLogement.trierParTarifs();

            // Mettre à jour la liste de tous les logements avec la liste triée
            allLogements.clear();
            allLogements.addAll(logementsTries);

            // Mettre à jour l'affichage des logements
            updatePage(currentPage);
        } catch (SQLException e) {
            e.printStackTrace();
            // Gérer l'exception
        }
    }
}
