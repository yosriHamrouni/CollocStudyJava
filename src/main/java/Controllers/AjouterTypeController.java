package Controllers;

import entities.TypeOffres;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.Alert;
import javafx.scene.control.TextField;
import services.ServiceOffres;
import services.ServiceType;

import java.sql.SQLException;


public class AjouterTypeController {
    private final ServiceType serviceType = new ServiceType();
    @FXML
    private TextField Ajoutertype;

    @FXML
    void addType(ActionEvent event) {
        try {
            String type = Ajoutertype.getText();

            // Vérification de la validité du type
            if (type.isEmpty()) {
                throw new IllegalArgumentException("Le type ne peut pas être vide.");
            }

            // Vérification que le type ne contient pas de symboles
            if (!type.matches("[a-zA-Z0-9 ]+")) {
                throw new IllegalArgumentException("Le type ne peut pas contenir de symboles.");
            }

            // Création du service des types d'offres
            ServiceType serviceType = new ServiceType();

            // Ajout du nouveau type d'offre
            TypeOffres typeOffres = new TypeOffres(type);
            serviceType.ajouter(typeOffres);

            // Effacement du champ de texte
            //clearFields();

            // Affichage d'une alerte de succès
            showAlert("Succès", "Le type a été ajouté avec succès.");

        } catch (IllegalArgumentException e) {
            // Affichage d'une alerte en cas d'erreur de saisie
            showAlert("Erreur de saisie", e.getMessage());
        } catch (SQLException e) {
            // Affichage d'une alerte en cas d'erreur SQL
            showAlert("Erreur SQL", "Une erreur s'est produite lors de l'ajout du type dans la base de données!");
        }
    }


    //private void clearFields() {
       // Ajoutertype.clear();
    //}

    private void showAlert(String title, String content) {
        Alert alert = new Alert(Alert.AlertType.INFORMATION);
        alert.setTitle(title);
        alert.setHeaderText(null);
        alert.setContentText(content);
        alert.showAndWait();
    }
}
