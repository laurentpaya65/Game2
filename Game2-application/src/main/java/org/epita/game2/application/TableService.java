package org.epita.game2.application;

import org.epita.game2.domaine.Carte;
import org.epita.game2.domaine.Table;
import org.epita.game2.domaine.TableDto;

public interface TableService {

    void initTable();

    void carteSurPile(int numPile, Carte carte);

    boolean testCarteSurPile( int numPile, Carte carte);

    void tableToDto(Table table);

    Table dtoToTable();


}
