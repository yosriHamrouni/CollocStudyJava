package Controllers;

import entities.Offres;
import entities.TypeOffres;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.Alert;
import javafx.scene.control.ComboBox;
import javafx.scene.control.TextField;
import javafx.stage.Stage;
import services.ServiceOffres;
import services.ServiceType;

import java.sql.SQLException;
import java.util.List;

public class AjouterOffreController {

    private final ServiceOffres so = new ServiceOffres();
    private Stage stage;

    @FXML
    private TextField DescripTF;
    @FXML
    private ComboBox<TypeOffres> typeComboBox;
    @FXML
    private TextField HorairedebTF;
    @FXML
    private TextField HoraireterTF;

    @FXML
    private TextField LieuTF;
    @FXML
    private TextField NumtelTF;
    @FXML
    private TextField SalaireTF;

    public void setStage(Stage stage) {
        this.stage = stage;
    }

    @FXML
    void AjouterOffre(ActionEvent event) {
        try {
            TypeOffres selectedType = typeComboBox.getValue();
               // public Offres(String descrip, Double salaire, String horairedeb, String horaireter, String lieu, String num_tel, int typeoffre_id ) {

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
        }
    }
    @FXML
    void initialize() {
        // Initialisation du ComboBox avec les types de coworking
        try {
            ServiceType serviceType = new ServiceType();
            List<TypeOffres> types = serviceType.afficher();
            typeComboBox.getItems().addAll(types);
        } catch (SQLException e) {
            Alert alert = new Alert(Alert.AlertType.ERROR);
            alert.setTitle("Erreur");
            alert.setContentText("Erreur lors de la récupération des types de coworking.");
            alert.show();
        }
    }
}
