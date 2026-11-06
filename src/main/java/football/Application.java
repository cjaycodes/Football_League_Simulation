package football;

import football.model.*;
import football.service.*;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;

public class Application {
    public static void main(String[] args) {
        System.out.println("Football League starting...");

        // 1. Build teams (hardcoded for now, FileLoader replaces this in S4)
        List<Team> teams = new ArrayList<>();
        // TODO: create at least 4 teams, each with players (mix of GK, DEF, MID, STR)

        // 2. Wire the league
        // TODO: create the simulator (give it a Random) and the League

        // 3. Play and compute the table
        // TODO: play all matches, then calculate standings from the results

        // 4. Print the table
        System.out.printf("%-15s %2s %2s %2s %2s %3s %3s %3s %3s%n",
                "Team", "P", "W", "D", "L", "GF", "GA", "GD", "Pts");
        // TODO: loop over the rows and print each one with the same format
    }
}