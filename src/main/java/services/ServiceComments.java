package services;

import entities.Posts;
import utils.MyDB;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

public class ServiceComments {

    private Connection con;

    public ServiceComments() {
        con = MyDB.getInstance().getConnection();
    }

    // Method to retrieve comments associated with a specific post
    public List<String> getCommentsForPost(Posts post) throws SQLException {
        List<String> comments = new ArrayList<>();

        String query = "SELECT contenu FROM comment WHERE publication_id = ?";
        try (PreparedStatement statement = con.prepareStatement(query)) {
            statement.setInt(1, post.getId());
            ResultSet resultSet = statement.executeQuery();

            while (resultSet.next()) {
                String content = resultSet.getString("contenu");
                comments.add(content);
            }
        }

        return comments;
    }
}
