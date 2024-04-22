package gui;

import config.InputValidation;
import entities.logement;
import entities.typelog;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.scene.control.TextField;
import javafx.scene.image.Image;
import javafx.stage.Stage;
import services.ServiceLogement;
import services.ServiceTypelog;

import java.io.File;
import java.sql.SQLException;

public class ModifierType {

    @FXML
    private Button confirmerButton;

    @FXML
    private TextField descriptionTextField;

    @FXML
    private TextField typeTextField;

    private typelog selectedType;

    public void initData(typelog selectedType) {
        this.selectedType = selectedType;

        // Populate the fields in the UI with the data from selected
        typeTextField.setText(selectedType.getType());
        descriptionTextField.setText(selectedType.getDescription());



    }

    @FXML
    void updateOne(ActionEvent event) {

        String selectedDescription = descriptionTextField.getText();
        String selectedtype = typeTextField.getText();


        // Create a new logement object with retrieved values
        typelog type = new typelog(selectedType.getId(), selectedtype, selectedDescription);

        ServiceTypelog serviceLogement = new ServiceTypelog();

        try {
            if (InputValidation.isTextFieldEmpty(selectedtype)) {
                InputValidation.showAlert("Erreur de saisie", null, "Veuillez entrer un type.");
            } else if (InputValidation.isTextFieldEmpty(selectedDescription)) {
                InputValidation.showAlert("Erreur de saisie", null, "Veuillez entrer une description valide.");
            } else {
                // Update the logement
                serviceLogement.modifier(type);
                showAlert("Succès", "Le type a été mis à jour avec succès.");
                Stage stage = (Stage) confirmerButton.getScene().getWindow();
                stage.close();
            }
        } catch (SQLException e) {
            showAlert("Erreur SQL", "Échec de la mise à jour du type : " + e.getMessage());
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
