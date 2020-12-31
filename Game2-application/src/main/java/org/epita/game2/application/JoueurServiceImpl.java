package org.epita.game2.application;

import org.epita.game2.domaine.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Set;

@Service
public class JoueurServiceImpl implements JoueurService {

    @Autowired
    private MainService mainService;

    @Override
    public Joueur completeMain(Joueur joueur) {
        joueur.setMain(mainService.genererMain(joueur.getMain()));
        return joueur;
    }

    @Override
    public Joueur jouerUneCarte(Joueur joueur, Carte carte) {
        if (carte != null) {
            for (Carte c : joueur.getMain().getUneMain()) {
                if ( c.getValeur() == carte.getValeur() ) {
                    joueur.getMain().getUneMain().remove(c);
                    return joueur;
                }
            }
        }
        return joueur;
    }


}
