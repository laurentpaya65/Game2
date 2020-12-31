package org.epita.game2.application;

import org.epita.game2.domaine.Carte;
import org.epita.game2.domaine.Pile;
import org.epita.game2.domaine.PileDto;
import org.springframework.stereotype.Service;

import java.util.*;

import static org.epita.game2.domaine.PileDto.TAILLE_PILE;

@Service
public class PileServiceImpl implements PileService {

    @Override
    public PileDto initPile(PileDto pileDto) {
        Carte carte = new Carte();
        if (pileDto.getSens() == 'A') {
            carte.setValeur(1);
        } else  {
            carte.setValeur(TAILLE_PILE+2);
        }
        Deque<Carte> pile = new ArrayDeque<>();
        pileDto.setCartePile(pile);
        pileDto.getCartePile().add(carte);
        return pileDto;
    }

    @Override
    public PileDto ajoutCarte(PileDto pileDto,Carte carte) {
        if (pileDto.getSens() == 'A' && carte.getValeur() <= pileDto.getCartePile().getLast().getValeur()) {
            return pileDto;
        }
        if (pileDto.getSens() == 'D' && carte.getValeur() >= pileDto.getCartePile().getLast().getValeur()) {
            return pileDto;
        }
        pileDto.getCartePile().add(carte);
        return pileDto;
    }

    @Override
    public boolean testAjoutCarte(PileDto pileDto, Carte carte) {
        if (pileDto.getSens() == 'A' && carte.getValeur() <= pileDto.getCartePile().getLast().getValeur()
                && pileDto.getCartePile().getLast().getValeur() - carte.getValeur() != 10 ) {
            return false;
        }
        if (pileDto.getSens() == 'D' && carte.getValeur() >= pileDto.getCartePile().getLast().getValeur()
                && carte.getValeur() - pileDto.getCartePile().getLast().getValeur() != 10 ) {
            return false;
        }
        return true;
    }

    @Override
    public boolean testRemonteTemps(PileDto pileDto, Carte carte) {
        if (pileDto.getSens() == 'A' && pileDto.getCartePile().getLast().getValeur() - carte.getValeur() == 10) {
            return true;
        }
        if (pileDto.getSens() == 'D' && carte.getValeur() - pileDto.getCartePile().getLast().getValeur() == 10) {
            return true;
        }
        return false;
    }

    @Override
    public int diffCartePile(PileDto pileDto, Carte carte) {
        int diff = TAILLE_PILE + 2;
        if (pileDto.getSens() == 'A') {
            if (carte.getValeur() - pileDto.getCartePile().getLast().getValeur() > 0) {
                diff = carte.getValeur() - pileDto.getCartePile().getLast().getValeur();
            }
        }
        if (pileDto.getSens() == 'D') {
            if (pileDto.getCartePile().getLast().getValeur() - carte.getValeur() > 0) {
                diff = pileDto.getCartePile().getLast().getValeur() - carte.getValeur();
            }
        }
        return diff;
    }

    @Override
    public PileDto pileToDto(Pile pile) {
        PileDto dto = new PileDto();
        dto.setId(pile.getId());
        dto.setSens(pile.getSens());
        dto.setCartePile(trierPile(pile));
        return dto;
    }

    // trier la Pile selon qu'elle est ascendante ou descendante
    private Deque<Carte> trierPile(Pile pile) {
        Deque<Carte> pileCarte = new ArrayDeque<>();
        TreeMap<Integer,Carte> pileATrier = new TreeMap<>();
        for (Carte carte : pile.getCartePile()) {
            pileATrier.put(carte.getValeur(),carte);
        }
        if (pile.getSens() == 'A') {
            for (Map.Entry<Integer, Carte> carte : pileATrier.entrySet()) {
                pileCarte.addLast(carte.getValue());
            }
        } else {
            for (Map.Entry<Integer, Carte> carte : pileATrier.entrySet()) {
                pileCarte.addFirst(carte.getValue());
            }
        }
        return pileCarte;
    }

    @Override
    public Pile dtoToPile(PileDto pileDto) {
        Pile pile = new Pile();
        pile.setId(pileDto.getId());
        pile.setSens(pileDto.getSens());
        Set<Carte> cartePile = new HashSet<>();
        pile.setCartePile(cartePile);
        for (Carte carte : pileDto.getCartePile()) {
            pile.getCartePile().add(carte);
        }
        return pile;
    }
}
