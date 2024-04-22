package gui;

import entities.Coworking;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Node;
import javafx.scene.control.ScrollPane;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;
import services.ServiceCoworking;

import java.io.IOException;
import java.net.URL;
import java.sql.SQLException;
import java.util.List;
import java.util.ResourceBundle;


public class AffichcoF implements Initializable {

    private ObservableList<Coworking> coworkingList;


    @FXML
    private ScrollPane scroll;
    public void setCoworkingList(ObservableList<Coworking> coworkingList) {
        this.coworkingList = coworkingList;
    }

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        try {
            AfficherCoF();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }
    @FXML
    public void AfficherCoF() throws SQLException{
        try {
            // Créer un VBox pour contenir tous les éléments Iteam
            VBox mainVBox = new VBox();
            mainVBox.setSpacing(20.0); // Espacement vertical entre les lignes

            // Récupérer la liste des activités depuis la base de données
            ServiceCoworking servicecoworking = new ServiceCoworking();
            List<Coworking> coworkingList = servicecoworking.afficher();

            // Créer une HBox pour chaque ligne d'éléments
            HBox hBox = new HBox();
            hBox.setSpacing(20.0); // Espacement horizontal entre les éléments

            // Ajouter chaque paire d'éléments à une ligne dans la HBox
            for (Coworking coworking : coworkingList) {
                FXMLLoader loader = new FXMLLoader(getClass().getResource("/iteam.fxml"));
                Node itemNode = loader.load();
                IteamController controller = loader.getController();
                controller.setData(coworking, null);

                // Ajouter l'élément à la ligne actuelle
                hBox.getChildren().add(itemNode);

                // Si la ligne est pleine (2 éléments), l'ajouter au VBox principal et créer une nouvelle ligne
                if (hBox.getChildren().size() == 2) {
                    mainVBox.getChildren().add(hBox);
                    hBox = new HBox();
                    hBox.setSpacing(20.0); // Réinitialiser l'espacement horizontal pour la nouvelle ligne
                }
            }

            // Ajouter la dernière ligne si elle n'est pas pleine
            if (!hBox.getChildren().isEmpty()) {
                mainVBox.getChildren().add(hBox);
            }

            // Définir le contenu du ScrollPane comme le VBox principal
            scroll.setContent(mainVBox);

            System.out.println("Chargement des éléments terminé avec succès.");

        } catch (IOException e) {
            e.printStackTrace();
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }

    }
}
