package gui;

import com.google.protobuf.Service;
import com.itextpdf.text.Paragraph;
import com.itextpdf.text.pdf.PdfDocument;
import com.itextpdf.text.pdf.PdfWriter;
import javafx.beans.property.SimpleFloatProperty;
import javafx.beans.property.SimpleIntegerProperty;
import javafx.beans.property.SimpleStringProperty;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.collections.transformation.FilteredList;
import javafx.collections.transformation.SortedList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.chart.PieChart;
import javafx.scene.control.TextField;
import javafx.scene.control.*;
import javafx.scene.control.Button;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.input.KeyEvent;
import javafx.stage.FileChooser;
import javafx.stage.Stage;
import javafx.stage.StageStyle;
import entities.logement;
import org.dom4j.DocumentException;
import services.ServiceLogement;


import java.io.FileOutputStream;
import com.itextpdf.text.Document;


import java.awt.*;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.net.URL;
import java.sql.SQLException;
import java.util.List;
import java.util.Map;
import java.util.ResourceBundle;
import javafx.scene.control.Alert;
import java.awt.Desktop;
import java.util.function.Predicate;


import static config.InputValidation.showAlert;

public class dashboardajout implements Initializable {

    @FXML
    private TableColumn<logement, String> adresseCol;

    @FXML
    private Button cancelButton;
    @FXML
    private TextField filtrefield;

    @FXML
    private ImageView img;
    @FXML
    private PieChart pieChart;
    @FXML
    private Button cancel;

    private ObservableList<logement> logList = FXCollections.observableArrayList();
    private FilteredList<logement> filteredList;
    @FXML
    private Button stats;
    @FXML
    private TableColumn<logement, String> descriptionCol;

    @FXML
    private TableColumn<logement, Integer> idColT;

    @FXML
    private TableColumn<logement, String> imageCol;
   /* @FXML
    private TableColumn<logement, Integer> typeIdCol;*/

    @FXML
    private TableView<logement> devisTableView;

    @FXML
    private TableColumn<logement, Integer> dispoCol;

    @FXML
    private TableColumn<logement, Float> tarifsCol;
    @FXML
    private Button QrcodeB;
    @FXML
    private Button supprimerButton;

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        try {
            populateTableView();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    private void afficherImageLogement(logement selectedLogement) {
        String imagePath = selectedLogement.getImage();
        if (imagePath != null && !imagePath.isEmpty()) {
            File file = new File(imagePath);
            Image image = new Image(file.toURI().toString());
            img.setImage(image);
        } else {
            // Afficher une image par défaut si aucune image n'est disponible
            // logementImageView.setImage(...);
        }
    }

    public void populateTableView() throws SQLException {
        // Retrieve data from the database
        ServiceLogement serviceLogement = new ServiceLogement();
        List<logement> logementList = serviceLogement.afficher();

        // Clear existing items in the TableView
        devisTableView.getItems().clear();

        // Setup columns
        adresseCol.setCellValueFactory(new PropertyValueFactory<>("adresse"));
        descriptionCol.setCellValueFactory(new PropertyValueFactory<>("description"));
        imageCol.setCellValueFactory(new PropertyValueFactory<>("image"));
        dispoCol.setCellValueFactory(new PropertyValueFactory<>("dispo"));
        tarifsCol.setCellValueFactory(new PropertyValueFactory<>("tarifs"));
        //typeIdCol.setCellValueFactory(cellData -> new SimpleIntegerProperty(cellData.getValue().getId_type()).asObject()); // Ajout de cette ligne

        // Add the retrieved data to the TableView
        devisTableView.getItems().addAll(logementList);

        // Double-click handler
        devisTableView.setOnMouseClicked(event -> {
            if (event.getClickCount() == 2) {
                logement selectedLogement = devisTableView.getSelectionModel().getSelectedItem();
                if (selectedLogement != null) {
                    navigateToUpdateLogement(selectedLogement);
                }
            }
        });
        // Wrap the ObservableList in a FilteredList (initially display all data).
        filteredList = new FilteredList<>(devisTableView.getItems(), b -> true);

        // Set the filter Predicate whenever the filter changes.
        // Ajouter un écouteur de changement de texte au TextField
        filtrefield.textProperty().addListener((observable, oldValue, newValue) -> {
            filterTableView();
        });
        updatePieChart();

    }

    private void updatePieChart() {
        ServiceLogement serviceLogement = new ServiceLogement();

        // Récupération des statistiques sur les logements par adresse
        Map<String, Integer> logementByAdresse = serviceLogement.getLogementByAdresse();

        // Création des données pour le graphique PieChart
        ObservableList<PieChart.Data> pieChartData = FXCollections.observableArrayList();
        for (Map.Entry<String, Integer> entry : logementByAdresse.entrySet()) {
            pieChartData.add(new PieChart.Data(entry.getKey(), entry.getValue()));
        }

        // Configuration du PieChart
        pieChart.setData(pieChartData);
        pieChart.setTitle("Statistique des logements par adresse");
    }




        private void navigateToUpdateLogement(logement logement) {
        try {
            FXMLLoader loader = new FXMLLoader(getClass().getResource("../ModifierLogement.fxml"));
            Parent root = loader.load();
            ModifierLogement controller = loader.getController();
            controller.initData(logement);
            Stage stage = new Stage();
            stage.setScene(new Scene(root));
            stage.setOnCloseRequest(event -> {
                try {
                    populateTableView();
                } catch (SQLException e) {
                    e.printStackTrace();
                }
            });
            stage.showAndWait();
            populateTableView();
        } catch (IOException | SQLException e) {
            e.printStackTrace();
        }
    }

    @FXML
    private void AjoutOne(ActionEvent event) {
        try {
            Parent root = FXMLLoader.load(getClass().getResource("AjouterLogement.fxml"));
            Stage stage = new Stage();
            stage.setScene(new Scene(root));
            stage.show();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    @FXML
    private void getReponseDevis(ActionEvent event) {
        try {
            Parent root = FXMLLoader.load(getClass().getResource("afficherLogement.fxml"));
            Stage pStage= new Stage();
            pStage.initStyle(StageStyle.UNDECORATED);
            pStage.setScene(new Scene(root, 667,556));
            pStage.show();
            Stage stage = (Stage) cancelButton.getScene().getWindow();
            stage.close();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    @FXML
    private void ouvrirtypes(ActionEvent event) {
        ouvrirFenetreT("../AfficherType.fxml", "Types");
    }

    private void ouvrirFenetreT(String fxmlPath, String title) {
        try {
            Parent root = FXMLLoader.load(getClass().getResource(fxmlPath));
            Scene scene = new Scene(root);
            Stage stage = new Stage();
            stage.setScene(scene);
            stage.setTitle(title);
            stage.show();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }




    public void ouvrirAjouterLogement(ActionEvent actionEvent) {
        ouvrirFenetre("../AjouterLogement.fxml", "Ajouter");
    }

    public void ouvrirModiferLogement(ActionEvent actionEvent) {
        ouvrirFenetre("../ModifierLogement.fxml", "Modifier");
    }

    private void ouvrirFenetre(String fxmlPath, String title) {
        try {
            Parent root = FXMLLoader.load(getClass().getResource(fxmlPath));
            Scene scene = new Scene(root);
            Stage stage = new Stage();
            stage.setScene(scene);
            stage.setTitle(title);
            stage.show();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    @FXML
    private void deleteOne(ActionEvent event) {
        logement selectedLogement = devisTableView.getSelectionModel().getSelectedItem();
        if (selectedLogement != null) {
            Alert confirmAlert = new Alert(Alert.AlertType.CONFIRMATION);
            confirmAlert.setTitle("Dialogue de Confirmation");
            confirmAlert.setHeaderText("Supprimer logement");
            confirmAlert.setContentText("Êtes-vous sûr de vouloir supprimer ce logement ?");
            confirmAlert.showAndWait().ifPresent(response -> {
                if (response == ButtonType.OK) {
                    try {
                        ServiceLogement serviceLogement = new ServiceLogement();
                        serviceLogement.supprimer(selectedLogement);
                        Alert successAlert = new Alert(Alert.AlertType.INFORMATION);
                        successAlert.setTitle("Suppression Réussie");
                        successAlert.setHeaderText(null);
                        successAlert.setContentText("Logement supprimé avec succès.");
                        successAlert.showAndWait();
                        populateTableView();
                    } catch (SQLException ex) {
                        Alert errorAlert = new Alert(Alert.AlertType.ERROR);
                        errorAlert.setTitle("Erreur de Suppression");
                        errorAlert.setHeaderText(null);
                        errorAlert.setContentText("Erreur lors de la suppression du logement : " + ex.getMessage());
                        errorAlert.showAndWait();
                    }
                }
            });
        } else {
            Alert alert = new Alert(Alert.AlertType.WARNING);
            alert.setTitle("Aucune Sélection");
            alert.setHeaderText(null);
            alert.setContentText("Veuillez sélectionner un logement à supprimer.");
            alert.showAndWait();
        }
    }
    @FXML
    private void filterTableView() {
        // Créer un prédicat pour filtrer les éléments de la TableView
        Predicate<logement> filterPredicate = logement -> {
            // Vérifier si le texte de filtrage correspond à l'une des propriétés du logement
            return logement.getDescription().toLowerCase().contains(filtrefield.getText().toLowerCase())
                    || logement.getAdresse().toLowerCase().contains(filtrefield.getText().toLowerCase())
                    || logement.getEquipement().toLowerCase().contains(filtrefield.getText().toLowerCase())
                    || String.valueOf(logement.getId()).contains(filtrefield.getText());
        };

        // Mettre à jour la liste filtrée
        filteredList.setPredicate(filterPredicate);

        // Lier la liste filtrée à la TableView
        SortedList<logement> sortedData = new SortedList<>(filteredList);
        sortedData.comparatorProperty().bind(devisTableView.comparatorProperty());
        devisTableView.setItems(sortedData);
    }
    public void ouvrirFenetreS(String fxmlPath,String title) {
        try {
            Parent root = FXMLLoader.load(getClass().getResource(fxmlPath));
            Scene scene = new Scene(root);
            Stage stage = new Stage();
            stage.setScene(scene);
            stage.setTitle(title);
            stage.show();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
    @FXML
    private void ouvrirS(ActionEvent event) {
        ouvrirFenetreS("../stats.fxml", "Statistiques");

    }


    @FXML
    void printLogPdf(ActionEvent event) {
        try {
            logement selectedLogement = devisTableView.getSelectionModel().getSelectedItem();
            if (selectedLogement == null) {
                showAlert("No logement Selected", "Please select a logement to print.");
                return;
            }
// Appeler la méthode generatePdf de la classe pdfgenerator pour générer le PDF avec les détails du logement sélectionné
            pdfgenerator.generatePdf(selectedLogement.getAdresse(), selectedLogement.getDescription(),selectedLogement.getEquipement(), selectedLogement.getTarifs());


            showAlert("PDF Created", "Logement details printed to PDF successfully.");



        } catch (Exception e) {
            showAlert("Error", "An error occurred while printing the logement to PDF.");
            e.printStackTrace();
        }
    }



    void showAlert(String title, String content) {
        Alert alert = new Alert(Alert.AlertType.INFORMATION);
        alert.setTitle(title);
        alert.setHeaderText(null);
        alert.setContentText(content);
        alert.showAndWait();
    }

    @FXML
    private void handleFiltering(KeyEvent event) {
        // Obtenez le texte saisi dans le champ texte filtrefield
        String filterText = filtrefield.getText().toLowerCase();

        // Appliquez le filtre en fonction du texte saisi
        filteredList.setPredicate(logement -> {
            // Vérifiez si l'une des propriétés du logement contient le texte filtré
            return logement.getDescription().toLowerCase().contains(filterText)
                    || logement.getAdresse().toLowerCase().contains(filterText)
                    || logement.getEquipement().toLowerCase().contains(filterText)
                    || String.valueOf(logement.getId()).contains(filterText);
        });
    }

    public void AfficherCoworking(ActionEvent actionEvent) {
        try {
            FXMLLoader loader = new FXMLLoader(getClass().getResource("../AfficherCoworking.fxml"));
            Parent root = loader.load();

            Scene scene = new Scene(root);
            Stage stage = new Stage();
            stage.setScene(scene);

            stage.show();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public void cancelButton(ActionEvent actionEvent) {
        Stage stage = (Stage) cancel.getScene().getWindow();
        stage.close();

    }
}

