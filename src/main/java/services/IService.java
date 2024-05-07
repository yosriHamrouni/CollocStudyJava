package services;

import entities.Comments;
import entities.Coworking;
import entities.Posts;
import entities.TypeCo;

import java.io.File;
import java.sql.SQLException;
import java.util.List;

import java.sql.SQLException;
import java.util.List;


public interface IService<T> {








    void ajouter(T t) throws SQLException;

    void modifier(T t) throws SQLException;



    void supprimer(T t) throws SQLException;


    List<T> afficher() throws SQLException;


    void incrementLikes(int postId);

    void DecrementLikes(int postId);

    void addComment(Posts post, Comments comment) throws SQLException;

    int getLikeCount(int postId) throws SQLException;

    // Function to get total likes from the database
    int getTotalLikesFromDatabase();

    void saveImageToDatabase(File imageFile, Posts posts);




}