package services;

import entities.Comments;
import entities.Posts;
import utils.MyDB;


import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class ServicePosts implements  IService<Posts>{

    private Connection con;


    public ServicePosts(){
        con= MyDB.getInstance().getConnection();

    }
    @Override
    public void ajouter(Posts posts) throws SQLException {

        String req = "insert into publication(title,contenu_pub) values('" + posts.getTitle() + "','" + posts.getContent() + "')";

         Statement ste= con.createStatement();
         ste.executeUpdate(req);

    }

    @Override
    public void modifier(Posts posts) throws SQLException {

        String req="update publication set title=? , contenu_pub=? where id=?";

        PreparedStatement pre= con.prepareStatement(req);


        // Set the values of the parameters
        pre.setString(1, posts.getTitle()); // Use index 1 for the first parameter
        pre.setString(2, posts.getContent()); // Use index 2 for the second parameter
        pre.setInt(3, Posts.getId()); // Use index 3 for the third parameter


        pre.executeUpdate();
    }

    @Override
    public void supprimer(Posts posts) throws SQLException {

                PreparedStatement pre =con.prepareStatement("delete from publication where id=?");
                pre.setInt(1,posts.getId());
                pre.executeUpdate();

    }

    @Override
    public List<Posts> afficher() throws SQLException {

        List<Posts> postsList= new ArrayList<>();



        String req="select * from publication";
        Statement ste=con.createStatement();
        ResultSet res= ste.executeQuery(req);
        while (res.next()){
            Posts p = new Posts();
            p.setId(res.getInt(1));
            p.setTitle(res.getString(2));
            p.setContent(res.getString(3));
            postsList.add(p);
        }
        return postsList;
    }


    @Override
    public void addComment(Posts post, Comments comment) throws SQLException {
        String req = "INSERT INTO comment(publication_id, contenu, date_commentaire) VALUES (?, ?, ?)";
        try (PreparedStatement pre = con.prepareStatement(req)) {
            pre.setInt(1, post.getId()); // Set the post_id foreign key
            pre.setString(2, comment.getContent()); // Set the content of the comment
            pre.setTimestamp(3, Timestamp.valueOf(comment.getDateCommentaire())); // Set the date_commentaire
            pre.executeUpdate();
        }
        // Set the content of the comment

    }








}
