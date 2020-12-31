package org.epita.game2.application;

import org.epita.game2.domaine.Carte;
import org.epita.game2.domaine.Deck;
import org.epita.game2.domaine.DeckDto;
import org.epita.game2.domaine.Main;

public interface MainService {

    Main genererMain(Main main);

    boolean rechercheCarte(Carte carte,Main main);
}
