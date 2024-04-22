package gui;

import entities.logement;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Label;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.stage.Stage;

import java.awt.event.ActionEvent;
import java.io.IOException;

public class DetailLogement {
    @FXML
    private Label lblAdresse;

    @FXML
    private Label lblEquipement;

    @FXML
    private Label lblDescription;
    private AfficherLogF affichageLogController;
    @FXML
    private Label lblTarifs;

    @FXML
    private ImageView imageView;

    private logement selectedLogement;
    public void setAffichagePubController(AfficherLogF controller) {
        this.affichageLogController = controller;
    }
    public void initData(logement logement) {
        System.out.println("Logement passed to initData: " + logement); // Vérifiez si logement est null ou contient les données correctes
        if (logement != null) {
            lblAdresse.setText(logement.getAdresse());
            lblEquipement.setText(logement.getEquipement());
            lblDescription.setText(logement.getDescription());
            lblTarifs.setText(String.valueOf(logement.getTarifs()) + " Dinars");
        } else {
            System.out.println("Logement is null!"); // Si logement est null, affichez un message
            // Autres instructions...
        }
    }


    @FXML
    void getDetail(ActionEvent event) {
        System.out.println("Selected logement: " + selectedLogement); // Vérifiez si selectedLogement est null ou contient les données correctes
        try {
            // Chargez le fichier FXML de la fenêtre des détails du logement
            FXMLLoader loader = new FXMLLoader(getClass().getResource("../DetailLogement.fxml"));
            Parent root = loader.load();

            // Obtenez le contrôleur de la fenêtre des détails du logement
            DetailLogement detailController = loader.getController();

            // Passez les informations du logement sélectionné au contrôleur de la fenêtre des détails du logement
            detailController.initData(selectedLogement);

            // Créez une nouvelle fenêtre (stage) et affichez-la
            Stage stage = new Stage();
            stage.setScene(new Scene(root));
            stage.setTitle("Détails du Logement");
            stage.show();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
