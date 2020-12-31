package org.epita.game2.application;

import org.epita.game2.domaine.Carte;
import org.epita.game2.domaine.Deck;
import org.epita.game2.domaine.DeckDto;
import org.epita.game2.domaine.TourDeJeuDto;
import org.springframework.stereotype.Service;

import java.util.*;

import static org.epita.game2.domaine.PileDto.TAILLE_PILE;

@Service
public class ServiceDeckImpl implements ServiceDeck {
    private Deque<Carte> pioche = new ArrayDeque<>();

     public void initDeck() {
         while (pioche.size() != 0) {
             pioche.pollLast();
         }

        for (int i = 2; i < 2 + TAILLE_PILE ; i++) {
            Carte carte = new Carte();
            carte.setValeur(i);
            pioche.add(carte);
        }
        melanger();

        TourDeJeuDto.deckDto = new DeckDto();
        Deque<Carte> deck = new ArrayDeque<>();
        TourDeJeuDto.deckDto.setDeck(deck);
        for (Carte carte : pioche) {
            TourDeJeuDto.deckDto.getDeck().add(carte);
        }
    }

    public Carte piocher() {
         if (TourDeJeuDto.deckDto.getDeck().size() == 0) {
             return null;
         }
         return TourDeJeuDto.deckDto.getDeck().pollLast();
    }

    private void melanger() {
        int taillePile = pioche.size();
        Carte[] tabCarte = new Carte[taillePile];

        for (int j = 0; j < taillePile; j++) {
            tabCarte[j] = pioche.poll();
        }
        int indPermut;
        Carte carte;
        for (int j = 0; j < tabCarte.length; j++) {
            indPermut = (int) (Math.random() * tabCarte.length);
//            System.out.println("indPermut="+indPermut);
            // permutation Indices  j  et indPermut
            carte = tabCarte[j];
            tabCarte[j] = tabCarte[indPermut];
            tabCarte[indPermut] = carte;
        }
        for (int j = 0; j < tabCarte.length; j++) {
            pioche.add(tabCarte[j]);
        }
    }

    @Override
    public void deckToDto(Deck deck) {
        TourDeJeuDto.deckDto = new DeckDto();
        TourDeJeuDto.deckDto.setId(deck.getId());
        Deque<Carte> deckCarte = new ArrayDeque<>();
        TourDeJeuDto.deckDto.setDeck(deckCarte);
        for (Carte carte : deck.getDeckCarte()) {
            TourDeJeuDto.deckDto.getDeck().add(carte);
        }
    }

    @Override
    public Deck dtoToDeck() {
        Deck deck = new Deck();
        deck.setId(TourDeJeuDto.deckDto.getId());
        Set<Carte> pioche = new HashSet<>();
        deck.setDeckCarte(pioche);
        for (Carte carte : TourDeJeuDto.deckDto.getDeck()) {
            deck.getDeckCarte().add(carte);
        }
        return deck;
    }
}
