package com.smartlogi.entity;

import javax.persistence.*;
import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;
import com.fasterxml.jackson.annotation.JsonIgnore;

@Entity
@Table(name = "livreurs") // Nom de la table en BDD
public class Livreur implements Serializable {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY) // Laisse PostgreSQL gérer l'auto-incrémentation
    private Long id;

    private String nom;
    private String prenom;
    private String vehicule;
    private String telephone;

    // --- Relation One-to-Many ---
    // mappedBy = "livreur" : Dit à JPA que c'est le champ "livreur"
    // dans la classe "Colis" qui gère la clé étrangère.
    // Le Livreur n'a pas de colonne "colis_id".
    @JsonIgnore
    @OneToMany(
            mappedBy = "livreur",
            cascade = CascadeType.ALL, // Si on supprime un livreur, ses colis sont supprimés
            orphanRemoval = true,      // Si on enlève un colis de la liste, il est supprimé
            fetch = FetchType.LAZY     // Ne charge les colis que si on le demande (performance)
    )
    private List<Colis> colis = new ArrayList<>();

    // --- Constructeurs ---
    // JPA a besoin d'un constructeur vide
    public Livreur() {
    }

    public Livreur(String nom, String prenom, String vehicule, String telephone) {
        this.nom = nom;
        this.prenom = prenom;
        this.vehicule = vehicule;
        this.telephone = telephone;
    }

    // --- Getters et Setters ---
    // Vous pouvez les générer avec votre IDE (Alt+Insert > Getters and Setters)

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getNom() {
        return nom;
    }

    public void setNom(String nom) {
        this.nom = nom;
    }

    public String getPrenom() {
        return prenom;
    }

    public void setPrenom(String prenom) {
        this.prenom = prenom;
    }

    public String getVehicule() {
        return vehicule;
    }

    public void setVehicule(String vehicule) {
        this.vehicule = vehicule;
    }

    public String getTelephone() {
        return telephone;
    }

    public void setTelephone(String telephone) {
        this.telephone = telephone;
    }

    public List<Colis> getColis() {
        return colis;
    }

    public void setColis(List<Colis> colis) {
        this.colis = colis;
    }
}