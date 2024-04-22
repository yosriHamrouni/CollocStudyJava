package entities;

public class Coworking {
    int id;
    String description,horaireouvr,horairefer,image,nomco,numtel,adresse;
    float tarifs;
    int dispo,likee,dislikee;
    int  typeco_id;

    public Coworking(int id, String description, String horaireouvr, String horairefer, String image, String nomco, String numtel, String adresse, float tarifs, int dispo) {
        this.id = id;
        this.description = description;
        this.horaireouvr = horaireouvr;
        this.horairefer = horairefer;
        this.image = image;
        this.nomco = nomco;
        this.numtel = numtel;
        this.adresse = adresse;
        this.tarifs = tarifs;
        this.dispo = dispo;
    }
    public Coworking()
    {

    }

    public Coworking(String description, String horaireouvr, String horairefer, String image, String nomco, String numtel, String adresse, float tarifs, int dispo, int typeco_id) {
        this.description = description;
        this.horaireouvr = horaireouvr;
        this.horairefer = horairefer;
        this.image = image;
        this.nomco = nomco;
        this.numtel = numtel;
        this.adresse = adresse;
        this.tarifs = tarifs;
        this.dispo = dispo;
       this.typeco_id=typeco_id;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public String getHoraireouvr() {
        return horaireouvr;
    }

    public void setHoraireouvr(String horaireouvr) {
        this.horaireouvr = horaireouvr;
    }

    public String getHorairefer() {
        return horairefer;
    }

    public void setHorairefer(String horairefer) {
        this.horairefer = horairefer;
    }

    public String getImage() {
        return image;
    }

    public void setImage(String image) {
        this.image = image;
    }

    public String getNomco() {
        return nomco;
    }

    public void setNomco(String nomco) {
        this.nomco = nomco;
    }

    public String getNumtel() {
        return numtel;
    }

    public void setNumtel(String numtel) {
        this.numtel = numtel;
    }

    public String getAdresse() {
        return adresse;
    }

    public void setAdresse(String adresse) {
        this.adresse = adresse;
    }

    public float getTarifs() {
        return tarifs;
    }

    public void setTarifs(float tarifs) {
        this.tarifs = tarifs;
    }

    public int getDispo() {
        return dispo;
    }

    public void setDispo(int dispo) {
        this.dispo = dispo;
    }

    public int getLikee() {
        return likee;
    }

    public void setLikee(int likee) {
        this.likee = likee;
    }

    public int getTypeco_id() {
        return typeco_id;
    }

    public void setTypeco_id(int  typeco_id) {
        this.typeco_id = typeco_id;
    }

    public int getDislikee() {
        return dislikee;
    }

    public void setDislikee(int dislikee) {
        this.dislikee = dislikee;
    }

    @Override
    public String toString() {
        return "coworking{" +
                "id=" + id +
                ", description='" + description + '\'' +
                ", horaireouvr='" + horaireouvr + '\'' +
                ", horaireder='" + horairefer + '\'' +
                ", image='" + image + '\'' +
                ", nomco='" + nomco + '\'' +
                ", numtel='" + numtel + '\'' +
                ", adresse='" + adresse + '\'' +
                ", tarifs=" + tarifs +
                ", dispo=" + dispo +
                ", likee=" + likee +
                ", dislikee=" + dislikee +
                '}';
    }
}
