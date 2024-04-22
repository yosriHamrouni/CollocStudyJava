package gui;

import entities.Coworking;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.TextField;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.stage.FileChooser;
import javafx.stage.Stage;
import services.ServiceCoworking;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.sql.SQLException;

public class ModifierCoworking {

    @FXML
    private TextField adresseTextField;

    @FXML
    private ImageView brandingImageView;

    @FXML
    private Button cancelButton;

    @FXML
    private Button confirmerButton;

    @FXML
    private TextField descriptionTextField;

    @FXML
    private TextField horairefermTextField;

    @FXML
    private TextField horaireouvrTextField;

    @FXML
    private TextField nomTextField;

    @FXML
    private TextField numtelTextField;

    @FXML
    private TextField taifsTextField;
    private Coworking selectedCo;
    @FXML
    private TextField imageTextField;
    @FXML
    private TextField dispoTextField;

    @FXML
    private ImageView ImageView;
    private String imagePathInDatabase;


    @FXML
    private Button selectImage;

    private AfficherCoworking AfficherCoworking;
    private File selectedImageFile;




    public void initData(Coworking selectedCo) {//, File selectedImageFile
        this.selectedCo = selectedCo;
        //File selectedImageFile=selectedCo.getImage();

        // Populate the fields in the UI with the data from selectedContrat
        nomTextField.setText( selectedCo.getNomco());
        descriptionTextField.setText( selectedCo.getDescription());
        numtelTextField.setText(selectedCo.getNumtel());
        horaireouvrTextField.setText(selectedCo.getHoraireouvr());
        horairefermTextField.setText(selectedCo.getHorairefer());
        adresseTextField.setText(selectedCo.getAdresse());
        taifsTextField.setText(String.valueOf( selectedCo.getTarifs()));
        imageTextField.setText(selectedCo.getImage());
        dispoTextField.setText(String.valueOf( selectedCo.getDispo()));
        /*if (selectedImageFile != null) {
            // Affichez l'image sélectionnée dans l'ImageView
            try {
                Image image = new Image(new FileInputStream(selectedImageFile));
                ImageView.setImage(image);
            } catch (FileNotFoundException e) {
                System.out.println("Erreur lors du chargement de l'image : " + e.getMessage());
            }
        } else {
            // Aucun fichier image sélectionné, conservez l'image existante de l'activité
            String imagePath = selectedCo.getImage();
            File imageFile = new File(imagePath);
            if (imageFile.exists()) {
                Image image = new Image(imageFile.toURI().toString());
                ImageView.setImage(image);
            } else {
                // Le fichier image n'existe pas, affichez un message d'erreur ou une image par défaut
                System.out.println("Le fichier image n'existe pas : " + imagePath);
            }
        }*/


    }
    public void browseImageAction(ActionEvent actionEvent) {
        FileChooser fileChooser = new FileChooser();
        fileChooser.setTitle("Choisir une image");
        File selectedFile = fileChooser.showOpenDialog(new Stage());
        if (selectedFile != null) {
            try {
                selectedImageFile = selectedFile;
                imagePathInDatabase = selectedFile.getAbsolutePath();
                Image image = new Image(new FileInputStream(selectedFile));
                ImageView.setImage(image);
            } catch (FileNotFoundException e) {
                System.out.println("Erreur lors du chargement de l'image : " + e.getMessage());
            }
        } else {
            System.out.println("Aucun fichier sélectionné");
        }

    }

    @FXML
    void Modifiercoworking(ActionEvent event) {
        try {
            String selectedNom = nomTextField.getText();
            String selectedDescription = descriptionTextField.getText();
            String selectednumtel = numtelTextField.getText();
            String selectedhoraireouvr = horaireouvrTextField.getText();
            String selectedhoraireferm = horairefermTextField.getText();
            String selectedadresse = adresseTextField.getText();
            String image=imagePathInDatabase;
            float selectedtarifs = Float.parseFloat(taifsTextField.getText());
            Integer selecteddispo = Integer.parseInt(dispoTextField.getText());




            // Create a new Coworking object with retrieved values
            Coworking coworking = new Coworking( selectedCo.getId(),selectedDescription, selectedhoraireouvr, selectedhoraireferm, image, selectedNom, selectednumtel, selectedadresse, selectedtarifs, selecteddispo);

            ServiceCoworking st = new ServiceCoworking();
            st.modifer(coworking);
            System.out.println("Coworking updated successfully.");
            // Optionally, refresh any associated UI elements or lists
            Stage stage = (Stage) confirmerButton.getScene().getWindow();
            stage.close();
        } catch (NumberFormatException e) {
            // Handle the NumberFormatException
            System.err.println("Error: Invalid input for tarifs or dispo.");
            e.printStackTrace(); // Print the stack trace for debugging purposes
            // You may show an alert or provide user feedback here
        }



    }

    @FXML
    void cancelButtonOnAction(ActionEvent event) {
        Stage stage = (Stage) cancelButton.getScene().getWindow();
        stage.close();

    }


    public void setListCoworkingController(gui.AfficherCoworking AfficherCoworking) {
            this.AfficherCoworking = AfficherCoworking;


    }
}
