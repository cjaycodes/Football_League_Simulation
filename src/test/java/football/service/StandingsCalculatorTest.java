package football.service;

import football.model.Match;
import football.model.TableRow;
import football.model.Team;
import org.junit.jupiter.api.Test;

import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

class StandingsCalculatorTest {

    private final StandingsCalculator calculator = new StandingsCalculator();

    @Test
    void sortsByPoints() {
        Team a = new Team("A");
        Team b = new Team("B");
        List<TableRow> table = calculator.calculate(List.of(new Match(a, b, 2, 0)));
        assertSame(a, table.get(0).getTeam());
        assertEquals(3, table.get(0).getPoints());
        assertSame(b, table.get(1).getTeam());
    }

    @Test
    void goalDifferenceBreaksPointsTie() {
        Team a = new Team("A");
        Team b = new Team("B");
        Team c = new Team("C");
        // A and B each win once (3 pts), A by a bigger margin
        List<TableRow> table = calculator.calculate(List.of(
                new Match(a, c, 5, 0),
                new Match(b, c, 1, 0)));
        assertSame(a, table.get(0).getTeam());
        assertSame(b, table.get(1).getTeam());
    }

    @Test
    void goalsForBreaksGoalDifferenceTie() {
        Team a = new Team("A");
        Team b = new Team("B");
        Team c = new Team("C");
        // both win by 1 goal, A scores more
        List<TableRow> table = calculator.calculate(List.of(
                new Match(a, c, 4, 3),
                new Match(b, c, 1, 0)));
        assertSame(a, table.get(0).getTeam());
    }

    @Test
    void noMatchesGivesEmptyTable() {
        assertTrue(calculator.calculate(List.of()).isEmpty());
    }
}