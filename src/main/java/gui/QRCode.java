package gui;

import com.google.zxing.BarcodeFormat;
import com.google.zxing.WriterException;
import entities.logement;
import entities.QRGenerator;
import javafx.embed.swing.SwingFXUtils;
import javafx.fxml.FXML;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;

import java.awt.image.BufferedImage;

public class QRCode {

    @FXML
    private ImageView qrCodeImageView;
    private String log;

    public void initialize() {
        // Initialisation, par exemple, si nécessaire
    }

    public void setLog(logement logement) {
        if (logement != null) {
            log = "Adresse:" + logement.getAdresse() +
                    " Equipement:" + logement.getEquipement() +
                    " Description:" + logement.getDescription();
            generateQRCode();  // Générer le code QR lorsque les informations du logement sont définies
        }
    }

    private void generateQRCode() {
        if (log == null || log.isEmpty()) {
            System.out.println("Les informations sur le logement ne sont pas définies.");
            return;
        }

        String contentToEncode = log;
        int qrCodeSize = 300;

        try {
            QRGenerator qrGenerator = new QRGenerator(
                    contentToEncode,
                    BarcodeFormat.QR_CODE,
                    qrCodeSize,
                    qrCodeSize,
                    BufferedImage.TYPE_INT_ARGB
            );

            BufferedImage qrImage = qrGenerator.qrImage();

            // Convertir BufferedImage en Image JavaFX
            Image image = SwingFXUtils.toFXImage(qrImage, null);

            // Définir l'image générée sur l'ImageView
           qrCodeImageView.setImage(image);

        } catch (WriterException e) {
            e.printStackTrace();
        }
    }
}
