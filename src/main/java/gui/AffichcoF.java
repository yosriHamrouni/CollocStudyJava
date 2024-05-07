package gui;

import entities.Coworking;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.CheckBox;
import javafx.scene.control.ScrollPane;
import javafx.scene.control.TextField;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;
import javafx.stage.Stage;
import services.ServiceCoworking;


import java.io.IOException;
import java.net.URL;
import java.sql.SQLException;
import java.util.List;
import java.util.ResourceBundle;
import java.util.stream.Collectors;

public class AffichcoF implements Initializable {

    private ObservableList<Coworking> coworkingList;
    private final int itemsPerPage = 6; // Nombre d'éléments par page
    private int currentPage = 0; // Page actuelle

    @FXML
    private Button suivant;
    @FXML
    private Button precedente;

    @FXML
    private ScrollPane scroll;
    @FXML
    private Button filtreButton;

    private int totalPages; // Nombre total de pages
    @FXML
    private CheckBox tarifsCheckBox;

    @FXML
    private CheckBox nomCoworkingCheckBox;
    @FXML
    private TextField cherchField;
    private ServiceCoworking servicecoworking;
    private ObservableList<Coworking> listeFiltree;
    @FXML
    private CheckBox adressecheck;
    @FXML
    private CheckBox nomcocheck;
    @FXML
    private Button trier;



    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        try {
            // Initialisation des données de coworking
            ServiceCoworking serviceCoworking = new ServiceCoworking();
            coworkingList = FXCollections.observableArrayList(serviceCoworking.afficher());
            listeFiltree = FXCollections.observableArrayList(coworkingList);

            // Configuration de la fonction de recherche
            cherchField.textProperty().addListener((observable, oldValue, newValue) -> {
                filtrerCoworking(newValue);
            });

            // Affichage initial
            afficherPage(currentPage);
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    // Méthode pour afficher les éléments de la page spécifiée
    private void afficherPage(int page) {
        try {
            // Créer un VBox pour contenir tous les éléments Item
            VBox mainVBox = new VBox();
            mainVBox.setSpacing(20.0); // Espacement vertical entre les lignes

            // Calculer l'index de début et de fin de la page
            int startIndex = page * itemsPerPage;
            int endIndex = Math.min(startIndex + itemsPerPage, listeFiltree.size());

            // Créer une HBox pour chaque ligne d'éléments
            HBox hBox = new HBox();
            hBox.setSpacing(10); // Espacement horizontal entre les éléments

            // Ajouter chaque paire d'éléments à une ligne dans la HBox
            for (int i = startIndex; i < endIndex; i++) {
                Coworking coworking = listeFiltree.get(i); // Utiliser listeFiltree
                FXMLLoader loader = new FXMLLoader(getClass().getResource("../Items.fxml"));
                Node itemNode = loader.load();
                IteamController controller = loader.getController();
                controller.setData(coworking, null); // Utiliser coworking

                // Ajouter l'élément à la ligne actuelle
                hBox.getChildren().add(itemNode);

                // Si la ligne est pleine (3 éléments), l'ajouter au VBox principal et créer une nouvelle ligne
                if (hBox.getChildren().size() == 3) {
                    mainVBox.getChildren().add(hBox);
                    hBox = new HBox();
                    hBox.setSpacing(20.0); // Réinitialiser l'espacement horizontal pour la nouvelle ligne
                }
            }

            // Ajouter la dernière ligne si elle n'est pas pleine
            if (!hBox.getChildren().isEmpty()) {
                mainVBox.getChildren().add(hBox);
            }

            // Définir le contenu du ScrollPane comme le VBox principal
            scroll.setContent(mainVBox);

            System.out.println("Chargement de la page " + (page + 1) + " terminé avec succès.");

        } catch (IOException e) {
            e.printStackTrace();
        }

    }

    @FXML
    void onclickprecedente(ActionEvent event) {
        if (currentPage > 0) {
            currentPage--;
            afficherPage(currentPage);
        }
    }

    @FXML
    public void onclicksuivant(ActionEvent actionEvent) {
        int totalPages = (int) Math.ceil((double) coworkingList.size() / itemsPerPage);
        if (currentPage < totalPages - 1) {
            currentPage++;
            afficherPage(currentPage);
        }
    }
   

    public void setCoworkingList(ObservableList<Coworking> coworkingList) {
        this.coworkingList = coworkingList;
    }

    private void filtrerCoworking(String searchText) {
        listeFiltree.clear();
        String searchTextLower = searchText.toLowerCase();
        ObservableList<Coworking> filteredList = FXCollections.observableArrayList();
        for (Coworking coworking : coworkingList) {
            String nom = coworking.getNomco().toLowerCase();
            String adresse = coworking.getAdresse().toLowerCase();
            if (nomcocheck.isSelected() && nom.contains(searchTextLower)) {
                filteredList.add(coworking);
            } else if (adressecheck.isSelected() && adresse.contains(searchTextLower)) {
                filteredList.add(coworking);
            }
        }
        listeFiltree.setAll(filteredList);
        afficherPage(0); // Afficher la première page des résultats filtrés
    }
    @FXML
    void OnclickTrier(ActionEvent event) throws SQLException {
        ServiceCoworking serviceCoworking = new ServiceCoworking();
        List<Coworking> coworkingListTrie = serviceCoworking.trierParTarifs(coworkingList);
        // Mettre à jour la liste filtrée et afficher la première page
        listeFiltree.setAll(coworkingListTrie);
        afficherPage(0);

    }


    public void AfficherLog(ActionEvent actionEvent) {
        try {
            // Load the AjouterCoworking.fxml file
            Parent root = FXMLLoader.load(getClass().getResource("../AfficherLogF.fxml"));

            // Show the scene containing the AjouterCoworking.fxml file
            Stage stage = new Stage();
            stage.setScene(new Scene(root));
            stage.show();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public void AfficherLogF(ActionEvent actionEvent) {
        try {
            // Load the AjouterCoworking.fxml file
            Parent root = FXMLLoader.load(getClass().getResource("../AfficherLogF.fxml"));

            // Show the scene containing the AjouterCoworking.fxml file
            Stage stage = new Stage();
            stage.setScene(new Scene(root));
            stage.show();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
