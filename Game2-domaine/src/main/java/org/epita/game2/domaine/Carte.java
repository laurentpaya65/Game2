package org.epita.game2.domaine;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;

@Entity
public class Carte {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private long id;

    private int valeur;

    public long getId() {
        return id;
    }

    public void setId(long id) {
        this.id = id;
    }

    public int getValeur() {
        return valeur;
    }

    public void setValeur(int valeur) {
        this.valeur = valeur;
    }

    @Override
    public String toString() {
        String valeurString;
        if (valeur > 9) {
            return "[" + valeur + "]";
        }
        return "[0" + valeur + "]";
    }
}
