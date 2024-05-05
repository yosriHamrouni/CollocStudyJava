package gui;

import entities.logement;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.AnchorPane;
import javafx.scene.web.WebEngine;
import javafx.scene.web.WebView;
import javafx.stage.Stage;

import java.io.InputStream;

public class CardV {
    @FXML
    private Button Detail;

    @FXML
    private AnchorPane cardPane;
    @FXML
    private Button mapButton;

    @FXML
    private Label descriptionPub;

    @FXML
    private ImageView imagePub;

    @FXML
    private Label tarifs;

    @FXML
    private Label titrePub;

    @FXML
    private Label Equip;
    @FXML
    private Button Qrcode;

    private logement logement;

    public logement getLogement() {
        return logement;
    }
    private AfficherLogF afficherLogFController;

    public void setAfficherLogFController(AfficherLogF controller) {
        this.afficherLogFController = controller;
    }

    public void setLogement(logement logement) {
        this.logement = logement;

        titrePub.setText(logement.getAdresse());
        Equip.setText(logement.getEquipement());
        descriptionPub.setText(logement.getDescription());
        tarifs.setText(String.valueOf(logement.getTarifs()));

        String imagePath = "/img/" + logement.getImage();
        InputStream imageStream = getClass().getResourceAsStream(imagePath);
        if (imageStream != null) {
            Image image = new Image(imageStream);
            imagePub.setImage(image);
        } else {
            System.out.println("L'image n'a pas pu être chargée : " + imagePath);
        }
    }
    @FXML
    void QrCode(ActionEvent event) {
// Récupérer le logement associé à cette carte
        logement selectedLogement = getLogement();

        try {
            // Chargez le fichier FXML des détails du logement
            FXMLLoader loader = new FXMLLoader(getClass().getResource("../QRCode.fxml"));
            Stage stage = new Stage();
            stage.setScene(new Scene(loader.load()));

            // Obtenez le contrôleur des détails du logement
            QRCode QrController = loader.getController();

            // Passez les informations du logement sélectionné au contrôleur des détails du logement
            QrController.setLog(selectedLogement);

            // Afficher la fenêtre modale des détails du logement
            stage.setTitle("Scan du QrCode");
            stage.show();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
    @FXML
    void afficherDetails(ActionEvent event) {
        // Récupérer le logement associé à cette carte
        logement selectedLogement = getLogement();

        try {
            // Chargez le fichier FXML des détails du logement
            FXMLLoader loader = new FXMLLoader(getClass().getResource("../DetailLogement.fxml"));
            Stage stage = new Stage();
            stage.setScene(new Scene(loader.load()));

            // Obtenez le contrôleur des détails du logement
            DetailLogement detailController = loader.getController();

            // Passez les informations du logement sélectionné au contrôleur des détails du logement
            detailController.setLogement(selectedLogement);

            // Afficher la fenêtre modale des détails du logement
            stage.setTitle("Détails du Logement");
            stage.show();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }


    // Add other fields and methods as needed

    /*@FXML
    void mapButtonClicked(ActionEvent event) {
        // Get the address of the accommodation
        String address = titrePub.getText(); // Assuming Adresse is a Label displaying the address

        // Construct the URL for displaying the map
        String mapURL = "https://www.google.com/maps?q=" + address;

        // Open the map in a new window or dialog
        openMap(mapURL);
    }

    private void openMap(String url) {
        // Display the map in a WebView
        WebView webView = new WebView();
        WebEngine webEngine = webView.getEngine();
        webEngine.load(url);

        // Create a new dialog to display the map
        Alert mapDialog = new Alert(Alert.AlertType.INFORMATION);
        mapDialog.setTitle("Map");
        mapDialog.setHeaderText(null);
        mapDialog.getDialogPane().setContent(webView);
        mapDialog.showAndWait();
    }*/
}
