package gui;

import entities.logement;
import entities.typelog;
import javafx.application.Platform;
import javafx.collections.FXCollections;
import javafx.embed.swing.SwingFXUtils;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.*;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.web.WebEngine;
import javafx.scene.web.WebView;
import javafx.stage.FileChooser;
//import nl.captcha.Captcha;
import services.ServiceLogement;
import services.ServiceTypelog;
import org.controlsfx.control.Notifications;
import java.io.File;
import java.io.IOException;
import java.sql.SQLException;
import java.util.List;

public class AjouterLogement {

    @FXML
    private TextField txtadr;
    //Captcha captcha;
    @FXML
    private Label labelNomType;
    @FXML
    private TextField txtdescr;
    @FXML
    private WebView webViewRecaptcha;

    @FXML
    private ChoiceBox<String> choiceDispo;

    @FXML
    private TextField txtequip;

    @FXML
    private ImageView imageView;

    @FXML
    private TextField txttarif;

    private String imagePathInDatabase;
    private ServiceTypelog serviceType;
    @FXML
    public ImageView captchaIV;
    @FXML
    public TextField captchaTF;


    // ComboBox pour les types de logement
    @FXML
    private ComboBox<typelog> typelogComboBox;

  //  private static final String IMAGES_DIR = "../resources/img/";

    @FXML
    void addLogement(ActionEvent event) {
        try {
            String description = txtdescr.getText();
            String equipement = txtequip.getText();
            String adresse = txtadr.getText();
            int tarifs = Integer.parseInt(txttarif.getText());

            // Vérification de la validité de l'adresse
            if (adresse.isEmpty()) {
                throw new IllegalArgumentException("L'adresse ne peut pas être vide.");
            }
            if (adresse.length() < 5) {
                throw new IllegalArgumentException("L'adresse doit contenir au moins 5 caractères.");
            }

            // Validation supplémentaire des champs numériques
            if (tarifs < 0) {
                throw new IllegalArgumentException("Le champ 'Tarif' doit être un nombre positif.");
            }

            if (imagePathInDatabase == null || imagePathInDatabase.isEmpty()) {
                throw new IllegalArgumentException("Veuillez sélectionner une image.");
            }

            // Obtenir le nom du fichier à partir du chemin complet de l'image
            File imageFile = new File(imagePathInDatabase);
            String imageName = imageFile.getName();

            // Récupérer le type de logement sélectionné
            typelog selectedType = typelogComboBox.getValue();

            // Vérifier si un type de logement a été sélectionné
            if (selectedType == null) {
                throw new IllegalArgumentException("Veuillez sélectionner un type de logement.");
            }

            // Vérifier le captcha
            /*String userCaptcha = captchaTF.getText().trim();
            if (!captcha.isCorrect(userCaptcha)) {
                throw new IllegalArgumentException("Captcha incorrect.");
            }
*/
            // Créer un nouveau logement avec le type sélectionné
            ServiceLogement sl = new ServiceLogement();
            logement l = new logement(adresse, equipement, description, imageName, choiceDispo.getValue().equals("Disponible") ? 1 : 0, tarifs, selectedType);
            sl.ajouter(l);
            showNotification("Success", "Logement added successfully!");
            //setCaptcha(); // Réinitialiser le captcha
            clearFields(); // Effacer les champs du formulaire

            showAlert("Succès", "Le logement a été ajouté avec succès.");

        } catch (NumberFormatException e) {
            showAlert("Erreur de saisie", "Veuillez vous assurer que le champ 'Tarif' est un nombre valide.");
        } catch (IllegalArgumentException e) {
            showAlert("Erreur de saisie", e.getMessage());
        } catch (SQLException e) {
            showAlert("Erreur SQL", "Une erreur s'est produite lors de l'ajout du logement dans la base de données!");
        }
    }

    private void showNotification(String title, String content) {
        Notifications notification =Notifications.create()
                .title(title)
                .text(content);

        Platform.runLater(() -> notification.showInformation());
    }
    @FXML
    void selecttypelog(ActionEvent event) {
        typelog selectedType = typelogComboBox.getValue();
        if (selectedType != null) {
            // Afficher le nom du type de logement sélectionné dans un label ou tout autre composant d'interface utilisateur
            labelNomType.setText(selectedType.getType());
        }
    }


    @FXML
    void browseImageAction(ActionEvent event) {
        FileChooser fileChooser = new FileChooser();
        fileChooser.setTitle("Choisir une image");
        fileChooser.getExtensionFilters().addAll(
                new FileChooser.ExtensionFilter("Images", "*.jpg", "*.png", "*.gif")
        );

        File selectedFile = fileChooser.showOpenDialog(null);
        if (selectedFile != null) {
            imagePathInDatabase = selectedFile.getAbsolutePath();
            Image image = new Image(selectedFile.toURI().toString());
            imageView.setImage(image);
        }
    }

    @FXML
    void initialize() {
        // Initialise votre ComboBox typelogComboBox avec les types de logement disponibles
        try {
            serviceType = new ServiceTypelog();
            List<typelog> types = serviceType.afficher();
            typelogComboBox.setItems(FXCollections.observableArrayList(types));
          //  WebEngine webEngine = webViewRecaptcha.getEngine();
          //  webEngine.load("https://www.google.com/recaptcha/enterprise.js?render=6LegstApAAAAAFAQ3cyhCK0twk3McSgGxptJYmPf");
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    private void clearFields() {
        txtadr.clear();
        txtdescr.clear();
        txtequip.clear();
        txttarif.clear();
        imageView.setImage(null);
    }
    /*
    void setCaptcha() {
        captcha = new Captcha.Builder(250, 200)
                .addText()
                .addBackground()
                .addNoise()
                .addBorder()
                .build();

        Image image = SwingFXUtils.toFXImage(captcha.getImage(), null);
        captchaIV.setImage(image);
    }*/
    @FXML
    public void resetCaptcha(ActionEvent ignored) {
        //setCaptcha();
        captchaTF.clear();
    }

    private void showAlert(String title, String content) {
        Alert alert = new Alert(Alert.AlertType.INFORMATION);
        alert.setTitle(title);
        alert.setHeaderText(null);
        alert.setContentText(content);
        alert.showAndWait();
    }
}
