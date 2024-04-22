package entities;

import javax.persistence.CascadeType;
import javax.persistence.OneToMany;
import java.util.List;

public class typelog {
    int id_type;
    String description,type;

    private List<logement>logements;
    public typelog()
    {}
    public typelog(int id_type,String type,String description) {
        this.id_type=id_type;
        this.type=type;
        this.description=description;}
    public typelog(String type,String description) {

        this.description=description;
        this.type=type;}
    public int getId() {
        return id_type;
    }


    public void setId(int id) {
        this.id_type = id_type;
    }
    public String getType() { return type;}

    public void setType(String type) {
        this.type = type;
    }
    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }
    @Override
    public String toString() {
        return "typelog{" +
                "id_type=" + id_type +
                "type=" + type +
                ", description=" + description +
                '}';
    }

}
