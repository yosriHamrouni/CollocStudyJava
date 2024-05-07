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
import java.util.stream.Collectors;

public class FavorisIndexController implements Initializable {

    @FXML
    private ScrollPane scroll;
    @FXML
    private TextField DescripField;

    @FXML
    private TextField lieuField;

    @FXML
    private TextField salaireField;
    private int pageSize = 6; // Nombre d'offres par page
    private int currentPage = 0; // Index de la page actuelle

    // Déclaration de la liste d'offres favoris
    private ObservableList<Offres> favorisList = FXCollections.observableArrayList();

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        // Récupérer la liste des offres mises en favoris depuis la base de données
        ServiceOffres serviceOffres = new ServiceOffres();
        favorisList = FXCollections.observableArrayList(serviceOffres.getOffresFavoris());
        // Ajouter des écouteurs aux champs de texte pour la recherche dynamique
        DescripField.textProperty().addListener((observable, oldValue, newValue) -> filtrerOffres());
        lieuField.textProperty().addListener((observable, oldValue, newValue) -> filtrerOffres());
        salaireField.textProperty().addListener((observable, oldValue, newValue) -> filtrerOffres());

        // Afficher la première page des offres mises en favoris
        afficherFavorisPage(currentPage);
    }

    // Méthode pour afficher les offres de la page actuelle
    // Méthode pour afficher les offres de la page actuelle
    private void afficherFavorisPage(int page) {
        // Calculer l'index de début et de fin des offres pour la page spécifiée
        int startIndex = page * pageSize;
        int endIndex = Math.min(startIndex + pageSize, favorisList.size());

        // Créer un VBox pour contenir les blocs horizontaux
        VBox pageVBox = new VBox();
        pageVBox.setSpacing(20.0);

        // Boucle pour créer des HBox horizontaux contenant chaque groupe de 3 offres
        for (int i = startIndex; i < endIndex; i += 3) {
            HBox groupHBox = new HBox();
            groupHBox.setSpacing(20.0);

            // Ajouter chaque offre du groupe actuel à la HBox horizontale
            for (int j = i; j < Math.min(i + 3, endIndex); j++) {
                try {
                    FXMLLoader loader = new FXMLLoader(getClass().getResource("../offresFxml/itemoffre.fxml"));
                    Node itemNode = loader.load();
                    itemoffreControlleur controller = loader.getController();
                    controller.setData(favorisList.get(j));
                    groupHBox.getChildren().add(itemNode);
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }

            // Ajouter la HBox horizontale (groupe d'offres) au VBox de la page
            pageVBox.getChildren().add(groupHBox);
        }

        // Définir le contenu du ScrollPane comme la VBox de la page actuelle
        scroll.setContent(pageVBox);

        System.out.println("Chargement de la page " + (page + 1) + " des offres mises en favoris.");
    }
    // Méthode pour passer à la page précédente
    @FXML
    public void previousPage() {
        if (currentPage > 0) {
            currentPage--;
            afficherFavorisPage(currentPage);
        }
    }

    // Méthode pour passer à la page suivante
    @FXML
    public void nextPage() {
        int totalPages = (int) Math.ceil((double) favorisList.size() / pageSize);
        if (currentPage < totalPages - 1) {
            currentPage++;
            afficherFavorisPage(currentPage);
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

            // Filtrer les offres filtrées pour ne garder que celles qui sont également mises en favoris
            List<Offres> offresFavorisFiltrees = offresFiltrees.stream()
                    .filter(Offres::getFavoris)
                    .collect(Collectors.toList());

            // Mettre à jour la liste des offres à afficher
            favorisList.setAll(offresFavorisFiltrees);

            // Afficher la première page des offres filtrées
            currentPage = 0;
            afficherFavorisPage(currentPage);
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

}
