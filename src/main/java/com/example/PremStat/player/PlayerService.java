package com.example.PremStat.player;

import jakarta.transaction.Transactional;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
public class PlayerService {

    @Autowired
    PlayerRepository playerRepository;

    public List<Player> getPlayers() {
        return playerRepository.findAll();
    }

    public List<Player> getPlayersFromTeam(String teamName) {
        return playerRepository.findAll().stream().filter(player -> teamName.equalsIgnoreCase(player.getTeam())).collect(Collectors.toList());
    }

    public List<Player> getPlayersByName(String searchText) {
        return playerRepository.findAll().stream()
                .filter(player -> player.getName().toLowerCase().contains(searchText.toLowerCase())).collect(Collectors.toList());
    }

    public List<Player> getPlayersByPos(String searchText) {
        return playerRepository.findAll().stream()
                .filter(player -> player.getPos().toLowerCase().contains(searchText.toLowerCase()))
                .collect(Collectors.toList());
    }

    public List<Player> getPlayersByNation(String searchText) {
        return playerRepository.findAll().stream()
                .filter(player -> player.getNation().toLowerCase().contains(searchText.toLowerCase()))
                .collect(Collectors.toList());
    }

    public List<Player> getPlayersByTeamAndPos(String team, String position) {
        return playerRepository.findAll().stream()
                .filter(player -> team.equalsIgnoreCase(player.getTeam()) && position.equalsIgnoreCase(player.getPos()))
                .collect(Collectors.toList());
    }

    public Player addPlayer(Player player) {
        return playerRepository.save(player);
    }

    public Player updatePlayer(Player updatedPlayer) {
        Optional<Player> existing = playerRepository.findByName(updatedPlayer.getName());

        if(existing.isPresent()) {
            Player player = existing.get();
            player.setName(updatedPlayer.getName());
            player.setNation(updatedPlayer.getNation());
            player.setAge(updatedPlayer.getAge());
            player.setTeam(updatedPlayer.getTeam());
            player.setPk(updatedPlayer.getPk());
            player.setPos(updatedPlayer.getPos());
            player.setMp(updatedPlayer.getMp());
            return playerRepository.save(player);
        }
        return null;
    }

    @Transactional
    public void deletePlayer(String name) {
        playerRepository.deleteByName(name);
    }
}
