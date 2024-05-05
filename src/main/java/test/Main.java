package test;

import com.itextpdf.text.DocumentException;
import entities.Coworking;
import entities.TypeCo;
import services.CoworkingPDFGenerator;
import services.ServiceCoworking;
import services.ServiceTypeco;
import utils.MyDB;


import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.sql.SQLException;
import java.util.List;

public class Main {
    public static void main (String[] args) throws SQLException, DocumentException, FileNotFoundException {



        //Coworking c1 =new Coworking("CCC","8H","18H","images/coworking4","Coworking4","52977656","Nabeul",14,1);

        ServiceCoworking sp=new ServiceCoworking();
        // Ajouter le coworking à la base de données
        //sp.ajouter(c);
        //System.out.println("Coworking ajouté avec succès !");
         //sp.ajouter(c1);
       //System.out.println("Coworking ajouté avec succès !");
        //sp.modifer(c);
       // sp.supprimer(c);
      System.out.println(sp.afficher());
      //Recherche
        List<Coworking> resultats = sp.rechercherCoworking("Coworking1",14);
        for (Coworking coworking : resultats) {
            System.out.println("ID: " + coworking.getId());
            System.out.println("Description: " + coworking.getDescription());
            System.out.println("Horaire d'ouverture: " + coworking.getHoraireouvr());
            System.out.println("Horaire de fermeture: " + coworking.getHorairefer());
            System.out.println("Image: " + coworking.getImage());
            System.out.println("Nom: " + coworking.getNomco());
            System.out.println("Numéro de téléphone: " + coworking.getNumtel());
            System.out.println("Adresse: " + coworking.getAdresse());
            System.out.println("Tarifs: " + coworking.getTarifs());
            System.out.println("Disponibilité: " + coworking.getDispo());
            System.out.println("----------------------------------------");
        }
        generatePDFAfterAdd(sp);






        //Type de coworking
        TypeCo tc=new TypeCo(3,"ttt");
        ServiceTypeco st=new ServiceTypeco();
        // st.ajouter(tc);
       // System.out.println(" type Coworking ajouté avec succès !");
       //System.out.println(st.afficher());
        //st.modifer(tc);



    }

    private static void generatePDFAfterAdd(ServiceCoworking sp) throws FileNotFoundException, SQLException, DocumentException {
        ServiceCoworking tp = new ServiceCoworking();
        List<Coworking> coworkingList = null;
        try {
            coworkingList = tp.afficher();
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }

        // Générer le PDF avec les données des espaces de coworking
        String outputPath = "coworking_list.pdf"; // Chemin de sortie du fichier PDF

        CoworkingPDFGenerator.generatePDF(coworkingList, new FileOutputStream("C:\\Users\\MSI\\IdeaProjects\\oumeima\\Coworkings.pdf"), "C:\\Users\\MSI\\IdeaProjects\\oumeima\\src\\main\\resources\\img\\colocstudy.jpg");
        System.out.println("PDF généré avec succès !");

    }


}

