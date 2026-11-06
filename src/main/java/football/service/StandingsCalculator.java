package football.service;

import football.model.Match;
import football.model.TableRow;
import football.model.Team;

import java.util.ArrayList;
import java.util.Comparator;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class StandingsCalculator {

    public List<TableRow> calculate(List<Match> matches) {
        Map<Team, TableRow> rows = createRows(matches);

        List<TableRow> table = new ArrayList<>(rows.values());

        table.sort(   Comparator.comparingInt(TableRow::getPoints)
                .thenComparingInt(TableRow::getGoalDifference)
                .thenComparingInt(TableRow::getGoalsFor)
                .reversed()
        );   // blank 3: points, then goal difference, then goals for, highest first

        return table;
    }

    private static Map<Team, TableRow> createRows(List<Match> matches) {
        Map<Team, TableRow> rows = new HashMap<>();

        for (Match match : matches) {
            TableRow homeRow = rows.computeIfAbsent(match.getHomeTeam(), TableRow::new);
            TableRow awayRow = rows.computeIfAbsent(match.getAwayTeam(),TableRow::new);   // blank 1: same idea, for the away team

            homeRow.addMatch(match.getHomeGoals(), match.getAwayGoals());   // blank 2: home goals scored, away goals conceded
            awayRow.addMatch(match.getAwayGoals(), match.getHomeGoals());   // blank 2: the reverse
        }
        return rows;
    }
}