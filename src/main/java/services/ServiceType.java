package services;

import entities.TypeOffres;
import utils.MyDB;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class ServiceType implements IServiceOffres <TypeOffres> {
    private Connection con;
    public ServiceType() {
        con = MyDB.getInstance().getConnection();
    }

    @Override
    public void ajouter(TypeOffres typeOffres) throws SQLException {
        String req = "INSERT INTO typeoffre (type) VALUES ('" +
                typeOffres.getType() + "')";


        Statement ste = con.createStatement();
        ste.executeUpdate(req);

    }
    @Override
    public void modifier(TypeOffres typeOffres) throws SQLException {
        String req = "UPDATE typeoffre SET type=? WHERE id=?";

        PreparedStatement pre = con.prepareStatement(req);
        pre.setString(1, typeOffres.getType());
        pre.setInt(2, typeOffres.getId());
        pre.executeUpdate();
    }

    @Override
    public void supprimer(TypeOffres typeOffres) throws SQLException {
        PreparedStatement pre = con.prepareStatement("delete from typeoffre where id=?");
        pre.setInt(1,typeOffres.getId());
        pre.executeUpdate();

    }

    @Override
    public List<TypeOffres> afficher() throws SQLException {
        List<TypeOffres> listTypeOffres = new ArrayList<>();
        String req = "SELECT * FROM typeoffre";
        try (Statement ste = con.createStatement();
             ResultSet res = ste.executeQuery(req)) {
            while (res.next()) {
                TypeOffres typeOffres = new TypeOffres(res.getInt("id"), res.getString("type"));
                listTypeOffres.add(typeOffres);
            }
        }
        return listTypeOffres;
    }
    public List<TypeOffres> getAllType() throws SQLException {
        List<TypeOffres> types = new ArrayList<>();
        String req = "SELECT * FROM typeoffre";
        PreparedStatement preparedStatement = con.prepareStatement(req);
        ResultSet resultSet = preparedStatement.executeQuery();
        while (resultSet.next()) {
            TypeOffres type = new TypeOffres (resultSet.getInt("id"), resultSet.getString("type"));
            types.add(type);
        }
        return types;
    }

}
