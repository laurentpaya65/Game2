package org.epita.game2.application;

import org.epita.game2.domaine.Carte;
import org.epita.game2.domaine.TourDeJeu;
import org.epita.game2.domaine.TourDeJeuDto;

public interface TourDeJeuService {

    TourDeJeuDto initTourDeJeu(TourDeJeuDto tour);

    TourDeJeuDto unTour(TourDeJeuDto tour);

    TourDeJeuDto tourToDto(TourDeJeu tourDeJeu);

    TourDeJeu dtoToTour(TourDeJeuDto tourDeJeuDto);

}
