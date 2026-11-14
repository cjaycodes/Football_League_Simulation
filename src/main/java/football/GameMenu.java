package football;

import football.model.Fixture;
import football.model.Match;
import football.model.Player;
import football.model.TableRow;
import football.model.Team;
import football.service.League;
import football.service.StandingsCalculator;

import java.util.List;
import java.util.Scanner;

public class GameMenu {
    private final League league;
    private final StandingsCalculator standings;
    private final Scanner scanner;
    private Team myTeam;

    public GameMenu(League league, StandingsCalculator standings, Scanner scanner) {
        this.league = league;
        this.standings = standings;
        this.scanner = scanner;
    }

    public void run() {
        myTeam = chooseTeam();
        System.out.println("\nYou are now managing " + myTeam.getName() + "!");

        boolean running = true;
        while (running) {
            printMenu();
            switch (readInt("Choose: ")) {
                case 1: playMatchday(); break;
                case 2: showTable(); break;
                case 3: showMyResults(); break;
                case 4: showMatchdayResults(); break;
                case 5: showNextFixtures(); break;
                case 6: showSquad(); break;
                case 7: simulateSeason(); break;
                case 0: running = false; break;
                default: System.out.println("Invalid option.");
            }
        }
        System.out.println("Goodbye!");
    }

    private Team chooseTeam() {
        List<Team> teams = league.getTeams();
        System.out.println("\nChoose your team:");
        for (int i = 0; i < teams.size(); i++) {
            System.out.printf("%2d. %-25s (strength %d)%n",
                    i + 1, teams.get(i).getName(), teams.get(i).getStrength());
        }
        int choice;
        do {
            choice = readInt("Team number: ");
        } while (choice < 1 || choice > teams.size());
        return teams.get(choice - 1);
    }

    private void printMenu() {
        System.out.println("\n=== " + myTeam.getName() + " | Matchday "
                + league.getCurrentMatchday() + "/" + league.getTotalMatchdays() + " ===");
        System.out.println("1. Play next matchday");
        System.out.println("2. League table");
        System.out.println("3. My team's results");
        System.out.println("4. Results of a matchday");
        System.out.println("5. Next fixtures");
        System.out.println("6. My squad");
        System.out.println("7. Simulate the rest of the season");
        System.out.println("0. Quit");
    }

    private void playMatchday() {
        if (!league.hasNextMatchday()) {
            System.out.println("The season is over.");
            return;
        }
        List<Match> played = league.playNextMatchday();
        System.out.println("\n--- Matchday " + league.getCurrentMatchday() + " results ---");
        for (Match m : played) {
            printMatch(m);
        }
        if (!league.hasNextMatchday()) {
            printSeasonEnd();
        }
    }

    private void showTable() {
        List<Match> results = league.getResults();
        if (results.isEmpty()) {
            System.out.println("No matches played yet.");
            return;
        }
        List<TableRow> table = standings.calculate(results);
        System.out.printf("%n%-3s %-25s %3s %3s %3s %3s %4s %4s %4s %4s%n",
                "#", "Team", "P", "W", "D", "L", "GF", "GA", "GD", "Pts");
        int position = 1;
        for (TableRow row : table) {
            String mark = row.getTeam() == myTeam ? " <--" : "";
            System.out.printf("%-3d %-25s %3d %3d %3d %3d %4d %4d %4d %4d%s%n",
                    position++, row.getTeam().getName(),
                    row.getPlayed(), row.getWins(), row.getDraws(), row.getLosses(),
                    row.getGoalsFor(), row.getGoalsAgainst(),
                    row.getGoalDifference(), row.getPoints(), mark);
        }
    }

    private void showMyResults() {
        List<Match> mine = league.getResultsFor(myTeam);
        if (mine.isEmpty()) {
            System.out.println("Your team hasn't played yet.");
            return;
        }
        System.out.println("\n--- " + myTeam.getName() + " results ---");
        for (Match m : mine) {
            printMatch(m);
        }
    }

    private void showMatchdayResults() {
        if (league.getCurrentMatchday() == 0) {
            System.out.println("No matches played yet.");
            return;
        }
        int day = readInt("Matchday (1-" + league.getCurrentMatchday() + "): ");
        if (day < 1 || day > league.getCurrentMatchday()) {
            System.out.println("Invalid matchday.");
            return;
        }
        System.out.println("\n--- Matchday " + day + " results ---");
        for (Match m : league.getMatchdayResults(day)) {
            printMatch(m);
        }
    }

    private void showNextFixtures() {
        List<Fixture> fixtures = league.getNextFixtures();
        if (fixtures.isEmpty()) {
            System.out.println("The season is over.");
            return;
        }
        System.out.println("\n--- Matchday " + (league.getCurrentMatchday() + 1) + " fixtures ---");
        for (Fixture f : fixtures) {
            boolean mine = f.getHome() == myTeam || f.getAway() == myTeam;
            System.out.printf("%-25s vs %-25s%s%n",
                    f.getHome().getName(), f.getAway().getName(), mine ? " <-- you" : "");
        }
    }

    private void showSquad() {
        System.out.println("\n--- " + myTeam.getName() + " squad ---");
        System.out.printf("%-12s %-25s %3s %7s%n", "Position", "Name", "Age", "Overall");
        for (Player p : myTeam.getPlayers()) {
            System.out.printf("%-12s %-25s %3d %7d%n",
                    p.getPosition(), p.getName(), p.getAge(), p.getOverallRating());
        }
    }

    private void simulateSeason() {
        if (!league.hasNextMatchday()) {
            System.out.println("The season is over.");
            return;
        }
        while (league.hasNextMatchday()) {
            league.playNextMatchday();
        }
        printSeasonEnd();
    }

    private void printSeasonEnd() {
        List<TableRow> table = standings.calculate(league.getResults());
        System.out.println("\n=== SEASON OVER ===");
        System.out.println("Champions: " + table.get(0).getTeam().getName());
        for (int i = 0; i < table.size(); i++) {
            if (table.get(i).getTeam() == myTeam) {
                System.out.println("You finished " + (i + 1) + " of " + table.size()
                        + " with " + table.get(i).getPoints() + " points.");
            }
        }
    }

    private void printMatch(Match m) {
        boolean mine = m.getHomeTeam() == myTeam || m.getAwayTeam() == myTeam;
        System.out.printf("%-25s %d - %d  %-25s%s%n",
                m.getHomeTeam().getName(), m.getHomeGoals(),
                m.getAwayGoals(), m.getAwayTeam().getName(), mine ? " <-- you" : "");
    }

    private int readInt(String prompt) {
        while (true) {
            System.out.print(prompt);
            try {
                return Integer.parseInt(scanner.nextLine().trim());
            } catch (NumberFormatException e) {
                System.out.println("Please enter a number.");
            }
        }
    }
}