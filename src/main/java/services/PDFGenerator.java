package services;

import entities.Coworking;
import org.apache.pdfbox.pdmodel.PDDocument;
import org.apache.pdfbox.pdmodel.PDPage;
import org.apache.pdfbox.pdmodel.PDPageContentStream;
import org.apache.pdfbox.pdmodel.font.PDType1Font;

import java.io.IOException;
import java.util.List;

public class PDFGenerator {
    public static void generatePDF(List<Coworking> coworkingList, String outputPath) {
        try (PDDocument document = new PDDocument()) {
            PDPage page = new PDPage();
            document.addPage(page);

            PDPageContentStream contentStream = new PDPageContentStream(document, page);
            contentStream.beginText();
            contentStream.setFont(PDType1Font.HELVETICA_BOLD, 12);
            contentStream.newLineAtOffset(100, 700);

            for (Coworking coworking : coworkingList) {
                contentStream.showText("Description: " + coworking.getDescription());
                contentStream.newLine();
                contentStream.showText("Horaire d'ouverture: " + coworking.getHoraireouvr());
                contentStream.newLine();
                contentStream.showText("Horaire de fermeture: " + coworking.getHorairefer());
                contentStream.newLine();
                // Ajoutez d'autres champs de coworking selon vos besoins
                contentStream.newLine();
            }

            contentStream.endText();
            contentStream.close();

            document.save(outputPath);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
