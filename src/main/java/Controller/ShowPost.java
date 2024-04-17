package Controller;

import entities.Posts;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Label;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.HBox;
import javafx.stage.Stage;

import java.io.IOException;
import java.net.URL;
import java.util.ResourceBundle;

public class ShowPost {


    @FXML
    private Label content;
    @FXML
    private Label username;

    private Posts post;

    @FXML
    private HBox commentContainer;


    public void setContent(String cont) {
        this.content.setText(cont);
    }

    public void setUsername(String user) {
        this.username.setText(user);
    }

    private Posts getPost(){
        Posts post = new Posts();
        post.setTitle("wiw");
        post.setContent("wwiiiw");
        return post;
    }





    public void setData(Posts post){

        this.post= post;
        username.setText(post.getTitle());
        if(post.getContent() != null && !post.getContent().isEmpty()){
            content.setText(post.getContent());
        }else{
            content.setManaged(false);
        }
    }

    public void onReactionImgPressed(MouseEvent mouseEvent) {
    }

    public void onLikeContainerPressed(MouseEvent mouseEvent) {
    }

    public void onLikeContainerMouseReleased(MouseEvent mouseEvent) {
    }

    @FXML
    void addComment(MouseEvent event) {
        try {
            // Load the comment FXML file
            FXMLLoader fxmlLoader = new FXMLLoader(getClass().getResource("/AddComments.fxml"));


            Parent commentRoot = fxmlLoader.load();

            // Create a new stage to display the comment scene
            Stage commentStage = new Stage();
            commentStage.setTitle("Add Comment");
            commentStage.setScene(new Scene(commentRoot));

            // Show the comment stage
            commentStage.show();
        } catch (IOException e) {
            e.printStackTrace();
            // Handle the exception
        }

    }








}
