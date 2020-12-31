package org.epita.game2.application;

import org.epita.game2.domaine.Carte;
import org.epita.game2.domaine.Deck;
import org.epita.game2.domaine.DeckDto;
import org.epita.game2.domaine.Joueur;

public interface JoueurService {

    Joueur completeMain(Joueur joueur);

    Joueur jouerUneCarte(Joueur joueur, Carte carte);



}
