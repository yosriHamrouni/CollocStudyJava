package org.example.controllers;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import org.example.entities.User;
import org.example.service.UserService;

import java.util.Objects;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class UserC {

    @FXML
    private Label ban;

    @FXML
    private Button btnmod;

    @FXML
    private Label mail;

    @FXML
    private Label name;

    @FXML
    private Label sexe;

    @FXML
    private TextField tf_email1;

    @FXML
    private TextField tf_fn1;

    @FXML
    private TextField tf_ln1;

    @FXML
    private TextField tf_sexe1;

    private User user= new User();
    UserService us = new UserService();
    private int id ;
    public void setData(User q) {
        this.user = q;
        this.id=q.getId();
        name.setText("Name : "+q.getName()+" "+q.getPrenom());
        mail.setText("Email :"+q.getEmail());
        sexe.setText("Sexe :"+q.getSexe());
        if(q.getIs_banned()!=0)
        {
            ban.setText("Utilisateur bloque");
        }else {
            ban.setText("Utilisateur non bloque");
        }
        tf_email1.setVisible(false);
        tf_fn1.setVisible(false);
        tf_ln1.setVisible(false);
        tf_sexe1.setVisible(false);
        tf_email1.setText(user.getEmail());
        tf_sexe1.setText(user.getSexe());
        tf_ln1.setText(user.getNom());
        tf_fn1.setText(user.getPrenom());

    }

    @FXML
    void supprimer(ActionEvent event) {
        User u = new User(this.id);
        us.supprimerEntite(u);
    }

    @FXML
    void modifier(ActionEvent event) {
        if(Objects.equals(btnmod.getText(), "Modifier"))
        {
            tf_email1.setVisible(true);
            tf_fn1.setVisible(true);
            tf_ln1.setVisible(true);
            tf_sexe1.setVisible(true);
            btnmod.setText("Valider");

        }else{
            if (tf_ln1.getText().isEmpty() ||tf_sexe1.getText().isEmpty() || tf_fn1.getText().isEmpty() ||tf_email1.getText().isEmpty()) {
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
            //    public User(String email, String roles, String password, String nom, String prenom, int tel, int bloque) {
            User u = new User(this.id,mail,"[\"ROLE_ADMIN\"]",user.getPassword(),fn,ln,tf_sexe1.getText(),0);
            us.modifierEntite(u);
            btnmod.setText("Modfier");
            tf_email1.setVisible(false);
            tf_fn1.setVisible(false);
            tf_ln1.setVisible(false);
            tf_sexe1.setVisible(false);
        }
    }

}
