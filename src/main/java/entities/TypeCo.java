package entities;

public class TypeCo {
    int id;
    String type;

    public TypeCo(int id, String type) {
        this.id = id;
        this.type = type;
    }

    public TypeCo(String type) {
        this.type = type;
    }
    public TypeCo() {

    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }

    @Override
    public String toString() {
        return "TypeCo{" +
                "id=" + id +
                ", type='" + type + '\'' +
                '}';
    }

    }

