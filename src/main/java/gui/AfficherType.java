package gui;

import entities.logement;
import entities.typelog;
import javafx.beans.property.SimpleFloatProperty;
import javafx.beans.property.SimpleIntegerProperty;
import javafx.beans.property.SimpleStringProperty;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.layout.AnchorPane;
import javafx.stage.Stage;
import services.ServiceLogement;
import services.ServiceTypelog;

import java.io.IOException;
import java.net.URL;
import java.sql.SQLException;
import java.util.List;
import java.util.ResourceBundle;
import gui.ModifierType;

public class AfficherType {

    @FXML
    private Button PoubelleButton;

    @FXML
    private TableColumn<typelog, String> descriptionCol;

    @FXML
    private TableColumn<typelog, Integer> idCol;

    @FXML
    private Button supprimerButton;

    @FXML
    private TableColumn<typelog, String> typeCol;

    @FXML
    private Button cancelButton;

    @FXML
    private TableView<typelog> typeTable;

    @FXML
    void cancelButtonOnAction(ActionEvent event) {
        Stage stage = (Stage) cancelButton.getScene().getWindow();
        stage.close();
    }

    @FXML
    public void initialize() {
        try {
            populateTableView();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    @FXML
    void populateTableView() throws SQLException {
        ServiceTypelog serviceTypelog = new ServiceTypelog();
        List<typelog> typeList = serviceTypelog.afficher();

        // Clear existing items in the TableView
        typeTable.getItems().clear();


        // Set cell value factories for each column
        //idCol.setCellValueFactory(cellData -> cellData.getValue().getIdProperty().asObject());
        typeCol.setCellValueFactory(cellData -> new SimpleStringProperty(cellData.getValue().getType()));
        descriptionCol.setCellValueFactory(cellData -> new SimpleStringProperty(cellData.getValue().getDescription()));
        typeTable.getItems().addAll(typeList);

        // Set event handler for double-clicking on a row
        typeTable.setOnMouseClicked(event -> {
            if (event.getClickCount() == 2) { // Double-click detected
                typelog selectedType = typeTable.getSelectionModel().getSelectedItem();
                if (selectedType != null) {
                    navigateToUpdateType(selectedType);
                }
            }
        });
    }

    private void navigateToUpdateType(typelog typelog) {
        try {
            FXMLLoader loader = new FXMLLoader(getClass().getResource("../ModifierType.fxml"));
            Parent root = loader.load();

            ModifierType controller = loader.getController();
            controller.initData(typelog);

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
        } catch (IOException e) {
            e.printStackTrace();
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }

    @FXML
    private void AjoutOne(ActionEvent event) {
        try {
            Parent root = FXMLLoader.load(getClass().getResource("AjouterType.fxml"));
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
        pStage.setScene(new Scene(root, 667,556));
        pStage.show();
        Stage stage = (Stage) cancelButton.getScene().getWindow();
        stage.close();
    }

    @FXML
    public void ouvrirAjouterLogement(ActionEvent actionEvent) {
        ouvrirFenetre("../AjouterType.fxml", "Ajouter");
    }

    private void ouvrirFenetre(String fxmlPath, String title) {
        try {
            FXMLLoader loader = new FXMLLoader(getClass().getResource(fxmlPath));
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
        // Récupérer le type sélectionné dans la table
        typelog selectedType = typeTable.getSelectionModel().getSelectedItem();
        if (selectedType != null) {
            // Afficher une boîte de dialogue de confirmation
            Alert confirmAlert = new Alert(Alert.AlertType.CONFIRMATION);
            confirmAlert.setTitle("Dialogue de Confirmation");
            confirmAlert.setHeaderText("Supprimer type");
            confirmAlert.setContentText("Êtes-vous sûr de vouloir supprimer ce type ?");

            // Utiliser une expression lambda pour gérer le choix du type
            confirmAlert.showAndWait().ifPresent(response -> {
                if (response == ButtonType.OK) {
                    // Type confirmé, procéder à la suppression
                    try {
                        // Appeler la méthode deleteOne avec le type sélectionné
                        ServiceTypelog serviceType = new ServiceTypelog();
                        serviceType.supprimer(selectedType);
                        // Afficher un message de réussite
                        Alert successAlert = new Alert(Alert.AlertType.INFORMATION);
                        successAlert.setTitle("Suppression Réussie");
                        successAlert.setHeaderText(null);
                        successAlert.setContentText("Type supprimé avec succès.");
                        successAlert.showAndWait();
                        // Rafraîchir la table après la suppression
                        populateTableView();
                    } catch (SQLException ex) {
                        // Afficher un message d'erreur si la suppression échoue
                        Alert errorAlert = new Alert(Alert.AlertType.ERROR);
                        errorAlert.setTitle("Erreur de Suppression");
                        errorAlert.setHeaderText(null);
                        errorAlert.setContentText("Erreur lors de la suppression du type : " + ex.getMessage());
                        errorAlert.showAndWait();
                    }
                }
            });
        } else {
            // Afficher une alerte si aucun type n'est sélectionné
            Alert alert = new Alert(Alert.AlertType.WARNING);
            alert.setTitle("Aucune Sélection");
            alert.setHeaderText(null);
            alert.setContentText("Veuillez sélectionner un type à supprimer.");
            alert.showAndWait();
        }
    }
}
