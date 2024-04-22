package Controllers;
import entities.Offres;
import javafx.beans.property.SimpleDoubleProperty;
import javafx.beans.property.SimpleIntegerProperty;
import javafx.beans.property.SimpleStringProperty;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.stage.Stage;
import services.ServiceOffres;
import javafx.scene.control.TableView;
import java.awt.event.ActionEvent;
import java.io.IOException;
import java.net.URL;
import java.sql.SQLException;
import java.util.List;
import java.util.ResourceBundle;

public class AfficherOffre implements Initializable {

    @FXML
    private TableView<Offres> offresTableView;

    @FXML
    private TableColumn<Offres, Integer> idtv;

    @FXML
    private TableColumn<Offres, String> Descriptv;

    @FXML
    private TableColumn<Offres, Double> salairetv;

    @FXML
    private TableColumn<Offres, String> Horairedebtv;

    @FXML
    private TableColumn<Offres, String> Horairefintv;

    @FXML
    private TableColumn<Offres, String> lieutv;

    @FXML
    private TableColumn<Offres, Integer> numteltv;
    @FXML
    private Button deletebtn;

    @FXML
    private Button editbtn;


    private final ServiceOffres serviceOffres = new ServiceOffres();


    public void initTableView() throws SQLException {
        // Retrieve data from the database
        ServiceOffres serviceOffres = new ServiceOffres();
        List<Offres> offresList = serviceOffres.afficher();

        // Clear existing items in the TableView
        offresTableView.getItems().clear();

        // Set cell value factories for each column
        idtv.setCellValueFactory(cellData -> new SimpleIntegerProperty(cellData.getValue().getId()).asObject());
        Descriptv.setCellValueFactory(cellData -> new SimpleStringProperty(cellData.getValue().getDescrip()));
        salairetv.setCellValueFactory(cellData -> new SimpleDoubleProperty(cellData.getValue().getSalaire()).asObject());
        Horairedebtv.setCellValueFactory(cellData -> new SimpleStringProperty(cellData.getValue().getHorairedeb()));
        Horairefintv.setCellValueFactory(cellData -> new SimpleStringProperty(cellData.getValue().getHoraireter()));
        lieutv.setCellValueFactory(cellData -> new SimpleStringProperty(cellData.getValue().getLieu()));
        numteltv.setCellValueFactory(cellData -> new SimpleIntegerProperty(cellData.getValue().getNum_tel()).asObject());

        // Add the retrieved data to the TableView
        offresTableView.getItems().addAll(offresList);
        //add
        offresTableView.setOnMouseClicked(event -> {
            if (event.getClickCount() == 2) { // Double-click detected
                Offres selectedoffre = offresTableView.getSelectionModel().getSelectedItem();
                if (selectedoffre != null) {
                    int selectedoffreId = selectedoffre.getId();
                    if (selectedoffre != null) {
                        // Navigate to UpdateUser.fxml
                        navigateToUpdateOffre(selectedoffre );
                    }
                }
            }
        });

    }

    private void navigateToUpdateOffre(Offres selectedoffre) {
        try {
            // Charger le fichier FXML de la fenêtre de mise à jour
            FXMLLoader loader = new FXMLLoader(getClass().getResource("../modifierOffre.fxml"));
            Parent root = loader.load();

            // Accéder au contrôleur et passer le logement sélectionné à celui-ci
            ModifierOffresController controller = loader.getController();
            controller.initData(selectedoffre);

            // Afficher la scène contenant le fichier FXML de la mise à jour
            Stage stage = new Stage();
            stage.setScene(new Scene(root));

            // Rafraîchir la TableView lorsque la fenêtre de mise à jour est fermée
            stage.setOnCloseRequest(event -> {
                try {
                    initTableView(); // Rafraîchir la TableView
                } catch (SQLException e) {
                    e.printStackTrace();
                }
            });
            stage.show();
           /* // Fermer la fenêtre actuelle lorsque la fenêtre de mise à jour est affichée
            Stage currentStage = (Stage) cancelButton.getScene().getWindow();
            currentStage.close();*/

            // Afficher la fenêtre de mise à jour

        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        try {
            initTableView();
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }
    @FXML
    private void deleteOne(ActionEvent event) {
        // Récupérer le logement sélectionné dans la table

        Offres selectedOffres = offresTableView.getSelectionModel().getSelectedItem();
        if (selectedOffres != null) {
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
                        ServiceOffres serviceOffres = new ServiceOffres();
                        serviceOffres.supprimer(selectedOffres);
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

    public void deleteOne(javafx.event.ActionEvent actionEvent) {
        // Récupérer le logement sélectionné dans la table

        Offres selectedOffres = offresTableView.getSelectionModel().getSelectedItem();
        if (selectedOffres != null) {
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
                        ServiceOffres serviceOffres = new ServiceOffres();
                        serviceOffres.supprimer(selectedOffres);
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





