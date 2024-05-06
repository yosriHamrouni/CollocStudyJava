package services;

import com.itextpdf.text.*;
import com.itextpdf.text.pdf.BaseFont;
import com.itextpdf.text.pdf.PdfPCell;
import com.itextpdf.text.pdf.PdfPTable;
import com.itextpdf.text.pdf.PdfWriter;
import entities.Offres;

import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.net.URL;
import java.time.LocalDate;
import java.util.List;

public class OffrePdfGenerator {

    private static final String FONT_PATH = "C:\\Users\\MSI\\Desktop\\Poppins-Regular.ttf"; // Chemin vers le fichier de police Poppins
    private static final String TITLE_FONT_PATH = "C:\\Users\\MSI\\Desktop\\Heavitas.ttf"; // Chemin vers le fichier de police Heavitas

    public static void generatePdf(List<Offres> offres, FileOutputStream fileOutputStream)
            throws FileNotFoundException, DocumentException {
        Document document = new Document(PageSize.A4);
        PdfWriter.getInstance(document, fileOutputStream);
        document.open();

        // Charger la police Poppins
        Font fontDate = FontFactory.getFont(FONT_PATH, BaseFont.IDENTITY_H, BaseFont.EMBEDDED, 12, Font.NORMAL);
        Font fontTitle = FontFactory.getFont(TITLE_FONT_PATH, BaseFont.IDENTITY_H, BaseFont.EMBEDDED, 24, Font.BOLD,BaseColor.RED);
        Font fontHeader = FontFactory.getFont(FONT_PATH, BaseFont.IDENTITY_H, BaseFont.EMBEDDED, 12, Font.BOLD, BaseColor.WHITE);

        // Définir la couleur de fond du tableau en bleu (#191f9e)
        BaseColor tableBackgroundColor = new BaseColor(25, 31, 158); // Bleu foncé

        // Ajouter le logo du projet
        try {
            URL logoUrl = OffrePdfGenerator.class.getResource("/img/logo-v2.png");
            if (logoUrl != null) {
                Image logo = Image.getInstance(logoUrl);
                logo.scaleToFit(100, 100); // Ajuster la taille du logo si nécessaire
                logo.setAlignment(Element.ALIGN_RIGHT);
                document.add(logo);
            } else {
                System.out.println("Le fichier de logo n'a pas été trouvé.");
            }
        } catch (IOException e) {
            e.printStackTrace();
        }

        // Ajout de la date actuelle
        LocalDate currentDate = LocalDate.now();
        Paragraph dateParagraph = new Paragraph("Date: " + currentDate.toString(), fontDate);
        dateParagraph.setAlignment(Element.ALIGN_RIGHT);
        document.add(dateParagraph);

        // Titre du document
        Paragraph titleParagraph = new Paragraph("LISTE DES OFFRES EXISTANTES", fontTitle);
        titleParagraph.setAlignment(Element.ALIGN_CENTER);
        document.add(titleParagraph);
        document.add(new Paragraph("\n"));

        // Contenu des offres
        PdfPTable table = new PdfPTable(4); // 4 colonnes pour les détails des offres
        table.setWidthPercentage(100);
        table.setSpacingBefore(10f);
        table.setSpacingAfter(10f);
        table.setHeaderRows(1); // Définir la première ligne comme ligne d'en-tête

        // En-têtes de colonnes
        PdfPCell cellDescription = new PdfPCell(new Phrase("Description", fontHeader));
        cellDescription.setBackgroundColor(tableBackgroundColor);
        cellDescription.setHorizontalAlignment(Element.ALIGN_CENTER);
        cellDescription.setVerticalAlignment(Element.ALIGN_MIDDLE);
        table.addCell(cellDescription);

        PdfPCell cellLieu = new PdfPCell(new Phrase("Lieu", fontHeader));
        cellLieu.setBackgroundColor(tableBackgroundColor);
        cellLieu.setHorizontalAlignment(Element.ALIGN_CENTER);
        cellLieu.setVerticalAlignment(Element.ALIGN_MIDDLE);
        table.addCell(cellLieu);

        PdfPCell cellSalaire = new PdfPCell(new Phrase("Salaire", fontHeader));
        cellSalaire.setBackgroundColor(tableBackgroundColor);
        cellSalaire.setHorizontalAlignment(Element.ALIGN_CENTER);
        cellSalaire.setVerticalAlignment(Element.ALIGN_MIDDLE);
        table.addCell(cellSalaire);

        PdfPCell cellNumeroTel = new PdfPCell(new Phrase("Numéro de téléphone", fontHeader));
        cellNumeroTel.setBackgroundColor(tableBackgroundColor);
        cellNumeroTel.setHorizontalAlignment(Element.ALIGN_CENTER);
        cellNumeroTel.setVerticalAlignment(Element.ALIGN_MIDDLE);
        table.addCell(cellNumeroTel);

        // Ajouter les données des offres
        Font fontData = FontFactory.getFont(FONT_PATH, BaseFont.IDENTITY_H, BaseFont.EMBEDDED, 12, Font.NORMAL, BaseColor.BLACK);
        for (Offres offre : offres) {
            table.addCell(new Phrase(offre.getDescrip(), fontData));
            table.addCell(new Phrase(offre.getLieu(), fontData));
            table.addCell(new Phrase(String.valueOf(offre.getSalaire()), fontData));
            table.addCell(new Phrase(String.valueOf(offre.getNum_tel()), fontData));
        }

        document.add(table);

        document.close();
    }
}
