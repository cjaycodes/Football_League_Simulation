package football.model;

public class Match {
    private Team homeTeam;
    private Team awayTeam;

    public Match(Team homeTeam, Team awayTeam) {
        this.homeTeam = homeTeam;
        this.awayTeam = awayTeam;
    }

    public void simulateMatch() {
        int homeStrength = homeTeam.getStrength();
        int awayStrength = awayTeam.getStrength();

        double homePerformance = homeStrength + (Math.random() * 30 - 15);
        double awayPerformance = awayStrength + (Math.random() * 30 - 15);

        // Generate simulated goals based on performance
        int homeGoals = Math.max(0, (int) (homePerformance / 30 + (Math.random() * 2)));
        int awayGoals = Math.max(0, (int) (awayPerformance / 30 + (Math.random() * 2)));

        System.out.println(homeTeam.getName() + " " + homeGoals + " - " + awayGoals + " " + awayTeam.getName());
    }
        // Print out the final result
        // TODO: Compare homeTeam.getStrength() and awayTeam.getStrength()
        // and print out a simulated score or result based on their strengths.
    }

