package org.epita.game2.application;

import org.epita.game2.domaine.Carte;
import org.epita.game2.domaine.Table;
import org.epita.game2.domaine.TableDto;

import java.util.Map;
import java.util.Set;

public interface TableService {

    void initTable();

    void carteSurPile(int numPile, Carte carte);

    boolean testCarteSurPile( int numPile, Carte carte);

    Map<Integer,Carte> testCoupOrdinateurNiv5(Set<Carte> cartes);
    Map<Integer,Carte> testCoupOrdinateurNiv3(Set<Carte[]> cartes);
    Map<Integer,Carte> testCoupOrdinateurNiv1(Set<Carte> cartes);

    void tableToDto(Table table);

    Table dtoToTable();


}
