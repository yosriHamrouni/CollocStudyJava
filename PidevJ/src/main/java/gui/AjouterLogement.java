package gui;

import entities.logement;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.control.Alert;
import javafx.scene.control.TextField;
import services.ServiceLogement;

import java.io.IOException;
import java.sql.SQLException;

public class AjouterLogement {

    @FXML
    private TextField txtadr;

    @FXML
    private TextField txtdescr;

    @FXML
    private TextField txtdispo;

    @FXML
    private TextField txtequip;

    @FXML
    private TextField txtimage;

    @FXML
    private TextField txttarif;

 //   @FXML
  //  private TextField txttype;

    @FXML
    void addLogement(ActionEvent event) {
        try {
            String description = txtdescr.getText();
            String equipement = txtequip.getText();
            String adresse = txtadr.getText();
            String image = txtimage.getText();
            int tarifs = Integer.parseInt(txttarif.getText());
            int dispo = Integer.parseInt(txtdispo.getText());

            // Vérification de la validité de l'adresse
            if (adresse.isEmpty()) {
                throw new IllegalArgumentException("L'adresse ne peut pas être vide.");
            }
            if (adresse.length() < 5) {
                throw new IllegalArgumentException("L'adresse doit contenir au moins 5 caractères.");
            }

            // Validation supplémentaire des champs numériques
            if (tarifs < 0 || dispo < 0) {
                throw new IllegalArgumentException("Les champs 'Tarif' et 'Disponibilité' doivent être des nombres positifs.");
            }

            ServiceLogement sl = new ServiceLogement();
            logement l = new logement(adresse, equipement, description, image, dispo, tarifs);
            sl.ajouter(l);

            FXMLLoader loader = new FXMLLoader(getClass().getResource("../AfficherLogement.fxml"));
            Parent root = loader.load();

          // al.setRlist(sl.readAll().toString());
            txtdescr.getScene().setRoot(root);
        } catch (NumberFormatException e) {
            showAlert("Erreur de saisie", "Veuillez vous assurer que les champs 'Tarif' et 'Disponibilité' sont des nombres valides.");
        } catch (IllegalArgumentException e) {
            showAlert("Erreur de saisie", e.getMessage());
        } catch (SQLException e) {
            showAlert("Erreur SQL", "Une erreur s'est produite lors de l'ajout du logement dans la base de données!");
        } catch (IOException e) {
            showAlert("Erreur d'E/S", "Une erreur s'est produite lors du chargement de la page AfficherLogement.");
        }
    }

    // Méthode utilitaire pour afficher une alerte
    private void showAlert(String title, String content) {
        Alert alert = new Alert(Alert.AlertType.ERROR);
        alert.setTitle(title);
        alert.setContentText(content);
        alert.show();
    }


    @FXML
    void initialize() {
        assert txtdescr != null : "fx:id=\"description\" was not injected: check your FXML file 'AjouterLogement.fxml'.";
        assert txtequip != null : "fx:id=\"equipement\" was not injected: check your FXML file 'AjouterLogement.fxml'.";
        assert txtadr != null : "fx:id=\"addresse\" was not injected: check your FXML file 'AjouterLogement.fxml'.";
        assert txtimage != null : "fx:id=\"image\" was not injected: check your FXML file 'AjouterLogement.fxml'.";
        assert txttarif != null : "fx:id=\"tarifs\" was not injected: check your FXML file 'AjouterLogement.fxml'.";
        assert txtdispo != null : "fx:id=\"dispo\" was not injected: check your FXML file 'AjouterLogement.fxml'.";
        //assert txttype != null : "fx:id=\"id_type\" was not injected: check your FXML file 'AjouterLogement.fxml'.";

    }}



