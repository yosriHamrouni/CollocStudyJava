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
import javafx.util.StringConverter;
import org.controlsfx.control.Notifications;
import services.ServiceOffres;
import services.ServiceType;

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
    private TextField LieuTF;

    @FXML
    private TextField NumtelTF;

    @FXML
    private TextField SalaireTF;


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

            // Création d'une nouvelle instance de Offres
            Offres o = new Offres(description, salaire, horairedeb, horaireter, lieu, numtel, selectedType.getId(), image);

            // Ajout de l'offre à la base de données
            so.ajouter(o);

            // Envoi d'un e-mail d'information avec les détails de l'offre
            String recipientEmail = "skandernacheb@gmail.com"; // Mettez l'adresse e-mail du destinataire ici
            serviceemail.sendEmail(recipientEmail, o);

            // Affichage d'une boîte de dialogue pour informer l'utilisateur que l'offre a été ajoutée avec succès
            Alert alert = new Alert(Alert.AlertType.INFORMATION);
            alert.setTitle("Ajout Réussi");
            alert.setContentText("Offre ajoutée avec succès !");
            alert.showAndWait();

            FXMLLoader loader = new FXMLLoader(getClass().getResource("../afficherOffre.fxml"));
            Parent root = loader.load();
            AfficherOffre ao = loader.getController();
            DescripTF.getScene().setRoot(root);
            // Code pour ajouter l'offre

            // Création de l'image du logo
            Image logoImage = new Image("file:///C:/Users/MSI/IdeaProjects/ColocS/src/main/resources/img/logo-v2.png");
            ImageView logoImageView = new ImageView(logoImage);
            logoImageView.setFitWidth(50); // Taille du logo
            logoImageView.setFitHeight(50);

            // Affichage de la notification
            Notifications.create()
                    .graphic(logoImageView) // Utilisation du logo comme icône de la notification
                    .title("Nouvelle Offre")
                    .text("Une nouvelle offre a été ajoutée!")
                    .darkStyle() // Style sombre pour la notification
                    .show();

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

            // Utilisation de la méthode getType() pour afficher uniquement le type d'offre
            typeComboBox.setConverter(new StringConverter<TypeOffres>() {
                @Override
                public String toString(TypeOffres typeOffres) {
                    return typeOffres.getType();
                }

                @Override
                public TypeOffres fromString(String string) {
                    return typeComboBox.getItems().stream()
                            .filter(t -> t.getType().equals(string))
                            .findFirst()
                            .orElse(null);
                }
            });

            typeComboBox.getItems().addAll(types);

            // Sélectionner le premier élément du ComboBox par défaut
            if (!typeComboBox.getItems().isEmpty()) {
                typeComboBox.getSelectionModel().selectFirst();
            }
        } catch (SQLException e) {
            Alert alert = new Alert(Alert.AlertType.ERROR);
            alert.setTitle("Erreur");
            alert.setContentText("Erreur lors de la récupération des types des offres.");
            alert.show();
        }
    }

}
