package football.service;

import football.model.Match;
import football.model.Team;

public class StrengthBasedSimulator implements MatchSimulator{

    @Override
    public Match simulate(Team homeTeam, Team awayTeam) {
            int homeStrength = homeTeam.getStrength();
            int awayStrength = awayTeam.getStrength();

            double homePerformance = homeStrength + (Math.random() * 30 - 15);
            double awayPerformance = awayStrength + (Math.random() * 30 - 15);

            // Generate simulated goals based on performance
            int homeGoals = Math.max(0, (int) (homePerformance / 30 + (Math.random() * 2)));
            int awayGoals = Math.max(0, (int) (awayPerformance / 30 + (Math.random() * 2)));

         return new Match(homeTeam,awayTeam,homeGoals,awayGoals);
    }
}
