package gui;

import entities.Coworking;
import entities.TypeCo;
import javafx.fxml.FXML;
import javafx.scene.control.Label;
import javafx.scene.layout.AnchorPane;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.web.WebEngine;
import javafx.stage.Stage;
import javafx.scene.control.Button;
import javafx.scene.web.WebEngine;
import javafx.scene.web.WebView;



import java.awt.*;
import java.awt.event.ActionEvent;
import java.io.File;
import java.io.InputStream;

import javafx.stage.StageStyle;
import javafx.scene.Scene;

import javafx.scene.web.WebView;






public class Detailcoworking {
    @FXML
    private Label discp_id;

    @FXML
    private Label loc_id;

    @FXML
    private Label nom_id;

    @FXML
    private Label numtel_id;

    @FXML
    private Label tarifs_id;
    @FXML
    private ImageView imgview;
    @FXML
    private Button cancelButton;

    private Coworking coworking;
    private TypeCo typeCo;


    // Méthode pour définir les données de l'activité

    public void setDatadetail(Coworking coworking) {
        this.coworking = coworking;


        if (coworking!= null) {
            this.nom_id.setText("Nom :"+coworking.getNomco());
            this.tarifs_id.setText("Tarifs :"+coworking.getTarifs() + " DT/Personne");
            this.loc_id.setText("Adresse :"+coworking.getAdresse());
            this.numtel_id.setText("Num-tel :"+coworking.getNumtel());
            this.discp_id.setText("Description :"+coworking.getDescription());



            String imagePath ="/img/"+coworking.getImage();
            InputStream imageStream = getClass().getResourceAsStream(imagePath);
            if (imageStream != null) {
                Image image = new Image(imageStream);
                imgview.setImage(image);
            } else {
                // Image par défaut si l'image spécifiée n'est pas trouvée
                // Par exemple : imagePub.setImage(new Image("/images/default.png"));
                System.out.println("L'image n'a pas pu être chargée : " + imagePath);
            }
           /* if (imagePath != null && !imagePath.isEmpty()) {
                // Vérifiez si le fichier image existe
                File file = new File(imagePath);
               /* if (file.exists() && !file.isDirectory()) {
                    try {
                        // Charger l'image à partir du chemin d'accès spécifié
                        Image image = new Image(file.toURI().toString());
                        double desiredWidth = 600; // Spécifiez la largeur souhaitée
                        double desiredHeight = 600;
                        // Définir l'image dans l'élément ImageView
                        imgview.setImage(image);
                        imgview.setFitWidth(desiredWidth);
                        imgview.setFitHeight(desiredHeight);
                    } catch (Exception e) {
                        System.err.println("Erreur lors du chargement de l'image : " + e.getMessage());
                        // Gérer l'erreur de chargement de l'image
                    }
                } else {
                    System.err.println("Le fichier image n'existe pas : " + imagePath);
                    // Gérer le cas où le fichier image est introuvable
                }
            } else {
                System.err.println("Chemin d'accès à l'image non spécifié.");
                // Gérer le cas où le chemin d'accès de l'image n'est pas spécifié
            }*/
        } else {
            // Gérer le cas où activite est null, par exemple, en effaçant les valeurs des labels et de l'image
            this.nom_id.setText("");
            this.tarifs_id.setText("");
            this.numtel_id.setText("");
            this.discp_id.setText("");
            this.loc_id.setText("");
            this.imgview.setImage(null); // Effacer l'image
        }

    }


    public void onclickfermer(javafx.event.ActionEvent actionEvent) {
        Stage stage = (Stage) cancelButton.getScene().getWindow();
        stage.close();

    }
}
