package org.epita.game2.domaine;

import org.springframework.stereotype.Component;

import javax.persistence.*;
import java.util.Deque;
import java.util.List;
import java.util.Set;

@Component
public class PartieDto {
    public static PartieDto partieDto = new PartieDto();

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private long id;

    private String nom;
    private String nomFichier;

    // "ENC" pour partie en cours ou interrompue
    // "FIN" pour partie Terminée
    private String etat;

    private List<Joueur> joueurs;

    private Deque<TourDeJeuDto> tours;

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

    public List<Joueur> getJoueurs() {
        return joueurs;
    }

    public void setJoueurs(List<Joueur> joueurs) {
        this.joueurs = joueurs;
    }

    public Deque<TourDeJeuDto> getTours() {
        return tours;
    }

    public void setTours(Deque<TourDeJeuDto> tours) {
        this.tours = tours;
    }

    @Override
    public String toString() {
        String partieAffiche="------------- PARTIE "+nom+" -------------------\n";
        for (Joueur joueur : joueurs) {
            if (joueur.getMain() == null) {
                partieAffiche += joueur.getNom() + " , ";
            } else {
                partieAffiche += joueur.toString()+" , ";
            }
        }
        partieAffiche+="\n";
        if (tours == null) {
            partieAffiche += "Pas de tour\n";
        } else {
            for (TourDeJeuDto tour : tours) {
                partieAffiche += tour.toString();
            }
        }
        return partieAffiche;
    }
}
