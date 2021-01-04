package org.epita.game2.domaine;

import org.springframework.stereotype.Component;

import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import java.util.Deque;

@Component
public class PileDto {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private long id;
    public final static int TAILLE_PILE = 25;

    private char sens;

    private Deque<Carte> cartePile;

    public long getId() {
        return id;
    }

    public void setId(long id) {
        this.id = id;
    }

    public char getSens() {
        return sens;
    }

    public void setSens(char sens) {
        this.sens = sens;
    }

    public Deque<Carte> getCartePile() {
        return cartePile;
    }

    public void setCartePile(Deque<Carte> cartePile) {
        this.cartePile = cartePile;
    }

    @Override
    public String toString() {
        String pileAffiche = "";
        if (sens == 'A') {
            pileAffiche = "Pile Asc : ";
        } else {
            pileAffiche = "Pile Dsc : ";
        }

        for (Carte carte : cartePile) {
            pileAffiche += carte.toString();
        }
        return pileAffiche;
    }
}
