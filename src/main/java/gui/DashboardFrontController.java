package gui;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.layout.AnchorPane;
import javafx.stage.Stage;

import java.io.IOException;

public class DashboardFrontController {

    @FXML
    private AnchorPane midlast;
    @FXML
    private Button buttonnid;

    @FXML
    void AfficherCoworking(ActionEvent event) {
        try {
            // Load the AfficherCoworking.fxml file
            Parent root = FXMLLoader.load(getClass().getResource("../AfficherCoworking.fxml"));

            // Show the scene containing the AjouterCoworking.fxml file
            Stage stage = new Stage();
            stage.setScene(new Scene(root));
            stage.show();
        } catch (IOException e) {
            e.printStackTrace();
        }


    }



}
