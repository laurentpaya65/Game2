package org.epita.game2.application;

import org.epita.game2.domaine.Carte;
import org.epita.game2.domaine.Pile;
import org.epita.game2.domaine.PileDto;

public interface PileService {

    PileDto initPile(PileDto pileDto);

    PileDto ajoutCarte(PileDto pileDto, Carte carte);

    boolean testAjoutCarte(PileDto pileDto, Carte carte);

    boolean testRemonteTemps(PileDto pileDto, Carte carte);

    int diffCartePile(PileDto pileDto, Carte carte);

    int diffCartePileNiv3(PileDto pileDto, Carte[] cartes);

    PileDto pileToDto(Pile pile);

    Pile dtoToPile(PileDto pileDto);
}
