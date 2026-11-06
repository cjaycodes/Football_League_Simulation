package football.service;

import football.model.Match;
import football.model.Team;
import java.util.Random;

public class StrengthBasedSimulator implements MatchSimulator {

    private static final double HOME_ADVANTAGE = 1.05;
    private static final double CHANCE_DIVISOR = 10.0;
    private static final double BASE_CONVERSION = 0.15;
    private static final double MAX_CONVERSION = 0.6;

    private final Random random;

    public StrengthBasedSimulator(Random random) {
        if (random == null) {
            throw new IllegalArgumentException("random must not be null");
        }
        this.random = random;
    }

    @Override
    public Match simulate(Team homeTeam, Team awayTeam) {
        if (homeTeam == null || awayTeam == null) {
            throw new IllegalArgumentException("teams must not be null");
        }

        double homeStrength = homeTeam.getStrength() * HOME_ADVANTAGE;
        double awayStrength = awayTeam.getStrength();

        if (homeStrength <= 0 || awayStrength <= 0) {
            throw new IllegalArgumentException("teams must have a positive strength");
        }

        int homeChances = toChances(homeStrength);
        int awayChances = toChances(awayStrength);

        double homeConversion = toConversion(homeStrength, awayStrength);
        double awayConversion = toConversion(awayStrength, homeStrength);

        int homeGoals = countGoals(homeChances, homeConversion);
        int awayGoals = countGoals(awayChances, awayConversion);

        return new Match(homeTeam, awayTeam, homeGoals, awayGoals);
    }

    private int toChances(double strength) {
        return (int) Math.round(strength / CHANCE_DIVISOR);
    }

    private double toConversion(double own, double opponent) {
        return Math.min(MAX_CONVERSION, BASE_CONVERSION * own / opponent);
    }

    private int countGoals(int chances, double conversionProbability) {
        int goals = 0;
        for (int i = 0; i < chances; i++) {
            if (random.nextDouble() < conversionProbability) {
                goals++;
            }
        }
        return goals;
    }
}