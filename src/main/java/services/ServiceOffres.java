package services;

import entities.Offres;
import utils.MyDB;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class ServiceOffres implements IServiceOffres <Offres> {
    private Connection con;
    public ServiceOffres(){
        con= MyDB.getInstance().getConnection();
    }



    @Override
    public void ajouter(Offres offres) throws SQLException  {
        String req = "INSERT INTO offres (descrip, salaire, horairedeb, horaireter, lieu,image, num_tel,id_type) VALUES ('" +
                offres.getDescrip() + "', " +
                offres.getSalaire() + ", '" +
                offres.getHorairedeb() + "', '" +
                offres.getHoraireter() + "', '" +
                offres.getLieu() + "', '" + // Ajout d'un guillemet simple manquant ici*
                offres.getImage() + "', '" +
                offres.getNum_tel() + "', '" +
                offres.getTypeoffre_id() + "')";


        Statement ste = con.createStatement();
        ste.executeUpdate(req);

    }


    @Override
    public void modifier(Offres offres) {
        String req = "UPDATE offres SET descrip=?, horairedeb=?, horaireter=?, lieu=?, num_tel=?, salaire=?, favoris=?,image=? WHERE id=?";
        try (PreparedStatement pre = con.prepareStatement(req)) {
            pre.setString(1, offres.getDescrip());
            pre.setString(2, offres.getHorairedeb());
            pre.setString(3, offres.getHoraireter());
            pre.setString(4, offres.getLieu());
            pre.setInt(5, offres.getNum_tel());
            pre.setDouble(6, offres.getSalaire() != null ? offres.getSalaire() : 0.0);
            pre.setBoolean(7, offres.getFavoris()); // Modifier l'index pour correspondre à la position de favoris dans la requête SQL
            pre.setString(8, offres.getImage() != null ? offres.getImage() : ""); // Assurer que la valeur de l'image n'est pas null
            pre.setInt(9, offres.getId());
            //pre.setString(9, offres.getImagePath());
            int rowsUpdated = pre.executeUpdate();
            if (rowsUpdated > 0) {
                System.out.println("L'offre a été mise à jour avec succès.");
            } else {
                System.out.println("Aucune offre n'a été mise à jour ou aucune offre correspondante n'a été trouvée.");
            }
        } catch (SQLException e) {
            throw new RuntimeException("Une erreur est survenue lors de la mise à jour de l'offre.", e);
        }
    }



    @Override
    public void supprimer(Offres offres) throws SQLException {
        PreparedStatement pre = con.prepareStatement("delete from offres where id=?");
        pre.setInt(1,offres.getId());
        pre.executeUpdate();

    }

    @Override
    public List<Offres> afficher() throws SQLException {
        List<Offres> listoffres = new ArrayList<>();

        String req = "SELECT * FROM offres ";
        Statement ste = con.createStatement();
        ResultSet res = ste.executeQuery(req);
        while (res.next()) {
            Offres offres = new Offres();
            offres.setId(res.getInt("id"));
            offres.setDescrip(res.getString("descrip"));
            offres.setSalaire(res.getDouble("salaire"));
            offres.setHorairedeb(res.getString("horairedeb"));
            offres.setHoraireter(res.getString("horaireter"));
            offres.setLieu(res.getString("lieu"));
            offres.setNum_tel(res.getInt("num_tel"));
            offres.setImage(res.getString("image"));
            offres.setFavoris(res.getBoolean("favoris"));

            // Add the Offres object to the list
            listoffres.add(offres);
        }
        return listoffres;
    }
    public static Offres getoffreId(int id ) {

        Connection conn = null;
        PreparedStatement stmt = null;
        ResultSet rs = null;
        Offres offres= null;

        try {
            conn = MyDB.getInstance().getConnection();
            if (conn == null) {
                throw new SQLException("La connexion à la base de données est nulle.");
            }

            String query = "SELECT * FROM offres WHERE id = ?";
            stmt = conn.prepareStatement(query);
            stmt.setInt(1, id);
            rs = stmt.executeQuery();

            // Si une ligne est renvoyée, créer un objet Coworking
            if (rs.next()) {
                offres= new Offres();
                offres.setId(rs.getInt("id"));
                offres.setDescrip(rs.getString("descrip"));
                offres.setSalaire(rs.getDouble("salaire"));
                offres.setHorairedeb(rs.getString("horairedeb"));
                offres.setHoraireter(rs.getString("horaireter"));
                offres.setLieu(rs.getString("lieu"));
                offres.setNum_tel(rs.getInt("num_tel"));
                offres.setImage(rs.getString("image"));
            }
        } catch (SQLException e) {
            e.printStackTrace();
            // Vous pouvez gérer l'exception en renvoyant une valeur par défaut ou en lançant une nouvelle exception personnalisée
        } finally {
            // Ne fermez pas les ressources ici
        }

        return offres;
    }
    public List<Offres> getOffresFavoris() {
        List<Offres> offresFavoris = new ArrayList<>();
        try {
            String req = "SELECT * FROM offres WHERE favoris = ?";
            PreparedStatement pre = con.prepareStatement(req);
            pre.setBoolean(1, true);
            ResultSet res = pre.executeQuery();
            while (res.next()) {
                Offres offres = new Offres();
                offres.setId(res.getInt("id"));
                offres.setDescrip(res.getString("descrip"));
                offres.setSalaire(res.getDouble("salaire"));
                offres.setHorairedeb(res.getString("horairedeb"));
                offres.setHoraireter(res.getString("horaireter"));
                offres.setLieu(res.getString("lieu"));
                offres.setNum_tel(res.getInt("num_tel"));
                offres.setImage(res.getString("image"));
                offres.setFavoris(res.getBoolean("favoris"));
                offresFavoris.add(offres);
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return offresFavoris;
    }
    public List<Offres> filtrerOffres(String lieu, double salaireMin, String description) throws SQLException {
        List<Offres> offresFiltrees = new ArrayList<>();
        StringBuilder reqBuilder = new StringBuilder("SELECT * FROM offres WHERE 1=1");

        // Vérifier et ajouter la condition de filtrage par lieu si spécifié
        if (!lieu.isEmpty()) {
            reqBuilder.append(" AND lieu LIKE ?");
        }

        // Vérifier et ajouter la condition de filtrage par salaire minimum si spécifié
        if (salaireMin > 0) {
            reqBuilder.append(" AND salaire >= ?");
        }

        // Vérifier et ajouter la condition de filtrage par description si spécifiée
        if (!description.isEmpty()) {
            reqBuilder.append(" AND descrip LIKE ?");
        }

        try (PreparedStatement pre = con.prepareStatement(reqBuilder.toString())) {
            int paramIndex = 1;

            // Définir les paramètres de la requête en fonction des critères spécifiés
            if (!lieu.isEmpty()) {
                pre.setString(paramIndex++, "%" + lieu + "%");
            }

            if (salaireMin > 0) {
                pre.setDouble(paramIndex++, salaireMin);
            }

            if (!description.isEmpty()) {
                pre.setString(paramIndex++, "%" + description + "%");
            }

            try (ResultSet res = pre.executeQuery()) {
                while (res.next()) {
                    Offres offres = new Offres();
                    offres.setId(res.getInt("id"));
                    offres.setDescrip(res.getString("descrip"));
                    offres.setSalaire(res.getDouble("salaire"));
                    offres.setHorairedeb(res.getString("horairedeb"));
                    offres.setHoraireter(res.getString("horaireter"));
                    offres.setLieu(res.getString("lieu"));
                    offres.setNum_tel(res.getInt("num_tel"));
                    offres.setImage(res.getString("image"));
                    offres.setFavoris(res.getBoolean("favoris"));
                    offresFiltrees.add(offres);
                }
            }
        }
        return offresFiltrees;
    }
    public void updateImage(int offreId, String imagePath) {
        try {
            String query = "UPDATE offres SET image = ? WHERE id = ?";
            PreparedStatement preparedStatement = con.prepareStatement(query);
            preparedStatement.setString(1, imagePath);
            preparedStatement.setInt(2, offreId);
            preparedStatement.executeUpdate();
            System.out.println("Chemin de l'image mis à jour avec succès.");
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }






}
