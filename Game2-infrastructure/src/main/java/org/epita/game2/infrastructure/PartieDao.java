package org.epita.game2.infrastructure;

import org.epita.game2.domaine.Partie;

import java.util.List;

public interface PartieDao {

    void create(Partie partie);

    Partie getPartieByNom(String nom);

    List<String> getNomsPartieByDebutNom(String nom);
}
