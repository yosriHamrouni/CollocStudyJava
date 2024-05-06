package Controllers;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.scene.control.TextField;
import javafx.scene.image.ImageView;
import javafx.stage.FileChooser;
import javafx.stage.Stage;
import services.ServiceOffres;
import entities.Offres;
import javafx.scene.image.Image;

import java.io.File;
import java.sql.SQLException;

public class ModifierOffresController {

    @FXML
    private TextField descriptiontextfield;

    @FXML
    private TextField salairetextfield;

    @FXML
    private TextField horairedebtextfield;

    @FXML
    private TextField horairetertextfield;

    @FXML
    private TextField lieutextfield;

    @FXML
    private TextField numteltextfield;

    @FXML
    private Button ModifierBtn;

    @FXML
    private Button AnnulerBtn;

    @FXML
    private ImageView imageView;

    private Offres offre;

    public void initData(Offres offre) {
        this.offre = offre; // Assigner l'offre reçue à la variable de classe
        // Utiliser les données de l'offre pour initialiser les champs de saisie
        descriptiontextfield.setText(offre.getDescrip());
        salairetextfield.setText(String.valueOf(offre.getSalaire()));
        horairedebtextfield.setText(offre.getHorairedeb());
        horairetertextfield.setText(offre.getHoraireter());
        lieutextfield.setText(offre.getLieu());
        numteltextfield.setText(String.valueOf(offre.getNum_tel()));
    }

    @FXML
    void modifierOffre(ActionEvent event) throws SQLException, NumberFormatException {
        try {
            // Récupérer les nouvelles valeurs saisies par l'utilisateur dans les champs de texte
            String description = descriptiontextfield.getText();
            double salaire = Double.parseDouble(salairetextfield.getText());
            String horaireDeb = horairedebtextfield.getText();
            String horaireFin = horairetertextfield.getText();
            String lieu = lieutextfield.getText();
            Integer numTel = Integer.parseInt(numteltextfield.getText());

            // Vérifier si la description est vide
            if (description.isEmpty()) {
                throw new IllegalArgumentException("La description ne peut pas être vide.");
            }

            if (lieu.isEmpty()) {
                throw new IllegalArgumentException("Lieu ne peut pas être vide.");
            }
            if (salaire < 0) {
                throw new IllegalArgumentException("Le salaire doit être un nombre positif.");
            }

            // Créer un objet Offres avec les nouvelles valeurs
            Offres offreModifiee = new Offres(offre.getId(), description, horaireDeb, horaireFin, lieu, salaire, numTel);

            // Utiliser le service ServiceOffres pour mettre à jour l'offre dans la base de données
            ServiceOffres serviceOffres = new ServiceOffres();
            serviceOffres.modifier(offreModifiee);

            System.out.println("Offre mise à jour avec succès !");

            // Mettre à jour le chemin de l'image dans la base de données
            if (offre.getImage() != null && !offre.getImage().isEmpty()) {
                serviceOffres.updateImage(offre.getId(), offre.getImage());
            }

            // Fermer la fenêtre après la mise à jour
            closeWindow(event);
        } catch (NumberFormatException e) {
            Alert alert = new Alert(Alert.AlertType.ERROR);
            alert.setTitle("Erreur de saisie");
            alert.setContentText("Veuillez vérifier les champs numériques.");
            alert.show();
        } catch (IllegalArgumentException e) {
            Alert alert = new Alert(Alert.AlertType.ERROR);
            alert.setTitle("Erreur de saisie");
            alert.setContentText(e.getMessage());
            alert.show();
        }
    }

    @FXML
    void browseImageAction(ActionEvent event) {
        FileChooser fileChooser = new FileChooser();
        fileChooser.setTitle("Choisir une image");
        File selectedFile = fileChooser.showOpenDialog(new Stage());
        if (selectedFile != null) {
            // Récupérer uniquement le nom du fichier à partir du chemin complet
            String fileName = selectedFile.getName();

            // Mettre à jour l'aperçu de l'image dans l'interface utilisateur
            Image image = new Image(selectedFile.toURI().toString());
            imageView.setImage(image);

            // Mettre à jour le nom de l'image dans l'offre
            offre.setImage(fileName);
        }

}

    @FXML
    void annuler(ActionEvent event) {
        // Fermer la fenêtre lorsque le bouton "Annuler" est pressé
        Stage stage = (Stage) AnnulerBtn.getScene().getWindow();
        stage.close();
    }

    // Méthode pour fermer la fenêtre
    private void closeWindow(ActionEvent event) {
        // Récupérer la scène à partir de l'événement
        Stage stage = (Stage) ((Button) event.getSource()).getScene().getWindow();
        // Fermer la fenêtre
        stage.close();
    }
}
