package Controller;

import entities.Posts;
import javafx.application.Platform;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.scene.control.Label;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.HBox;
import javafx.stage.Stage;
import services.ServicePosts;

import java.io.IOException;
import java.sql.SQLException;

public class ShowPost_back {




    @FXML
    private Label content;

    @FXML
    private Label idPost;
    @FXML
    private Label username;

Dashboard_Back db;



    public void setContent(String cont) {
        this.content.setText(cont);
    }

    public void setUsername(String user) {
        this.username.setText(user);
    }
    private Posts post;
    private Posts getPost(){
        Posts post = new Posts();
        post.setId(1);
        post.setTitle("wiw");
        post.setContent("wwiiiw");
        return post;
    }


    private Dashboard_Back dashboardController; // Référence au contrôleur Dashboard_Back

    // Méthode pour définir la référence au contrôleur Dashboard_Back
    public void setDashboardController(Dashboard_Back dashboardController) {
        this.dashboardController = dashboardController;
    }





    public void setData(Posts post){

        this.post= post;

        idPost.setText(String.valueOf(post.getId()));
        username.setText(post.getTitle());
        if(post.getContent() != null && !post.getContent().isEmpty()){
            content.setText(post.getContent());
        }else{
            content.setManaged(false);
        }
    }

    public void DeletePost(MouseEvent mouseEvent) throws SQLException {



        Posts post= new Posts(idPost.getText(),content.getText(),username.getText());
        ServicePosts ps=new ServicePosts();
        //post.setContent(content.getId());
            ps.supprimer(post);

        // Rafraîchir la liste des publications dans l'interface utilisateur

        dashboardController.refreshPosts();
       //db.refreshPosts();


    }

    public void onCommentContainerClicked(MouseEvent mouseEvent) {




        try {


            FXMLLoader fxmlLoader = new FXMLLoader(getClass().getResource("/back_office/ShowComments_back.fxml"));
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


/*
    public void refreshConsultationsView() {
        Platform.runLater(() -> {
            ShowPost_back(null); // Reload all publications
        });
    }

*/
  /*  @FXML
    void DeletePost(MouseEvent event) throws SQLException {



    }*/




























}
