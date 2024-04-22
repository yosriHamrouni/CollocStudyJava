package test;

import services.ServiceLogement;
import entities.logement;
import services.ServiceTypelog;
import entities.typelog;
import java.sql.SQLException;

public class Main {
    public static void main(String[] args) throws SQLException {
        // Créer une instance de ServiceLogement
        ServiceLogement sl = new ServiceLogement();
        ServiceTypelog tl = new ServiceTypelog();

        typelog t1 = new typelog("suite", "suite_a_trois");
        typelog t2 = new typelog(2,"villa", "suite_a_trois");
        // Créer des instances de Logement
        // Créer des instances de Logement
        //logement l1 = new logement( "tunis", "villa", "suite", "", 0, 2100,14);
      //logement l2 = new logement(2,"Djerba", "suite", "suite", "", 0, 500);
        //logement l3 = new logement("Nabeul", "piscine", "suite", "", 0, 2100);

        //logement l3 = new logement("Nabeul", "piscine", "suite", "", 0, 2100);
       // typelog t1=new typelog(8,"suite","suite_a_deux");

       // logement l2 = new logement(4, "bizerte", "suite", "suite", "", 0, 500, typeSuite);
  try {
           // sl.ajouter(l1);
            System.out.println(sl.afficher());
        } catch (SQLException e) {
            System.err.println("Erreur: " + e.getMessage());
        }
        // Ajouter les logements à la base de données
      /* try {
            tl.supprimer(t2);
            System.out.println(tl.afficher());
        } catch (SQLException e) {
            System.err.println("Erreur: " + e.getMessage());
        }*/



       /* try {
           // sl.modifier(l2);
            //sl.supprimer(l2);
            System.out.println(sl.afficher());
        } catch (SQLException e) {
            System.err.println("Erreur: " + e.getMessage());
        }*/

    }
}