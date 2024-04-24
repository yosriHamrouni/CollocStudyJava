package Controllers;

import entities.Offres;
import entities.TypeOffres;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.control.Alert;
import javafx.scene.control.ComboBox;
import javafx.scene.control.TextField;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.stage.FileChooser;
import javafx.stage.Stage;

import services.ServiceOffres;
import services.ServiceType;
import javafx.scene.control.Button;
import java.awt.*;
import java.io.File;
import java.io.IOException;
import java.sql.SQLException;
import java.util.List;

public class AjouterOffreController {

    private final ServiceOffres so = new ServiceOffres();
    private Stage stage;
    private String imagePathInDatabase;

    @FXML
    private TextField DescripTF;

    @FXML
    private ComboBox<TypeOffres> typeComboBox;

    @FXML
    private TextField HorairedebTF;

    @FXML
    private TextField HoraireterTF;
    @FXML
    private ImageView imageview;

    @FXML
    private Button selectImage;


    @FXML
    private TextField LieuTF;

    @FXML
    private TextField NumtelTF;

    @FXML
    private TextField SalaireTF;


    public void setStage(Stage stage) {
        this.stage = stage;
    }

    /*@FXML
    void AjouterOffre(ActionEvent event) {
        try {
            TypeOffres selectedType = typeComboBox.getValue();

            // Vérification de la validité de la saisie du salaire
            if (SalaireTF.getText().isEmpty() || Double.parseDouble(SalaireTF.getText()) <= 0) {
                throw new IllegalArgumentException("Le salaire doit être spécifié et supérieur à zéro.");
            }

            // Vérification de la validité du numéro de téléphone
            if (NumtelTF.getText().length() != 8) {
                throw new IllegalArgumentException("Le numéro de téléphone doit contenir 8 chiffres.");
            }

            Offres off = new Offres(
                    DescripTF.getText(),
                    Double.parseDouble(SalaireTF.getText()),
                    HorairedebTF.getText(),
                    HoraireterTF.getText(),
                    LieuTF.getText(),
                    NumtelTF.getText(),
                    selectedType.getId()
            );
            so.ajouter(off);

            // Vous pouvez également afficher une boîte de dialogue pour informer l'utilisateur que l'offre a été ajoutée avec succès
            Alert alert = new Alert(Alert.AlertType.INFORMATION);
            alert.setTitle("Succès");
            alert.setContentText("L'offre a été ajoutée avec succès!");
            alert.show();
        } catch (SQLException | NumberFormatException e) {
            Alert alert = new Alert(Alert.AlertType.ERROR);
            alert.setTitle("Erreur de saisie");
            alert.setContentText("Vous avez une erreur dans la saisie de vos données!");
            System.out.println(e);
            alert.show();
        } catch (IllegalArgumentException e) {
            Alert alert = new Alert(Alert.AlertType.ERROR);
            alert.setTitle("Erreur de saisie");
            alert.setContentText(e.getMessage());
            alert.show();
        }
    }*/
    @FXML
    void AjouterOffre(ActionEvent event) {
        try {
            String description = DescripTF.getText();
            String horairedeb = HorairedebTF.getText();
            String horaireter = HoraireterTF.getText();
            String lieu = LieuTF.getText();
            String numtel = NumtelTF.getText();
            double salaire = Double.parseDouble(SalaireTF.getText());
            TypeOffres selectedType = typeComboBox.getValue();

            if (selectedType == null) {
                throw new IllegalArgumentException("Veuillez sélectionner un type d'offre.");
            }

            File imageFile = new File(imagePathInDatabase);
            String image = imageFile.getName();

            if (lieu.isEmpty()) {
                throw new IllegalArgumentException("Lieu ne peut pas être vide.");
            }
            if (salaire < 0) {
                throw new IllegalArgumentException("Le salaire doit être un nombre positif.");
            }
            if (imagePathInDatabase == null || imagePathInDatabase.isEmpty()) {
                throw new IllegalArgumentException("Veuillez sélectionner une image.");
            }

            ServiceOffres so = new ServiceOffres();

            // Creating an instance of Offres
            Offres o = new Offres(description, salaire, horairedeb, horaireter, lieu, numtel, selectedType.getId(), image);

            so.ajouter(o);

            Alert alert = new Alert(Alert.AlertType.INFORMATION);
            alert.setTitle("Ajout Réussi");
            alert.setContentText("Offre ajoutée avec succès !");
            alert.showAndWait();

            FXMLLoader loader = new FXMLLoader(getClass().getResource("../AfficherOffres.fxml"));
            Parent root = loader.load();
            AfficherOffre ao = loader.getController();
            DescripTF.getScene().setRoot(root);

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
        } catch (IOException | SQLException e) {
            throw new RuntimeException(e);
        }
    }
    @FXML
    void browseImageAction(ActionEvent event) {
        FileChooser fileChooser = new FileChooser();
        fileChooser.setTitle("Choisir une image");
        File selectedFile = fileChooser.showOpenDialog(new Stage());
        if (selectedFile != null) {
            imagePathInDatabase = selectedFile.getAbsolutePath();
            Image image = new Image(selectedFile.toURI().toString());
            imageview.setImage(image);
        }
    }


    @FXML
    void initialize() {
        // Initialisation du ComboBox avec les types de offres
        try {
            ServiceType serviceType = new ServiceType();
            List<TypeOffres> types = serviceType.afficher();
            typeComboBox.getItems().addAll(types);
        } catch (SQLException e) {
            Alert alert = new Alert(Alert.AlertType.ERROR);
            alert.setTitle("Erreur");
            alert.setContentText("Erreur lors de la récupération des types des offres.");
            alert.show();
        }
    }
}
