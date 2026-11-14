package football.utils;

import football.exception.InvalidDataException;
import football.model.Team;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.io.TempDir;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

class FileLoaderTest {

    private static final String HEADER = "team,name,age,rating,position,stat1,stat2,stat3,stat4\n";

    @TempDir
    Path dir;

    private String write(String content) throws IOException {
        Path file = dir.resolve("teams.csv");
        Files.writeString(file, content);
        return file.toString();
    }

    @Test
    void loadsTeamsAndGroupsPlayers() throws Exception {
        String path = write(HEADER
                + "Alpha,P1,25,80,GOALKEEPER,80,80,80,80\n"
                + "Alpha,P2,26,81,DEFENDER,80,80,80,80\n"
                + "Beta,P3,27,82,MIDFIELDER,80,80,80,80\n"
                + "Beta,P4,28,83,STRIKER,80,80,80,80\n");
        List<Team> teams = new football.util.FileLoader().loadTeams(path);
        assertEquals(2, teams.size());
        assertEquals("Alpha", teams.get(0).getName());
        assertEquals(2, teams.get(0).getPlayers().size());
    }

    @Test
    void missingFileThrows() {
        assertThrows(InvalidDataException.class,
                () -> new football.util.FileLoader().loadTeams(dir.resolve("nope.csv").toString()));
    }

    @Test
    void wrongColumnCountReportsLineNumber() throws Exception {
        String path = write(HEADER
                + "Alpha,P1,25,80,GOALKEEPER,80,80,80,80\n"
                + "Alpha,P2,26,81,DEFENDER,80,80\n");
        InvalidDataException e = assertThrows(InvalidDataException.class,
                () -> new football.util.FileLoader().loadTeams(path));
        assertEquals(3, e.getLineNumber());
    }

    @Test
    void nonNumericValueThrows() throws Exception {
        String path = write(HEADER + "Alpha,P1,abc,80,GOALKEEPER,80,80,80,80\n");
        InvalidDataException e = assertThrows(InvalidDataException.class,
                () -> new football.util.FileLoader().loadTeams(path));
        assertEquals(2, e.getLineNumber());
    }

    @Test
    void unknownPositionThrows() throws Exception {
        String path = write(HEADER + "Alpha,P1,25,80,WINGER,80,80,80,80\n");
        assertThrows(InvalidDataException.class, () -> new football.util.FileLoader().loadTeams(path));
    }

    @Test
    void ratingOutsideRangeThrows() throws Exception {
        String path = write(HEADER + "Alpha,P1,25,150,GOALKEEPER,80,80,80,80\n");
        InvalidDataException e = assertThrows(InvalidDataException.class,
                () -> new football.util.FileLoader().loadTeams(path));
        assertEquals(2, e.getLineNumber());
    }

    @Test
    void fewerThanTwoTeamsThrows() throws Exception {
        String path = write(HEADER + "Alpha,P1,25,80,GOALKEEPER,80,80,80,80\n");
        assertThrows(InvalidDataException.class, () -> new football.util.FileLoader().loadTeams(path));
    }
}