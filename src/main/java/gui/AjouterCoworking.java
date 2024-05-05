package gui;

import com.mysql.cj.BindValue;
import entities.Coworking;
import entities.TypeCo;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.control.Alert;
import javafx.scene.control.ChoiceBox;
import javafx.scene.control.ComboBox;
import javafx.scene.control.TextField;
import javafx.stage.FileChooser;
import javafx.stage.Stage;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.util.StringConverter;
import services.ServiceCoworking;
import services.ServiceTypeco;

import java.nio.file.Paths;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.sql.SQLException;
import java.util.List;

import com.twilio.Twilio;
import com.twilio.rest.api.v2010.account.Message;
import com.twilio.type.PhoneNumber;

public class AjouterCoworking {

    @FXML
    private TextField txtadresse;

    @FXML
    private TextField txtdescription;

    @FXML
    private TextField txthoraireferm;

    @FXML
    private TextField txthoraireouv;

    @FXML
    private TextField txtnomco;

    @FXML
    private TextField txtnumtel;

    @FXML
    private TextField txttarifs;

    @FXML
    private ImageView imageView;

    @FXML
    private ComboBox<TypeCo> typeCoComboBox;
    private File selectedImageFile;
    @FXML
    private TextField txtimage;



    private String imagePathInDatabase;
    @FXML
    private ChoiceBox<String> choiceDispo;


    // Méthode pour envoyer un SMS
    public void sendSMS(String recipientPhoneNumber, String messageBody) {
       // Twilio.init(ACCOUNT_SID, AUTH_TOKEN);

        Message message = Message.creator(
                        new PhoneNumber(recipientPhoneNumber),
                        new PhoneNumber("+14697079006"),
                        messageBody)
                .create();

        System.out.println("SMS envoyé avec SID: " + message.getSid());
    }


    @FXML
    void browseImageAction(ActionEvent event) {
        FileChooser fileChooser = new FileChooser();
        fileChooser.setTitle("Choisir une image");
        // Filtrer les types de fichiers si nécessaire
        File selectedFile = fileChooser.showOpenDialog(new Stage());
        if (selectedFile != null) {
            // Stocker le chemin de l'image sélectionnée dans la variable de classe
            imagePathInDatabase = selectedFile.getAbsolutePath();
            // Charger l'image sélectionnée dans l'ImageView
            Image image = new Image(selectedFile.toURI().toString());
            imageView.setImage(image);
        }
    }


    @FXML
    void addcoworking(ActionEvent event) {



        try {
            String description = txtdescription.getText();
            String adresse = txtadresse.getText();
            //TypeCo selectedType = typeCoComboBox.getValue();

            if (imagePathInDatabase == null) {
                throw new IllegalArgumentException("Veuillez sélectionner une image.");
            }
            File imageFile = new File(imagePathInDatabase);


            String image = imageFile.getName();
           // String image=imagePathInDatabase;

            String horaireouv = txthoraireouv.getText();
            String horaireferm = txthoraireferm.getText();
            String nomco = txtnomco.getText();
            String numtel = txtnumtel.getText();

            int tarifs = Integer.parseInt(txttarifs.getText());

            if (adresse.isEmpty()) {
                throw new IllegalArgumentException("L'adresse ne peut pas être vide.");
            }
            if (tarifs < 0) {
                throw new IllegalArgumentException("Le champ 'Tarif' doit être un nombre positif.");
            }
            if (imagePathInDatabase == null || imagePathInDatabase.isEmpty()) {
                throw new IllegalArgumentException("Veuillez sélectionner une image.");
            }


// Vérifier si le numéro de téléphone a une longueur de 8 chiffres
            if (numtel.length() != 8 || !numtel.matches("[0-9]+")) {
                throw new IllegalArgumentException("Le numéro de téléphone doit être composé de 8 chiffres.");
            }

// Vérifier si la description est vide ou non
            if (description.isEmpty()) {
                throw new IllegalArgumentException("La description ne peut pas être vide.");
            }

            TypeCo selectedType = typeCoComboBox.getValue();
            if (selectedType == null) {
                throw new IllegalArgumentException("Veuillez sélectionner un type de coworking.");
            }



            ServiceCoworking sc = new ServiceCoworking();

            Coworking c = new Coworking(description, horaireouv, horaireferm, image, nomco, numtel, adresse, tarifs, choiceDispo.getValue().equals("Disponible") ? 1 : 0, selectedType.getId());

            sc.ajouter(c);
            String recipientPhoneNumber = "+21652977655";
            String messageBody = "Un nouveau coworking a été ajouté ! Nom du coworking : " ;

           // sendSMS(recipientPhoneNumber, messageBody);

            Alert alert = new Alert(Alert.AlertType.INFORMATION);
            alert.setTitle("Ajout Réussi");
            alert.setContentText("Coworking ajouté avec succès !");
            alert.showAndWait();

            FXMLLoader loader = new FXMLLoader(getClass().getResource("../AfficherCoworking.fxml"));
            Parent root = loader.load();
            AfficherCoworking ac = loader.getController();
            txtdescription.getScene().setRoot(root);

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
        } catch (IOException e) {
            throw new RuntimeException(e);
        }



    }




    @FXML
    void initialize() {
        // Initialisation du ComboBox avec les types de coworking
        try {
            ServiceTypeco serviceTypeCo = new ServiceTypeco();
            List<TypeCo> types = serviceTypeCo.afficher();
            typeCoComboBox.getItems().addAll(types);
        } catch (SQLException e) {
            Alert alert = new Alert(Alert.AlertType.ERROR);
            alert.setTitle("Erreur");
            alert.setContentText("Erreur lors de la récupération des types de coworking.");
            alert.show();
        }
    }
}
