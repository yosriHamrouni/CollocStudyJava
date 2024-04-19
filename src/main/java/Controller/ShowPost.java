package Controller;

import entities.Posts;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Label;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.HBox;
import javafx.stage.Stage;

import java.awt.event.ActionEvent;
import java.io.IOException;

public class ShowPost {


    @FXML
    private Label content;
    @FXML
    private Label username;
    @FXML
    private Label idPost;


    private Posts post;

    @FXML
    private HBox commentContainer;
    private AddPost AddPostController; // Référence au contrôleur Dashboard_Back

    // Méthode pour définir la référence au contrôleur Dashboard_Back
    public void setAddPostController(AddPost AddPostController) {
        this.AddPostController = AddPostController;
    }

    public void setContent(String cont) {
        this.content.setText(cont);
    }

    public void setUsername(String user) {
        this.username.setText(user);
    }

    private Posts getPost(){
        Posts post = new Posts();
        post.setId(Integer.parseInt(idPost.getText()));
        post.setTitle(username.getText());
        post.setContent(content.getText());
        return post;
    }





    public void setData(Posts post){

        this.post= post;
        String s=Integer.toString(post.getId());

        idPost.setText(String.valueOf(post.getId()));
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
    void OnCommentContainerClicked(MouseEvent event) {

        try {



            FXMLLoader fxmlLoader = new FXMLLoader(getClass().getResource("/front_office/AddComments.fxml"));
            Parent root = fxmlLoader.load();

            // Get the controller instance
            AddComment addComment = fxmlLoader.getController();

            // Set the post data
            addComment.setPost(getPost());
            // Assuming selectedPost is the post you want to edit

            Stage stage = new Stage();
            stage.setTitle("Add Comment");
            stage.setScene(new Scene(root, 450, 450));
            stage.show();
        } catch (IOException e) {
            e.printStackTrace();
        }



    }


@FXML
    void editPost(MouseEvent event) {
        try {



            FXMLLoader fxmlLoader = new FXMLLoader(getClass().getResource("/front_office/editPost.fxml"));
            Parent root = fxmlLoader.load();

            // Get the controller instance
            editPost editPost = fxmlLoader.getController();

            // Set the post data
            editPost.setPost(getPost());
            // Assuming selectedPost is the post you want to edit

            Stage stage = new Stage();
            stage.setTitle("Edit Post");
            stage.setScene(new Scene(root, 450, 450));
            stage.show();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }











}
