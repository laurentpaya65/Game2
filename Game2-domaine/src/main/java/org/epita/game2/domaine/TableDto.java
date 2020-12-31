package org.epita.game2.domaine;

import org.springframework.stereotype.Component;

import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import java.util.Arrays;

@Component
public class TableDto {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private long id;

    private PileDto[] piles = new PileDto[4];

    public long getId() {
        return id;
    }

    public void setId(long id) {
        this.id = id;
    }

    public PileDto[] getPiles() {
        return piles;
    }

    public void setPiles(PileDto[] piles) {
        this.piles = piles;
    }

    @Override
    public String toString() {
        String tableAffiche = "";
        int j =1;
        for (int i = 0; i < 4; i++) {
            tableAffiche += "Pile n°"+j+" "+piles[i].toString()+"\n";
            j++;
        }
        return tableAffiche;
    }
}
