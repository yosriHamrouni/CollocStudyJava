package gui;

import config.InputValidation;
import entities.logement;
import entities.typelog;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.scene.control.ChoiceBox;
import javafx.scene.control.TextField;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.stage.FileChooser;
import javafx.stage.Stage;
import services.ServiceLogement;

import java.io.File;
import java.sql.SQLException;

public class ModifierLogement {

    @FXML
    private TextField AdrTextField;

    @FXML
    private ImageView imageView;

    @FXML
    private TextField EquipTextField1;

    @FXML
    private ChoiceBox<String> choiceDispo;

    @FXML
    private Button selectImage;

    @FXML
    private Button cancelButton;

    @FXML
    private Button confirmerButton;

    @FXML
    private TextField descriptionTextField;

    @FXML
    private TextField tarifsTextField;

    private logement selectedLogement;

    private String imagePathInDatabase;

    @FXML
    void cancelButtonOnAction(ActionEvent event) {
        Stage stage = (Stage) cancelButton.getScene().getWindow();
        stage.close();
    }

    public void initData(logement selectedLogement) {
        this.selectedLogement = selectedLogement;

        // Populate the fields in the UI with the data from selected
        AdrTextField.setText(selectedLogement.getAdresse());
        EquipTextField1.setText(selectedLogement.getEquipement());
        descriptionTextField.setText(selectedLogement.getDescription());
        tarifsTextField.setText(String.valueOf(selectedLogement.getTarifs()));

        // Initialize choice box for availability
        choiceDispo.getItems().addAll("Disponible", "Non disponible");
        choiceDispo.setValue(selectedLogement.getDispo() == 1 ? "Disponible" : "Non disponible");

        // Set image
        imagePathInDatabase = selectedLogement.getImage();
        if (imagePathInDatabase != null && !imagePathInDatabase.isEmpty()) {
            Image image = new Image(new File(imagePathInDatabase).toURI().toString());
            imageView.setImage(image);
        }
    }

    @FXML
    void updateOne(ActionEvent event) {
        String selectedAdresse = AdrTextField.getText();
        String selectedDescription = descriptionTextField.getText();
        String selectedEquipement = EquipTextField1.getText();
        float selectedTarifs = Float.parseFloat(tarifsTextField.getText());
        int selectedDispo = choiceDispo.getValue().equals("Disponible") ? 1 : 0;

        // Create a new logement object with retrieved values
        logement logement = new logement(selectedLogement.getId(), selectedAdresse, selectedEquipement, selectedDescription, imagePathInDatabase, selectedDispo, selectedTarifs);

        ServiceLogement serviceLogement = new ServiceLogement();

        try {
            if (InputValidation.isTextFieldEmpty(selectedAdresse)) {
                InputValidation.showAlert("Erreur de saisie", null, "Veuillez entrer une adresse valide.");
            } else if (InputValidation.isTextFieldEmpty(selectedDescription)) {
                InputValidation.showAlert("Erreur de saisie", null, "Veuillez entrer une description valide.");
            } else {
                // Update the logement
                serviceLogement.modifier(logement);
                showAlert("Succès", "Le logement a été mis à jour avec succès.");
                Stage stage = (Stage) confirmerButton.getScene().getWindow();
                stage.close();
            }
        } catch (SQLException e) {
            showAlert("Erreur SQL", "Échec de la mise à jour du logement : " + e.getMessage());
        }
    }

    @FXML
    void browseImageAction(ActionEvent event) {
        FileChooser fileChooser = new FileChooser();
        fileChooser.setTitle("Choisir une image");

        // Filtrer les types de fichiers si nécessaire
        fileChooser.getExtensionFilters().addAll(
                new FileChooser.ExtensionFilter("Images", "*.jpg", "*.png", "*.gif")
        );

        // Afficher la boîte de dialogue de sélection de fichier
        File selectedFile = fileChooser.showOpenDialog(new Stage());
        if (selectedFile != null) {
            // Stocker le chemin absolu de l'image sélectionnée dans la variable de classe
            imagePathInDatabase = selectedFile.getAbsolutePath();
            System.out.println("Chemin de l'image sélectionnée : " + imagePathInDatabase); // Afficher le chemin dans la console

            // Charger l'image sélectionnée dans l'ImageView
            Image image = new Image(selectedFile.toURI().toString());
            imageView.setImage(image);
        }
    }

    private void showAlert(String title, String content) {
        Alert alert = new Alert(Alert.AlertType.INFORMATION);
        alert.setTitle(title);
        alert.setHeaderText(null);
        alert.setContentText(content);
        alert.showAndWait();
    }
}
