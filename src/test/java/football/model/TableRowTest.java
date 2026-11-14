package football.model;

import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.*;

class TableRowTest {

    @Test
    void winGivesThreePoints() {
        TableRow row = new TableRow(new Team("T"));
        row.addMatch(2, 0);
        assertEquals(1, row.getWins());
        assertEquals(3, row.getPoints());
        assertEquals(2, row.getGoalDifference());
    }

    @Test
    void drawGivesOnePoint() {
        TableRow row = new TableRow(new Team("T"));
        row.addMatch(1, 1);
        assertEquals(1, row.getDraws());
        assertEquals(1, row.getPoints());
    }

    @Test
    void lossGivesZeroPoints() {
        TableRow row = new TableRow(new Team("T"));
        row.addMatch(0, 3);
        assertEquals(1, row.getLosses());
        assertEquals(0, row.getPoints());
        assertEquals(-3, row.getGoalDifference());
    }

    @Test
    void severalMatchesAccumulate() {
        TableRow row = new TableRow(new Team("T"));
        row.addMatch(2, 0);
        row.addMatch(1, 1);
        assertEquals(2, row.getPlayed());
        assertEquals(3, row.getGoalsFor());
        assertEquals(1, row.getGoalsAgainst());
        assertEquals(4, row.getPoints());
    }
}