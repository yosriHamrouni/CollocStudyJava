package gui;

import entities.Coworking;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.web.WebEngine;
import javafx.scene.web.WebView;
import javafx.stage.Stage;
import services.ServiceCoworking;

import java.io.File;
import java.io.IOException;
import java.io.InputStream;
//import com.esri.arcgisruntime.mapping.ArcGISMap;


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

    @FXML
    private Label idLabel;



    public void setData(Coworking Co, Object o) {
        this.nomco.setText(Co.getNomco());
        this.numtel.setText(Co.getNumtel());
        this.adresse.setText(Co.getAdresse());
        this.tarifs.setText(String.valueOf(Co.getTarifs()));
        this.idLabel.setText(String.valueOf(Co.getId()));


        String imagePath ="/img/"+ Co.getImage();
        InputStream imageStream = getClass().getResourceAsStream(imagePath);
        if (imageStream != null) {
            Image image = new Image(imageStream);
            imgview.setImage(image);
        } else {
            // Image par défaut si l'image spécifiée n'est pas trouvée
            // Par exemple : imagePub.setImage(new Image("/images/default.png"));
            System.out.println("L'image n'a pas pu être chargée : " + imagePath);
        }
    }

    @FXML
    void onclickdetail(javafx.event.ActionEvent event) {
        // Récupérer le texte de l'ID Label
        String idText = idLabel.getText().trim();

        System.out.println("Valeur de idLabel : " + idText); // Ajout pour débogage

        // Vérifier si le texte est vide
        if (idText.isEmpty()) {
            System.out.println("ID Label est vide.");
            return; // Sortir de la méthode
        }

        try {
            // Convertir le texte en entier
            int id = Integer.parseInt(idText);

            // Récupérer l'activité correspondante à partir de votre source de données
            Coworking serviceCoworking = ServiceCoworking.getCoworkingarId(id);

            if (serviceCoworking != null) {
                try {
                    // Charger la vue FXML de détails
                    FXMLLoader loader = new FXMLLoader(getClass().getResource("/detailcoworking.fxml"));
                    Parent root = loader.load();

                    // Accéder au contrôleur de vue de détails
                    Detailcoworking controller = loader.getController();

                    // Appeler la méthode setDatadetail du contrôleur de vue de détails pour initialiser les données de l'activité
                    controller.setDatadetail(serviceCoworking);

                    // Créer une nouvelle scène
                    Stage stage = new Stage();
                    stage.setScene(new Scene(root));
                    stage.show();
                } catch (IOException e) {
                    e.printStackTrace();
                }
            } else {
                // Gérer le cas où l'activité est null
                System.out.println("Aucune activité trouvée avec l'ID : " + id);
            }
        } catch (NumberFormatException e) {
            System.out.println("Format d'ID invalide : " + idText);
        }
    }



    public void ShowMap(ActionEvent actionEvent) {
        String location = adresse.getText(); // Récupérer la localisation de l'activité
        openMap(location);
    }
    private void openMap(String location) {
        try {
            // Charger le fichier FXML de la vue de la carte
            FXMLLoader loader = new FXMLLoader(getClass().getResource("../mapView.fxml"));
            Parent root = loader.load();

            // Obtenir le contrôleur de la vue de la carte
            MapsController mapController = loader.getController();

            // Passer la localisation au contrôleur de la vue de la carte
            mapController.setLocation(location);

            // Afficher la vue de la carte dans une nouvelle fenêtre
            Stage stage = new Stage();
            stage.setScene(new Scene(root));
            stage.show();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}


