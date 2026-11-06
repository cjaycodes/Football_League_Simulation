package football.service;

import football.model.Match;
import football.model.Team;

import java.util.ArrayList;
import java.util.List;

public class League {
    private final List<Team> teams;
    private final MatchSimulator simulator;
    private final List<Match> results = new ArrayList<>();
    private boolean played = false;

    public League(List<Team> teams, MatchSimulator simulator) {
        if (teams == null || teams.size() < 2) {
            throw new IllegalArgumentException("a league needs at least 2 teams");
        }
        if (simulator == null) {
            throw new IllegalArgumentException("simulator must not be null");
        }
        this.teams = new ArrayList<>(teams);
        this.simulator = simulator;
    }

    public void playAll() {
        if (played) {
            throw new IllegalStateException("season already played");
        }
        for (Team home : teams) {
            for (Team away : teams) {
                if (home != away) {
                    results.add(simulator.simulate(home, away));
                }
            }
        }
        played = true;
    }

    public List<Match> getResults() {
        return List.copyOf(results);
    }

    public List<Team> getTeams() {
        return List.copyOf(teams);
    }
}