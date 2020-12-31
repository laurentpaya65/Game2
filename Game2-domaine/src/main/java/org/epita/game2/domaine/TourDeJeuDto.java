package org.epita.game2.domaine;

import org.springframework.stereotype.Component;

import javax.persistence.*;

@Component
public class TourDeJeuDto {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private long id;

    private int cptTour;

    private Joueur joueurCourant;

    public static TableDto tableDto;

    public static DeckDto deckDto;

    public long getId() {
        return id;
    }

    public void setId(long id) {
        this.id = id;
    }

    public int getCptTour() {
        return cptTour;
    }

    public void setCptTour(int cptTour) {
        this.cptTour = cptTour;
    }

    public Joueur getJoueurCourant() {
        return joueurCourant;
    }

    public void setJoueurCourant(Joueur joueurCourant) {
        this.joueurCourant = joueurCourant;
    }

    public TableDto getTableDto() {
        return tableDto;
    }

    public DeckDto getDeckDto() {
        return deckDto;
    }

    @Override
    public String toString() {
        String tourDeJeuAffiche="";
        if (joueurCourant == null) {
            tourDeJeuAffiche += "----------  TOUR n° "+cptTour+" ---------------------\n";
            tourDeJeuAffiche += "Pas de joueur courant\n";
        } else {
            tourDeJeuAffiche += "----------  TOUR n° "+cptTour+" de "+joueurCourant.getNom()+" ---------------------\n";
            tourDeJeuAffiche += joueurCourant.toString() + "\n";
        }
        if (deckDto == null) {
            tourDeJeuAffiche += "Pas de Deck \n";
        } else {
            if (deckDto.getDeck().size() == 0) {
                tourDeJeuAffiche += "Deck vide !!!\n";
            } else {
                tourDeJeuAffiche += deckDto.toString() + "\n";
            }
        }
        if (tableDto == null) {
            tourDeJeuAffiche += "Pas de table de jeu\n";
        } else {
            tourDeJeuAffiche += tableDto.toString();
        }
        return tourDeJeuAffiche;
    }
}
