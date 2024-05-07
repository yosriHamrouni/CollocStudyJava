package Controllers;

import com.itextpdf.text.DocumentException;
import entities.Offres;
import javafx.beans.property.SimpleDoubleProperty;
import javafx.beans.property.SimpleIntegerProperty;
import javafx.beans.property.SimpleStringProperty;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.collections.transformation.FilteredList;
import javafx.collections.transformation.SortedList;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.stage.Stage;
import services.OffrePdfGenerator;
import services.ServiceOffres;

import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.net.URL;
import java.sql.SQLException;
import java.util.List;
import java.util.ResourceBundle;
import java.util.function.Predicate;

public class AfficherOffre implements Initializable {

    @FXML
    private TableView<Offres> offresTableView;

    @FXML
    private TableColumn<Offres, Integer> idtv;

    @FXML
    private TableColumn<Offres, String> Descriptv;

    @FXML
    private TableColumn<Offres, Double> salairetv;

    @FXML
    private TableColumn<Offres, String> Horairedebtv;

    @FXML
    private TableColumn<Offres, String> Horairefintv;

    @FXML
    private TableColumn<Offres, String> lieutv;

    @FXML
    private TableColumn<Offres, Integer> numteltv;

    @FXML
    private TableColumn<Offres, String> imagetv;

    @FXML
    private TextField filtrefield;

    @FXML
    private Button deletebtn;

    @FXML
    private Button editbtn;
    @FXML
    private Button generatePdfBtn;




    private ObservableList<Offres> offresList = FXCollections.observableArrayList();

    private FilteredList<Offres> filteredList;

    private final ServiceOffres serviceOffres = new ServiceOffres();

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        try {
            initTableView();
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }

    public void initTableView() throws SQLException {
        List<Offres> offresList = serviceOffres.afficher();
        offresTableView.getItems().clear();

        idtv.setCellValueFactory(cellData -> new SimpleIntegerProperty(cellData.getValue().getId()).asObject());
        Descriptv.setCellValueFactory(cellData -> new SimpleStringProperty(cellData.getValue().getDescrip()));
        salairetv.setCellValueFactory(cellData -> new SimpleDoubleProperty(cellData.getValue().getSalaire()).asObject());
        Horairedebtv.setCellValueFactory(cellData -> new SimpleStringProperty(cellData.getValue().getHorairedeb()));
        Horairefintv.setCellValueFactory(cellData -> new SimpleStringProperty(cellData.getValue().getHoraireter()));
        lieutv.setCellValueFactory(cellData -> new SimpleStringProperty(cellData.getValue().getLieu()));
        numteltv.setCellValueFactory(cellData -> new SimpleIntegerProperty(cellData.getValue().getNum_tel()).asObject());
        imagetv.setCellValueFactory(cellData -> new SimpleStringProperty(cellData.getValue().getImage()));

        offresTableView.getItems().addAll(offresList);
        offresTableView.setOnMouseClicked(event -> {
            if (event.getClickCount() == 2) {
                Offres selectedoffre = offresTableView.getSelectionModel().getSelectedItem();
                if (selectedoffre != null) {
                    navigateToUpdateOffre(selectedoffre);
                }
            }
        });

        filteredList = new FilteredList<>(offresTableView.getItems(), b -> true);

        filtrefield.textProperty().addListener((observable, oldValue, newValue) -> {
            filterTableView();
        });
    }

    @FXML
    private void filterTableView() {
        Predicate<Offres> filterPredicate = offre -> {
            return offre.getDescrip().toLowerCase().contains(filtrefield.getText().toLowerCase())
                    || offre.getLieu().toLowerCase().contains(filtrefield.getText().toLowerCase())
                    || String.valueOf(offre.getId()).contains(filtrefield.getText())
                    || String.valueOf(offre.getNum_tel()).contains(filtrefield.getText());
        };

        filteredList.setPredicate(filterPredicate);

        SortedList<Offres> sortedData = new SortedList<>(filteredList);
        sortedData.comparatorProperty().bind(offresTableView.comparatorProperty());
        offresTableView.setItems(sortedData);
    }

    private void navigateToUpdateOffre(Offres selectedoffre) {
        try {
            FXMLLoader loader = new FXMLLoader(getClass().getResource("../modifierOffre.fxml"));
            Parent root = loader.load();

            ModifierOffresController controller = loader.getController();
            controller.initData(selectedoffre);

            Stage stage = new Stage();
            stage.setScene(new Scene(root));
            stage.setTitle("Modifier Offre");
            stage.setOnCloseRequest(event -> {
                try {
                    initTableView();
                } catch (SQLException e) {
                    e.printStackTrace();
                }
            });
            stage.show();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    @FXML
    private void deleteOne(javafx.event.ActionEvent actionEvent) {
        Offres selectedOffres = offresTableView.getSelectionModel().getSelectedItem();
        if (selectedOffres != null) {
            Alert confirmAlert = new Alert(Alert.AlertType.CONFIRMATION);
            confirmAlert.setTitle("Confirmation de Suppression");
            confirmAlert.setHeaderText(null);
            confirmAlert.setContentText("Voulez-vous vraiment supprimer cette offre ?");

            confirmAlert.showAndWait().ifPresent(response -> {
                if (response == ButtonType.OK) {
                    try {
                        ServiceOffres serviceOffres = new ServiceOffres();
                        serviceOffres.supprimer(selectedOffres);
                        Alert successAlert = new Alert(Alert.AlertType.INFORMATION);
                        successAlert.setTitle("Suppression Réussie");
                        successAlert.setHeaderText(null);
                        successAlert.setContentText("L'offre a été supprimée avec succès.");
                        successAlert.showAndWait();
                        initTableView();
                    } catch (SQLException ex) {
                        Alert errorAlert = new Alert(Alert.AlertType.ERROR);
                        errorAlert.setTitle("Erreur de Suppression");
                        errorAlert.setHeaderText(null);
                        errorAlert.setContentText("Erreur lors de la suppression de l'offre : " + ex.getMessage());
                        errorAlert.showAndWait();
                    }
                }
            });
        } else {
            Alert alert = new Alert(Alert.AlertType.WARNING);
            alert.setTitle("Aucune Sélection");
            alert.setHeaderText(null);
            alert.setContentText("Veuillez sélectionner une offre à supprimer.");
            alert.showAndWait();
        }
    }

    @FXML
    private void editOne(javafx.event.ActionEvent actionEvent) {
        Offres selectedOffre = offresTableView.getSelectionModel().getSelectedItem();
        if (selectedOffre != null) {
            navigateToUpdateOffre(selectedOffre);
        } else {
            Alert alert = new Alert(Alert.AlertType.WARNING);
            alert.setTitle("Aucune Sélection");
            alert.setHeaderText(null);
            alert.setContentText("Veuillez sélectionner une offre à modifier.");
            alert.showAndWait();
        }
    }



    public void generatePdf(javafx.event.ActionEvent actionEvent) {
        try {
            List<Offres> offres = serviceOffres.afficher(); // Utiliser le serviceOffres initialisé globalement
            OffrePdfGenerator.generatePdf(offres, new FileOutputStream("C:\\Users\\MSI\\IdeaProjects\\ColocS\\Coworkings.pdf")); // Assurez-vous que le chemin de sortie est correct
            Alert alert = new Alert(Alert.AlertType.INFORMATION);
            alert.setTitle("PDF Généré");
            alert.setHeaderText(null);
            alert.setContentText("Le PDF des offres a été généré avec succès!");
            alert.showAndWait();
        } catch (FileNotFoundException | DocumentException | SQLException e) {
            Alert alert = new Alert(Alert.AlertType.ERROR);
            alert.setTitle("Erreur");
            alert.setHeaderText(null);
            alert.setContentText("Erreur lors de la génération du PDF des offres: " + e.getMessage());
            alert.showAndWait();
        }
    }
}
