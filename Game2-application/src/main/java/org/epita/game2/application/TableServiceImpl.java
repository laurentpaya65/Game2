package org.epita.game2.application;

import org.epita.game2.domaine.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.*;

import static org.epita.game2.domaine.PileDto.TAILLE_PILE;

@Service
public class TableServiceImpl implements TableService {

    @Autowired
    private PileService pileService;

    @Override
    public void initTable() {
        PileDto[] piles = new PileDto[4];

        PileDto pileA1 = new PileDto();
        pileA1.setSens('A');
        piles[0] = pileService.initPile(pileA1);

        PileDto pileA2 = new PileDto();
        pileA2.setSens('A');
        piles[1] = pileService.initPile(pileA2);

        PileDto pileD1 = new PileDto();
        pileD1.setSens('D');
        piles[2] = pileService.initPile(pileD1);

        PileDto pileD2 = new PileDto();
        pileD2.setSens('D');
        piles[3] = pileService.initPile(pileD2);

        TourDeJeuDto.tableDto = new TableDto();
        TourDeJeuDto.tableDto.setPiles(piles);
    }

    @Override
    public void carteSurPile(int numPile, Carte carte) {
        PileDto[] piles = TourDeJeuDto.tableDto.getPiles();
        piles[numPile-1] = pileService.ajoutCarte(TourDeJeuDto.tableDto.getPiles()[numPile-1],carte );
        TourDeJeuDto.tableDto.setPiles(piles);
    }

    @Override
    public boolean testCarteSurPile(int numPile, Carte carte) {
        if (numPile < 1 || carte == null) {
            return false;
        }
        boolean test = pileService.testAjoutCarte(TourDeJeuDto.tableDto.getPiles()[numPile-1],carte);
//        if (!test) {
//            System.out.println("Mouvement impossible sur cette pile, rejouez !!!");
//        }
        return test;
    }

    @Override
    public Map<Integer,Carte> testCoupOrdinateurNiv5(Set<Carte> cartes) {
        if (cartes == null || cartes.size() == 0) {
            return null;
        }

        Map<Integer, Carte> pileCarte = new HashMap<>();

        // test si on peut remonter dans le temps
        for (Carte carte : cartes) {
            for (int i = 0; i < 4; i++) {
                if (pileService.testRemonteTemps(TourDeJeuDto.tableDto.getPiles()[i], carte)) {
                    pileCarte.put(i + 1, carte);
                    return pileCarte;
                }
            }
        }
        return null;
    }

    @Override
    public Map<Integer, Carte> testCoupOrdinateurNiv3(Set<Carte[]> cartes) {
        if (cartes == null || cartes.size() == 0) {
            return null;
        }

        Map<Integer, Carte> pileCarte = new HashMap<>();

        // recherche de la plus grande différence permettant de remonter le temps
        int pile = 5;
        Carte[] carte1 =  new Carte[2];
        int max = 0;
        int diff = 0;

        for (Carte[] carte: cartes) {
            for (int i = 0; i < 4; i++) {
                diff = pileService.diffCartePileNiv3(TourDeJeuDto.tableDto.getPiles()[i],carte);
                if ( diff > max ) {
                    max = diff;
                    carte1 = carte;
                    pile = i+1;
                }
            }
        }
        if (pile == 5) {
            return  null;
        }
        Carte carte = new Carte();
        if (TourDeJeuDto.tableDto.getPiles()[pile-1].getSens() == 'A') {
            carte = carte1[1];
        } else {
            carte = carte1[0];
        }
        pileCarte.put(pile,carte);
        return pileCarte;
    }

    @Override
    public Map<Integer,Carte> testCoupOrdinateurNiv1(Set<Carte> cartes) {
        if (cartes == null || cartes.size() == 0) {
            return null;
        }

        Map<Integer,Carte> pileCarte = new HashMap<>();
        // recherche du minimum entre carte et dessus de Pile
        int pile = 5;
        Carte carte1 =  new Carte();
        int min = TAILLE_PILE + 2;
        int diff = 0;
        for (Carte carte : cartes) {
            for (int i = 0; i < 4; i++) {
                diff = pileService.diffCartePile(TourDeJeuDto.tableDto.getPiles()[i],carte);
                if ( diff < min ) {
                    min = diff;
                    carte1 = carte;
                    pile = i+1;

                }
            }
        }
        pileCarte.put(pile,carte1);
        return pileCarte;
    }

    @Override
    public void tableToDto(Table table) {
        TourDeJeuDto.tableDto = new TableDto();
        TourDeJeuDto.tableDto.setId(table.getId());
        PileDto[] pilesDto = new PileDto[4];
        int iA = 1;
        int iB = 3;
        for (Pile pile : table.getPiles()) {
            if (pile.getSens() == 'A') {
                pilesDto[iA] = pileService.pileToDto(pile);
                iA--;
            } else {
                pilesDto[iB] = pileService.pileToDto(pile);
                iB--;
            }
        }
        TourDeJeuDto.tableDto.setPiles(pilesDto);
    }

    @Override
    public Table dtoToTable() {
        Table table = new Table();
        table.setId(TourDeJeuDto.tableDto.getId());
        Set<Pile> piles = new HashSet<>();
        for (int i = 0; i < 4; i++) {
            piles.add(pileService.dtoToPile(TourDeJeuDto.tableDto.getPiles()[i]));
        }
        table.setPiles(piles);
        return table;
    }
}
