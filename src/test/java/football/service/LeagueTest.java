package football.service;

import football.model.Match;
import football.model.Team;
import org.junit.jupiter.api.Test;

import java.util.ArrayList;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

class LeagueTest {

    // MatchSimulator has one method, so a lambda works as a stub: home always wins 1-0
    private final MatchSimulator stub = (home, away) -> new Match(home, away, 1, 0);

    private List<Team> teams(int n) {
        List<Team> list = new ArrayList<>();
        for (int i = 0; i < n; i++) {
            list.add(new Team("T" + i));
        }
        return list;
    }

    @Test
    void constructorValidatesArguments() {
        FixtureScheduler s = new FixtureScheduler();
        assertThrows(IllegalArgumentException.class, () -> new League(null, stub, s));
        assertThrows(IllegalArgumentException.class, () -> new League(teams(1), stub, s));
        assertThrows(IllegalArgumentException.class, () -> new League(teams(4), null, s));
        assertThrows(IllegalArgumentException.class, () -> new League(teams(4), stub, null));
    }

    @Test
    void playingOneMatchdayStoresItsResults() {
        League league = new League(teams(4), stub, new FixtureScheduler());
        List<Match> played = league.playNextMatchday();
        assertEquals(2, played.size());
        assertEquals(1, league.getCurrentMatchday());
        assertEquals(2, league.getResults().size());
    }

    @Test
    void fullSeasonHasNTimesNMinusOneMatches() {
        League league = new League(teams(20), stub, new FixtureScheduler());
        while (league.hasNextMatchday()) {
            league.playNextMatchday();
        }
        assertEquals(380, league.getResults().size());
        assertEquals(38, league.getTotalMatchdays());
    }

    @Test
    void playingAfterSeasonEndThrows() {
        League league = new League(teams(4), stub, new FixtureScheduler());
        while (league.hasNextMatchday()) {
            league.playNextMatchday();
        }
        assertThrows(IllegalStateException.class, league::playNextMatchday);
    }

    @Test
    void matchdayResultsRejectsUnplayedMatchday() {
        League league = new League(teams(4), stub, new FixtureScheduler());
        assertThrows(IllegalArgumentException.class, () -> league.getMatchdayResults(1));
    }

    @Test
    void resultsForTeamOnlyContainsItsMatches() {
        List<Team> teams = teams(4);
        League league = new League(teams, stub, new FixtureScheduler());
        league.playNextMatchday();
        league.playNextMatchday();
        assertEquals(2, league.getResultsFor(teams.get(0)).size());
    }

    @Test
    void resultsListIsReadOnly() {
        League league = new League(teams(4), stub, new FixtureScheduler());
        league.playNextMatchday();
        assertThrows(UnsupportedOperationException.class, () -> league.getResults().clear());
    }
}