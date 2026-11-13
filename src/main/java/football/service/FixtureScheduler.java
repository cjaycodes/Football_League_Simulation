package football.service;

import football.model.Fixture;
import football.model.Team;

import java.util.ArrayList;
import java.util.List;

public class FixtureScheduler {

    // Circle method: team 0 stays fixed, the others rotate each round.
    public List<List<Fixture>> generate(List<Team> teams) {
        List<Team> rotation = new ArrayList<>(teams);
        if (rotation.size() % 2 != 0) {
            rotation.add(null); // a null opponent means "bye" for that team
        }
        int n = rotation.size();

        List<List<Fixture>> firstHalf = new ArrayList<>();
        for (int round = 0; round < n - 1; round++) {
            List<Fixture> matchday = new ArrayList<>();
            for (int i = 0; i < n / 2; i++) {
                Team a = rotation.get(i);
                Team b = rotation.get(n - 1 - i);
                if (a == null || b == null) {
                    continue;
                }
                // swap home/away every round so it stays balanced
                matchday.add(round % 2 == 0 ? new Fixture(a, b) : new Fixture(b, a));
            }
            firstHalf.add(matchday);
            rotation.add(1, rotation.remove(n - 1)); // rotate everyone except index 0
        }

        // second half of the season: same pairings, home/away reversed
        List<List<Fixture>> season = new ArrayList<>(firstHalf);
        for (List<Fixture> matchday : firstHalf) {
            List<Fixture> reversed = new ArrayList<>();
            for (Fixture f : matchday) {
                reversed.add(new Fixture(f.getAway(), f.getHome()));
            }
            season.add(reversed);
        }
        return season;
    }
}