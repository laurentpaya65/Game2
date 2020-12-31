package org.epita.game2.domaine;

import javax.persistence.*;
import java.util.Set;

@Entity
@javax.persistence.Table(name="TABLE_JEU")
public class Table {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private long id;

    @OneToMany(cascade = CascadeType.ALL,fetch = FetchType.EAGER)
    private Set<Pile> piles;

    public long getId() {
        return id;
    }

    public void setId(long id) {
        this.id = id;
    }

    public Set<Pile> getPiles() {
        return piles;
    }

    public void setPiles(Set<Pile> piles) {
        this.piles = piles;
    }

    @Override
    public String toString() {
        String tableAffiche = "";
        for (Pile pile : piles) {
            tableAffiche += pile.toString()+"\n";
        }
        return tableAffiche;
    }
}
