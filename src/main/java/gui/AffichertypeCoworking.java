package gui;

import entities.Coworking;
import entities.TypeCo;
import javafx.beans.property.SimpleIntegerProperty;
import javafx.beans.property.SimpleStringProperty;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.layout.AnchorPane;
import javafx.stage.Stage;
import services.ServiceCoworking;
import services.ServiceTypeco;

import java.io.IOException;
import java.net.URL;
import java.sql.SQLException;
import java.util.List;
import java.util.ResourceBundle;

public class AffichertypeCoworking  implements Initializable {


        @FXML
        private Button AjouterButton;

        @FXML
        private Button cancelButton;

        @FXML
        private TableColumn<TypeCo,String> col_Typeco;

        @FXML
        private TableColumn<TypeCo, Integer> col_id;

        @FXML
        private AnchorPane midlast;

        @FXML
        private Button supprimerButton;

        @FXML
        private TableView<TypeCo> table_typecoworking;


    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        try {
            populateTableView();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

        @FXML
        void Supprimercoworking(ActionEvent event) {
            TypeCo selectedtype = table_typecoworking.getSelectionModel().getSelectedItem();
            if (selectedtype == null) {
                // Afficher une alerte si aucun coworking n'est sélectionné
                Alert alert = new Alert(Alert.AlertType.WARNING);
                alert.setTitle("Aucune Sélection");
                alert.setHeaderText(null);
                alert.setContentText("Veuillez sélectionner un type  coworking à supprimer.");
                alert.showAndWait();
                return; // Sortir de la méthode si aucun coworking n'est sélectionné
            }

            // Afficher une boîte de dialogue de confirmation
            Alert confirmAlert = new Alert(Alert.AlertType.CONFIRMATION);
            confirmAlert.setTitle("Confirmation de Suppression");
            confirmAlert.setHeaderText("Supprimer Type Coworking");
            confirmAlert.setContentText("Êtes-vous sûr de vouloir supprimer ce Type de coworking ?");

            // Utiliser une expression lambda pour gérer le choix de l'utilisateur
            confirmAlert.showAndWait().ifPresent(response -> {
                if (response == ButtonType.OK) {
                    // Coworking confirmé, procéder à la suppression
                    try {
                        // Appeler la méthode de service pour supprimer le coworking sélectionné
                        ServiceTypeco serviceTypeco = new ServiceTypeco();
                        serviceTypeco.supprimer(selectedtype);
                        // Afficher un message de réussite
                        Alert successAlert = new Alert(Alert.AlertType.INFORMATION);
                        successAlert.setTitle("Suppression Réussie");
                        successAlert.setHeaderText(null);
                        successAlert.setContentText("Coworking supprimé avec succès.");
                        successAlert.showAndWait();
                        // Rafraîchir la table après la suppression
                        populateTableView();
                    } catch (SQLException ex) {
                        // Afficher un message d'erreur si la suppression échoue
                        Alert errorAlert = new Alert(Alert.AlertType.ERROR);
                        errorAlert.setTitle("Erreur de Suppression");
                        errorAlert.setHeaderText(null);
                        errorAlert.setContentText("Erreur lors de la suppression du coworking : " + ex.getMessage());
                        errorAlert.showAndWait();
                    }
                }
            });

        }

        @FXML
        void addtypecoworking(ActionEvent event) {
            try {
                // Load the AjouterCoworking.fxml file
                Parent root = FXMLLoader.load(getClass().getResource("../AjouterTypeCoworking.fxml"));

                // Show the scene containing the AjouterCoworking.fxml file
                Stage stage = new Stage();
                stage.setScene(new Scene(root));
                stage.show();
            } catch (IOException e) {
                e.printStackTrace();
            }

        }

        @FXML
        void cancelButtonOnAction(ActionEvent event) {
            Stage stage = (Stage) cancelButton.getScene().getWindow();
            stage.close();

        }

        @FXML
        void populateTableView() throws SQLException {
            {
                ServiceTypeco serviceTypeco = new ServiceTypeco();
                List<TypeCo> coworkingtypeList = serviceTypeco.afficher();

                // Clear existing items in the TableView
                table_typecoworking.getItems().clear();

                // Set cell value factories for each column
                col_id.setCellValueFactory(cellData -> new SimpleIntegerProperty(cellData.getValue().getId()).asObject());
                col_Typeco.setCellValueFactory(cellData -> new SimpleStringProperty(cellData.getValue().getType()));
                table_typecoworking.getItems().addAll(coworkingtypeList);
                table_typecoworking.setOnMouseClicked(event -> {
                    if (event.getClickCount() == 2) { // Double-click detected
                        TypeCo selectedtype = table_typecoworking.getSelectionModel().getSelectedItem();
                        if (selectedtype != null) {
                            // Navigate to UpdateUser.fxml
                            navigateToModifierTypeCoworking(selectedtype);
                        }
                    }
                });
            }



        }

    private void navigateToModifierTypeCoworking(TypeCo selectedtype) {
        try {
            // Load the UpdateUser.fxml file
            FXMLLoader loader = new FXMLLoader(getClass().getResource("../ModifiertypeCoworking.fxml"));
            Parent root = loader.load();


            // Access the controller and pass the selected user to it
            ModifiertypeCoworking controller = loader.getController();
            controller.initData(selectedtype);
            controller.setListCoworkingtypeController(this);



            // Show the scene containing the UpdateUser.fxml file
            Stage stage = new Stage();
            stage.setScene(new Scene(root));
            stage.setOnCloseRequest(event -> {
                try {
                    // Refresh the TableView when the PopUp stage is closed
                    populateTableView();
                } catch (SQLException e) {
                    e.printStackTrace();
                }
            });
            stage.show();
            Stage gg = (Stage) cancelButton.getScene().getWindow();
            gg.close();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
