// Fichier non utilisé dans le code, mais mis en place pour montrer comment fonctionne un MODEL dans un MVC

package com.example.model;

public class Aliment {
    private int id; // L'identifiant unique de l'aliment
    private String nom; // Le nom de l'aliment
    private float poidsMoyen; // Le poids moyen de l'aliment
    private int calories; // La quantité de calories de l'aliment
    private float vitaminesC; // La quantité de vitamine C dans l'aliment
    private Integer typeId; // L'identifiant du type d'aliment (peut être null)
    private Integer couleurId; // L'identifiant de la couleur de l'aliment (peut être null)

    // Constructeur par défaut
    public Aliment() {
    }

    // Constructeur avec tous les attributs
    public Aliment( int id,String nom, float poidsMoyen, int calories, float vitaminesC, Integer typeId, Integer couleurId) {       
        this.id = id;
        this.nom = nom;
        this.poidsMoyen = poidsMoyen;
        this.calories = calories;
        this.vitaminesC = vitaminesC;
        this.typeId = typeId;
        this.couleurId = couleurId;
    }

    public Aliment( String nom, float poidsMoyen, int calories, float vitaminesC, Integer typeId, Integer couleurId) {       
        this.nom = nom;
        this.poidsMoyen = poidsMoyen;
        this.calories = calories;
        this.vitaminesC = vitaminesC;
        this.typeId = typeId;
        this.couleurId = couleurId;
    }

    // Getters et Setters pour chaque attribut
    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getNom() {
        return nom;
    }

    public void setNom(String nom) {
        this.nom = nom;
    }

    public float getPoidsMoyen() {
        return poidsMoyen;
    }

    public void setPoidsMoyen(float poidsMoyen) {
        this.poidsMoyen = poidsMoyen;
    }

    public int getCalories() {
        return calories;
    }

    public void setCalories(int calories) {
        this.calories = calories;
    }

    public float getVitaminesC() {
        return vitaminesC;
    }

    public void setVitaminesC(float vitaminesC) {
        this.vitaminesC = vitaminesC;
    }

    public Integer getTypeId() {
        return typeId;
    }

    public void setTypeId(Integer typeId) {
        this.typeId = typeId;
    }

    public Integer getCouleurId() {
        return couleurId;
    }

    public void setCouleurId(Integer couleurId) {
        this.couleurId = couleurId;
    }

    // Méthode toString pour afficher les informations de l'aliment
    @Override
    public String toString() {
        return "Aliment{" +
                "id=" + id +
                ", nom='" + nom + '\'' +
                ", poidsMoyen=" + poidsMoyen +
                ", calories=" + calories +
                ", vitaminesC=" + vitaminesC +
                ", typeId=" + typeId +
                ", couleurId=" + couleurId +
                '}';
    }
}