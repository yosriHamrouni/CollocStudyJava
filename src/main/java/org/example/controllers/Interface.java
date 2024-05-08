package org.example.controllers;

import com.twilio.Twilio;
import com.twilio.rest.api.v2010.account.Message;
import com.twilio.type.PhoneNumber;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.*;
import javafx.scene.layout.Pane;
import org.example.entities.User;
import org.example.service.UserService;
import org.example.tools.DBconnexion;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.Optional;
import java.util.UUID;
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
    private TextField tf_phone;
    @FXML
    private TextField tf_resetEmail;
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
    private Pane pn_forgotpassword;

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
        if (tf_ln.getText().isEmpty() || tf_sexe.getText().isEmpty() || tf_fn.getText().isEmpty() || tf_email.getText().isEmpty() || tf_pass.getText().isEmpty() || tf_phone.getText().isEmpty()) {
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
        String phone = tf_phone.getText();
        String sexe = tf_sexe.getText();

        User u = new User(mail, "[\"ROLE_ETUDIANT\"]", pass, fn, ln, sexe, 0, phone);
        us.ajouterEntite(u);

        tf_fn.clear();
        tf_ln.clear();
        tf_email.clear();
        tf_pass.clear();
        tf_phone.clear();
        tf_sexe.clear();
        pn_signin.toFront();
    }


    @FXML
    void deactivateAccount(ActionEvent event) {
        try {
            int userId = Integer.parseInt(id.getText());

            // Create a confirmation dialog
            Alert confirmationDialog = new Alert(Alert.AlertType.CONFIRMATION);
            confirmationDialog.setTitle("Confirmation");
            confirmationDialog.setHeaderText(null);
            confirmationDialog.setContentText("Are you sure you want to deactivate your account?");

            // Add "OK" and "Cancel" buttons to the dialog
            confirmationDialog.getButtonTypes().setAll(ButtonType.OK, ButtonType.CANCEL);

            // Show the confirmation dialog and wait for the user's response
            ButtonType userResponse = confirmationDialog.showAndWait().orElse(ButtonType.CANCEL);

            // If the user clicked "OK" in the confirmation dialog, proceed with the deactivation
            if (userResponse == ButtonType.OK) {
                User userToDeactivate = new User(userId);
                us.deactivateAccount(userToDeactivate);

                // Inform the user of successful deactivation
                Alert infoAlert = new Alert(Alert.AlertType.INFORMATION);
                infoAlert.setTitle("Account Deactivated");
                infoAlert.setHeaderText(null);
                infoAlert.setContentText("Your account has been successfully deactivated.");
                infoAlert.showAndWait();

                // Move to the "signin" pane
                pn_signin.toFront();
            }
        } catch (NumberFormatException e) {
            // Handle the case where the ID entered by the user is not a valid integer
            // Display an error message or handle it as appropriate for your application
            Alert errorAlert = new Alert(Alert.AlertType.ERROR);
            errorAlert.setTitle("Error");
            errorAlert.setHeaderText("Invalid User ID");
            errorAlert.setContentText("Please enter a valid user ID.");
            errorAlert.showAndWait();
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
    void toForgotPassword(ActionEvent event) {
        pn_forgotpassword.toFront();
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
        ResultSet resultSet = us.log(tf_log.getText(), tf_passw.getText());
        try {
            if (resultSet.next()) {
                boolean isActive = resultSet.getBoolean("isActive");
                if (!isActive) {
                    Alert alert = new Alert(Alert.AlertType.CONFIRMATION);
                    alert.setTitle("Account Deactivated");
                    alert.setHeaderText("Your account has been deactivated.");
                    alert.setContentText("Would you like to reactivate your account?");

                    ButtonType reactivateButton = new ButtonType("Reactivate");
                    ButtonType cancelButton = new ButtonType("Cancel", ButtonBar.ButtonData.CANCEL_CLOSE);
                    alert.getButtonTypes().setAll(reactivateButton, cancelButton);

                    Optional<ButtonType> result = alert.showAndWait();
                    if (result.isPresent() && result.get() == reactivateButton) {
                        // Call the method to reactivate the account
                        reactivateAccount(resultSet.getInt("id"));
                    }
                } else {
                    // Account is active, proceed with login
                    proceedToHomePage(resultSet);
                }
            } else {
                // Incorrect email or password
                Alert alert = new Alert(Alert.AlertType.WARNING);
                alert.setTitle("Login Failed");
                alert.setHeaderText(null);
                alert.setContentText("Email or password is incorrect.");
                alert.showAndWait();
            }
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }

    private void proceedToHomePage(ResultSet resultSet) throws SQLException {
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
    }

    private void reactivateAccount(int userId) {
        User userToActivate = new User(userId);
        us.activateAccount(userToActivate);

        Alert infoAlert = new Alert(Alert.AlertType.INFORMATION);
        infoAlert.setTitle("Account Reactivated");
        infoAlert.setHeaderText(null);
        infoAlert.setContentText("Your account has been successfully reactivated.");
        infoAlert.showAndWait();

        pn_signin.toFront();
    }




    @FXML
    void toHome(ActionEvent event) {
        System.out.println("helo");
        pn_home.toFront();
        pn_update.toBack();
    }
    @FXML
    private void handleForgotPassword(ActionEvent event) {
        String userEmail = tf_resetEmail.getText();
        if (!userEmail.contains("@") || !userEmail.contains(".")) {
            showAlert("Invalid Email", "Please enter a valid email address.");
            return;
        }

        User user = us.getUserByEmail(userEmail);
        if (user != null && user.getPhone() != null) {
            String tempPassword = generateTempPassword();
            if (us.updatePassword(userEmail, tempPassword)) {
                sendSMS(user.getPhone(), "Your temporary password is: " + tempPassword);
                showAlert("SMS Sent", "A temporary password has been sent to your phone.");
                pn_signin.toFront();

            } else {
                showAlert("Failed", "Failed to update password. Please try again later.");
            }
        } else {
            showAlert("No User Found", "No account found with that email.");
        }
    }

    private String generateTempPassword() {
        return UUID.randomUUID().toString().substring(0, 8); // Simple temporary password
    }

    private void sendSMS(String phone, String message) {
        Twilio.init(ACCOUNT_SID, AUTH_TOKEN);
        Message.creator(
                new PhoneNumber(phone),
                new PhoneNumber(TWILIO_NUMBER),
                message
        ).create();
        System.out.println("SMS sent successfully to " + phone);
    }

    private void showAlert(String title, String content) {
        Alert alert = new Alert(Alert.AlertType.INFORMATION);
        alert.setTitle(title);
        alert.setHeaderText(null);
        alert.setContentText(content);
        alert.showAndWait();
    }


}
