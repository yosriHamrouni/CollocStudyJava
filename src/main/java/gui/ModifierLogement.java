package gui;

import config.InputValidation;
import entities.logement;
import entities.typelog;
import javafx.application.Platform;
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
import org.controlsfx.control.Notifications;
import services.ServiceLogement;
import services.ServiceTypelog;

import java.io.File;
import java.sql.SQLException;
import java.util.List;

public class ModifierLogement {

    @FXML
    private TextField AdrTextField;

    @FXML
    private ChoiceBox<typelog> typeCombo;

    @FXML
    private ImageView imageView;

    @FXML
    private TextField EquipTextField1;
    @FXML
    private TextField tarifsTextField;

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

    private logement selectedLogement;

    private String imagePathInDatabase;

    @FXML
    void cancelButtonOnAction(ActionEvent event) {
        Stage stage = (Stage) cancelButton.getScene().getWindow();
        stage.close();
    }

    public void initialize() throws SQLException {
        ServiceTypelog serviceTypelog = new ServiceTypelog();
        List<typelog> types = serviceTypelog.getAllTypes();
        typeCombo.getItems().addAll(types);
    }

    public void initData(logement selectedLogement) {
        this.selectedLogement = selectedLogement;

        AdrTextField.setText(selectedLogement.getAdresse());
        EquipTextField1.setText(selectedLogement.getEquipement());
        descriptionTextField.setText(selectedLogement.getDescription());
        choiceDispo.getItems().addAll("Disponible", "Non disponible");
        choiceDispo.setValue(selectedLogement.getDispo() == 1 ? "Disponible" : "Non disponible");
        tarifsTextField.setText(String.valueOf(selectedLogement.getTarifs()));

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

        typelog selectedType = typeCombo.getValue();

        logement logement = new logement(selectedLogement.getId(), selectedAdresse, selectedEquipement, selectedDescription, imagePathInDatabase, selectedDispo, selectedTarifs, selectedType);

        ServiceLogement serviceLogement = new ServiceLogement();

        try {
            if (InputValidation.isTextFieldEmpty(selectedAdresse)) {
                InputValidation.showAlert("Erreur de saisie", null, "Veuillez entrer une adresse valide.");
            } else if (InputValidation.isTextFieldEmpty(selectedDescription)) {
                InputValidation.showAlert("Erreur de saisie", null, "Veuillez entrer une description valide.");
            } else {
                serviceLogement.modifier(logement);
                showAlert("Succès", "Le logement a été mis à jour avec succès.");
                showNotification("Success", "Logement added successfully!");
                Stage stage = (Stage) confirmerButton.getScene().getWindow();
                stage.close();
            }
        } catch (SQLException e) {
            showAlert("Erreur SQL", "Échec de la mise à jour du logement : " + e.getMessage());
        }
    }
    private void showNotification(String title, String content) {
        Notifications notification =Notifications.create()
                .title(title)
                .text(content);

        Platform.runLater(() -> notification.showInformation());
    }
    @FXML
    void browseImageAction(ActionEvent event) {
        FileChooser fileChooser = new FileChooser();
        fileChooser.setTitle("Choisir une image");

        fileChooser.getExtensionFilters().addAll(
                new FileChooser.ExtensionFilter("Images", "*.jpg", "*.png", "*.gif")
        );

        File selectedFile = fileChooser.showOpenDialog(new Stage());
        if (selectedFile != null) {
            imagePathInDatabase = selectedFile.getAbsolutePath();
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
