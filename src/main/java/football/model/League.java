package football.model;

import football.service.MatchSimulator;
import java.util.ArrayList;
import java.util.List;

public class League {
        private final List<Team> teams;
        private final MatchSimulator simulator;
        private final List<Match> results = new ArrayList<>();

        public League(List<Team> teams, MatchSimulator simulator) {
            this.teams = teams;
            this.simulator = simulator;
        }
    public void playAll() {
            for (int i = 0; i < teams.size(); i++) {
                for (int j = 0; j < teams.size(); j++) {
                    if (i == j) {
                        continue;
                    }
                    Team home = teams.get(i);
                    Team away = teams.get(j);
                    Match match = simulator.simulate(home, away);
                    results.add(match);
                }
            }
        }

        public List<Match> getResults() {
            return results;
        }
    }



