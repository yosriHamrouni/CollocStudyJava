package Controllers;

import entities.Offres;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.stage.Stage;
import services.ServiceOffres;

import java.io.IOException;
import java.io.InputStream;

public class itemoffreControlleur {

    @FXML
    private Label descrip;

    @FXML
    private Button detailsbutton;

    @FXML
    private ImageView imageoffre;

    @FXML
    private Label lieu;

    @FXML
    private Label salaire;

    @FXML
    private Label idLabel;

    @FXML
    private Button toggleFavoriteButton;

    private Offres offres;
    private ServiceOffres serviceOffres;

    public void setData(Offres offres) {
        this.offres = offres;
        this.descrip.setText("Description : " + offres.getDescrip());
        this.salaire.setText("Salaire : " + String.valueOf(offres.getSalaire()));
        this.lieu.setText("Lieu : " + offres.getLieu());
        this.idLabel.setText(String.valueOf(offres.getId()));
        String imagePath = "/img/" + offres.getImage();
        InputStream imageStream = getClass().getResourceAsStream(imagePath);
        if (imageStream != null) {
            Image image = new Image(imageStream);
            imageoffre.setImage(image);
        } else {
            // Image par défaut si l'image spécifiée n'est pas trouvée
            System.out.println("L'image n'a pas pu être chargée : " + imagePath);
        }

        // Mettre à jour l'apparence du bouton en fonction de l'état de favoris de l'offre
        updateFavoriteButtonAppearance();
    }

    @FXML
    void onclickdetail(javafx.event.ActionEvent event) {
        String idText = idLabel.getText().trim();

        if (idText.isEmpty()) {
            System.out.println("ID Label est vide.");
            return;
        }

        try {
            int id = Integer.parseInt(idText);
            Offres offre = ServiceOffres.getoffreId(id);

            if (offre != null) {
                try {
                    FXMLLoader loader = new FXMLLoader(getClass().getResource("/detailsoffres.fxml"));
                    Parent root = loader.load();
                    DetailsOffresController controller = loader.getController();
                    controller.setDatadetail(offre);

                    Stage stage = new Stage();
                    stage.setScene(new Scene(root));
                    stage.show();
                } catch (IOException e) {
                    e.printStackTrace();
                }
            } else {
                System.out.println("Aucune offre trouvée avec l'ID : " + id);
            }
        } catch (NumberFormatException e) {
            System.out.println("Format d'ID invalide : " + idText);
        }
    }

    public void initialize() {
        serviceOffres = new ServiceOffres();
    }

    public void setOffre(Offres offres) {
        this.offres = offres;
    }

    @FXML
    public void toggleFavorite(javafx.event.ActionEvent actionEvent) {
        if (offres != null) {
            // Inverser la valeur de l'attribut favoris
            offres.setFavoris(!offres.getFavoris());

            // Mettre à jour l'offre dans la base de données
            updateFavoriteInDatabase();

            // Afficher un message pour indiquer que l'offre a été ajoutée aux favoris
            showAlert(offres.getFavoris() ? "L'offre a été ajoutée aux favoris." : "L'offre a été retirée des favoris.");

            // Mettre à jour l'apparence du bouton en fonction de la nouvelle valeur de favoris
            updateFavoriteButtonAppearance();
        }
    }

    private void updateFavoriteInDatabase() {
        // Mettre à jour l'offre dans la base de données ou votre source de données
        ServiceOffres serviceOffres = new ServiceOffres();
        serviceOffres.modifier(offres);
    }

    private void showAlert(String message) {
        Alert alert = new Alert(Alert.AlertType.INFORMATION);
        alert.setTitle("Favoris");
        alert.setHeaderText(null);
        alert.setContentText(message);
        alert.showAndWait();
    }

    private void updateFavoriteButtonAppearance() {
        if (offres != null) {
            if (offres.getFavoris()) {
                toggleFavoriteButton.setText("Retirer des favoris");
            } else {
                toggleFavoriteButton.setText("Ajouter aux favoris");
            }
        }
    }
}
