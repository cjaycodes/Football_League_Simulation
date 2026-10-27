package football.model;

import java.util.ArrayList;
import java.util.List;

public class Team {
    private String name;
    private List<Player> players;

    public Team(String name) {
        this.name = name;
        this.players = new ArrayList<>();
    }

    public void addPlayer(Player player) {
        //  Add player to the team's list
        players.add(player);
    }

    public int getStrength() {
        if(players.isEmpty()){
            return 0;
        }

        int totalRating = 0;

        for(Player player: players){

            totalRating += player.getOverallRating();
        }
        return totalRating / players.size();
        // Calculate and return the average overall rating of all players in the list
        // Remember to handle the case where the team has 0 players to avoid division by zero!

    }

    public String getName() {
        return name;
    }
}