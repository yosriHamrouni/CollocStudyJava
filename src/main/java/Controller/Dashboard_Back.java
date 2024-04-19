package Controller;

import entities.Posts;
import javafx.application.Platform;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;
import services.ServicePosts;

import java.io.IOException;
import java.net.URL;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;
import java.util.ResourceBundle;

public class Dashboard_Back implements Initializable {




    @FXML
    private VBox postContainer;
    List<Posts> posts;



/*

    @Override
    public void initialize(URL location, ResourceBundle resourceBundle) {
        Platform.runLater(() -> {
            ServicePosts ps = new ServicePosts();
            try {
                posts = new ArrayList<>(ps.afficher());
            } catch (SQLException e) {
                throw new RuntimeException(e);
            }

            for (Posts post : posts) {
                FXMLLoader fxmlLoader = new FXMLLoader();
                fxmlLoader.setLocation(getClass().getResource("/back_office/item.fxml"));
                try {
                    VBox vBox = fxmlLoader.load();
                    ShowPost_back showPost = fxmlLoader.getController();
                    showPost.setData(post);
                    postContainer.getChildren().add(vBox);
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
        });
    }
*/

    @Override
    public void initialize(URL location, ResourceBundle resourceBundle) {
        // Initialiser les publications lors du chargement de la vue
      //  loadPosts();


        refreshPosts();
    }

    // Méthode pour charger les publications
    private void loadPosts() {
        Platform.runLater(() -> {


            postContainer.getChildren().clear(); // Effacer les publications existantes




            ServicePosts ps = new ServicePosts();
            try {
                posts = new ArrayList<>(ps.afficher());
            } catch (SQLException e) {
                throw new RuntimeException(e);
            }

            for (Posts post : posts) {
                FXMLLoader fxmlLoader = new FXMLLoader();
                fxmlLoader.setLocation(getClass().getResource("/back_office/item.fxml"));
                try {
                    VBox vBox = fxmlLoader.load();
                    ShowPost_back showPost = fxmlLoader.getController();
                    showPost.setData(post);
                    showPost.setDashboardController(this);
                    postContainer.getChildren().add(vBox);

                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
        });
    }

    // Méthode pour rafraîchir les publications




    public void refreshPosts() {
        loadPosts(); // Appeler loadPosts() pour rafraîchir les publications
    }








}
