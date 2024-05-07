package gui;

import entities.Coworking;
import entities.TypeCo;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.scene.control.TextField;
import services.ServiceCoworking;
import services.ServiceTypeco;

import java.io.IOException;


public class AjouterTypeCoworking {

    @FXML
    private Button confirmerButton;

    @FXML
    private TextField txttypeco;

    @FXML
    void addtypecoworking(ActionEvent event) throws RuntimeException {
        try {
            String typeco = txttypeco.getText();
            if (typeco.length() < 5) {
                throw new IllegalArgumentException("Type coworking doit contenir au moins 5 caractères.");
            }
            ServiceTypeco sc = new ServiceTypeco();
            TypeCo T = new TypeCo(typeco );

            sc.ajouter(T);
            FXMLLoader loader = new FXMLLoader(getClass().getResource("../AfficherCoworking.fxml"));
            Parent root = loader.load();
            AfficherCoworking ac = loader.getController();
            txttypeco.getScene().setRoot(root);
            //ac.setRlist(sc.afficher().toString);
        } catch (NumberFormatException e) {
            Alert alert = new Alert(Alert.AlertType.ERROR);
            alert.setTitle("Erreur de saisie");
            alert.setContentText("Vous avez une erreur dans la saisie de vos données!");
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

}
