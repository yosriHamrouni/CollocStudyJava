package services;

import entities.Coworking;
import javafx.collections.ObservableList;
import config.MyDB;

import java.sql.*;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class ServiceCoworking implements IServiceCoworking <Coworking>{
    private Connection con;
    private List<Coworking> coworkingList;



    public ServiceCoworking(List<Coworking> coworkingList) {
        this.coworkingList = coworkingList;
    }
    public ServiceCoworking(){
        con= MyDB.getInstance().getCon();
    }
    @Override
    public void ajouter(Coworking coworking) {


        String req = "INSERT INTO coworking (description, horaireouvr, horairefer, typeco_id, image, nomco, numtel, adresse, tarifs, dispo) VALUES ('"
                + coworking.getDescription() + "','"
                + coworking.getHoraireouvr() + "','"
                + coworking.getHorairefer() + "','"
                + coworking.getTypeco_id() + "','"
                + coworking.getImage() + "','"
                + coworking.getNomco() + "','"
                + coworking.getNumtel() + "','"
                + coworking.getAdresse() + "',"
                + coworking.getTarifs() + ","
                + coworking.getDispo() + ")";


        try {
            Statement ste = con.createStatement();
            ste.executeUpdate(req);

        } catch (SQLException e) {
            throw new RuntimeException(e);
        }


    }

<<<<<<< HEAD
=======


>>>>>>> ff868ae (Gestion coworking+ gestion logement + gestion newsfeed)
    @Override
    public void modifer(Coworking coworking) {
        String req = "UPDATE coworking SET description=?, horaireouvr=?, horairefer=?, image=?, nomco=?, numtel=?, adresse=?, tarifs=?, dispo=? WHERE id=?";


        PreparedStatement pre = null;
        try {
            pre = con.prepareStatement(req);
            pre.setString(1,coworking.getDescription());
            pre.setString(2,coworking.getHoraireouvr());
            pre.setString(3,coworking.getHorairefer());
            pre.setString(4,coworking.getImage());
            pre.setString(5,coworking.getNomco());
            pre.setString(6,coworking.getNumtel());
            pre.setString(7,coworking.getAdresse());
            pre.setFloat(8,coworking.getTarifs());
            pre.setInt(9,coworking.getDispo());
            pre.setInt(10,coworking.getId());

            pre.executeUpdate();
            System.out.println("coworking modifié !");

        } catch (SQLException e) {
            throw new RuntimeException(e);
        }



    }

    @Override
    public void supprimer(Coworking coworking) throws SQLException {
        PreparedStatement pre= con.prepareStatement("delete from coworking where id=?");
        pre.setInt(1,coworking.getId());
        pre.executeUpdate();


    }

    @Override
    public List<Coworking> afficher() throws SQLException {
        List<Coworking>listcoworkings=new ArrayList<>();
        String req="select * from coworking";
        Statement ste= con.createStatement();
        ResultSet res=ste.executeQuery(req);
        while (res.next()){
            Coworking c= new Coworking();
            c.setId(res.getInt(1));
            c.setDescription(res.getString(2));
            c.setTarifs(res.getFloat(3));
            c.setDispo(res.getInt(4));
            c.setHoraireouvr(res.getString(5));
            c.setHorairefer(res.getString(6));
            c.setImage(res.getString(8));
            c.setNomco(res.getString(9));
            c.setNumtel(res.getString(10));
            c.setAdresse(res.getString(13));
            c.setTypeco_id(res.getInt(7));
            listcoworkings.add(c);
        }
        return listcoworkings;
    }
    public List<Coworking> rechercherCoworking(String critere ,float tarifs) throws SQLException {
        List<Coworking> resultats = new ArrayList<>();
        String req = "SELECT * FROM coworking WHERE nomco LIKE ? OR tarifs <= ?";

        try (PreparedStatement pre = con.prepareStatement(req)) {
            // Utilisation du caractère de joker "%" pour rechercher des correspondances partielles
            pre.setString(1, "%" + critere + "%");
            pre.setFloat(2, tarifs);


            try (ResultSet res = pre.executeQuery()) {
                while (res.next()) {
                    Coworking coworking = new Coworking();
                    coworking.setId(res.getInt("id"));
                    coworking.setDescription(res.getString("description"));
                    coworking.setHoraireouvr(res.getString("horaireouvr"));
                    coworking.setHorairefer(res.getString("horairefer"));
                    coworking.setImage(res.getString("image"));
                    coworking.setNomco(res.getString("nomco"));
                    coworking.setNumtel(res.getString("numtel"));
                    coworking.setAdresse(res.getString("adresse"));
                    coworking.setTarifs(res.getFloat("tarifs"));
                    coworking.setDispo(res.getInt("dispo"));
                    resultats.add(coworking);
                }
            }
        }
        return resultats;
    }
    public static Coworking getCoworkingarId(int id) {
        Connection conn = null;
        PreparedStatement stmt = null;
        ResultSet rs = null;
        Coworking coworking = null;

        try {
            conn = MyDB.getInstance().getCon();
            if (conn == null) {
                throw new SQLException("La connexion à la base de données est nulle.");
            }

            String query = "SELECT * FROM coworking WHERE id = ?";
            stmt = conn.prepareStatement(query);
            stmt.setInt(1, id);
            rs = stmt.executeQuery();

            // Si une ligne est renvoyée, créer un objet Coworking
            if (rs.next()) {
                coworking = new Coworking();
                coworking.setId(rs.getInt("id"));
                coworking.setNomco(rs.getString("nomco"));
                coworking.setNumtel(rs.getString("numtel"));
                coworking.setAdresse(rs.getString("adresse"));
                coworking.setTarifs((float) rs.getDouble("tarifs"));
                coworking.setDescription(rs.getString("description"));
                coworking.setImage(rs.getString("image"));
                // Vous devrez peut-être ajouter d'autres attributs ici en fonction de votre modèle Coworking
            }
        } catch (SQLException e) {
            e.printStackTrace();
            // Vous pouvez gérer l'exception en renvoyant une valeur par défaut ou en lançant une nouvelle exception personnalisée
        } finally {
            // Ne fermez pas les ressources ici
        }

        return coworking;
    }


    public Map<String, Integer> getCoworkingByAdresse() {
        Map<String, Integer> coworkingByAdresse = new HashMap<>();

        try {
            String query = "SELECT adresse, COUNT(*) AS count FROM coworking GROUP BY adresse";
            PreparedStatement preparedStatement = con.prepareStatement(query);
            ResultSet resultSet = preparedStatement.executeQuery();
            while (resultSet.next()) {
                String adresse = resultSet.getString("adresse");
                int count = resultSet.getInt("count");
                coworkingByAdresse.put(adresse, count);
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }

        return coworkingByAdresse;
    }

    public List<Coworking> trierParTarifs(ObservableList<Coworking> coworkingList)throws SQLException {
        List<Coworking> listcoworkings = new ArrayList<>();
        String req = "SELECT * FROM coworking ORDER BY tarifs";
        try (Statement ste = con.createStatement();
             ResultSet res = ste.executeQuery(req)) {
            while (res.next()) {
                Coworking c = new Coworking();
                c.setId(res.getInt(1));
                c.setDescription(res.getString(2));
                c.setTarifs(res.getFloat(3));
                c.setDispo(res.getInt(4));
                c.setHoraireouvr(res.getString(5));
                c.setHorairefer(res.getString(6));
                c.setImage(res.getString(8));
                c.setNomco(res.getString(9));
                c.setNumtel(res.getString(10));
                c.setAdresse(res.getString(13));
                c.setTypeco_id(res.getInt(7));
                listcoworkings.add(c);
            }
        }
        return listcoworkings;
    }
}
