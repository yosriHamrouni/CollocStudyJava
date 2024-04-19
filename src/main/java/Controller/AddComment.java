package Controller;

import entities.Comments;
import entities.Posts;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.ListView;
import javafx.scene.control.TextArea;
import services.ServiceComments;
import services.ServicePosts;
import utils.BadWordFilter;

import java.sql.SQLException;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

public class AddComment {


    private Posts post;

    @FXML
    private ListView<String> commentListView;

    @FXML
    private TextArea commentTextArea;

    public void setPost(Posts post) {
        this.post = post;
        // You can use the post in this controller as needed
    }






    public void initialize() {
        try {

            ServiceComments sc = new ServiceComments();
            // Retrieve comments associated with the selected post
            List<String> comments = new ArrayList<>(sc.getCommentsForPost(post));

            // Create an ObservableList from the comments list



            ObservableList<String> observableComments = FXCollections.observableArrayList(comments);

            // Populate the ListView with the comments
            commentListView.setItems(observableComments);
        } catch (SQLException e) {
            e.printStackTrace();
            // Handle exception
        }
    }
    public void addComment(ActionEvent actionEvent) throws SQLException {
      //  LocalDateTime dateCommentaire = LocalDateTime.now(); // Get the current date and time


        String censoredText = BadWordFilter.getCensoredText(commentTextArea.getText());

        Comments comment = new Comments(censoredText);


        ServicePosts ps=new ServicePosts();
        //post.setContent(content.getId());
        try {
            ps.addComment(post,comment);
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }

    }
}
