package org.epita.game2.exposition;

import org.epita.game2.application.PartieService;
import org.epita.game2.domaine.PartieDto;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api")
public class PartieController {

    @Autowired
    private PartieService partieService;

    // ************ NOUVELLE PARTIE pour 2 joueurs **************
//    {"nom":"partie0",
//            "joueurs": [{"nom":"jojo"},{"nom":"juju"}]
//    }
    // ************ NOUVELLE PARTIE pour 1 joueur = "ordinateur" **************
//{"nom":"partie9",
//        "joueurs": [{"nom":"ordinateur"}]
//}
    @PostMapping(value = {"/initpartie"},consumes = {"application/json"})
    public String initPartie(@RequestBody final PartieDto partieDto1) {
        PartieDto.partieDto = partieDto1;
        // déclenchement partie
        return partieService.nouvellePartie();
    }

    // ************ REPRISE PARTIE pour 2 joueurs **************
    // >>>>>>>>>> la partie doit déjà exister
//    {"nom":"partie0",
//            "joueurs": [{"nom":"jojo"},{"nom":"juju"}]
//    }
    @PostMapping(value = {"/reprisepartie"},consumes = {"application/json"})
    public String reprisePartie(@RequestBody final PartieDto partieDto1) {
        PartieDto.partieDto = partieDto1;
        return partieService.recupererPartie();
    }
}
