package football.model;

import org.junit.jupiter.api.Test;

import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

class PlayerTest {

    @Test
    void ratingBelowOneIsRejected() {
        assertThrows(IllegalArgumentException.class,
                () -> new Defender("A", 25, 0, 50, 50, 50, 50));
    }

    @Test
    void ratingAboveHundredIsRejected() {
        assertThrows(IllegalArgumentException.class,
                () -> new Defender("A", 25, 101, 50, 50, 50, 50));
    }

    @Test
    void setRatingValidates() {
        Player p = new Defender("A", 25, 70, 50, 50, 50, 50);
        assertThrows(IllegalArgumentException.class, () -> p.setRating(150));
        p.setRating(80);
        assertEquals(80, p.getRating());
    }

    @Test
    void overallEqualsInputsWhenAllAreTheSame() {
        // weights sum to 1.0, so all 90s must give exactly 90
        Player p = new Defender("A", 25, 90, 90, 90, 90, 90);
        assertEquals(90, p.getOverallRating());
    }

    @Test
    void overallUsesTheDefenderFormula() {
        // rating 50 -> 10 points, first stat 100 at weight 0.28 -> 28 points
        assertEquals(38, new Defender("D", 25, 50, 100, 0, 0, 0).getOverallRating());
        // all stats 0 -> only the rating's 20% remains
        assertEquals(10, new Defender("D", 25, 50, 0, 0, 0, 0).getOverallRating());
    }

    @Test
    void everySubclassOverridesOverallRating() {
        // polymorphism: called through the Player type, each subclass uses its own override
        List<Player> players = List.of(
                new Goalkeeper("G", 25, 50, 100, 100, 100, 100),
                new Defender("D", 25, 50, 100, 100, 100, 100),
                new Midfielder("M", 25, 50, 100, 100, 100, 100),
                new Striker("S", 25, 50, 100, 100, 100, 100));
        for (Player p : players) {
            // 0.20 x 50 + 0.80 x 100 = 90, because the weights sum to 1.0
            assertEquals(90, p.getOverallRating());
        }
    }
}