package services;



import java.sql.SQLException;
import java.util.List;

public interface IServices<T>{
    void ajouter(T t) throws SQLException;
    void modifier(T t) throws SQLException;

    boolean supprimer(T t) throws SQLException;
    List<T> afficher() throws SQLException;
}
