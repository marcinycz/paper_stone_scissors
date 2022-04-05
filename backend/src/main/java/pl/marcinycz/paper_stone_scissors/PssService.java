package pl.marcinycz.paper_stone_scissors;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Optional;

@RestController
public class PssService {

    @Autowired
    PssRepository pssRepository;

    @Autowired
    ObjectMapper objectMapper;

    @CrossOrigin(origins = "http://localhost:3000/")
    @PostMapping("/pss")
    public ResponseEntity sendMyChoice(@RequestBody Pss pss){
        int lastTotalScore = 0;

        if(!pssRepository.findAll().isEmpty()){
            List<Pss> pssFromDb = pssRepository.findAll();
            lastTotalScore = pssFromDb.get(pssFromDb.size() - 1).getTotalScore();
        }


        pss.startPss(lastTotalScore);

        Pss savedPss = pssRepository.save(pss);
        return ResponseEntity.ok(pss);
    }

    @PostMapping("/pssDeleteStory")
    public ResponseEntity pssDeleteStory(){

        pssRepository.deleteAll();

        return ResponseEntity.ok("Delete all database");
    }

    //Game story
    @GetMapping("/pss")
    public ResponseEntity getLastGame() throws JsonProcessingException {
        List<Pss> allGames = pssRepository.findAll();
        return ResponseEntity.ok(objectMapper.writeValueAsString(allGames));
    }
}
