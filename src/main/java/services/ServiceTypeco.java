package services;

import entities.Coworking;
import entities.TypeCo;
import utils.MyDB;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class ServiceTypeco implements IService <TypeCo> {
    private Connection con;

    public ServiceTypeco() {
        con = MyDB.getInstance().getConnection();
    }

    @Override
    public void ajouter(TypeCo typeCo) {
        String req = "INSERT INTO typeco (type) VALUES ('" + typeCo.getType() + "')";

        try {
            Statement ste = con.createStatement();
            ste.executeUpdate(req);

        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }


    @Override
    public void modifer(TypeCo typeCo) {
        String req = "UPDATE typeco SET type = ? WHERE id = ?";
        PreparedStatement pre = null;


        try {
            pre = con.prepareStatement(req);
            pre.setString(1, typeCo.getType());
            pre.setInt(2, typeCo.getId()); // Assurez-vous que votre objet TypeCo contient un attribut id

            pre.executeUpdate();
            System.out.println(" type coworking modifié !");

        } catch (SQLException e) {
            throw new RuntimeException(e);
        }

    }

    @Override
    public void supprimer(TypeCo typeCo) throws SQLException {
        PreparedStatement pre = con.prepareStatement("delete from typeco where id=?");
        pre.setInt(1, typeCo.getId());
        pre.executeUpdate();

    }

    @Override
    public List<TypeCo> afficher() throws SQLException {
        List<TypeCo> listtypeco = new ArrayList<>();
        String req = "select * from typeco";
        Statement ste = con.createStatement();
        ResultSet res = ste.executeQuery(req);
        while (res.next()) {
            TypeCo tc = new TypeCo();
            tc.setId(res.getInt(1));
            tc.setType(res.getString(2));
            listtypeco.add(tc);
        }
        return listtypeco;

    }

    public List<TypeCo> getAllTypes() {
        List<TypeCo> types = new ArrayList<>();

        try {
            String query = "SELECT type FROM typeco";
            Statement statement = con.createStatement();
            ResultSet resultSet = statement.executeQuery(query);

            while (resultSet.next()) {
              //int id = resultSet.getInt("id");
                String type = resultSet.getString("type");

                TypeCo typeCo = new TypeCo(type);
                types.add(typeCo);
            }

            resultSet.close();
            statement.close();

        } catch (SQLException e) {
            e.printStackTrace();
        }

        return types;
    }

}
