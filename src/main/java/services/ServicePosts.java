package services;

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

        String req = "insert into posts(title,content) values('" + posts.getTitle() + "','" + posts.getContent() + "')";

         Statement ste= con.createStatement();
         ste.executeUpdate(req);

    }

    @Override
    public void modifier(Posts posts) throws SQLException {

        String req="update posts set title=? , content=? where id=?";

        PreparedStatement pre= con.prepareStatement(req);
        pre.setString(2,posts.getTitle());
        pre.setString(3, posts.getContent());

        pre.executeUpdate();
    }

    @Override
    public void supprimer(Posts posts) throws SQLException {

                PreparedStatement pre =con.prepareStatement("delete from posts where id=?");
                pre.setInt(1,posts.getId());
                pre.executeUpdate();

    }

    @Override
    public List<Posts> afficher() throws SQLException {

        List<Posts> postsList= new ArrayList<>();



        String req="select * from posts";
        Statement ste=con.createStatement();
        ResultSet res= ste.executeQuery(req);
        while (res.next()){
            Posts p = new Posts();
            p.setTitle(res.getString(2));

            p.setContent(res.getString(3));

            postsList.add(p);
        }




        return postsList;
    }
}
