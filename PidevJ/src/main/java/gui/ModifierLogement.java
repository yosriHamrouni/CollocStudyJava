package gui;

import config.InputValidation;
import entities.logement;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.scene.control.ButtonType;
import javafx.scene.control.TextField;
import javafx.scene.image.ImageView;
import javafx.stage.Stage;
import services.ServiceLogement;

import java.sql.SQLException;

public class ModifierLogement {
    @FXML
    private TextField idTextField111;
    @FXML
    private TextField AdrTextField;

    @FXML
    private TextField EquipTextField1;

    @FXML
    private ImageView brandingImageView;

    @FXML
    private Button cancelButton;

    @FXML
    private Button confirmerButton;

    @FXML
    private TextField descriptionTextField;

    @FXML
    private TextField dispoTextField11;

    @FXML
    private TextField imageTextField11;

    @FXML
    private TextField tarifsTextField;
    private logement selectedLogement;
    @FXML
    void cancelButtonOnAction(ActionEvent event) {

    }





    public void initData(logement selectedLogement) {
        this.selectedLogement=selectedLogement;

        // Populate the fields in the UI with the data from selected

        AdrTextField.setText(selectedLogement.getAdresse());
        EquipTextField1.setText(selectedLogement.getEquipement());
        descriptionTextField.setText(selectedLogement.getDescription());
        tarifsTextField.setText(String.valueOf(selectedLogement.getTarifs()));
        dispoTextField11.setText(String.valueOf(selectedLogement.getDispo()));
        imageTextField11.setText(selectedLogement.getImage());

    }
    @FXML
    void updateOne(ActionEvent event) throws SQLException {
       // selectedLogement =logement ;
        String selectedAdresse = AdrTextField.getText();
        String selectedDescription = descriptionTextField.getText();
        String selectedEquipement = EquipTextField1.getText();
        String selectedImage = imageTextField11.getText();
        float selectedTarifs = Float.parseFloat(tarifsTextField.getText());
        Integer selectedDispo = Integer.parseInt(dispoTextField11.getText());
       // Integer selectedId = Integer.parseInt(idTextField111.getText());


        // Create a new logement object with retrieved values
        logement logement = new logement(selectedLogement.getId(), selectedAdresse, selectedEquipement, selectedDescription, selectedImage,selectedDispo, selectedTarifs);

        ServiceLogement serviceLogement = new ServiceLogement();

        try {
            if (InputValidation.isTextFieldEmpty(selectedAdresse)) {
                InputValidation.showAlert("Input Error", null, "Please enter a valid Address");
            } else if (InputValidation.isTextFieldEmpty(selectedDescription)) {
                InputValidation.showAlert("Input Error", null, "Please enter a valid Description");
            } else {
                // Update the logement
                serviceLogement.modifier(logement);
                System.out.println("Logement updated successfully.");


             /*   if (AfficherLogement!= null) {
                    AfficherLogement.refreshList();
                }*/
                Stage stage = (Stage) cancelButton.getScene().getWindow();
                stage.close();
            }
        } catch (SQLException e) {
            e.printStackTrace();
            System.err.println("Failed to update logement: " + e.getMessage());
        }
    }




}
