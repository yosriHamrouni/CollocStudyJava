package org.example.entities;

public class User {
    private int id  ;
    private String email  ;
    private String roles ;
    private String password ;
    private String nom ;
    private String prenom ;

    public String getNom() {
        return nom;
    }

    public void setNom(String nom) {
        this.nom = nom;
    }

    public String getSexe() {
        return sexe;
    }

    public void setSexe(String sexe) {
        this.sexe = sexe;
    }

    public int getBloque() {
        return bloque;
    }

    public void setBloque(int bloque) {
        this.bloque = bloque;
    }

    private String sexe ;
    private int bloque ;

    public User() {
    }

    public User(int id, String email, String roles, String password, String nom, String prenom,String sexe, int bloque) {
        this.id = id;
        this.email = email;
        this.roles = roles;
        this.password = password;
        this.nom = nom;
        this.prenom = prenom;
        this.sexe=sexe;
        this.bloque = bloque;
    }

    public User(String email, String roles, String password, String nom, String prenom,String sexe, int bloque) {
        this.email = email;
        this.roles = roles;
        this.password = password;
        this.nom = nom;
        this.prenom = prenom;
        this.sexe=sexe;
        this.bloque = bloque;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getRoles() {
        return roles;
    }

    public void setRoles(String roles) {
        this.roles = roles;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public String getName() {
        return nom;
    }

    public User(int id) {
        this.id = id;
    }

    public void setName(String nom) {
        this.nom = nom;
    }

    public String getPrenom() {
        return prenom;
    }

    public void setPrenom(String prenom) {
        this.prenom = prenom;
    }


    public int getIs_banned() {
        return bloque;
    }

    public void setIs_banned(int bloque) {
        this.bloque = bloque;
    }

    @Override
    public String toString() {
        return "user{" +
                "nom='" + nom + '\'' +
                ", prenom='" + prenom + '\'' +
                '}';
    }
}
