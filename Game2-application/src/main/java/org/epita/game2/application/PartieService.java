package org.epita.game2.application;

import org.epita.game2.domaine.Partie;
import org.epita.game2.domaine.PartieDto;

public interface PartieService {

    String nouvellePartie();

    String recupererPartie();

    void partieToDto(Partie partie);

    Partie dtoToPartie();

    void create();
}
