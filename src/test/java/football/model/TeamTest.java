package football.model;

import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.*;

class TeamTest {

    @Test
    void emptyTeamHasZeroStrength() {
        assertEquals(0, new Team("Empty").getStrength());
    }

    @Test
    void strengthIsAverageOfOverallRatings() {
        Team t = new Team("T");
        t.addPlayer(new Defender("A", 25, 80, 80, 80, 80, 80));
        t.addPlayer(new Defender("B", 25, 60, 60, 60, 60, 60));
        assertEquals(70, t.getStrength());
    }

    @Test
    void nullPlayerIsRejected() {
        assertThrows(IllegalArgumentException.class, () -> new Team("T").addPlayer(null));
    }

    @Test
    void getPlayersIsReadOnly() {
        Team t = new Team("T");
        t.addPlayer(new Defender("A", 25, 80, 80, 80, 80, 80));
        assertThrows(UnsupportedOperationException.class, () -> t.getPlayers().clear());
    }
}