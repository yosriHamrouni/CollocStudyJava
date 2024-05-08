package org.example.controllers;

import com.twilio.Twilio;
import com.twilio.rest.api.v2010.account.Message;
import com.twilio.type.PhoneNumber;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.geometry.Insets;
import javafx.scene.chart.*;
import javafx.scene.control.*;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.Pane;
import javafx.scene.layout.Region;
import org.example.entities.User;
import org.example.service.UserService;

import java.io.IOException;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.time.LocalDate;
import java.util.*;
import java.util.logging.Level;
import java.util.logging.Logger;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class Backoffice {

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
    private PieChart accountPieChart;

    @FXML
    private Pane pn_signup;

    @FXML
    private Pane pn_update;

    @FXML
    private Pane pn_forgotPassword;
    @FXML
    private Pane pn_stats;
    @FXML
    private TextField tf_email;

    @FXML
    private TextField tf_email1;

    @FXML
    private TextField tf_fn;

    @FXML
    private PasswordField tf_pass;

    @FXML
    private TextField tf_fn1;

    @FXML
    private TextField tf_ln;

    @FXML
    private TextField tf_ln1;

    @FXML
    private PasswordField tf_passw;

    @FXML
    private TextField tf_sexe;

    @FXML
    private TextField tf_sexe1;

    @FXML
    private PasswordField tf_pass1;

    User tmpp = new User();
    UserService us = new UserService();

    @FXML
    private GridPane grid;
    @FXML
    private BarChart<String, Number> userRegistrationChart;

    @FXML
    public void initialize() {


        grid.getChildren().clear();
        displayg();

        int activatedCount = us.countActivatedAccounts();
        int deactivatedCount = us.countDeactivatedAccounts();

        // Create data for the pie chart
        PieChart.Data activatedData = new PieChart.Data("Activated Accounts", activatedCount);
        PieChart.Data deactivatedData = new PieChart.Data("Deactivated Accounts", deactivatedCount);

        // Add data to the pie chart
        accountPieChart.getData().addAll(activatedData, deactivatedData);

        // Show legend
        accountPieChart.setLegendVisible(true);
        CategoryAxis xAxis = new CategoryAxis();
        xAxis.setLabel("Date");

        NumberAxis yAxis = new NumberAxis();
        yAxis.setLabel("Number of Registrations");

        userRegistrationChart.setTitle("User Registrations in Last 5 Days");

        XYChart.Series<String, Number> series = new XYChart.Series<>();
        series.setName("Registrations");

        // Retrieve registration data for the last 5 days
        Map<LocalDate, Integer> registrations = getRegistrationsForLast5Days();

        // Populate series with registration data
        registrations.forEach((date, count) -> {
            series.getData().add(new XYChart.Data<>(date.toString(), count));
        });

        // Add series to the chart
        userRegistrationChart.getData().add(series);
    }
    private Map<LocalDate, Integer> getRegistrationsForLast5Days() {
        Map<LocalDate, Integer> registrations = new HashMap<>();

        // Calculate the start date (5 days ago)
        LocalDate startDate = LocalDate.now().minusDays(4); // Subtract 4 to get 5 days ago

        // Query the database for registrations for each day in the last 5 days
        for (int i = 0; i < 5; i++) {
            LocalDate currentDate = startDate.plusDays(i);
            int count = us.countRegistrationsForDay(currentDate);
            registrations.put(currentDate, count);
        }

        return registrations;
    }








    @FXML
    void refresh(ActionEvent event) {
        grid.getChildren().clear();
        displayg();
    }
    private void displayg() {
        ///////////////////////////////////////////////////////////////
        ResultSet resultSet2 = us.Getall();
        User pppp = new User();
        int column = 0;
        int row = 2;
        try {
            while (resultSet2.next()) {
                //chargement de 'etiquette
                FXMLLoader fxmlLoader = new FXMLLoader();
                fxmlLoader.setLocation(getClass().getResource("/User.fxml"));
                try {
                    //chargement du controller
                    AnchorPane anchorPane = fxmlLoader.load();
                    UserC itemController = fxmlLoader.getController();
                    int id=resultSet2.getInt("id");
                    String mail=resultSet2.getString("email");
                    String pass=resultSet2.getString("password");
                    String ln=resultSet2.getString("nom");
                    String fn=resultSet2.getString("prenom");
                    String sexe=resultSet2.getString("sexe");
                    int isbanned= resultSet2.getInt("bloque");
                    User ppppp = new User(id,mail,"",pass,ln,fn,sexe,isbanned);
                    itemController.setData(ppppp);
                    if (column == 1) {
                        column = 0;
                        row++;
                    }
                    grid.add(anchorPane, column++, row); //(child,column,row)
                    //set grid width
                    grid.setMinWidth(Region.USE_COMPUTED_SIZE);
                    grid.setPrefWidth(Region.USE_COMPUTED_SIZE);
                    grid.setMaxWidth(Region.USE_PREF_SIZE);
                    //set grid height
                    grid.setMinHeight(Region.USE_COMPUTED_SIZE);
                    grid.setPrefHeight(Region.USE_COMPUTED_SIZE);
                    grid.setMaxHeight(Region.USE_PREF_SIZE);
                    GridPane.setMargin(anchorPane, new Insets(10));
                } catch (IOException ex) {
                    Logger.getLogger(Backoffice.class.getName()).log(Level.SEVERE, null, ex);
                }
            }
        } catch (SQLException ex) {
            Logger.getLogger(Backoffice.class.getName()).log(Level.SEVERE, null, ex);
        }
    }
    @FXML
    void signup(ActionEvent event) {
        if (tf_ln.getText().isEmpty() ||tf_sexe.getText().isEmpty() || tf_fn.getText().isEmpty() ||tf_email.getText().isEmpty()||tf_pass.getText().isEmpty()||tf_phone.getText().isEmpty()) {
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
        String phone = tf_phone.getText();


        //    public User(String email, String roles, String password, String nom, String prenom, int tel, int bloque) {
        User u = new User(mail,"[\"ROLE_ADMIN\"]",pass,fn,ln,tf_sexe.getText(),0,phone);
        us.ajouterEntite(u);
        tf_fn.clear();
        tf_ln.clear();
        tf_email.clear();
        tf_pass.clear();
        tf_phone.clear();
        pn_signin.toFront();

    }

    @FXML
    void deactivateaccount(ActionEvent event) {
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
        User u = new User(Integer.parseInt(id.getText()),mail,"[\"ROLE_USER\"]",pass,fn,ln,tf_sexe1.getText(),0);
        us.modifierEntite(u);

    }

    @FXML
    void toSignup(ActionEvent event) {
        pn_signup.toFront();
    }
    @FXML
    void toForgotPassword(ActionEvent event) {
        pn_forgotPassword.toFront();
    }

    @FXML
    void toUpdate(ActionEvent event) {
        pn_update.toFront();
    }
    @FXML
    void toStats(ActionEvent event) {
        pn_stats.toFront();
    }

    @FXML
    void tosignin(ActionEvent event) {
        pn_signin.toFront();
    }

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
                        reactivateAccount(resultSet.getInt("id"));
                    }
                    return; // Exit the method if the account is deactivated
                }

                // Continue with normal login processing if account is active
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
                System.out.println(tmpp.getRoles());
                if(Objects.equals(tmpp.getRoles(), "[\"ROLE_ETUDIANT\"]")) {
                    pn_signin.toFront();
                    Alert alert = new Alert(Alert.AlertType.WARNING);
                    alert.setTitle("Access Denied");
                    alert.setHeaderText(null);
                    alert.setContentText("Please enter an admin account!");
                    alert.showAndWait();
                }
            } else {
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
