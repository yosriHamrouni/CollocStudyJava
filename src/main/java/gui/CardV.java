package gui;

import entities.logement;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.AnchorPane;
import javafx.stage.Stage;

import java.awt.event.ActionEvent;
import java.io.IOException;
import java.io.InputStream;

public class CardV {

    @FXML
    private Button Detail;

    @FXML
    private AnchorPane cardPane;

    @FXML
    private Label descriptionPub;

    @FXML
    private ImageView imagePub;

    @FXML
    private Button map;

    @FXML
    private Label tarifs;

    @FXML
    private Label titrePub;
    @FXML
    private Label Equip;
    private AfficherLogF affichageLogController;
    private logement selectedLogement;
    private boolean selected; // Ajoutez un champ pour indiquer si la carte est sélectionnée

    // Autres champs et méthodes

    public boolean isSelected() {
        return selected;
    }

    public void setSelected(boolean selected) {
        this.selected = selected;
        // Ajoutez du code pour mettre à jour l'apparence de la carte en fonction de son état sélectionné
        // Par exemple, vous pouvez changer la couleur de fond ou ajouter une bordure
    }
    private logement logement;

    // Autres méthodes...

    public logement getLogement() {
        return logement;
    }



    public void setAffichagePubController(AfficherLogF controller) {
        this.affichageLogController = controller;
    }
    public void setLogement(logement logement) {
        // Mettre à jour les champs avec les données du logement
        titrePub.setText(logement.getAdresse());
        Equip.setText(logement.getEquipement());
        descriptionPub.setText(logement.getDescription());
        // dispoLabel.setText(String.valueOf(logement.getDispo()));
        tarifs.setText(String.valueOf(logement.getTarifs()));

        // Chargement de l'image
        String imagePath = "/img/" + logement.getImage();
        InputStream imageStream = getClass().getResourceAsStream(imagePath);
        if (imageStream != null) {
            Image image = new Image(imageStream);
            imagePub.setImage(image);
        } else {
            // Image par défaut si l'image spécifiée n'est pas trouvée
            // Par exemple : imagePub.setImage(new Image("/images/default.png"));
            System.out.println("L'image n'a pas pu être chargée : " + imagePath);
        }
    }


}
