package entities;

public class logement {
    int id_log;
    String adresse, equipement, description, image;
    int dispo;
    float tarifs;


    private int id_type;



    public logement()
    {}
    public logement(int id_log, String adresse, String equipement, String description, String image, int dispo, float tarifs) {
        this.id_log = id_log;
        this.adresse = adresse;
        this.description = description;
        this.image = image;
        this.equipement = equipement;
        this.dispo = dispo;
        this.tarifs = tarifs;
       // this.id_type = id_type;
    }


    public logement(String adresse, String equipement, String description, String image, int dispo, float tarifs ) {
        this.adresse = adresse;
        this.equipement = equipement;
        this.description = description;
        this.image = image;
        this.dispo = dispo;
        this.tarifs = tarifs;
       // this.id_type = id_type;
    }

    public int getId_log() {
        return id_log;
    }

    public void setId_log(int id_log) {
        this.id_log = id_log;
    }

    public String getAdresse() {
        return adresse;
    }

    public void setAdresse(String adresse) {
        this.adresse = adresse;
    }

    public String getEquipement() {
        return equipement;
    }

    public void setEquipement(String equipement) {
        this.equipement = equipement;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public String getImage() {
        return image;
    }

    public void setImage(String image) {
        this.image = image;
    }

    public int getDispo() {
        return dispo;
    }

    public void setDispo(int dispo) {
        this.dispo = dispo;
    }

    public float getTarifs() {
        return tarifs;
    }

    public void setTarifs(int tarifs) {
        this.tarifs = tarifs;
    }

    public int getId_type() {
        return id_type;
    }

    public void setId_type(int id_type) {
        this.id_type = id_type;
    }


    @Override
    public String toString() {
        return "logement{" +
                "id_log=" + id_log +
                ", adresse='" + adresse + '\'' +
                ", equipement='" + equipement + '\'' +
                ", description='" + description + '\'' +
                ", image='" + image + '\'' +
                ", dispo='" + dispo + '\'' +
                ", tarifs=" + tarifs +
                '}';
    }
    public int getId() {
        return id_log;
    }

    public void setId(int id) {
        this.id_log = id;
    }

   /* public int getId_Type() {
        if (typelog != null) {
            return typelog.getId();
        } else {
            return -1; // ou une autre valeur par défaut appropriée
        }*/
    }


