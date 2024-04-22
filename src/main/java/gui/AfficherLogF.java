package gui;

import javafx.fxml.FXML;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.layout.FlowPane;
import javafx.scene.control.ScrollPane;
import javafx.stage.Stage;
import services.ServiceLogement;
import entities.logement;
import javafx.fxml.FXMLLoader;
import javafx.scene.Node;
import gui.CardV;

import java.awt.event.ActionEvent;
import java.io.IOException;
import java.util.List;

public class AfficherLogF {

    @FXML
    private ScrollPane scroll;

    @FXML
    private FlowPane logementsFlowPane;

    private final ServiceLogement serviceLogement = new ServiceLogement();

    @FXML
    private void initialize() {
        loadLogement(null); // Charge tous les logements initialement
    }

    private void loadLogement(String searchTerm) {
        try {
            List<logement> logements = serviceLogement.afficher();

            // Nettoie le conteneur avant d'ajouter de nouveaux logements
            logementsFlowPane.getChildren().clear();

            for (logement log : logements) {
                FXMLLoader loader = new FXMLLoader(getClass().getResource("../CardV.fxml"));
                Node card = loader.load(); // Cette ligne peut générer une IOException
                CardV controller = loader.getController();
                controller.setLogement(log);
                controller.setAffichagePubController(this); // Passe la référence à ce contrôleur
                logementsFlowPane.getChildren().add(card);
            }
        } catch (Exception e) { // Attrape toute exception ici
            e.printStackTrace();
        }
    }
    @FXML
    void handleDetailButton(ActionEvent event) {
        for (Node node : logementsFlowPane.getChildren()) {
            if (node instanceof CardV) {
                CardV card = (CardV) node;
                logement selectedLogement = card.getLogement();
                if (selectedLogement != null) {
                    // Passer le logement sélectionné à l'interface DetailLogement
                    FXMLLoader loader = new FXMLLoader(getClass().getResource("../DetailLogement.fxml"));
                    Parent root = loader.load();

                    DetailLogement detailController = loader.getController();
                    detailController.initData(selectedLogement);

                    Stage stage = new Stage();
                    stage.setScene(new Scene(root));
                    stage.setTitle("Détails du Logement");
                    stage.show();
                } else {
                    // Gérer le cas où aucun logement n'est sélectionné
                }
            }
        }

    }


}
