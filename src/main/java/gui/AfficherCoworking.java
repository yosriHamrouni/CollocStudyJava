package gui;

import com.itextpdf.text.DocumentException;
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
import javafx.stage.Stage;
import entities.Coworking;
import services.CoworkingPDFGenerator;
import services.ServiceCoworking;
import javafx.scene.image.ImageView;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.net.URL;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;
import java.util.ResourceBundle;






public class AfficherCoworking implements Initializable {

    @FXML
    private TableColumn<Coworking, String> col_adresse;

    @FXML
    private Button cancelButton;

    @FXML
    private TableColumn<Coworking, String> col_description;


    @FXML
    private TableColumn<Coworking, Integer>col_idtype;



    @FXML
    private TableColumn<Coworking, String> col_image;

    @FXML
    private TableView<Coworking> table_coworking;

    @FXML
    private TableColumn<Coworking, Integer> col_dispo;

    @FXML
    private TableColumn<Coworking, Float> col_tarifs;

    @FXML
    private TableColumn<Coworking, String> col_horairefer;

    @FXML
    private TableColumn<Coworking, String> col_horaireouvr;
    @FXML
    private TableColumn<Coworking, String> col_nomco;

    @FXML
    private TableColumn<Coworking, String> col_numtel;

    @FXML
    private Button AjouterButton;

    @FXML
    private Button supprimerButton;
    private File selectedImageFile;
    @FXML
    private ImageView image;
    @FXML
    private Button stat;

    private List<Coworking> coworkingList; // Déclaration de la variable membre coworkingList




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

    public void populateTableView() throws SQLException {
        // Retrieve data from the database
        ServiceCoworking serviceCoworking = new ServiceCoworking();
        List<Coworking> coworkingList = serviceCoworking.afficher();

        // Clear existing items in the TableView
        table_coworking.getItems().clear();

        // Set cell value factories for each column
        col_adresse.setCellValueFactory(cellData -> new SimpleStringProperty(cellData.getValue().getAdresse()));
        col_description.setCellValueFactory(cellData -> new SimpleStringProperty(cellData.getValue().getDescription()));
        col_image.setCellValueFactory(cellData -> new SimpleStringProperty(cellData.getValue().getImage()));
        col_dispo.setCellValueFactory(cellData -> new SimpleIntegerProperty(cellData.getValue().getDispo()).asObject());
        col_tarifs.setCellValueFactory(cellData -> new SimpleFloatProperty(cellData.getValue().getTarifs()).asObject());
        col_horaireouvr.setCellValueFactory(cellData -> new SimpleStringProperty(cellData.getValue().getHoraireouvr()));
        col_horairefer.setCellValueFactory(cellData -> new SimpleStringProperty(cellData.getValue().getHorairefer()));
        col_nomco.setCellValueFactory(cellData -> new SimpleStringProperty(cellData.getValue().getNomco()));
        col_numtel.setCellValueFactory(cellData -> new SimpleStringProperty(cellData.getValue().getNumtel()));
        col_idtype.setCellValueFactory(cellData -> new SimpleIntegerProperty(cellData.getValue().getTypeco_id()).asObject());



        table_coworking.getItems().addAll(coworkingList);
        table_coworking.setOnMouseClicked(event -> {
            if (event.getClickCount() == 2) { // Double-click detected
                Coworking selectedCo = table_coworking.getSelectionModel().getSelectedItem();
                if (selectedCo != null) {
                    // Navigate to UpdateUser.fxml
                    navigateToModifierCoworking(selectedCo);
                }
            }
        });
        table_coworking.setOnMouseClicked(event -> {
            if (event.getClickCount() == 1) { // Single-click detected
                Coworking selectedCo = table_coworking.getSelectionModel().getSelectedItem();

                String imagePath = "file:///C:/Users/MSI/IdeaProjects/oumeima/src/main/resources/img/" + selectedCo.getImage();

                if (imagePath != null && !imagePath.isEmpty()) {
                    Image imageToShow = new Image(imagePath);
                    image.setImage(imageToShow);
                } else {
                    // Gérer le cas où le chemin d'accès à l'image est null ou vide
                    System.err.println("Chemin d'accès à l'image vide ou null");
                    // Afficher une image par défaut ou un message d'erreur
                    // Image placeholderImage = new Image("img/default-image.png");
                    // image.setImage(placeholderImage);
                }
            }
        });
    }

    private void navigateToModifierCoworking(Coworking selectedCo) {

    }

   /* @FXML
    private void AjoutOne(ActionEvent event) {
        try {
            // Load the AjouterCoworking.fxml file
            Parent root = FXMLLoader.load(getClass().getResource("../AjouterCoworking.fxml"));

            // Show the scene containing the AjouterCoworking.fxml file
            Stage stage = new Stage();
            stage.setScene(new Scene(root));
            stage.show();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }*/

    @FXML
    void addcoworking(ActionEvent event) {
        try {
            // Load the AjouterCoworking.fxml file
            Parent root = FXMLLoader.load(getClass().getResource("../AjouterCoworking.fxml"));

            // Show the scene containing the AjouterCoworking.fxml file
            Stage stage = new Stage();
            stage.setScene(new Scene(root));
            stage.show();
        } catch (IOException e) {
            e.printStackTrace();
        }

    }
    @FXML
    void ModifierCoworking(ActionEvent event) {
        try {
            Coworking selectedCo = table_coworking.getSelectionModel().getSelectedItem();

            // Load the UpdateUser.fxml file
            FXMLLoader loader = new FXMLLoader(getClass().getResource("../ModifierCoworking.fxml"));
            Parent root = loader.load();


            // Access the controller and pass the selected user to it
            ModifierCoworking controller = loader.getController();
            controller.initData(selectedCo);//,selectedImageFile
            controller.setListCoworkingController(this);


            // Show the scene containing the UpdateUser.fxml file
            Stage stage = new Stage();
            stage.setScene(new Scene(root));
           /* stage.setOnCloseRequest(event -> {
                try {
                    // Refresh the TableView when the PopUp stage is closed
                    populateTableView();
                } catch (SQLException e) {
                    e.printStackTrace();
                }
            });*/
            stage.show();
            Stage gg = (Stage) cancelButton.getScene().getWindow();
            gg.close();
        } catch (IOException e) {
            e.printStackTrace();
        }

    }


    @FXML
    void Supprimercoworking(ActionEvent event) {
        // Récupérer le coworking sélectionné dans la table
        Coworking selectedCo = table_coworking.getSelectionModel().getSelectedItem();
        if (selectedCo == null) {
            // Afficher une alerte si aucun coworking n'est sélectionné
            Alert alert = new Alert(Alert.AlertType.WARNING);
            alert.setTitle("Aucune Sélection");
            alert.setHeaderText(null);
            alert.setContentText("Veuillez sélectionner un coworking à supprimer.");
            alert.showAndWait();
            return; // Sortir de la méthode si aucun coworking n'est sélectionné
        }

        // Afficher une boîte de dialogue de confirmation
        Alert confirmAlert = new Alert(Alert.AlertType.CONFIRMATION);
        confirmAlert.setTitle("Confirmation de Suppression");
        confirmAlert.setHeaderText("Supprimer Coworking");
        confirmAlert.setContentText("Êtes-vous sûr de vouloir supprimer ce coworking ?");

        // Utiliser une expression lambda pour gérer le choix de l'utilisateur
        confirmAlert.showAndWait().ifPresent(response -> {
            if (response == ButtonType.OK) {
                // Coworking confirmé, procéder à la suppression
                try {
                    // Appeler la méthode de service pour supprimer le coworking sélectionné
                    ServiceCoworking serviceCoworking = new ServiceCoworking();
                    serviceCoworking.supprimer(selectedCo);
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

    public void opentypeco(ActionEvent actionEvent) {
        try {
            // Load the AjouterCoworking.fxml file
            Parent root = FXMLLoader.load(getClass().getResource("../AfficherTypeCo.fxml"));

            // Show the scene containing the AjouterCoworking.fxml file
            Stage stage = new Stage();
            stage.setScene(new Scene(root));
            stage.show();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    @FXML
    void GeneratePdf(ActionEvent event) throws DocumentException, SQLException {
        try {
            ArrayList<Coworking> coworkings = (ArrayList<Coworking>) new ServiceCoworking().afficher();
            CoworkingPDFGenerator.generatePDF(coworkings, new FileOutputStream("D:\\IdeaProjects\\oumeima"), "D:\\IdeaProjects\\oumeima\\src\\main\\resources\\img\\colocstudy.jpg");
            Alert alert = new Alert(Alert.AlertType.INFORMATION);
            alert.setTitle("PDF Généré");
            alert.setHeaderText(null);
            alert.setContentText("Le PDF des coworkings a été généré avec succès!");
            alert.showAndWait();
        } catch (FileNotFoundException | DocumentException e) {
            Alert alert = new Alert(Alert.AlertType.ERROR);
            alert.setTitle("Erreur");
            alert.setHeaderText(null);
            alert.setContentText("Erreur lors de la génération du PDF des Coworkings: " + e.getMessage());
            alert.showAndWait();
        }
    }
    @FXML
    void afficherStat(ActionEvent event) {
        try {
            // Load the AjouterCoworking.fxml file
            Parent root = FXMLLoader.load(getClass().getResource("../Stat.fxml"));

            // Show the scene containing the AjouterCoworking.fxml file
            Stage stage = new Stage();
            stage.setScene(new Scene(root));
            stage.show();
        } catch (IOException e) {
            e.printStackTrace();
        }

    }


    public void AfficherLogement(ActionEvent actionEvent) {
        try {
            // Load the AjouterCoworking.fxml file
            Parent root = FXMLLoader.load(getClass().getResource("../dashboardajout.fxml"));

            // Show the scene containing the AjouterCoworking.fxml file
            Stage stage = new Stage();
            stage.setScene(new Scene(root));
            stage.show();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}






