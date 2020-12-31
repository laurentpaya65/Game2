package org.epita.game2.application;

import org.epita.game2.domaine.*;
import org.epita.game2.infrastructure.PartieDao;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.io.BufferedWriter;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.nio.file.StandardOpenOption;
import java.util.*;

import static java.nio.file.StandardOpenOption.APPEND;
import static java.nio.file.StandardOpenOption.CREATE;

@Service
public class PartieServiceImpl implements PartieService {

    @Autowired
    private TourDeJeuService tourDeJeuService;
    @Autowired
    private PartieDao partieDao;

    public static String affichePartie = "";
    public static boolean finDePartie = false;

    @Override
    public String nouvellePartie() {
        Partie partie = partieDao.getPartieByNom(PartieDto.partieDto.getNom());
        if (partie != null) {
            return "Il existe déjà une Partie avec ce nom !!!";
        }
//        String nomPartie = nomPartie(PartieDto.partieDto.getNom());
        PartieDto.partieDto.setNomFichier(PartieDto.partieDto.getNom());
        PartieDto.partieDto.setEtat("ENC");
        affichePartie += "****************************************************************\n";
        affichePartie += "                NOUVELLE PARTIE "+PartieDto.partieDto.getNom()+"\n";
        affichePartie += "****************************************************************\n";
        finDePartie = false;
        affichePartie = "";
//        PartieDto.partieDto = new PartieDto();   ==> fait dans PartieController()

//        System.out.println(PartieDto.partieDto.toString());
        TourDeJeuDto tourDeJeuDto = new TourDeJeuDto();

        Deque<TourDeJeuDto> tours = new ArrayDeque<>();
        tours.add(tourDeJeuService.initTourDeJeu(tourDeJeuDto));
        PartieDto.partieDto.setTours(tours);

        // déclenchement de la partie
        return partie()+" sauvegardée dans "+PartieDto.partieDto.getNomFichier()+".txt";
    }

    @Override
    public String recupererPartie() {
        Partie partie = partieDao.getPartieByNom(PartieDto.partieDto.getNom());
        if (partie == null) {
            return "Pas de partie avec ce nom !!!";
        }
        if (partie.getEtat() == "FIN") {
            return "Partie déjà terminée !!!";
        }
        // initialise la variable static PartieDto.partieDto à partir de la table
        partieToDto(partie);
        affichePartie += "****************************************************************\n";
        affichePartie += "                PARTIE REPRISE "+PartieDto.partieDto.getNom()+"\n";
        affichePartie += "****************************************************************\n";
        finDePartie = false;
        affichePartie = "************** DERNIER TOUR JOUE *********************\n";
        affichePartie += PartieDto.partieDto.getTours().getLast().toString();

        // déclenchement de la partie
        return partie()+" sauvegardée dans "+PartieDto.partieDto.getNomFichier()+".txt";
    }

    //  METHODE PRINCIPALE
    // déroulement de la partie
    private String partie() {
        while (true) {
            for (Joueur joueurCourant : PartieDto.partieDto.getJoueurs()) {
                TourDeJeuDto tourDeJeuDto = new TourDeJeuDto();
                // on incrémente le compteur de Tour
                tourDeJeuDto.setCptTour(PartieDto.partieDto.getTours().getLast().getCptTour() + 1);
                tourDeJeuDto.setJoueurCourant(joueurCourant);

                tourDeJeuDto = tourDeJeuService.unTour(tourDeJeuDto);
                PartieDto.partieDto.getTours().add(tourDeJeuDto);
                if (finPartie()) {
                    return "Fin de partie ou partie interrompue";
                }
            }
        }
    }

    private boolean finPartie() {
        if (finDePartie || acquerirSuspendrePartie()) {
            System.out.println(affichePartie);
            ecritureFichier();
            if (finDePartie) {
                PartieDto.partieDto.setEtat("FIN");
            }
            create();
            return true;
        }
        return false;
    }

    @Override
    public void create() {
        Partie partie = dtoToPartie();
        partieDao.create(partie);
    }

    // écriture du déroulement de la partie dans un fichier
    private void ecritureFichier(){
        Path path = Paths.get("C:/Users/laure/Game2/"+PartieDto.partieDto.getNomFichier()+".txt");
        StandardOpenOption standardOpenOption;
        if ( Files.exists(path)) {
            standardOpenOption = APPEND;
        } else {
            standardOpenOption = CREATE;
        }
        String ligne = "";
        String[] lignes = affichePartie.split("\n");
        int total=0;

        try (BufferedWriter bufferedWriter = Files.newBufferedWriter(path,standardOpenOption)) {
            for (int i = 0; i < lignes.length; i++) {
                ligne = lignes[i] + "\n";
                bufferedWriter.write(ligne);
                total++ ;
            }
            ligne = "*******************************************************\n";
            bufferedWriter.write(ligne);
            ligne = ">>>>>>>>>>>>>>>>>>>>>>> Nombre de lignes écrites "+total+" <<<<<<<<<<<<<<<<<<<<<<<<<<<<\n\n";
            bufferedWriter.write(ligne);
        } catch (IOException ex) {
            ex.printStackTrace();
        }
    }

    // regarde si une partie du même nom existe déjà
//    private String nomPartie(String nom) {
//        // on récupère toutes les parties commençant par nom
//        List<String> nomParties = partieDao.getNomsPartieByDebutNom(nom);
//        if (nomParties == null || nomParties.size() == 0 ) {
//            return nom;
//        }
//        int max = 0;
//        for (String nomPartie : nomParties) {
//            String[] partNom = nomPartie.split("_");
//            if (partNom[partNom.length-1].matches("^[0-9]{1,2}$") ) {
//                if (Integer.parseInt(partNom[partNom.length-1]) > max) {
//                    max = Integer.parseInt(partNom[partNom.length-1]);
//                }
//            }
//        }
//        return nom+"_"+(max+1);
//    }

    private boolean acquerirSuspendrePartie() {
        String reponse = "";
        while (!reponse.matches("o|O|n|N")) {
            System.out.println("Voulez suspendre la Partie (o/n) ?");
            Scanner sc = new Scanner(System.in);
            reponse = sc.next(); //C'est cette instruction qui laisse la main à l'utilisateur
        }
        if (reponse.equals("o") || reponse.equals("O")) {
            return true;
        }
        return false;
    }


    @Override
    public void partieToDto(Partie partie) {
//        PartieDto.partieDto = new PartieDto();
        PartieDto.partieDto.setId(partie.getId());

        List<Joueur> joueurs = new ArrayList<>();
        for (Joueur joueur : partie.getJoueurs()) {
            joueurs.add(joueur);
        }
        PartieDto.partieDto.setJoueurs(joueurs);

        PartieDto.partieDto.setNom(partie.getNom());
        PartieDto.partieDto.setNomFichier((partie.getNomFichier()));
        PartieDto.partieDto.setEtat(partie.getEtat());

        Deque<TourDeJeuDto> toursDto = new ArrayDeque<>();
        for (TourDeJeu tour : partie.getTours()) {
            toursDto.add(tourDeJeuService.tourToDto(tour));
        }
        PartieDto.partieDto.setTours(toursDto);
    }

    @Override
    public Partie dtoToPartie() {
        Partie partie = new Partie();
        partie.setId(PartieDto.partieDto.getId());
        partie.setNom(PartieDto.partieDto.getNom());
        partie.setNomFichier(PartieDto.partieDto.getNomFichier());
        partie.setEtat(PartieDto.partieDto.getEtat());

        Set<Joueur> joueurs = new HashSet<>();
        partie.setJoueurs(joueurs);
        for (Joueur joueur : PartieDto.partieDto.getJoueurs()) {
            partie.getJoueurs().add(joueur);
        }
        Set<TourDeJeu> tours = new HashSet<>();
//        for (TourDeJeuDto tourDto : partieDto.getTours()) {
//            tours.add(tourDeJeuService.dtoToTour(tourDto));
//        }
        tours.add((tourDeJeuService.dtoToTour(PartieDto.partieDto.getTours().getLast())));
        partie.setTours(tours);
        return partie;
    }
}
