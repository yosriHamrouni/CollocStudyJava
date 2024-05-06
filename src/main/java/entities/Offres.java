package entities;

import java.util.List;

public class Offres {
    int id ;
    String descrip ,horairedeb , horaireter ,image ,lieu;
    Double salaire;
    int typeoffre_id ,num_tel;
    boolean favoris;
    //private int id_type;



    private List<String> myList;



    public Offres(String descrip, Double salaire, String horairedeb, String horaireter, String lieu, String num_tel, int typeoffre_id, String image) {
        this.descrip = descrip;
        this.salaire = salaire;
        this.horairedeb = horairedeb;
        this.horaireter = horaireter;
        this.lieu = lieu;
        this.num_tel = Integer.parseInt(num_tel);
        this.typeoffre_id  = typeoffre_id ;
        this.image = image;
    }





    public Offres(String descrip,Double salaire, String horairedeb, String horaireter, String lieu, int num_tel,String image) {
        this.descrip = descrip;
        this.horairedeb = horairedeb;
        this.horaireter = horaireter;
        this.lieu = lieu;
        this.salaire = salaire;
        this.num_tel = num_tel;
        this.image = image;
    }



    public Offres(int id, String descrip, String horairedeb, String horaireter, String lieu, Double salaire, int num_tel) {
        this.id = id;
        this.descrip = descrip;
        this.horairedeb = horairedeb;
        this.horaireter = horaireter;
        this.lieu = lieu;
        this.salaire = salaire;
        this.num_tel = num_tel;
    }



    public Offres() {

    }






    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getDescrip() {
        return descrip;
    }

    public void setDescrip(String descrip) {
        this.descrip = descrip;
    }
    public String getList() {
        List<String> myList = null;
        return myList.toString();
    }

    public void setList(List<String> myList) {
        this.myList = myList;
    }
    public String getHorairedeb() {
        return horairedeb;
    }

    public void setHorairedeb(String horairedeb) {
        this.horairedeb = horairedeb;
    }

    public String getHoraireter() {
        return horaireter;
    }

    public void setHoraireter(String horaireter) {
        this.horaireter = horaireter;
    }

    public String getImage() {
        return image;
    }

    public void setImage(String image) {
        this.image = image;
    }

    public String getLieu() {
        return lieu;
    }

    public void setLieu(String lieu) {
        this.lieu = lieu;
    }

    public Double getSalaire() {
        return salaire;
    }

    public void setSalaire(Double salaire) {
        this.salaire = salaire;
    }

    public int getTypeoffre_id() {
        return typeoffre_id;
    }

    public void setTypeoffre_id(int typeoffre_id) {
        this.typeoffre_id = typeoffre_id;
    }

    public boolean getFavoris() {
        return favoris;
    }

    public void setFavoris(boolean favoris) {
        this.favoris = favoris;
    }

    public int getNum_tel() {
        return num_tel;
    }

    public void setNum_tel(int num_tel) {
        this.num_tel = num_tel;
    }

    @Override
    public String toString() {
        return "Offre{" +
                "id=" + id +
                ", descrip='" + descrip + '\'' +
                ", horairedeb='" + horairedeb + '\'' +
                ", horaireter='" + horaireter + '\'' +
                ", typeoffre_id=" + typeoffre_id +
                ", image='" + image + '\'' +
                ", lieu='" + lieu + '\'' +
                ", favoris=" + favoris +
                ", num_tel=" + num_tel +
                '}';
    }

}
