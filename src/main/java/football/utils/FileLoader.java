package football.utils;

import football.exception.InvalidDataException;
import football.model.*;

import java.io.BufferedReader;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.ArrayList;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;

public class FileLoader {

    private static final int EXPECTED_COLUMNS = 9;

    public List<Team> loadTeams(String filePath) throws InvalidDataException {
        Map<String, Team> teams = new LinkedHashMap<>();

        try (BufferedReader reader = Files.newBufferedReader(Path.of(filePath))) {
            reader.readLine(); // skip the header line
            String line;
            int lineNumber = 1;

            while ((line = reader.readLine()) != null) {
                lineNumber++;
                if (line.isBlank()) {
                    continue;
                }

                String[] parts = line.split(",", -1);
                if (parts.length != EXPECTED_COLUMNS) {
                    throw new InvalidDataException(
                            "expected " + EXPECTED_COLUMNS + " columns but found " + parts.length,
                            lineNumber);
                }

                String teamName = parts[0].trim();
                String name = parts[1].trim();
                String position = parts[4].trim();
                if (teamName.isEmpty() || name.isEmpty()) {
                    throw new InvalidDataException("team and player name must not be empty", lineNumber);
                }

                try {
                    int age = Integer.parseInt(parts[2].trim());
                    int rating = Integer.parseInt(parts[3].trim());
                    int s1 = Integer.parseInt(parts[5].trim());
                    int s2 = Integer.parseInt(parts[6].trim());
                    int s3 = Integer.parseInt(parts[7].trim());
                    int s4 = Integer.parseInt(parts[8].trim());

                    Player player = createPlayer(position, name, age, rating, s1, s2, s3, s4, lineNumber);
                    teams.computeIfAbsent(teamName, Team::new).addPlayer(player);
                } catch (NumberFormatException e) {
                    throw new InvalidDataException("age, rating and stats must be whole numbers", lineNumber);
                } catch (IllegalArgumentException e) {
                    throw new InvalidDataException(e.getMessage(), lineNumber);
                }
            }
        } catch (IOException e) {
            throw new InvalidDataException("Cannot read file: " + filePath);
        }

        if (teams.size() < 2) {
            throw new InvalidDataException("the file must contain at least 2 teams");
        }
        return new ArrayList<>(teams.values());
    }

    // CSV stat order per position:
    // GOALKEEPER: diving, handling, positioning, reflexes
    // DEFENDER:   tackling, heading, physical, positioning
    // MIDFIELDER: passing, stamina, dribbling, shooting
    // STRIKER:    finishing, pace, positioning, shooting
    private Player createPlayer(String position, String name, int age, int rating,
                                int s1, int s2, int s3, int s4, int lineNumber)
            throws InvalidDataException {
        switch (position.toUpperCase()) {
            case "GOALKEEPER":
                return new Goalkeeper(name, age, rating, s1, s2, s3, s4);
            case "DEFENDER":
                return new Defender(name, age, rating, s1, s2, s3, s4);
            case "MIDFIELDER":
                return new Midfielder(name, age, rating, s1, s2, s3, s4);
            case "STRIKER":
                return new Striker(name, age, rating, s1, s2, s3, s4);
            default:
                throw new InvalidDataException("unknown position: " + position, lineNumber);
        }
    }
}