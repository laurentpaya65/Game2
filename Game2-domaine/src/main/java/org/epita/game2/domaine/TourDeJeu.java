package org.epita.game2.domaine;

import javax.persistence.*;

@Entity
public class TourDeJeu {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private long id;

    private int cptTour;

    @OneToOne(cascade = CascadeType.ALL,fetch = FetchType.EAGER)
    private Joueur joueurCourant;

    @OneToOne(cascade = CascadeType.ALL,fetch = FetchType.EAGER)
    private Table table;

    @OneToOne(cascade = CascadeType.ALL,fetch = FetchType.EAGER)
    private Deck deck;

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

    public Table getTable() {
        return table;
    }

    public void setTable(Table table) {
        this.table = table;
    }

    public Deck getDeck() {
        return deck;
    }

    public void setDeck(Deck deck) {
        this.deck = deck;
    }
}
