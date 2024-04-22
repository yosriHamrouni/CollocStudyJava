package test;

import entities.Offres;
import entities.TypeOffres;
import services.ServiceOffres;
import services.ServiceType;
import utils.MyDB;
/*import entities.Typeoffre;
import services.ServiceTypeoffres;*/

import java.sql.SQLException;
public class Main {
    public static void main(String[] args) {

        MyDB db = MyDB.getInstance();
        System.out.println(db.hashCode());


        //Offres offres5 = new Offres("aaaa", 450.0,"17H","20H","ZZZ",22334456);
        //ServiceOffres so = new ServiceOffres();
        ServiceType so = new ServiceType();
        TypeOffres typeOffres1 = new TypeOffres(2,"hhh");
       // TypeOffres typeOffres2 = new TypeOffres("Babysitting");
        try {
      /*  so.ajouter(offres1);
        so.ajouter(offres2);
        so.ajouter(offres3);
        so.ajouter(offres4);*/
        //so.ajouter(offres5);
        so.supprimer(typeOffres1);
        } catch (SQLException e) {
            System.out.println(e.getMessage());
        }


        /*
        try {
            so.modifier(offres2);
            so.supprimer(offres1);
        }catch (SQLException e){
            System.out.println(e.getMessage());
        }
        /*try {
            so.supprimer(offres2);
        }catch (SQLException e){
            System.out.println(e.getMessage());
        }*/
        /*try {
            System.out.println(so.afficher());
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }*/


    }
}
