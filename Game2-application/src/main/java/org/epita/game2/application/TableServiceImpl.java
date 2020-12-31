package org.epita.game2.application;

import org.epita.game2.domaine.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Deque;
import java.util.HashSet;
import java.util.Set;

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
    public void tableToDto(Table table) {
        TourDeJeuDto.tableDto = new TableDto();
        TourDeJeuDto.tableDto.setId(table.getId());
        PileDto[] pilesDto = new PileDto[4];
        int iA =0;
        int iB =2;
        for (Pile pile : table.getPiles()) {
            if (pile.getSens() == 'A') {
                pilesDto[iA] = pileService.pileToDto(pile);
                iA++;
            } else {
                pilesDto[iB] = pileService.pileToDto(pile);
                iB++;
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
