package gui;


import entities.Coworking;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.image.ImageView;
import javafx.scene.image.Image;

import java.io.File;


public class IteamController {

    @FXML
    private Label adresse;

    @FXML
    private Button detail;

    @FXML
    private ImageView imgview;

    @FXML
    private Button map;

    @FXML
    private Label nomco;

    @FXML
    private Label numtel;

    @FXML
    private Label tarifs;
    //private MyListener myListener;

    public void setData(Coworking Co, Object o) {

        this.nomco.setText( Co.getNomco());
        //descriptionTextField.setText( selectedCo.getDescription());
        this.numtel.setText(Co.getNumtel());
        //horaireouvrTextField.setText(selectedCo.getHoraireouvr());
        //horairefermTextField.setText(selectedCo.getHorairefer());
        this.adresse.setText(Co.getAdresse());
        this.tarifs.setText(String.valueOf( Co.getTarifs()));

        //dispoTextField.setText(String.valueOf( selectedCo.getDispo()));
        //imgview.setFitWidth(350); // Ajuster la largeur de l'image
        //imgview.setFitHeight(350); // Ajuster la hauteur de l'image

        String imagePath = Co.getImage();
        if (imagePath != null) {
            // Charger l'image à partir du chemin d'accès spécifié
            try {
                Image image = new Image(new File(imagePath).toURI().toString());
                this.imgview.setImage(image);
            } catch (IllegalArgumentException e) {
                // Gérer l'exception (afficher un message d'erreur, charger une image par défaut, etc.)
                e.printStackTrace(); // Ou utilisez un autre mécanisme de journalisation approprié
            }

        } else {
            // Afficher une image par défaut ou gérer le cas où l'image est absente
        }

    }


}

