package football.service;

import football.model.Fixture;
import football.model.Match;
import football.model.Team;

import java.util.ArrayList;
import java.util.List;

public class League {
    private final List<Team> teams;
    private final MatchSimulator simulator;
    private final List<List<Fixture>> schedule;
    private final List<List<Match>> matchdayResults = new ArrayList<>();

    public League(List<Team> teams, MatchSimulator simulator, FixtureScheduler scheduler) {
        if (teams == null || teams.size() < 2) {
            throw new IllegalArgumentException("a league needs at least 2 teams");
        }
        if (simulator == null || scheduler == null) {
            throw new IllegalArgumentException("simulator and scheduler must not be null");
        }
        this.teams = new ArrayList<>(teams);
        this.simulator = simulator;
        this.schedule = scheduler.generate(this.teams);
    }

    public boolean hasNextMatchday() {
        return matchdayResults.size() < schedule.size();
    }

    // plays ONE matchday, stores the results and returns them
    public List<Match> playNextMatchday() {
        if (!hasNextMatchday()) {
            throw new IllegalStateException("the season is over");
        }
        List<Match> played = new ArrayList<>();
        for (Fixture f : schedule.get(matchdayResults.size())) {
            played.add(simulator.simulate(f.getHome(), f.getAway()));
        }
        matchdayResults.add(played);
        return List.copyOf(played);
    }

    public int getCurrentMatchday() { return matchdayResults.size(); }   // matchdays played so far
    public int getTotalMatchdays() { return schedule.size(); }

    public List<Fixture> getNextFixtures() {
        if (!hasNextMatchday()) {
            return List.of();
        }
        return List.copyOf(schedule.get(matchdayResults.size()));
    }

    public List<Match> getMatchdayResults(int matchday) { // 1-based
        if (matchday < 1 || matchday > matchdayResults.size()) {
            throw new IllegalArgumentException("matchday not played yet: " + matchday);
        }
        return List.copyOf(matchdayResults.get(matchday - 1));
    }

    public List<Match> getResults() {
        List<Match> all = new ArrayList<>();
        for (List<Match> day : matchdayResults) {
            all.addAll(day);
        }
        return List.copyOf(all);
    }

    public List<Match> getResultsFor(Team team) {
        List<Match> mine = new ArrayList<>();
        for (Match m : getResults()) {
            if (m.getHomeTeam() == team || m.getAwayTeam() == team) {
                mine.add(m);
            }
        }
        return mine;
    }

    public List<Team> getTeams() {
        return List.copyOf(teams);
    }
}