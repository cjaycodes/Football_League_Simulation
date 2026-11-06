package football.model;

public class TableRow {
    private final Team team;
    private int played;
    private int wins;
    private int draws;
    private int losses;
    private int goalsFor;
    private int goalsAgainst;

    public TableRow(Team team) {
        this.team = team;
    }

    public void addMatch(int scored, int conceded) {
        played++;
       goalsFor += scored;
       goalsAgainst += conceded;

        if (scored > conceded) {
            wins += 1;
        } else if (scored == conceded) {
            draws += 1;
        } else {
            losses += 1;
        }

    }

    public int getGoalDifference() {
        return goalsFor - goalsAgainst;
    }

    public int getPoints() {
        return wins * 3 + draws;
    }

    public Team getTeam() { return team; }
    public int getPlayed() { return played; }
    public int getWins() { return wins; }
    public int getDraws() { return draws; }
    public int getLosses() { return losses; }
    public int getGoalsFor() { return goalsFor; }
    public int getGoalsAgainst() { return goalsAgainst; }
}