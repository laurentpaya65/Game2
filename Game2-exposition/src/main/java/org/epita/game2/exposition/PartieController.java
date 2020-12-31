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

//    {"nom":"partie0",
//            "joueurs": [{"nom":"jojo"},{"nom":"juju"}]
//    }
    @PostMapping(value = {"/initpartie"},consumes = {"application/json"})
    public String initPartie(@RequestBody final PartieDto partieDto1) {
        PartieDto.partieDto = partieDto1;
        // déclenchement partie
        return partieService.nouvellePartie();
    }

    @PostMapping(value = {"/reprisepartie"},consumes = {"application/json"})
    public String reprisePartie(@RequestBody final PartieDto partieDto1) {
        PartieDto.partieDto = partieDto1;
        return partieService.recupererPartie();
    }
}
