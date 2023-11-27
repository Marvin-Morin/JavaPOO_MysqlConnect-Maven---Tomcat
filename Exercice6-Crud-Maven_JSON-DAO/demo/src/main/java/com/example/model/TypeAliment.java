// Fichier non utilisé dans le code, mais mis en place pour montrer comment fonctionne un MODEL dans un MVC

package com.example.model;

public class TypeAliment {
    
    private String nom;

    // Constructeur
    public TypeAliment(String nom) {
        this.nom = nom;
    }

    // Getters et setters
    public String getNom() {
        return nom;
    }

    public void setNom(String nomCouleur) {
        this.nom = nomCouleur;
    }
}
