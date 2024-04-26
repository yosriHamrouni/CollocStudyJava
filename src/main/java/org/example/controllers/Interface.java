package org.example.controllers;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.Alert;
import javafx.scene.control.ButtonType;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.scene.layout.Pane;
import org.example.entities.User;
import org.example.service.UserService;
import org.example.tools.DBconnexion;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.logging.Level;
import java.util.logging.Logger;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class Interface {

    @FXML
    private Label id;
    @FXML
    private TextField tf_log;
    @FXML
    private Pane pn_home;

    @FXML
    private Pane pn_index;

    @FXML
    private Pane pn_signin;

    @FXML
    private Pane pn_signup;

    @FXML
    private Pane pn_update;

    @FXML
    private TextField tf_email;

    @FXML
    private TextField tf_email1;

    @FXML
    private TextField tf_fn;

    @FXML
    private TextField tf_pass;

    @FXML
    private TextField tf_fn1;

    @FXML
    private TextField tf_ln;

    @FXML
    private TextField tf_ln1;

    @FXML
    private TextField tf_passw;

    @FXML
    private TextField tf_sexe;

    @FXML
    private TextField tf_sexe1;

    @FXML
    private TextField tf_pass1;

    User tmpp = new User();
    UserService us = new UserService();
    @FXML
    void signup(ActionEvent event) {
        if (tf_ln.getText().isEmpty() ||tf_sexe.getText().isEmpty() || tf_fn.getText().isEmpty() ||tf_email.getText().isEmpty()||tf_pass.getText().isEmpty()) {
            // Afficher un message d'alerte
            Alert alert = new Alert(Alert.AlertType.WARNING);
            alert.setTitle("Champs manquants");
            alert.setHeaderText(null);
            alert.setContentText("Veuillez remplir tous les champs !");
            alert.showAndWait();
            return;
        }

        String emailPattern = "^[a-zA-Z0-9_+&*-]+(?:\\.[a-zA-Z0-9_+&*-]+)*@(?:[a-zA-Z0-9-]+\\.)+[a-zA-Z]{2,7}$";
        Pattern pattern = Pattern.compile(emailPattern);
        Matcher matcher = pattern.matcher(tf_email.getText());

        if (!matcher.matches()) {
            // If the email doesn't match the pattern, display an alert
            Alert alert = new Alert(Alert.AlertType.WARNING);
            alert.setTitle("Mail incorrect");
            alert.setHeaderText(null);
            alert.setContentText("Veuillez choisir un mail valide!");
            alert.showAndWait();
            return;
        }
        String ln = tf_ln.getText();
        String fn = tf_fn.getText();
        String mail = tf_email.getText();
        String pass = tf_pass.getText();
        //    public User(String email, String roles, String password, String nom, String prenom, int tel, int bloque) {
        User u = new User(mail,"[\"ROLE_ETUDIANT\"]",pass,fn,ln,tf_sexe.getText(),0);
        us.ajouterEntite(u);
        tf_fn.clear();
        tf_ln.clear();
        tf_email.clear();
        tf_pass.clear();
        pn_signin.toFront();
    }

    @FXML
    void delete(ActionEvent event) {
        try {
            int userId = Integer.parseInt(id.getText());

            // Create a confirmation dialog
            Alert confirmationDialog = new Alert(Alert.AlertType.CONFIRMATION);
            confirmationDialog.setTitle("Confirmation");
            confirmationDialog.setHeaderText(null);
            confirmationDialog.setContentText("etes vous sure de vouloir supprimer votre compte?");

            // Add "OK" and "Cancel" buttons to the dialog
            confirmationDialog.getButtonTypes().setAll(ButtonType.OK, ButtonType.CANCEL);

            // Show the confirmation dialog and wait for the user's response
            ButtonType userResponse = confirmationDialog.showAndWait().orElse(ButtonType.CANCEL);

            // If the user clicked "OK" in the confirmation dialog, proceed with the deletion
            if (userResponse == ButtonType.OK) {
                // Create a new User instance with the provided ID
                User userToDelete = new User(userId);

                // Call the method to delete the user entity
                us.supprimerEntite(userToDelete);

                // Move to the "signin" pane
                pn_signin.toFront();
            }
        } catch (NumberFormatException e) {
            // Handle the case where the ID entered by the user is not a valid integer
            // Display an error message or handle it as appropriate for your application
            e.printStackTrace(); // Or log the error
        }
    }

    @FXML
    void update(ActionEvent event) {
        if (tf_ln1.getText().isEmpty() ||tf_sexe1.getText().isEmpty() || tf_fn1.getText().isEmpty() ||tf_email1.getText().isEmpty()||tf_pass1.getText().isEmpty()) {
            // Afficher un message d'alerte
            Alert alert = new Alert(Alert.AlertType.WARNING);
            alert.setTitle("Champs manquants");
            alert.setHeaderText(null);
            alert.setContentText("Veuillez remplir tous les champs !");
            alert.showAndWait();
            return;
        }
        try {
            // Attempt to parse the text as an integer
        } catch (NumberFormatException e) {
            // If parsing fails (NumberFormatException is thrown), display an alert
            Alert alert = new Alert(Alert.AlertType.WARNING);
            alert.setTitle("Num tel incorrect");
            alert.setHeaderText(null);
            alert.setContentText("Veuillez choisir un num exact !");
            alert.showAndWait();

            // Return or perform any necessary action based on the invalid input
            return;
        }
        String emailPattern = "^[a-zA-Z0-9_+&*-]+(?:\\.[a-zA-Z0-9_+&*-]+)*@(?:[a-zA-Z0-9-]+\\.)+[a-zA-Z]{2,7}$";
        Pattern pattern = Pattern.compile(emailPattern);
        Matcher matcher = pattern.matcher(tf_email1.getText());

        if (!matcher.matches()) {
            // If the email doesn't match the pattern, display an alert
            Alert alert = new Alert(Alert.AlertType.WARNING);
            alert.setTitle("Mail incorrect");
            alert.setHeaderText(null);
            alert.setContentText("Veuillez choisir un mail valide!");
            alert.showAndWait();
            return;
        }
        String ln = tf_ln1.getText();
        String fn = tf_fn1.getText();
        String mail = tf_email1.getText();
        String pass = tf_pass1.getText();
        //    public User(String email, String roles, String password, String nom, String prenom, int tel, int bloque) {
        User u = new User(Integer.parseInt(id.getText()),mail,"[\"ROLE_ETUDIANT\"]",pass,fn,ln,tf_sexe1.getText(),0);
        us.modifierEntite(u);

    }

    @FXML
    void toSignup(ActionEvent event) {
        pn_signup.toFront();
    }

    @FXML
    void toUpdate(ActionEvent event) {
        pn_update.toFront();
    }
    //logout
    @FXML
    void tosignin(ActionEvent event) {
        pn_signin.toFront();
        tmpp= null;
    }
    // j'ai deja un cmpt
    @FXML
    void toSignin(ActionEvent event) {
        pn_signin.toFront();
    }

    @FXML
    void login(ActionEvent event) {

        ResultSet resultSet = us.log(tf_log.getText(),tf_passw.getText());
        try {
            if (resultSet.next()) {
                tmpp = new User(
                        resultSet.getInt("id"),
                        resultSet.getString("email"),
                        resultSet.getString("roles"),
                        resultSet.getString("password"),
                        resultSet.getString("nom"),
                        resultSet.getString("prenom"),
                        resultSet.getString("sexe"),
                        resultSet.getInt("bloque")
                );
                id.setText(String.valueOf(tmpp.getId()));
                tf_email1.setText(tmpp.getEmail());
                tf_pass1.setText(tmpp.getPassword());
                tf_fn1.setText(tmpp.getPrenom());
                tf_ln1.setText(tmpp.getName());
                tf_sexe1.setText(tmpp.getSexe());
                pn_home.toFront();
                pn_index.toFront();
            }else
            {
                Alert alert = new Alert(Alert.AlertType.WARNING);
                alert.setTitle("Information incorrect");
                alert.setHeaderText(null);
                alert.setContentText("email ou mot de passe incorrect !");
                alert.showAndWait();
            }
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }


    }

    @FXML
    void toHome(ActionEvent event) {
        System.out.println("helo");
        pn_home.toFront();
        pn_update.toBack();
    }
}
