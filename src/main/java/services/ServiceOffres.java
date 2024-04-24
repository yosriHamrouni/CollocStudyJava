package services;

import entities.Offres;
import utils.MyDB;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class ServiceOffres implements IService<Offres> {
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
        String req = "UPDATE offres SET descrip=?, horairedeb=?, horaireter=?, lieu=?, num_tel=?, salaire=? WHERE id=?";
        try (PreparedStatement pre = con.prepareStatement(req)) {
            pre.setString(1, offres.getDescrip());
            pre.setString(2, offres.getHorairedeb());
            pre.setString(3, offres.getHoraireter());
            pre.setString(4, offres.getLieu());
            pre.setInt(5, offres.getNum_tel());
            pre.setString(6,offres.getImage());
            // Vérifier si le salaire est null avant de l'utiliser
            if (offres.getSalaire() != null) {
                pre.setDouble(6, offres.getSalaire());
            } else {
                // Si le salaire est null, vous devez décider de la manière dont vous souhaitez gérer cette situation.
                // Ici, je vais définir le salaire sur 0.0, mais vous pouvez modifier cela en fonction de vos besoins.
                pre.setDouble(6, 0.0);
            }

            pre.setInt(7, offres.getId());
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

            // Add the Offres object to the list
            listoffres.add(offres);
        }
        return listoffres;
    }}
