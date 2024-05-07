package Controllers;

import entities.Offres;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.AnchorPane;
import javafx.stage.Stage;

import java.io.InputStream;

public class DetailsOffresController {

    @FXML
    private Label descrip;

    @FXML
    private AnchorPane det_id;

    @FXML
    private Label horairedeb;

    @FXML
    private Label horairefin;

    @FXML
    private ImageView imgview;

    @FXML
    private Label lieu;

    @FXML
    private Label salaire;

    @FXML
    private Label Numtel;

    @FXML
    private Button cancelButton;

    private Offres offres;

    public void setDatadetail(Offres offres) {
        this.offres = offres;

        if (offres != null) {
            this.descrip.setText(offres.getDescrip());
            this.salaire.setText(String.valueOf(offres.getSalaire()));
            this.horairedeb.setText(offres.getHorairedeb());
            this.horairefin.setText(offres.getHoraireter());
            this.lieu.setText(offres.getLieu());
            this.Numtel.setText(String.valueOf(offres.getNum_tel()));

            String imagePath ="/img/"+offres.getImage();
            InputStream imageStream = getClass().getResourceAsStream(imagePath);
            if (imageStream != null) {
                Image image = new Image(imageStream);
                imgview.setImage(image);
            } else {
                // Image par défaut si l'image spécifiée n'est pas trouvée
                // Par exemple : imagePub.setImage(new Image("/images/default.png"));
                System.out.println("L'image n'a pas pu être chargée : " + imagePath);
            }




            // Assurez-vous d'attribuer les autres champs de manière similaire
            // ...
        } else {
            // Gérer le cas où coworking est null
        }
    }

    @FXML
    public void onclickfermer(javafx.event.ActionEvent actionEvent) {
        Stage stage = (Stage) cancelButton.getScene().getWindow();
        stage.close();
    }
}
