package football.service;

import football.model.Fixture;
import football.model.Team;
import org.junit.jupiter.api.Test;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

import static org.junit.jupiter.api.Assertions.*;

class FixtureSchedulerTest {

    private List<Team> teams(int n) {
        List<Team> list = new ArrayList<>();
        for (int i = 0; i < n; i++) {
            list.add(new Team("T" + i));
        }
        return list;
    }

    @Test
    void twentyTeamsGiveThirtyEightMatchdaysOfTenFixtures() {
        List<List<Fixture>> season = new FixtureScheduler().generate(teams(20));
        assertEquals(38, season.size());
        for (List<Fixture> day : season) {
            assertEquals(10, day.size());
        }
    }

    @Test
    void everyTeamPlaysOncePerMatchday() {
        for (List<Fixture> day : new FixtureScheduler().generate(teams(20))) {
            Set<Team> seen = new HashSet<>();
            for (Fixture f : day) {
                assertTrue(seen.add(f.getHome()));
                assertTrue(seen.add(f.getAway()));
            }
            assertEquals(20, seen.size());
        }
    }

    @Test
    void everyOrderedPairHappensExactlyOnce() {
        Set<String> pairs = new HashSet<>();
        for (List<Fixture> day : new FixtureScheduler().generate(teams(20))) {
            for (Fixture f : day) {
                assertTrue(pairs.add(f.getHome().getName() + ">" + f.getAway().getName()));
            }
        }
        assertEquals(20 * 19, pairs.size());
    }

    @Test
    void oddNumberOfTeamsGivesEveryTeamABye() {
        // 5 teams: 10 matchdays, 2 fixtures each, one team rests every matchday
        List<List<Fixture>> season = new FixtureScheduler().generate(teams(5));
        assertEquals(10, season.size());
        for (List<Fixture> day : season) {
            assertEquals(2, day.size());
        }
    }
}