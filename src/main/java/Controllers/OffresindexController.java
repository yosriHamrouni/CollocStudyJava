package Controllers;

import entities.Offres;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Node;
import javafx.scene.control.ScrollPane;
import javafx.scene.control.TextField;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;
import services.ServiceOffres;

import java.io.IOException;
import java.net.URL;
import java.sql.SQLException;
import java.util.List;
import java.util.ResourceBundle;

public class OffresindexController implements Initializable {

    @FXML
    private ScrollPane scroll;
    private int pageSize = 6; // Nombre d'offres par page
    private int currentPage = 0; // Index de la page actuelle

    @FXML
    private TextField DescripField;

    @FXML
    private TextField lieuField;

    @FXML
    private TextField salaireField;

    // Déclaration de la liste d'offres
    private ObservableList<Offres> offresList = FXCollections.observableArrayList();


    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        try {
            // Récupérer la liste des offres depuis la base de données
            ServiceOffres serviceOffres = new ServiceOffres();
            offresList = FXCollections.observableArrayList(serviceOffres.afficher()); // Utilisation de la liste observable définie au niveau de la classe

            // Afficher la première page des offres
            afficherOffresPage(currentPage);
            // Ajouter des écouteurs aux champs de texte pour la recherche dynamique
            DescripField.textProperty().addListener((observable, oldValue, newValue) -> filtrerOffres());
            lieuField.textProperty().addListener((observable, oldValue, newValue) -> filtrerOffres());
            salaireField.textProperty().addListener((observable, oldValue, newValue) -> filtrerOffres());
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    // Méthode pour afficher les offres de la page actuelle
    private void afficherOffresPage(int page) {
        // Calculer l'index de début et de fin des offres pour la page spécifiée
        int startIndex = page * pageSize;
        int endIndex = Math.min(startIndex + pageSize, offresList.size());

        // Créer un VBox pour contenir les deux blocs horizontaux
        VBox pageVBox = new VBox();
        pageVBox.setSpacing(20.0);

        // Créer deux HBox pour les deux blocs horizontaux
        HBox firstRow = new HBox();
        firstRow.setSpacing(20.0);

        HBox secondRow = new HBox();
        secondRow.setSpacing(20.0);

        // Ajouter chaque paire d'éléments à la VBox de la page
        for (int i = startIndex; i < endIndex; i++) {
            try {
                FXMLLoader loader = new FXMLLoader(getClass().getResource("../offresFxml/itemoffre.fxml"));
                Node itemNode = loader.load();
                itemoffreControlleur controller = loader.getController();
                controller.setData(offresList.get(i));

                // Ajouter les trois premières offres à la première ligne et les trois suivantes à la deuxième ligne
                if (i < startIndex + 3) {
                    firstRow.getChildren().add(itemNode);
                } else {
                    secondRow.getChildren().add(itemNode);
                }
            } catch (IOException e) {
                e.printStackTrace();
            }
        }

        // Ajouter les deux blocs horizontaux à la VBox
        pageVBox.getChildren().addAll(firstRow, secondRow);

        // Définir le contenu du ScrollPane comme la VBox de la page actuelle
        scroll.setContent(pageVBox);

        System.out.println("Chargement de la page " + (page + 1) + " des offres.");
    }

    // Méthode pour passer à la page précédente
    @FXML
    public void previousPage() {
        if (currentPage > 0) {
            currentPage--;
            afficherOffresPage(currentPage);
        }
    }

    // Méthode pour passer à la page suivante
    @FXML
    public void nextPage() {
        int totalPages = (int) Math.ceil((double) offresList.size() / pageSize);
        if (currentPage < totalPages - 1) {
            currentPage++;
            afficherOffresPage(currentPage);
        }
    }

    @FXML
    private void filtrerOffres() {
        // Récupérer les valeurs saisies par l'utilisateur
        String lieu = lieuField.getText().trim();
        double salaireMin = !salaireField.getText().isEmpty() ? Double.parseDouble(salaireField.getText()) : 0.0;
        String description = DescripField.getText().trim();

        try {
            // Récupérer la liste des offres filtrées depuis le service
            ServiceOffres serviceOffres = new ServiceOffres();
            List<Offres> offresFiltrees = serviceOffres.filtrerOffres(lieu, salaireMin, description);

            // Mettre à jour la liste des offres à afficher
            offresList.setAll(offresFiltrees);

            // Afficher la première page des offres filtrées
            currentPage = 0;
            afficherOffresPage(currentPage);
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

}
