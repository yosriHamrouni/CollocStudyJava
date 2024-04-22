package services;

import java.sql.SQLException;
import java.util.List;

public interface IService <T>{
    void ajouter(T t);
    void modifer(T t);
    void supprimer(T t) throws SQLException;
    List<T> afficher() throws SQLException;
}
