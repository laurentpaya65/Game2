package org.epita.game2.application;

import org.epita.game2.domaine.Carte;
import org.epita.game2.domaine.Deck;
import org.epita.game2.domaine.DeckDto;

public interface ServiceDeck {

    void initDeck();

    Carte piocher();

    void deckToDto(Deck deck);

    Deck dtoToDeck();
}
