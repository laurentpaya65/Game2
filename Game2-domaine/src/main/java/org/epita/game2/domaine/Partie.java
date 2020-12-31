package org.epita.game2.domaine;

import javax.persistence.*;
import java.util.Set;

@Entity
public class Partie {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private long id;

    private String nom;
    private String nomFichier;

    private String etat;

    @OneToMany(cascade = CascadeType.ALL,fetch = FetchType.EAGER)
    private Set<Joueur> joueurs;

    @OneToMany(cascade = CascadeType.ALL,fetch = FetchType.EAGER)
    private Set<TourDeJeu> tours;

    public long getId() {
        return id;
    }

    public void setId(long id) {
        this.id = id;
    }

    public String getNom() {
        return nom;
    }

    public void setNom(String nom) {
        this.nom = nom;
    }

    public String getNomFichier() {
        return nomFichier;
    }

    public void setNomFichier(String nomFichier) {
        this.nomFichier = nomFichier;
    }

    public String getEtat() {
        return etat;
    }

    public void setEtat(String etat) {
        this.etat = etat;
    }

    public Set<Joueur> getJoueurs() {
        return joueurs;
    }

    public void setJoueurs(Set<Joueur> joueurs) {
        this.joueurs = joueurs;
    }

    public Set<TourDeJeu> getTours() {
        return tours;
    }

    public void setTours(Set<TourDeJeu> tours) {
        this.tours = tours;
    }
}
