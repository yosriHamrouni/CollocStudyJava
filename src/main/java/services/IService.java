package services;

import java.util.List;
import java.sql.SQLException;


public interface IService<T> {
    // CRUD
    void ajouter(T t) throws SQLException;
    void modifier(T t) throws SQLException;
    void supprimer(T t) throws SQLException;
    List<T> afficher() throws SQLException;

}
