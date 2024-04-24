package Controllers;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.ComboBox;
import javafx.scene.control.TextField;
import javafx.stage.Stage;
import services.ServiceOffres;
import entities.Offres;

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

    private Offres offre;

    public void initData(Offres offre) {
        this.offre = offre; // Assigner l'offre reçue à la variable de classe
        // Utilisez les données de l'offre pour initialiser les champs de saisie
        descriptiontextfield.setText(offre.getDescrip());
        salairetextfield.setText(String.valueOf(offre.getSalaire()));
        horairedebtextfield.setText(offre.getHorairedeb());
        horairetertextfield.setText(offre.getHoraireter());
        lieutextfield.setText(offre.getLieu());
        numteltextfield.setText(String.valueOf(offre.getNum_tel()));
    }

    @FXML
    void modifierOffre(ActionEvent event) throws SQLException, NumberFormatException {
        // Récupérer les nouvelles valeurs saisies par l'utilisateur dans les champs de texte
        String description = descriptiontextfield.getText();
        double salaire = Double.parseDouble(salairetextfield.getText());
        String horaireDeb = horairedebtextfield.getText();
        String horaireFin = horairetertextfield.getText();
        String lieu = lieutextfield.getText();
        Integer numTel = Integer.parseInt(numteltextfield.getText());

        // Créer un objet Offres avec les nouvelles valeurs
        Offres offreModifiee = new Offres(offre.getId(), description, horaireDeb, horaireFin, lieu, salaire, numTel);

        // Utiliser le service ServiceOffres pour mettre à jour l'offre dans la base de données
        ServiceOffres serviceOffres = new ServiceOffres();
        serviceOffres.modifier(offreModifiee);

        System.out.println("Offre mise à jour avec succès !");

        // Fermer la fenêtre après la mise à jour
        closeWindow(event);
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
