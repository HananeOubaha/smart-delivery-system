package com.smartlogi.entity;

import com.smartlogi.enums.StatutColis;

import javax.persistence.*;
import java.io.Serializable;
import com.fasterxml.jackson.annotation.JsonIgnore;

@Entity
@Table(name = "colis")
public class Colis implements Serializable {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String destinataire;
    private String adresse;
    private Double poids;

    @Enumerated(EnumType.STRING) // Stocke l'enum en tant que String ("EN_TRANSIT") et non en nombre (1)
    private StatutColis statut;

    // --- Relation Many-to-One ---
    // C'est l'entité "propriétaire".
    @JsonIgnore
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "livreur_id", nullable = false) // Crée la colonne "livreur_id" dans la table "colis"
    private Livreur livreur;

    // --- Constructeurs ---
    public Colis() {
    }

    public Colis(String destinataire, String adresse, Double poids, StatutColis statut, Livreur livreur) {
        this.destinataire = destinataire;
        this.adresse = adresse;
        this.poids = poids;
        this.statut = statut;
        this.livreur = livreur;
    }

    // --- Getters et Setters ---
    // (Générez-les avec votre IDE)

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getDestinataire() {
        return destinataire;
    }

    public void setDestinataire(String destinataire) {
        this.destinataire = destinataire;
    }

    public String getAdresse() {
        return adresse;
    }

    public void setAdresse(String adresse) {
        this.adresse = adresse;
    }

    public Double getPoids() {
        return poids;
    }

    public void setPoids(Double poids) {
        this.poids = poids;
    }

    public StatutColis getStatut() {
        return statut;
    }

    public void setStatut(StatutColis statut) {
        this.statut = statut;
    }

    public Livreur getLivreur() {
        return livreur;
    }

    public void setLivreur(Livreur livreur) {
        this.livreur = livreur;
    }
}