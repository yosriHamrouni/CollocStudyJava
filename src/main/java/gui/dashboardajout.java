package gui;

import javafx.beans.property.SimpleFloatProperty;
import javafx.beans.property.SimpleIntegerProperty;
import javafx.beans.property.SimpleStringProperty;
import javafx.event.ActionEvent;
import javafx.scene.control.*;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.TableView;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.stage.Stage;
import entities.logement;
import javafx.stage.StageStyle;
import services.ServiceLogement;

import java.io.File;
import java.io.IOException;
import java.net.URL;
import java.sql.SQLException;
import java.util.List;
import java.util.ResourceBundle;



public class dashboardajout implements Initializable {

    @FXML
    private TableColumn<logement, String> adresseCol;

    @FXML
    private Button cancelButton;
    @FXML
    private Button typebutton;
    @FXML
    private ImageView img;

    @FXML
    private TableColumn<logement, String> descriptionCol;

    @FXML
    private TableColumn<logement, Integer> idCol;

    @FXML
    private TableColumn<logement, String> imageCol;


    @FXML
    private TableView<logement> devisTableView;

    @FXML
    private TableColumn<logement, Integer> dispoCol;

    @FXML
    private TableColumn<logement, Float> tarifsCol;

    @FXML
    private Button supprimerButton;

    public void refreshList() {
        try {
            populateTableView();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    @FXML
    void cancelButtonOnAction(ActionEvent event) {
        Stage stage = (Stage) cancelButton.getScene().getWindow();
        stage.close();
    }

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        try {
            populateTableView();
        } catch (SQLException e) {
            e.printStackTrace();
        }

    }

    private void afficherImageLogement(logement selectedLogement) {
        String imagePath = selectedLogement.getImage();
        if (imagePath != null && !imagePath.isEmpty()) {
            File file = new File(imagePath);
            Image image = new Image(file.toURI().toString());
            img.setImage(image);
        } else {
            // Afficher une image par défaut si aucune image n'est disponible
            // logementImageView.setImage(...);
        }
    }

    public void populateTableView() throws SQLException {
        // Retrieve data from the database
        ServiceLogement serviceLogement = new ServiceLogement();
        List<logement> logementList = serviceLogement.afficher();

        // Clear existing items in the TableView
        devisTableView.getItems().clear();


        adresseCol.setCellValueFactory(cellData -> new SimpleStringProperty(cellData.getValue().getAdresse()));
        descriptionCol.setCellValueFactory(cellData -> new SimpleStringProperty(cellData.getValue().getDescription()));
        imageCol.setCellValueFactory(cellData -> new SimpleStringProperty(cellData.getValue().getImage()));
        dispoCol.setCellValueFactory(cellData -> new SimpleIntegerProperty(cellData.getValue().getDispo()).asObject());
        tarifsCol.setCellValueFactory(cellData ->new SimpleFloatProperty(cellData.getValue().getTarifs()).asObject());

        // Add the retrieved data to the TableView
        devisTableView.getItems().addAll(logementList);
        //add
        devisTableView.setOnMouseClicked(event -> {
            if (event.getClickCount() == 2) { // Double-click detected
                logement selectedLogement = devisTableView.getSelectionModel().getSelectedItem();
                if (selectedLogement != null) {
                    int logementId = selectedLogement.getId();
                    if (selectedLogement != null) {
                        // Navigate to UpdateUser.fxml
                        navigateToUpdateLogement(selectedLogement);
                    }
                }
            }
        });
    }



    private void navigateToUpdateLogement(logement logement) {
        try {
            // Charger le fichier FXML de la fenêtre de mise à jour
            FXMLLoader loader = new FXMLLoader(getClass().getResource("../ModifierLogement.fxml"));
            Parent root = loader.load();

            // Accéder au contrôleur et passer le logement sélectionné à celui-ci
            ModifierLogement controller = loader.getController();
            controller.initData(logement);

            // Afficher la scène contenant le fichier FXML de la mise à jour
            Stage stage = new Stage();
            stage.setScene(new Scene(root));

            // Rafraîchir la TableView lorsque la fenêtre de mise à jour est fermée
            stage.setOnCloseRequest(event -> {
                try {
                    populateTableView(); // Rafraîchir la TableView
                } catch (SQLException e) {
                    e.printStackTrace();
                }
            });
            stage.showAndWait();
            populateTableView();
           /* // Fermer la fenêtre actuelle lorsque la fenêtre de mise à jour est affichée
            Stage currentStage = (Stage) cancelButton.getScene().getWindow();
            currentStage.close();*/

            // Afficher la fenêtre de mise à jour

        } catch (IOException e) {
            e.printStackTrace();
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }

    @FXML
    private void AjoutOne(ActionEvent event) {
        try {
            // Load the AjouterLogement.fxml file
            Parent root = FXMLLoader.load(getClass().getResource("AjouterLogement.fxml"));

            // Show the scene containing the AjouterLogement.fxml file
            Stage stage = new Stage();
            stage.setScene(new Scene(root));
            stage.show();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
    @FXML
    void getReponseDevis(ActionEvent event) throws IOException {
        Parent root = FXMLLoader.load(getClass().getResource("afficherLogement.fxml"));
        Stage pStage= new Stage();
        pStage.initStyle(StageStyle.UNDECORATED);
        pStage.setScene(new Scene(root, 667,556));
        pStage.show();
        Stage stage = (Stage) cancelButton.getScene().getWindow();
        stage.close();
    }
    @FXML
    void ouvrirtypes(ActionEvent event) { {
        ouvrirFenetreT("../AfficherType.fxml", "Types");
    }

    }
    private void ouvrirFenetreT(String fxmlPath, String title) {
        try {
            FXMLLoader loader = new FXMLLoader(getClass().getResource("../AfficherType.fxml"));
            Parent root = loader.load();

            Scene scene = new Scene(root);
            Stage stage = new Stage();
            stage.setScene(scene);
            stage.setTitle(title);
            stage.show();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
    public void ouvrirAjouterLogement(ActionEvent actionEvent) {
        ouvrirFenetre("../AjouterLogement.fxml", "Ajouter");
    }
    private void ouvrirFenetre(String fxmlPath, String title) {
        try {
            FXMLLoader loader = new FXMLLoader(getClass().getResource("../AjouterLogement.fxml"));
            Parent root = loader.load();

            Scene scene = new Scene(root);
            Stage stage = new Stage();
            stage.setScene(scene);
            stage.setTitle(title);
            stage.show();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public void ouvrirModiferLogement(ActionEvent actionEvent) {
        ouvrirFenetre("../ModifierLogement.fxml", "Modifier");

    }
    private void ouvrirModiferLogement(String fxmlPath, String title) {
        try {
            FXMLLoader loader = new FXMLLoader(getClass().getResource("../ModifierLogement.fxml"));
            Parent root = loader.load();

            Scene scene = new Scene(root);
            Stage stage = new Stage();
            stage.setScene(scene);
            stage.setTitle(title);
            stage.show();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
    @FXML
    private void deleteOne(ActionEvent event) {
        // Récupérer le logement sélectionné dans la table
        logement selectedLogement = devisTableView.getSelectionModel().getSelectedItem();
        if (selectedLogement != null) {
            // Afficher une boîte de dialogue de confirmation
            Alert confirmAlert = new Alert(Alert.AlertType.CONFIRMATION);
            confirmAlert.setTitle("Dialogue de Confirmation");
            confirmAlert.setHeaderText("Supprimer logement");
            confirmAlert.setContentText("Êtes-vous sûr de vouloir supprimer ce logement ?");

            // Utiliser une expression lambda pour gérer le choix du logement
            confirmAlert.showAndWait().ifPresent(response -> {
                if (response == ButtonType.OK) {
                    // Logement confirmé, procéder à la suppression
                    try {
                        // Appeler la méthode deleteOne avec le logement sélectionné
                        ServiceLogement serviceLogement = new ServiceLogement();
                        serviceLogement.supprimer(selectedLogement);
                        // Afficher un message de réussite
                        Alert successAlert = new Alert(Alert.AlertType.INFORMATION);
                        successAlert.setTitle("Suppression Réussie");
                        successAlert.setHeaderText(null);
                        successAlert.setContentText("Logement supprimé avec succès.");
                        successAlert.showAndWait();
                        // Rafraîchir la table après la suppression
                        populateTableView();
                    } catch (SQLException ex) {
                        // Afficher un message d'erreur si la suppression échoue
                        Alert errorAlert = new Alert(Alert.AlertType.ERROR);
                        errorAlert.setTitle("Erreur de Suppression");
                        errorAlert.setHeaderText(null);
                        errorAlert.setContentText("Erreur lors de la suppression du logement : " + ex.getMessage());
                        errorAlert.showAndWait();
                    }
                }
            });
        } else {
            // Afficher une alerte si aucun logement n'est sélectionné
            Alert alert = new Alert(Alert.AlertType.WARNING);
            alert.setTitle("Aucune Sélection");
            alert.setHeaderText(null);
            alert.setContentText("Veuillez sélectionner un logement à supprimer.");
            alert.showAndWait();
        }
    }
}
