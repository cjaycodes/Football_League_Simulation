package football;

import football.exception.InvalidDataException;
import football.model.Team;
import football.service.FixtureScheduler;
import football.service.League;
import football.service.StandingsCalculator;
import football.service.StrengthBasedSimulator;
import football.utils.FileLoader;

import java.util.List;
import java.util.Random;
import java.util.Scanner;

public class Application {
    private static final String TEAMS_FILE = "teams.csv";

    public static void main(String[] args) {
        System.out.println("Football League starting...");

        try {
            List<Team> teams = new FileLoader().loadTeams(TEAMS_FILE);
            League league = new League(teams,
                    new StrengthBasedSimulator(new Random()),
                    new FixtureScheduler());
            new GameMenu(league, new StandingsCalculator(), new Scanner(System.in)).run();
        } catch (InvalidDataException e) {
            System.err.println("Could not load data: " + e.getMessage());
        }
    }
}