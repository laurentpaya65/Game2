package org.epita.game2.domaine;

import javax.persistence.*;
import java.util.Collection;
import java.util.List;
import java.util.Queue;

@Entity
public class Deck {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private long id;

    @OneToMany(cascade = CascadeType.ALL,fetch = FetchType.EAGER)
    private Collection<Carte> deckCarte;

    public long getId() {
        return id;
    }

    public void setId(long id) {
        this.id = id;
    }

    public Collection<Carte> getDeckCarte() {
        return deckCarte;
    }

    public void setDeckCarte(Collection<Carte> deckCarte) {
        this.deckCarte = deckCarte;
    }

    @Override
    public String toString() {
        String deckAffiche = "";
        for (Carte carte : deckCarte) {
            deckAffiche += carte.toString();
        }
        return deckAffiche;
    }
}
