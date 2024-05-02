package gui;

import javafx.fxml.FXML;
import javafx.scene.Node;
import javafx.scene.control.ScrollPane;
import javafx.scene.layout.FlowPane;
import javafx.fxml.FXMLLoader;
import javafx.scene.control.Alert;
import services.ServiceLogement;
import entities.logement;

import java.io.IOException;
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
    private void initialize() {
        loadLogement(); // Charge tous les logements initialement
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
}
