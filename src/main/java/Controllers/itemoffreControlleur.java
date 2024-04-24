package Controllers;

import entities.Offres;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
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

    public void setData(Offres offres) {
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
            // Par exemple : imagePub.setImage(new Image("/images/default.png"));
            System.out.println("L'image n'a pas pu être chargée : " + imagePath);
        }
    }

    @FXML
    void onclickdetail(javafx.event.ActionEvent event) {
        // Récupérer le texte de l'ID Label
        String idText = idLabel.getText().trim();

        //System.out.println("Valeur de idLabel : " + idText); // Ajout pour débogage

        // Vérifier si le texte est vide
        if (idText.isEmpty()) {
            System.out.println("ID Label est vide.");
            return; // Sortir de la méthode
        }

        try {
            // Convertir le texte en entier
            int id = Integer.parseInt(idText);

            // Récupérer l'offre correspondante à partir de votre source de données
            Offres offre = ServiceOffres.getoffreId(id); // Assurez-vous que ServiceOffres est correctement défini

            if (offre != null) {
                try {
                    // Charger la vue FXML de détails
                    FXMLLoader loader = new FXMLLoader(getClass().getResource("/detailsoffres.fxml"));
                    Parent root = loader.load();

                    // Accéder au contrôleur de vue de détails
                    DetailsOffresController controller = loader.getController();

                    // Appeler la méthode setDatadetail du contrôleur de vue de détails pour initialiser les données de l'offre
                    controller.setDatadetail(offre);

                    // Créer une nouvelle fenêtre (Stage) pour afficher les détails de l'offre
                    Stage stage = new Stage();
                    stage.setScene(new Scene(root));
                    stage.show();
                } catch (IOException e) {
                    e.printStackTrace();
                }
            } else {
                // Gérer le cas où l'offre est null
                System.out.println("Aucune offre trouvée avec l'ID : " + id);
            }
        } catch (NumberFormatException e) {
            System.out.println("Format d'ID invalide : " + idText);
        }
    }
}
