package org.example.service;

import org.example.entities.User;
import org.example.tools.DBconnexion;

import java.sql.*;
import java.util.List;

public class UserService implements ICrud<User>{
    Connection cnx2;
    public UserService() {
        cnx2 = DBconnexion.getInstance().getCnx();
    }

    public ResultSet SelectionnerSingle(int id) {
        ResultSet rs = null;
        try {
            String req = "SELECT * FROM `user` WHERE `id` ="+id;
            PreparedStatement st = cnx2.prepareStatement(req);
            rs = st.executeQuery(req);
        } catch (SQLException ex) {
            System.err.println(ex.getMessage());
        }
        return rs;
    }
    @Override
    public void ajouterEntite(User p) {
        String req1 = "INSERT INTO `user` (`email`, `roles`, `password`, `nom`, `prenom`, `sexe`, `bloque`, `phone`) VALUES (?, ?, ?, ?, ?, ?, ?, ?)";
        try (PreparedStatement st = cnx2.prepareStatement(req1)) {
            st.setString(1, p.getEmail());
            st.setString(2, p.getRoles());
            st.setString(3, p.getPassword());
            st.setString(4, p.getName());
            st.setString(5, p.getPrenom());
            st.setString(6, p.getSexe());
            st.setInt(7, p.getIs_banned());
            st.setString(8, p.getPhone());

            st.executeUpdate();
            System.out.println("User ajouté");
        } catch (SQLException e) {
            System.err.println("SQL Error: " + e.getMessage());
        }
    }



    @Override
    public void modifierEntite(User p) {
        String requet = "UPDATE user SET email=?, password=?,nom=?,prenom=?,sexe=? WHERE id =?";
        try {
            PreparedStatement st = cnx2.prepareStatement(requet);
            st.setInt(6,p.getId());
            st.setString(1, p.getEmail());
            st.setString(2, p.getPassword());
            st.setString(3, p.getName());
            st.setString(4, p.getPrenom());
            st.setString(5, p.getSexe());
            int rowsAffected = st.executeUpdate();
            if (rowsAffected > 0) {
                System.out.println("Modification réussie");
            } else {
                System.out.println("Aucune modification effectuée. Vérifiez l'ID.");
            }
        } catch (SQLException e) {
            System.out.println(e.getMessage());
        }
    }

    @Override
    public void supprimerEntite(User p) {
        String requet = "DELETE FROM user WHERE id =?";
        try {
            PreparedStatement pst = cnx2.prepareStatement(requet);
            pst.setInt(1, p.getId());  // Assuming getQuizId() returns the Quiz ID
            int rowsAffected = pst.executeUpdate();

            if (rowsAffected > 0) {
                System.out.println("Suppression réussie");
            } else {
                System.out.println("Aucune suppression effectuée. Vérifiez l'ID.");
            }
        } catch (SQLException e) {
            System.out.println(e.getMessage());
        }
    }

    public ResultSet Getall() {
        ResultSet rs = null;
        try {
            String req = "SELECT * FROM `user`";
            PreparedStatement st = cnx2.prepareStatement(req);
            rs = st.executeQuery(req);
        } catch (SQLException ex) {
            System.err.println(ex.getMessage());
        }
        return rs;    }

    public ResultSet log(String email, String pw) {
        ResultSet rs = null;
        try {
            String req = "SELECT * FROM `user` WHERE `email` = ? AND `password` = ?";
            PreparedStatement st = cnx2.prepareStatement(req);
            st.setString(1, email);
            st.setString(2, pw);
            rs = st.executeQuery();
        } catch (SQLException ex) {
            System.err.println(ex.getMessage());
        }
        return rs;
    }


    public void deactivateAccount(User user) {
        String sql = "UPDATE user SET isActive = FALSE WHERE id = ?";
        try (PreparedStatement st = cnx2.prepareStatement(sql)) {
            st.setInt(1, user.getId());
            st.executeUpdate();
            System.out.println("Compte désactivé avec succès");
        } catch (SQLException ex) {
            System.err.println(ex.getMessage());
        }
    }

    public void activateAccount(User user) {
        String sql = "UPDATE user SET isActive = TRUE WHERE id = ?";
        try (PreparedStatement st = cnx2.prepareStatement(sql)) {
            st.setInt(1, user.getId());
            st.executeUpdate();
            System.out.println("Compte activé avec succès");
        } catch (SQLException ex) {
            System.err.println(ex.getMessage());
        }
    }
    public boolean updatePassword(String email, String newPassword) {
        String sql = "UPDATE user SET password = ? WHERE email = ?";
        try (PreparedStatement stmt = cnx2.prepareStatement(sql)) {
            stmt.setString(1, newPassword);
            stmt.setString(2, email);
            return stmt.executeUpdate() > 0;
        } catch (SQLException ex) {
            System.err.println(ex.getMessage());
            return false;
        }
    }
    public User getUserByEmail(String email) {
        String sql = "SELECT * FROM user WHERE email = ?";
        try (PreparedStatement stmt = cnx2.prepareStatement(sql)) {
            stmt.setString(1, email);
            ResultSet rs = stmt.executeQuery();
            if (rs.next()) {
                return new User(rs.getInt("id"), rs.getString("email"), rs.getString("phone"));
            }
        } catch (SQLException ex) {
            System.err.println(ex.getMessage());
        }
        return null;
    }
}








