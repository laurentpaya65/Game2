package org.epita.game2.domaine;

import javax.persistence.*;
import java.util.ArrayList;
import java.util.Collection;
import java.util.List;
import java.util.Set;

@Entity
public class Pile {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private long id;

    private char sens;

    @OneToMany(cascade = CascadeType.ALL,fetch = FetchType.EAGER)
    private Collection<Carte> cartePile;

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

    public Collection<Carte> getCartePile() {
        return cartePile;
    }

    public void setCartePile(Collection<Carte> cartePile) {
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
