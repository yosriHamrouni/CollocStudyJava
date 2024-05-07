package Controller;

import entities.Posts;

import javafx.application.Platform;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.image.Image;
import javafx.scene.input.MouseEvent;
import javafx.stage.FileChooser;

import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.scene.image.ImageView;
import javafx.scene.layout.VBox;
import services.ServicePosts;
import utils.MyDB;

import java.io.File;
import java.io.IOException;
import java.net.URL;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;
import java.util.ResourceBundle;

public class AddPost implements Initializable {













    @FXML
    private Button AddPost;

    @FXML
    private ImageView audience;

    @FXML
    private Label caption;

    @FXML
    private TextField content;




    private ServicePosts servicePosts;
    @FXML
    private VBox postContainer;

    List<Posts> posts;





    @FXML
    void AddPost(MouseEvent event) throws SQLException {

        Posts post= new Posts(content.getText() ,"wiw");
        ServicePosts ps=new ServicePosts();


        //post.setContent(content.getId());
        ps.ajouter(post);




    }



























    public void onReactionImgPressed(MouseEvent mouseEvent) {
    }

    public void onLikeContainerPressed(MouseEvent mouseEvent) {
    }

    public void onLikeContainerMouseReleased(MouseEvent mouseEvent) {
    }


    @Override
    public void initialize(URL location, ResourceBundle resourceBundle) {
       refreshPosts();
    }


    private void loadPosts() {


      //  postContainer.getChildren().clear(); // Effacer les publications existantes

        Platform.runLater(() -> {
            ServicePosts ps = new ServicePosts();
            try {
                posts = new ArrayList<>(ps.afficher());


            } catch (SQLException e) {
                throw new RuntimeException(e);
            }

            for (Posts post : posts) {
                FXMLLoader fxmlLoader = new FXMLLoader();
                fxmlLoader.setLocation(getClass().getResource("/front_office/AfficherPost.fxml"));
                try {
                    VBox vBox = fxmlLoader.load();
                    ShowPost showPost = fxmlLoader.getController();
                    showPost.setData(post);
                    postContainer.getChildren().add(vBox);
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
        });
    }

    public void refreshPosts() {
        loadPosts(); // Appeler loadPosts() pour rafraîchir les publications
    }

    public void AddImage(ActionEvent actionEvent) {
        Posts post = new Posts(content.getText(), "wiw");

        ServicePosts Sp = new ServicePosts();
        FileChooser fileChooser = new FileChooser();
        fileChooser.setTitle("Choose Image File");
        fileChooser.getExtensionFilters().addAll(
                new FileChooser.ExtensionFilter("Image Files", "*.png", "*.jpg", "*.gif", "*.bmp", "*.jpeg")
        );
        File selectedFile = fileChooser.showOpenDialog(null);
        if (selectedFile != null) {
            String imagePathInDatabase = selectedFile.getAbsolutePath();

            Image image = new Image(selectedFile.toURI().toString());

            Sp.saveImageToDatabase(selectedFile, post);
        }
    }






/*
    private void displayPosts() {
        try {
            List<Posts> posts =servicePosts.afficher() ;
            for (Posts post : posts) {


                // Create UI components to represent each post
                Label titleLabel = new Label(post.getTitle());
                Label contentLabel = new Label(post.getContent());
                // Add them to postContainer
                postContainer.getChildren().addAll(titleLabel, contentLabel);
            }
        } catch (SQLException e) {
            e.printStackTrace();
            // Handle exception
        }
    }*/






}



