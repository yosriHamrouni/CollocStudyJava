package services;

import config.MyDB;
import entities.logement;
import entities.typelog;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;

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
        String req = "INSERT INTO logement (adresse, description, equipement, image, dispo, tarifs, id_type) " +
                "VALUES ('" + logement.getAdresse() + "','" + logement.getDescription() + "','" +
                logement.getEquipement() + "','" + logement.getImage() + "','" + logement.getDispo() + "','" +
                logement.getTarifs() + "','" + logement.getTypelog().getId() + "')";

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
    public List<logement> afficher() throws SQLException {
        List<logement> listLogements = new ArrayList<>();
        String query = "SELECT * FROM logement";
        try (PreparedStatement preparedStatement = con.prepareStatement(query)) {
            ResultSet resultSet = preparedStatement.executeQuery();
            while (resultSet.next()) {
                logement logement = new logement();
                logement.setId_log(resultSet.getInt("id_log"));
                logement.setAdresse(resultSet.getString("adresse"));
                logement.setEquipement(resultSet.getString("equipement"));
                logement.setDescription(resultSet.getString("description"));
                logement.setImage(resultSet.getString("image"));
                logement.setDispo(resultSet.getInt("dispo"));
                logement.setTarifs((int) resultSet.getFloat("tarifs"));
                logement.setId_type(resultSet.getInt("id_type"));



                listLogements.add(logement);
            }
        }
        return listLogements;
    }
    // Function to get engagement levels from the database
    public int[] getPriceLevels() {
        int highEngagementThreshold = 700;
        int moderateEngagementThreshold = 300;

        int highEngagementCount = 0;
        int moderateEngagementCount = 0;




        int lowEngagementCount = 0;



        try {


            String query = "SELECT tarifs FROM logement";
            PreparedStatement preparedStatement = con.prepareStatement(query);
            ResultSet resultSet = preparedStatement.executeQuery();
            while (resultSet.next()) {
                int tarifs = resultSet.getInt("tarifs");
                if (tarifs > highEngagementThreshold) {
                    highEngagementCount++;
                } else if (tarifs >= moderateEngagementThreshold) {
                    moderateEngagementCount++;
                } else {
                    lowEngagementCount++;
                }
            }
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            try {
                if (con!= null) {
                    con.close();
                }
            } catch (Exception e) {
                e.printStackTrace();
            }
        }

        return new int[]{highEngagementCount, moderateEngagementCount, lowEngagementCount};
    }

  /*  @Override
    public List<logement> afficher() throws SQLException {
        List<logement> listLogements = new ArrayList<>();
        String query = "SELECT * FROM logement";
        try (PreparedStatement preparedStatement = con.prepareStatement(query)) {
            ResultSet resultSet = preparedStatement.executeQuery();
            while (resultSet.next()) {
                logement logement = new logement();
                logement.setId_log(resultSet.getInt("id_log"));
                // Récupérer les autres attributs du logement

                // Récupérer le type du logement à partir de l'ID du type
                int typeId = resultSet.getInt("id_type");
                typelog type = fetchTypeById(typeId);
                logement.setTypelog(type);

                listLogements.add(logement);
            }
        }
        return listLogements;
    }*/

    public typelog fetchTypeById(int typeId) throws SQLException {
        String query = "SELECT * FROM typelog WHERE id = ?";
        PreparedStatement pst = con.prepareStatement(query);
        pst.setInt(1, typeId);
        ResultSet rs = pst.executeQuery();
        if (rs.next()) {
            typelog type = new typelog();
            type.setId(rs.getInt("id"));
            type.setType(rs.getString("type"));
            type.setDescription(rs.getString("description"));
            // Autres attributs du type de logement si nécessaire
            return type;
        } else {
            return null; // Gérer le cas où aucun type avec cet ID n'est trouvé
        }
    }


    @Override
    public void modifier(logement logement) throws SQLException {
        String req = "UPDATE logement SET adresse=?, description=?, equipement=?, image=?, dispo=?, tarifs=?,id_type=? WHERE id_log=?";
        PreparedStatement pre = con.prepareStatement(req);
        pre.setString(1, logement.getAdresse());
        pre.setString(2, logement.getDescription());
        pre.setString(3, logement.getEquipement());
        pre.setString(4, logement.getImage());
        pre.setInt(5, logement.getDispo());
        pre.setFloat(6, logement.getTarifs());
        pre.setInt(8, logement.getId());
        pre.setInt(7, logement.getTypelog().getId());
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


    // Méthode pour récupérer tous les logements depuis la base de données
    public List<logement> getAllLogements() throws SQLException {
        // Utilisez votre méthode afficher() de ServiceLogement pour récupérer les logements
        ServiceLogement serviceLogement = new ServiceLogement();
        List<logement> logements = serviceLogement.afficher();
        return logements;
    }


  /*  @Override
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
    }*/





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
