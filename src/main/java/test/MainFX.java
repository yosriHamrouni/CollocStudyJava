package test;

import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Stage;

import java.io.IOException;

public class MainFX extends Application {

  /*  @Override
    public void start(Stage primaryStage) throws IOException {
        FXMLLoader loader = new FXMLLoader(getClass().
                getResource("../AjouterLogement.fxml"));
        try {
            Parent root = loader.load();
           Scene scene = new Scene(root) ;
            primaryStage.setScene(scene);
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }*/
    @Override
    public void start(Stage primaryStage) throws IOException {
        FXMLLoader loader = new FXMLLoader(getClass().getResource("../dashboardFront.fxml"));
        Parent root = loader.load();
        Scene scene =new Scene(root);
        primaryStage.setScene(scene);
        primaryStage.setTitle("Dashboard");
        primaryStage.show();
    }

    public static void main(String[] args) {
        launch(args);
    }

}
