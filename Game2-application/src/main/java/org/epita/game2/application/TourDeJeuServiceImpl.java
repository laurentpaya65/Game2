package org.epita.game2.application;

import org.epita.game2.domaine.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Scanner;

@Service
public class TourDeJeuServiceImpl implements TourDeJeuService {

    @Autowired
    private ServiceDeck serviceDeck;
    @Autowired
    private JoueurService joueurService;
    @Autowired
    private TableService tableService;
    @Autowired
    private MainService mainService;

    private Carte carteSaisie =null;
    private int numPile = 0;

    @Override
    public TourDeJeuDto initTourDeJeu(TourDeJeuDto tour) {
        serviceDeck.initDeck();
        tableService.initTable();
        tour.setCptTour(0);
        return tour;
    }

    @Override
    public TourDeJeuDto unTour(TourDeJeuDto tour) {
        // on initialise la Main du Joueur au besoin
        if (tour.getJoueurCourant().getMain() == null) {
            tour.setJoueurCourant(joueurService.completeMain(tour.getJoueurCourant()));
        }

        PartieServiceImpl.affichePartie +="************** TOUR de "+tour.getJoueurCourant().getNom()+" *******************\n";
        PartieServiceImpl.affichePartie +=tour.getJoueurCourant().toString()+"\n";

        int nbCarteJoue = 0;

        while (true ) {
            // demande Fin Tour ?
            if (finTour(nbCarteJoue,tour.getJoueurCourant())) {
                // joueur pioche
                tour.setJoueurCourant(joueurService.completeMain(tour.getJoueurCourant()));
                System.out.print(tour.getJoueurCourant().toString()+"\n");
                PartieServiceImpl.affichePartie += tour.toString();
                return tour;
            }

            // saisie de la Carte et de la Pile
            System.out.print(tour.toString());
            // on teste si le joueur peut jouer sinon il  a perdu
            if (!testJoueurPeutJouer(tour.getJoueurCourant())) {
                return tour;
            }

            saisieCarteEtPile(tour.getJoueurCourant());

            // joueur joue une carte
            tour.setJoueurCourant(joueurService.jouerUneCarte(tour.getJoueurCourant(), carteSaisie));

            System.out.println(tour.getJoueurCourant().getNom()+" a joué la carte n° "+(nbCarteJoue+1)+" "+
                                    carteSaisie.toString()+" sur la pile n°"+numPile+"\n");
            PartieServiceImpl.affichePartie += tour.getJoueurCourant().getNom()+" a joué la carte n° "+(nbCarteJoue+1)+" "+
                                    carteSaisie.toString()+" sur la pile n°"+numPile+"\n";

            // la carte est ajoutée sur la pile
            tableService.carteSurPile(numPile, carteSaisie);
            // si Deck est vide et Main Joueur est vide ==> FIN de partie
            if (finPartie(tour.getJoueurCourant())) {
                return tour;
            }

            System.out.print(tour.getJoueurCourant().toString()+"\n");
//            PartieServiceImpl.affichePartie += tour.toString();
            // on incrémente le nombre de cartes jouées
            nbCarteJoue++;
        }
    }

    @Override
    public TourDeJeuDto tourToDto(TourDeJeu tourDeJeu) {
        TourDeJeuDto dto = new TourDeJeuDto();
        dto.setCptTour(tourDeJeu.getCptTour());
        tableService.tableToDto(tourDeJeu.getTable());
        dto.setJoueurCourant(tourDeJeu.getJoueurCourant());
        serviceDeck.deckToDto(tourDeJeu.getDeck());
        return dto;
    }

    @Override
    public TourDeJeu dtoToTour(TourDeJeuDto tourDeJeuDto) {
        TourDeJeu tour = new TourDeJeu();
        tour.setCptTour(tourDeJeuDto.getCptTour());
        tour.setTable(tableService.dtoToTable());
        tour.setJoueurCourant(tourDeJeuDto.getJoueurCourant());
        tour.setDeck(serviceDeck.dtoToDeck());
        return tour;
    }


    private boolean finTour(int nbCarteJoue,Joueur joueur) {
        int nbCarteAJoue = 0;
        if (TourDeJeuDto.deckDto.getDeck().size() == 0) {
            nbCarteAJoue = 1;
        } else {
            nbCarteAJoue = 2;
        }

        if (nbCarteJoue < nbCarteAJoue) {
            return false;
        }
        if (joueur.getMain().getUneMain().size() == 0) {
            System.out.println("!!!! Le joueur n'a plus de carte et pioche "+"\n");
            PartieServiceImpl.affichePartie += "!!!! Le joueur n'a plus de carte et pioche "+"\n";
            return true;
        }
        if (acquerirFinTour()) {
            System.out.println("!!!! Fin du tour de " + joueur.getNom()+"\n");
            PartieServiceImpl.affichePartie += "!!!! Fin du tour de " + joueur.getNom()+"\n";
            return true;
        }
        return false;
    }

    // si Deck est vide et Main Joueur est vide ==> FIN de partie
    private boolean finPartie(Joueur joueur) {
        if (joueur.getMain().getUneMain().size() == 0
                && TourDeJeuDto.deckDto.getDeck().size() == 0) {
            System.out.println(joueur.getNom() + " a gagné la partie !!!!!!");
            PartieServiceImpl.affichePartie += joueur.getNom() + " a gagné la partie !!!!!!";
            PartieServiceImpl.finDePartie = true;
            return true;
        }
        return false;
    }

    // test si le Joueur peut jouer une Carte sur une des Piles
    private boolean testJoueurPeutJouer(Joueur joueur) {
        for (Carte carte : joueur.getMain().getUneMain()) {
            for (int i = 1; i <= 4 ; i++) {
                if (tableService.testCarteSurPile(i, carte) ) {
                    return true;

                }
            }
        }
        System.out.println(joueur.getNom()+" ne peut plus jouer et a perdu la partie !!!!\n");
        PartieServiceImpl.affichePartie += joueur.getNom() + " a perdu la partie !!!!!!\n";
        PartieServiceImpl.finDePartie = true;
        return false;
    }

    private void saisieCarteEtPile(Joueur joueur) {
        carteSaisie = null;
        numPile = 0;
        // tant que les saisies ne sont pas bonnes ou que le Joueur n'a pas demandé FIN
        while ( !tableService.testCarteSurPile(numPile, carteSaisie)) {
            numPile = acquerirPile(joueur);
            carteSaisie = acquerirCarte(joueur);
            if (!tableService.testCarteSurPile(numPile, carteSaisie)) {
                System.out.println("Mouvement impossible sur cette pile, rejouez !!!");
            }
        }
    }
    //************************************************************************************************
    //                      Les saisies utilisateurs
    //************************************************************************************************


    private Carte acquerirCarte(Joueur joueur) {
        String reponse = "";
        Carte carte = null;
        while ( !mainService.rechercheCarte(carte,joueur.getMain())) {
            System.out.println(joueur.getNom()+" : Veuillez entrer une carte (un nombre) :");
            Scanner sc = new Scanner(System.in);
            reponse = sc.next(); //C'est cette instruction qui laisse la main à l'utilisateur
            if (reponse.matches("^[0-9]{1,2}$")) {
                carte = new Carte();
                carte.setValeur(Integer.parseInt(reponse));
            }
        }
        return carte;
    }
    private int acquerirPile(Joueur joueur) {
        String reponse = "";
        while (!( reponse.matches("^[1-4]{1}$") ) ) {
            System.out.println("Veuillez entrer une pile (de 1 à 4) :");
            Scanner sc = new Scanner(System.in);
            reponse = sc.next(); //C'est cette instruction qui laisse la main à l'utilisateur
        }
        return Integer.parseInt(reponse);
    }
    private boolean acquerirFinTour() {
        String reponse = "";
        while (!reponse.matches("o|O|n|N")) {
            System.out.println("Voulez terminer le Tour (o/n) ?");
            Scanner sc = new Scanner(System.in);
            reponse = sc.next(); //C'est cette instruction qui laisse la main à l'utilisateur
        }
        if (reponse.equals("o") || reponse.equals("O")) {
            return true;
        }
        return false;
    }
}
