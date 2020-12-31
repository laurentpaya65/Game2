package org.epita.game2.domaine;

import javax.persistence.*;
import java.util.Set;

@Entity
public class Main {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private long id;

    public final static int TAILLE_MAIN = 3;

    @OneToMany(cascade = CascadeType.ALL,fetch = FetchType.EAGER)
    private Set<Carte> uneMain;

    public long getId() {
        return id;
    }

    public void setId(long id) {
        this.id = id;
    }

    public Set<Carte> getUneMain() {
        return uneMain;
    }

    public void setUneMain(Set<Carte> uneMain) {
        this.uneMain = uneMain;
    }

    @Override
    public String toString() {
        String mainAffiche = "";
        for (Carte carte : uneMain) {
            mainAffiche += carte.toString();
        }
        return mainAffiche;
    }
}
