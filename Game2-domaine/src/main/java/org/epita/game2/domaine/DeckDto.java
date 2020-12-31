package org.epita.game2.domaine;

import org.springframework.stereotype.Component;

import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import java.util.Deque;

@Component
public class DeckDto {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private long id;

    private Deque<Carte> deck;

    public long getId() {
        return id;
    }

    public void setId(long id) {
        this.id = id;
    }

    public Deque<Carte> getDeck() {
        return deck;
    }

    public void setDeck(Deque<Carte> deck) {
        this.deck = deck;
    }

    @Override
    public String toString() {
        String deckAffiche = "Deck : ";
        for (Carte carte : deck) {
            deckAffiche += carte.toString();
        }
        return deckAffiche;
    }
}
