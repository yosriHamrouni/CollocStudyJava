package gui;

import entities.logement;
import entities.typelog;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.Alert;
import javafx.scene.control.TextField;
import services.ServiceLogement;
import services.ServiceTypelog
        ;

import java.sql.SQLException;

public class AjouterType {

    @FXML
    private TextField txtdescr;

    @FXML
    private TextField txttype;

    @FXML
    void addType(ActionEvent event) {


            try {
                String description = txtdescr.getText();
                String type = txttype.getText();


                // Vérification de la validité de l'adresse
                if (description.isEmpty()) {
                    throw new IllegalArgumentException("La description ne peut pas être vide.");
                }
                if (type.length() < 5) {
                    throw new IllegalArgumentException("Le type doit contenir au moins 5 caractères.");
                }


                ServiceTypelog sl = new ServiceTypelog();
                typelog l = new typelog(type, description);
                sl.ajouter(l);

                // Clear the form fields
                clearFields();

                showAlert("Succès", "Le type a été ajouté avec succès.");


            } catch (IllegalArgumentException e) {
                showAlert("Erreur de saisie", e.getMessage());
            } catch (SQLException e) {
                showAlert("Erreur SQL", "Une erreur s'est produite lors de l'ajout du type dans la base de données!");
            }
        }

    private void clearFields() {
        txttype.clear();
        txtdescr.clear();

    }

    private void showAlert(String title, String content) {
        Alert alert = new Alert(Alert.AlertType.INFORMATION);
        alert.setTitle(title);
        alert.setHeaderText(null);
        alert.setContentText(content);
        alert.showAndWait();
    }
}
