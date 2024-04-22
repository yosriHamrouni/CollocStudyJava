package services;


import entities.typelog;
        import config.MyDB;
        import java.sql.*;
        import java.util.ArrayList;
        import java.util.List;

public class ServiceTypelog implements IServices<typelog> {
    private Connection con;
    public Statement ste;
    public void setConnection(Connection con) {
        this.con = con;
    }
    public ServiceTypelog(){
        con= MyDB.getInstance().getCon();
    }
    @Override
    public void ajouter(typelog typelog) throws SQLException {
        String req = "insert into typelog (type,description) values('"+typelog.getType()+"','"+typelog.getDescription()+"')";
        // req avec preparedStement - >    String req2="insert into personne (nom,prenom,age) values (?,?,?)";
        ste = con.createStatement();
        ste.executeUpdate(req);

    }



    @Override
    public void modifier(typelog typelog) throws SQLException {
        String req = "UPDATE typelog SET type=?, description=? WHERE id_type=?";
        PreparedStatement pre = con.prepareStatement(req);
        pre.setString(2, typelog.getDescription());
        pre.setInt(3, typelog.getId());
        pre.setString(1,typelog.getType());
        pre.executeUpdate();
    }

    @Override
    public boolean supprimer(typelog typelog) throws SQLException {
        String req = "DELETE FROM typelog WHERE id_type=?";
        PreparedStatement pre = con.prepareStatement(req);
        pre.setInt(1, typelog.getId());
        pre.executeUpdate();
        return false;
    }

    @Override
    public List<typelog> afficher() throws SQLException {
        List<typelog> typelogs = new ArrayList<>();
        String req = "SELECT * FROM typelog";
        try (Statement ste = con.createStatement(); ResultSet res = ste.executeQuery(req)) {
            while (res.next()) {
                int id_type = res.getInt("id_type");
                String description = res.getString("description");
                String type = res.getString("type");
                typelog l = new typelog(id_type,type,description);
                typelogs.add(l);
            }
        }
        return typelogs;
    }
    public List<typelog> getAllType() throws SQLException {
        List<typelog> types = new ArrayList<>();
        String req = "SELECT * FROM typelog";
        PreparedStatement preparedStatement = con.prepareStatement(req);
        ResultSet resultSet = preparedStatement.executeQuery();
        while (resultSet.next()) {
            typelog type = new typelog(resultSet.getInt("id"), resultSet.getString("type"),resultSet.getString("description"));
            types.add(type);
        }
        return types;
    }
}
