package services;

import config.MyDB;
import entities.logement;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class ServiceLogement implements IServices<logement> {
    private Connection con;
    public Statement ste;

    public void setConnection(Connection con) {
        this.con = con;
    }

    public ServiceLogement() {
        con = MyDB.getInstance().getCon();
    }

    public String getDescriptionTypeById(int id_type) throws SQLException {
        String query = "SELECT description FROM typelog WHERE id = ?";
        PreparedStatement pst = con.prepareStatement(query);
        pst.setInt(1, id_type);
        ResultSet rs = pst.executeQuery();
        if (rs.next()) {
            return rs.getString("description");
        } else {
            return null; // Gérer le cas où aucun type avec cet ID de logement n'est trouvé
        }
    }

    @Override
    public void ajouter(logement logement) throws SQLException {
        String req = "insert into logement (adresse, description, equipement, image, dispo, tarifs) values('" + logement.getAdresse() + "','" + logement.getDescription() + "','" + logement.getEquipement() + "','" + logement.getImage() + "','" + logement.getDispo() + "','" + logement.getTarifs() + "')";
        // req avec preparedStement - >    String req2="insert into personne (nom,prenom,age) values (?,?,?)";
        ste = con.createStatement();
        ste.executeUpdate(req);

    }
    /*
  @Override
  public void ajouter(logement logement) throws SQLException {
      String query = "INSERT INTO logement (adresse, description, equipement, image, dispo, tarifs, id_type) VALUES (?, ?, ?, ?, ?, ?, ?)";
      PreparedStatement pst = con.prepareStatement(query);
      pst.setString(5, logement.getAdresse());
      pst.setString(1, logement.getDescription());
      pst.setString(6, logement.getEquipement());
      pst.setString(, logement.getImage());
      pst.setInt(5, logement.getDispo());
      pst.setInt(6, logement.getTarifs());
      pst.setInt(7, logement.getId_Type());
      pst.executeUpdate();
  }*/


    @Override
    public void modifier(logement logement) throws SQLException {
        String req = "UPDATE logement SET adresse=?, description=?, equipement=?, image=?, dispo=?, tarifs=? WHERE id_log=?";
        PreparedStatement pre = con.prepareStatement(req);
        pre.setString(1, logement.getAdresse());
        pre.setString(2, logement.getDescription());
        pre.setString(3, logement.getEquipement());
        pre.setString(4, logement.getImage());
        pre.setInt(5, logement.getDispo());
        pre.setFloat(6, logement.getTarifs());
        pre.setInt(7, logement.getId());
        //pre.setInt(8, logement.getId_type());
        // pre.setInt(8, logement.getId_Type());
        pre.executeUpdate();
    }

    @Override
    public boolean supprimer(logement logement) throws SQLException {
        String req = "DELETE FROM logement WHERE id_log=?";
        PreparedStatement pre = con.prepareStatement(req);
        pre.setInt(1, logement.getId());
        pre.executeUpdate();
        return false;
    }





    @Override
    public List<logement> afficher() throws SQLException {
        List<logement> logements = new ArrayList<>();
        String req = "SELECT * FROM logement";
        try (Statement ste = con.createStatement(); ResultSet res = ste.executeQuery(req)) {
            while (res.next()) {
                int id_log = res.getInt("id_log");
                String adresse = res.getString("adresse");
                String description = res.getString("description");
                String equipement = res.getString("equipement");
                String image = res.getString("image");
                int dispo = res.getInt("dispo");
                int tarifs = res.getInt("tarifs");
                //int id_type = res.getInt("id_type");
                // Ajoutez un logement à la liste en récupérant d'abord l'ID du type du ce logement
               // Integer idtype = getLogementByTypeId(id_type);
                logement l = new logement(id_log, adresse, description, equipement, image, dispo, tarifs);
                logements.add(l);
            }
        }
        return logements;
    }





 /* @Override
  public List<logement> afficher() throws SQLException {
      List<logement> logements = new ArrayList<>();
      String req = "SELECT * FROM logement";
      try (Statement ste = con.createStatement(); ResultSet res = ste.executeQuery(req)) {
          while (res.next()) {
              int id_log = res.getInt("id_log");
              String adresse = res.getString("adresse");
              String description = res.getString("description");
              String equipement = res.getString("equipement");
              String image = res.getString("image");
              int dispo = res.getInt("dispo");
              int tarifs = res.getInt("tarifs");
              int id_type = res.getInt("id_type");

              // Récupérer la description du type de logement en utilisant son ID
              String descriptionType = getDescriptionTypeById(id_type);

              // Créer l'objet logement avec la description du type de logement
              logement l = new logement(id_log, adresse, description, equipement, image, dispo, tarifs);
             // l.setDescriptionType(descriptionType);

              // Ajouter l'objet logement à la liste
              logements.add(l);
          }
      }
      return logements;
  }*/


}
