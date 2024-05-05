package gui;

import entities.logement;
import javafx.fxml.FXML;
import javafx.scene.control.Label;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.input.MouseEvent;

import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;

public class DetailLogement {
    @FXML
    private ImageView imgview;
    @FXML
    private Label lblParagraphe;

    @FXML
    private Label lblAdresse;

    @FXML
    private Label lblEquipement;

    @FXML
    private Label lblDescription;

    @FXML
    private Label lblTarifs;

    private logement logement;

    @FXML
    private void click(MouseEvent mouseEvent) {
        // Logique de clic (si nécessaire)
    }



    public void setLogement(logement logement) {
        this.logement = logement;

        if (logement != null) {
            String adresse = logement.getAdresse();
            String equipement = logement.getEquipement();
            String description = logement.getDescription();
            double tarifs = logement.getTarifs();

            // Ajoute un retour à la ligne après chaque point dans la description
            description = description.replaceAll("\\.", ".\n");

            String paragraph = "Ce logement est situé à " + adresse + ". ";
            paragraph += "Il est équipé de: " + equipement + ". ";
            paragraph += "Il comprend: " + description + ". ";
            paragraph += "Les tarifs sont de " + tarifs + " Dinars.";

            // Afficher le paragraphe dans votre interface utilisateur
            lblParagraphe.setText(paragraph);

        } else {
            clearFields();
            return; // Ajout de ce return pour sortir de la méthode si logement est null
        }

        String imagePath = logement.getImage();
        if (imagePath != null && !imagePath.isEmpty()) {
            File file = new File(imagePath);
            if (file.exists() && !file.isDirectory()) {
                try {
                    Image image = new Image(file.toURI().toString());
                    double desiredWidth = 600;
                    double desiredHeight = 600;
                    imgview.setImage(image);
                    imgview.setFitWidth(desiredWidth);
                    imgview.setFitHeight(desiredHeight);
                } catch (Exception e) {
                    System.err.println("Erreur lors du chargement de l'image : " + e.getMessage());
                }
            } else {
                System.err.println("Le fichier image n'existe pas : " + imagePath);
            }
        } else {
            System.err.println("Chemin d'accès à l'image non spécifié.");
        }
    }


    private void clearFields() {
        lblAdresse.setText("");
        lblEquipement.setText("");
        lblDescription.setText("");
        lblTarifs.setText("");
        imgview.setImage(null);
    }
}
