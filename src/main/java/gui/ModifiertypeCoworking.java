package gui;

import entities.Coworking;
import entities.TypeCo;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.TextField;
import javafx.stage.Stage;
import services.ServiceCoworking;
import services.ServiceTypeco;

public class ModifiertypeCoworking {

    @FXML
    private Button confirmerButton;

    @FXML
    private TextField txttypeco;

    private TypeCo selectedtype;

    private gui.AffichertypeCoworking AffichertypeCoworking;


    public void initData(TypeCo selectedtype){//, File selectedImageFile
        this.selectedtype = selectedtype;
        //File selectedImageFile=selectedCo.getImage();

        // Populate the fields in the UI with the data from selectedContrat
        txttypeco.setText(selectedtype.getType());

    }

    @FXML
    void Modifiertypecoworking(ActionEvent event) {
        try {
            String selectedtypeco = txttypeco.getText();
            TypeCo typeCo = new TypeCo(selectedtype.getId(), selectedtypeco);

            ServiceTypeco st = new ServiceTypeco();
            st.modifer(typeCo);
            System.out.println(" Type Coworking updated successfully.");
            // Optionally, refresh any associated UI elements or lists
            Stage stage = (Stage) confirmerButton.getScene().getWindow();
            stage.close();


        }catch (NumberFormatException e) {
            // Handle the NumberFormatException
            System.err.println("Error: Invalid input for tarifs or dispo.");
            e.printStackTrace(); // Print the stack trace for debugging purposes
            // You may show an alert or provide user feedback here
        }

    }
    public void setListCoworkingtypeController(gui.AffichertypeCoworking AffichertypeCoworking) {
        this.AffichertypeCoworking= AffichertypeCoworking;


    }


}
