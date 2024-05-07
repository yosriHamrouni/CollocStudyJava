package Controllers;

import entities.TypeOffres;
import javafx.beans.property.SimpleIntegerProperty;
import javafx.beans.property.SimpleStringProperty;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.stage.Stage;
import services.ServiceType;

import java.io.IOException;
import java.net.URL;
import java.sql.SQLException;
import java.util.List;
import java.util.ResourceBundle;

public class AfficherTypeController implements Initializable {

    @FXML
    private TableView<TypeOffres> typeOffresTableView;

    @FXML
    private TableColumn<TypeOffres, Integer> idtv;

    @FXML
    private TableColumn<TypeOffres, String> typetv;

    @FXML
    private Button deletebtn;

    private ServiceType serviceType = new ServiceType();

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        try {
            initTableView();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    public void initTableView() throws SQLException {
        ServiceType serviceType=new ServiceType();

        List<TypeOffres> typeOffresList = serviceType.afficher();

        // Clear existing items in the TableView
        typeOffresTableView.getItems().clear();

        // Set cell value factories for each column
        idtv.setCellValueFactory(cellData -> new SimpleIntegerProperty(cellData.getValue().getId()).asObject());
        typetv.setCellValueFactory(cellData -> new SimpleStringProperty(cellData.getValue().getType()));

        // Add the retrieved data to the TableView
        typeOffresTableView.getItems().addAll(typeOffresList);

        // Double-click listener for editing
        typeOffresTableView.setOnMouseClicked(event -> {
            if (event.getClickCount() == 2) { // Double-click detected
                TypeOffres selectedTypeOffres = typeOffresTableView.getSelectionModel().getSelectedItem();
                if (selectedTypeOffres != null) {
                    navigateToUpdateTypeOffres(selectedTypeOffres);
                }
            }
        });
    }

    private void navigateToUpdateTypeOffres(TypeOffres selectedTypeOffres) {
        try {
            FXMLLoader loader = new FXMLLoader(getClass().getResource("../modifierType.fxml"));
            Parent root = loader.load();

            ModifierTypeController controller = loader.getController();
            controller.initData(selectedTypeOffres);

            Stage stage = new Stage();
            stage.setScene(new Scene(root));
            stage.setOnCloseRequest(event -> {
                try {
                    initTableView();
                } catch (SQLException e) {
                    e.printStackTrace();
                }
            });
            stage.show();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    @FXML
    private void deleteOne(java.awt.event.ActionEvent event) {
        // Récupérer le logement sélectionné dans la table

        TypeOffres selectedTypeOffre = typeOffresTableView.getSelectionModel().getSelectedItem();
        if (selectedTypeOffre != null) {
            // Afficher une boîte de dialogue de confirmation
            Alert confirmAlert = new Alert(Alert.AlertType.CONFIRMATION);
            confirmAlert.setTitle("Dialogue de Confirmation");
            confirmAlert.setHeaderText("Supprimer offre");
            confirmAlert.setContentText("Êtes-vous sûr de vouloir supprimer cette offre ?");

            // Utiliser une expression lambda pour gérer le choix du logement
            confirmAlert.showAndWait().ifPresent(response -> {
                if (response == ButtonType.OK) {
                    // Logement confirmé, procéder à la suppression
                    try {
                        // Appeler la méthode deleteOne avec le logement sélectionné
                        ServiceType serviceType = new ServiceType();
                        serviceType.supprimer(selectedTypeOffre);
                        // Afficher un message de réussite
                        Alert successAlert = new Alert(Alert.AlertType.INFORMATION);
                        successAlert.setTitle("Suppression Réussie");
                        successAlert.setHeaderText(null);
                        successAlert.setContentText("Offre supprimée avec succès.");
                        successAlert.showAndWait();
                        // Rafraîchir la table après la suppression
                        initTableView();
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
            alert.setContentText("Veuillez sélectionner une offre à supprimer.");
            alert.showAndWait();
        }


    }

    public void deletetype(ActionEvent actionEvent) {
        // Récupérer le logement sélectionné dans la table

        TypeOffres selectedTypeOffre = typeOffresTableView.getSelectionModel().getSelectedItem();
        if (selectedTypeOffre != null) {
            // Afficher une boîte de dialogue de confirmation
            Alert confirmAlert = new Alert(Alert.AlertType.CONFIRMATION);
            confirmAlert.setTitle("Dialogue de Confirmation");
            confirmAlert.setHeaderText("Supprimer offre");
            confirmAlert.setContentText("Êtes-vous sûr de vouloir supprimer cette offre ?");

            // Utiliser une expression lambda pour gérer le choix du logement
            confirmAlert.showAndWait().ifPresent(response -> {
                if (response == ButtonType.OK) {
                    // Logement confirmé, procéder à la suppression
                    try {
                        // Appeler la méthode deleteOne avec le logement sélectionné
                        serviceType = new ServiceType();
                        serviceType.supprimer(selectedTypeOffre);
                        // Afficher un message de réussite
                        Alert successAlert = new Alert(Alert.AlertType.INFORMATION);
                        successAlert.setTitle("Suppression Réussie");
                        successAlert.setHeaderText(null);
                        successAlert.setContentText("Offre supprimée avec succès.");
                        successAlert.showAndWait();
                        // Rafraîchir la table après la suppression
                        initTableView();
                    } catch (SQLException ex) {
                        // Afficher un message d'erreur si la suppression échoue
                        Alert errorAlert = new Alert(Alert.AlertType.ERROR);
                        errorAlert.setTitle("Erreur de Suppression");
                        errorAlert.setHeaderText(null);
                        errorAlert.setContentText("Erreur lors de la suppression de l'offre : " + ex.getMessage());
                        errorAlert.showAndWait();
                    }
                }
            });
        } else {
            // Afficher une alerte si aucun logement n'est sélectionné
            Alert alert = new Alert(Alert.AlertType.WARNING);
            alert.setTitle("Aucune Sélection");
            alert.setHeaderText(null);
            alert.setContentText("Veuillez sélectionner une offre à supprimer.");
            alert.showAndWait();
        }


    }
    }

