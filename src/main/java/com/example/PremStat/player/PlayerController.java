package com.example.PremStat.player;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping(path = "api/v1/player")
public class PlayerController {

    @Autowired
    PlayerService playerService;

    @GetMapping
    public List<Player> getPlayer(
            @RequestParam(required = false) String name,
            @RequestParam(required = false) String team,
            @RequestParam(required = false) String position,
            @RequestParam(required = false) String nation
    ) {
        if(team != null && position != null) {
            return playerService.getPlayersByTeamAndPos(team, position);
        } else if(team != null) {
            return playerService.getPlayersFromTeam(team);
        } else if(name != null) {
            return playerService.getPlayersByName(name);
        } else if(position != null) {
            return playerService.getPlayersByPos(position);
        } else if(nation != null) {
            return playerService.getPlayersByNation(nation);
        }else {
            return playerService.getPlayers();
        }
    }

    @PostMapping
    public ResponseEntity<Player> addPlayer(@RequestBody Player player) {
        Player added = playerService.addPlayer(player);
        return new ResponseEntity<>(added, HttpStatus.CREATED);
    }

    @PutMapping
    public ResponseEntity<Player> updatePlayer(@RequestBody Player player) {
        Player updated = playerService.updatePlayer(player);
        if(updated != null) {
            return new ResponseEntity<>(updated, HttpStatus.OK);
        } else {
            return new ResponseEntity<>(updated, HttpStatus.NOT_FOUND);
        }
    }

    @DeleteMapping("/{name}")
    public ResponseEntity<String> deletePlayer(@PathVariable String name) {
        playerService.deletePlayer(name);
        return new ResponseEntity<>("Player Deleted Successfully", HttpStatus.OK);
    }
}
