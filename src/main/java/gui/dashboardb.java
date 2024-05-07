package gui;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.stage.Stage;

import java.io.IOException;

public class dashboardb {

    @FXML
    private Button cof;

    @FXML
    private Button logf;

    @FXML
    void coworking(ActionEvent event) {
        try {
            FXMLLoader loader = new FXMLLoader(getClass().getResource("../AffichercoF.fxml"));
            Parent root = loader.load();

            Scene scene = new Scene(root);
            Stage stage = new Stage();
            stage.setScene(scene);

            stage.show();
        } catch (IOException e) {
            e.printStackTrace();
        }

    }







    public void restaurent(ActionEvent actionEvent) {
    }

    public void restaurant(ActionEvent actionEvent) {
    }

    public void offres(ActionEvent actionEvent) {
    }

    public void filactu(ActionEvent actionEvent) {
    }

    public void activite(ActionEvent actionEvent) {
    }

    public void AfficherLogF(ActionEvent actionEvent) {
        try {
            FXMLLoader loader = new FXMLLoader(getClass().getResource("../AfficherLogF.fxml"));
            Parent root = loader.load();

            Scene scene = new Scene(root);
            Stage stage = new Stage();
            stage.setScene(scene);

            stage.show();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
