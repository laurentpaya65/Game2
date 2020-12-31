package org.epita.game2.application;

import org.epita.game2.domaine.Carte;
import org.epita.game2.domaine.Deck;
import org.epita.game2.domaine.DeckDto;
import org.epita.game2.domaine.Main;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.HashSet;
import java.util.Set;

import static org.epita.game2.domaine.Main.TAILLE_MAIN;

@Service
public class MainServiceImpl implements MainService {

    @Autowired
    private ServiceDeck serviceDeck;

    @Override
    public Main genererMain(Main main) {
        if (main == null) {
            main = new Main();
            Set<Carte> uneMain = new HashSet<>();
            main.setUneMain(uneMain);
        }
        int i = main.getUneMain().size() ;
        boolean piocheNonNull = true;
        while (i < TAILLE_MAIN && piocheNonNull) {
            Carte carte = serviceDeck.piocher();
            if (carte != null) {
                main.getUneMain().add(carte);
            } else {
                piocheNonNull = false;
            }
            i++;
        }
        return main;
    }

    @Override
    public boolean rechercheCarte(Carte carte,Main main) {
        if (carte == null) {
            return false;
        }

//        boolean cartePresente = false;
        for (Carte carte1 : main.getUneMain()) {
            if (carte.getValeur() == carte1.getValeur()) {
                return true;
            }
        }
        return false;
    }

    @Override
    public Set<Carte[]> chercheDiffDix(Main main) {
        Set<Carte[]> paireADix = new HashSet<>();
        Carte[] paireCarte = new Carte[2];
        for (Carte carte : main.getUneMain()) {
            for (Carte carte1 : main.getUneMain()) {
                if (carte.getValeur() - carte1.getValeur() == 10) {
                    paireCarte[0] = carte1;
                    paireCarte[1] = carte;
                    paireADix.add(paireCarte);
                }
            }
        }
        return paireADix;
    }
}
