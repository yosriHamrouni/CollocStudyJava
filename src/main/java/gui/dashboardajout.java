package gui;

import javafx.beans.property.SimpleFloatProperty;
import javafx.beans.property.SimpleIntegerProperty;
import javafx.beans.property.SimpleStringProperty;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.stage.Stage;
import javafx.stage.StageStyle;
import entities.logement;
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
    private ImageView img;


    @FXML
    private TableColumn<logement, String> descriptionCol;

    @FXML
    private TableColumn<logement, Integer> idColT;

    @FXML
    private TableColumn<logement, String> imageCol;
    @FXML
    private TableColumn<logement, Integer> typeIdCol;

    @FXML
    private TableView<logement> devisTableView;

    @FXML
    private TableColumn<logement, Integer> dispoCol;

    @FXML
    private TableColumn<logement, Float> tarifsCol;

    @FXML
    private Button supprimerButton;

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

        // Setup columns
        adresseCol.setCellValueFactory(new PropertyValueFactory<>("adresse"));
        descriptionCol.setCellValueFactory(new PropertyValueFactory<>("description"));
        imageCol.setCellValueFactory(new PropertyValueFactory<>("image"));
        dispoCol.setCellValueFactory(new PropertyValueFactory<>("dispo"));
        tarifsCol.setCellValueFactory(new PropertyValueFactory<>("tarifs"));
        idColT.setCellValueFactory(cellData -> new SimpleIntegerProperty(cellData.getValue().getId_type()).asObject()); // Ajout de cette ligne

        // Add the retrieved data to the TableView
        devisTableView.getItems().addAll(logementList);

        // Double-click handler
        devisTableView.setOnMouseClicked(event -> {
            if (event.getClickCount() == 2) {
                logement selectedLogement = devisTableView.getSelectionModel().getSelectedItem();
                if (selectedLogement != null) {
                    navigateToUpdateLogement(selectedLogement);
                }
            }
        });
    }

    private void navigateToUpdateLogement(logement logement) {
        try {
            FXMLLoader loader = new FXMLLoader(getClass().getResource("../ModifierLogement.fxml"));
            Parent root = loader.load();
            ModifierLogement controller = loader.getController();
            controller.initData(logement);
            Stage stage = new Stage();
            stage.setScene(new Scene(root));
            stage.setOnCloseRequest(event -> {
                try {
                    populateTableView();
                } catch (SQLException e) {
                    e.printStackTrace();
                }
            });
            stage.showAndWait();
            populateTableView();
        } catch (IOException | SQLException e) {
            e.printStackTrace();
        }
    }

    @FXML
    private void AjoutOne(ActionEvent event) {
        try {
            Parent root = FXMLLoader.load(getClass().getResource("AjouterLogement.fxml"));
            Stage stage = new Stage();
            stage.setScene(new Scene(root));
            stage.show();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    @FXML
    private void getReponseDevis(ActionEvent event) {
        try {
            Parent root = FXMLLoader.load(getClass().getResource("afficherLogement.fxml"));
            Stage pStage= new Stage();
            pStage.initStyle(StageStyle.UNDECORATED);
            pStage.setScene(new Scene(root, 667,556));
            pStage.show();
            Stage stage = (Stage) cancelButton.getScene().getWindow();
            stage.close();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    @FXML
    private void ouvrirtypes(ActionEvent event) {
        ouvrirFenetreT("../AfficherType.fxml", "Types");
    }

    private void ouvrirFenetreT(String fxmlPath, String title) {
        try {
            Parent root = FXMLLoader.load(getClass().getResource(fxmlPath));
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

    public void ouvrirModiferLogement(ActionEvent actionEvent) {
        ouvrirFenetre("../ModifierLogement.fxml", "Modifier");
    }

    private void ouvrirFenetre(String fxmlPath, String title) {
        try {
            Parent root = FXMLLoader.load(getClass().getResource(fxmlPath));
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
        logement selectedLogement = devisTableView.getSelectionModel().getSelectedItem();
        if (selectedLogement != null) {
            Alert confirmAlert = new Alert(Alert.AlertType.CONFIRMATION);
            confirmAlert.setTitle("Dialogue de Confirmation");
            confirmAlert.setHeaderText("Supprimer logement");
            confirmAlert.setContentText("Êtes-vous sûr de vouloir supprimer ce logement ?");
            confirmAlert.showAndWait().ifPresent(response -> {
                if (response == ButtonType.OK) {
                    try {
                        ServiceLogement serviceLogement = new ServiceLogement();
                        serviceLogement.supprimer(selectedLogement);
                        Alert successAlert = new Alert(Alert.AlertType.INFORMATION);
                        successAlert.setTitle("Suppression Réussie");
                        successAlert.setHeaderText(null);
                        successAlert.setContentText("Logement supprimé avec succès.");
                        successAlert.showAndWait();
                        populateTableView();
                    } catch (SQLException ex) {
                        Alert errorAlert = new Alert(Alert.AlertType.ERROR);
                        errorAlert.setTitle("Erreur de Suppression");
                        errorAlert.setHeaderText(null);
                        errorAlert.setContentText("Erreur lors de la suppression du logement : " + ex.getMessage());
                        errorAlert.showAndWait();
                    }
                }
            });
        } else {
            Alert alert = new Alert(Alert.AlertType.WARNING);
            alert.setTitle("Aucune Sélection");
            alert.setHeaderText(null);
            alert.setContentText("Veuillez sélectionner un logement à supprimer.");
            alert.showAndWait();
        }
    }
}
