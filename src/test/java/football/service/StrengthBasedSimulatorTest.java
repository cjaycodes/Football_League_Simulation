package football.service;

import football.model.Defender;
import football.model.Match;
import football.model.Team;
import org.junit.jupiter.api.Test;

import java.util.Random;

import static org.junit.jupiter.api.Assertions.*;

class StrengthBasedSimulatorTest {

    private Team teamOf(String name, int level) {
        Team t = new Team(name);
        t.addPlayer(new Defender(name + "1", 25, level, level, level, level, level));
        return t;
    }

    @Test
    void nullRandomIsRejected() {
        assertThrows(IllegalArgumentException.class, () -> new StrengthBasedSimulator(null));
    }

    @Test
    void nullTeamsAreRejected() {
        StrengthBasedSimulator sim = new StrengthBasedSimulator(new Random(1));
        Team t = teamOf("T", 70);
        assertThrows(IllegalArgumentException.class, () -> sim.simulate(null, t));
        assertThrows(IllegalArgumentException.class, () -> sim.simulate(t, null));
    }

    @Test
    void sameSeedGivesSameResult() {
        Team home = teamOf("H", 80);
        Team away = teamOf("A", 70);
        Match m1 = new StrengthBasedSimulator(new Random(42)).simulate(home, away);
        Match m2 = new StrengthBasedSimulator(new Random(42)).simulate(home, away);
        assertEquals(m1.getHomeGoals(), m2.getHomeGoals());
        assertEquals(m1.getAwayGoals(), m2.getAwayGoals());
    }

    @Test
    void goalsAreNeverNegative() {
        StrengthBasedSimulator sim = new StrengthBasedSimulator(new Random(7));
        Team home = teamOf("H", 60);
        Team away = teamOf("A", 60);
        for (int i = 0; i < 200; i++) {
            Match m = sim.simulate(home, away);
            assertTrue(m.getHomeGoals() >= 0);
            assertTrue(m.getAwayGoals() >= 0);
        }
    }

    @Test
    void strongerTeamWinsMoreOften() {
        StrengthBasedSimulator sim = new StrengthBasedSimulator(new Random(42));
        Team strong = teamOf("Strong", 90);
        Team weak = teamOf("Weak", 40);
        int strongWins = 0;
        int weakWins = 0;
        for (int i = 0; i < 500; i++) {
            Match m = sim.simulate(strong, weak);
            if (m.getHomeGoals() > m.getAwayGoals()) strongWins++;
            if (m.getAwayGoals() > m.getHomeGoals()) weakWins++;
        }
        assertTrue(strongWins > weakWins * 2,
                "strong " + strongWins + " vs weak " + weakWins);
    }
}