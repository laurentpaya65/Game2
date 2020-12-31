package org.epita.game2.domaine;

import javax.persistence.*;

@Entity
public class Joueur {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private long id;

    private String nom;

    @OneToOne(cascade = CascadeType.ALL,fetch = FetchType.EAGER)
    private Main main;

    public long getId() {
        return id;
    }

    public void setId(long id) {
        this.id = id;
    }

    public String getNom() {
        return nom;
    }

    public void setNom(String nom) {
        this.nom = nom;
    }

    public Main getMain() {
        return main;
    }

    public void setMain(Main main) {
        this.main = main;
    }

    @Override
    public String toString() {
        if (main == null) {
            return nom+"-"+"Pas de main";
        }
        return nom+" "+main.toString();
    }
}
