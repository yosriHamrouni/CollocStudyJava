package gui;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.chart.PieChart;
import javafx.scene.control.Label;
import services.ServiceCoworking;

import java.net.URL;
import java.util.Map;
import java.util.ResourceBundle;

public class Statcoworking implements Initializable {

    @FXML
    private PieChart pieChart;

    @Override
    public void initialize(URL location, ResourceBundle resources) {
        ServiceCoworking serviceCoworking = new ServiceCoworking();

        // Récupération des statistiques sur les espaces de coworking par adresse
        Map<String, Integer> coworkingByAddress = serviceCoworking.getCoworkingByAdresse();

        // Création des données pour le graphique PieChart
        ObservableList<PieChart.Data> pieChartData = FXCollections.observableArrayList();
        for (Map.Entry<String, Integer> entry : coworkingByAddress.entrySet()) {
            pieChartData.add(new PieChart.Data(entry.getKey(), entry.getValue()));
        }


        // Configuration du PieChart
        pieChart.setData(pieChartData);
        pieChart.setTitle("Coworking Engagement by Address");
    }
}
