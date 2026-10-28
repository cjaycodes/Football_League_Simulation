package football.service;

import football.model.Match;
import football.model.Team;

public interface MatchSimulator {
    Match simulate(Team home, Team away);
}



