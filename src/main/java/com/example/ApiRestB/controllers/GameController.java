package com.example.ApiRestB.controllers;

import com.example.ApiRestB.models.Game;
import com.example.ApiRestB.repository.GameRepository;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Optional;

@RestController
@CrossOrigin(origins = "http://127.0.0.1:5500")
public class GameController {

    GameRepository repository;

    public GameController(GameRepository repository) {
        this.repository = repository;
    }

    @GetMapping("/api/createGame")
    public void createGame(){
        Game game1 = new Game("Fortnite", "PC", "BattleRoyale","https://www.infobae.com/new-resizer/pkgFxcBhAu_gRkRBKeHEMjcim_A=/992x558/filters:format(webp):quality(85)/cloudfront-us-east-1.images.arcpublishing.com/infobae/VKYFAEZVFVAQNNNJDQPL2JI4N4.webp");
        Game game2 = new Game("Lego", "PC", "Aventura", "https://www.infobae.com/new-resizer/pkgFxcBhAu_gRkRBKeHEMjcim_A=/992x558/filters:format(webp):quality(85)/cloudfront-us-east-1.images.arcpublishing.com/infobae/VKYFAEZVFVAQNNNJDQPL2JI4N4.webp");
        Game game3 = new Game("Rambo4", "PC", "Battle", "https://www.infobae.com/new-resizer/pkgFxcBhAu_gRkRBKeHEMjcim_A=/992x558/filters:format(webp):quality(85)/cloudfront-us-east-1.images.arcpublishing.com/infobae/VKYFAEZVFVAQNNNJDQPL2JI4N4.webp");

        repository.save(game1);
        repository.save(game2);
        repository.save(game3);
    }

    @GetMapping("/api/games")
    public List<Game> getGames() {
        return repository.findAll();
    }

    @GetMapping("/api/game/{id}")
    public ResponseEntity<Game> getGame(@PathVariable Long id){
        Optional<Game> opt = repository.findById(id);

        if(opt.isEmpty()){
            return ResponseEntity.badRequest().build();
        } else {
            return ResponseEntity.ok(opt.get());
        }
    }

    @PostMapping("/api/games")
    public ResponseEntity<Game> saveGame(@RequestBody Game game){
        if(game.getId()!=null){
            return ResponseEntity.badRequest().build();
        }
        repository.save(game);
        return ResponseEntity.ok(game);
    }

    @PutMapping("/api/game/{id}")
    @CrossOrigin(origins = "http://127.0.0.1:5500") // Anotación para CORS
    public ResponseEntity<Game> editGame(@PathVariable Long id, @RequestBody Game game){
        Optional<Game> opt = repository.findById(id);
        if(opt.isEmpty()){
            return ResponseEntity.notFound().build();
        }
        Game existingGame = opt.get();
        existingGame.setTitle(game.getTitle());
        existingGame.setPlatform(game.getPlatform());
        existingGame.setGenre(game.getGenre());
        existingGame.setImage(game.getImage()); // No olvides actualizar también la imagen si corresponde
        repository.save(existingGame);
        return ResponseEntity.ok(existingGame);
    }

    @DeleteMapping("/api/game/{id}")
    @CrossOrigin(origins = "http://127.0.0.1:5500")
    public ResponseEntity<Void> deleteGame(@PathVariable Long id) {
        Optional<Game> opt = repository.findById(id);
        if (opt.isEmpty()) {
            return ResponseEntity.notFound().build();
        }
        repository.deleteById(id);
        return ResponseEntity.noContent().build();
    }
}