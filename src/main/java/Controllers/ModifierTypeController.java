package Controllers;

import entities.TypeOffres;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.Alert;
import javafx.scene.control.TextField;
import services.ServiceType;

import java.sql.SQLException;

public class ModifierTypeController {

    private final ServiceType serviceType = new ServiceType();
    private TypeOffres selectedType;

    @FXML
    private TextField Modifiertype;

    public void initData(TypeOffres selectedType) {
        this.selectedType = selectedType;
        Modifiertype.setText(selectedType.getType());
    }

    @FXML
    void ModifierType(ActionEvent event) {
        String newType = Modifiertype.getText();

        try {
            if (newType.isEmpty()) {
                throw new IllegalArgumentException("Le type ne peut pas être vide.");
            }

            selectedType.setType(newType);
            serviceType.modifier(selectedType);

            showAlert("Succès", "Le type a été mis à jour avec succès.");
        } catch (IllegalArgumentException e) {
            showAlert("Erreur de saisie", e.getMessage());
        } catch (SQLException e) {
            showAlert("Erreur SQL", "Une erreur s'est produite lors de la mise à jour du type : " + e.getMessage());
        }
    }

    private void showAlert(String title, String content) {
        Alert alert = new Alert(Alert.AlertType.INFORMATION);
        alert.setTitle(title);
        alert.setHeaderText(null);
        alert.setContentText(content);
        alert.showAndWait();
    }

    public void setListOffrestypeController(AfficherTypeController afficherTypeController) {
    }

    public void annulermodif(ActionEvent actionEvent) {
    }
}
