# Football League Simulator

## Purpose
This is a console football game, a bit like career mode in FC. You choose one of the 20 Premier League teams and play the season one matchday at a time. Each match is simulated using the strength of the players in both teams. The results are saved and the league table updates after every matchday.

## How to run
You need Java 17 or newer and Maven. The file teams.csv must be in the project root, next to pom.xml, so run the program from there. Run the class football.Application and follow the menu.

## Main features
- Choose your team from the 20 teams in teams.csv
- Play the next matchday, or simulate the rest of the season
- See the league table, your team's results, the results of any matchday, the next fixtures and your squad
- A full season of 38 matchdays, home and away

## Main classes
- Player is an abstract class. Goalkeeper, Defender, Midfielder and Striker extend it.
- Position is an enum with the four positions.
- Team holds a list of players. Its strength is the average of their overall ratings.
- Match is a played game with a score. Fixture is a scheduled game with no score yet.
- MatchSimulator is an interface. StrengthBasedSimulator decides the score of a match.
- FixtureScheduler builds the calendar of 38 matchdays.
- League plays one matchday at a time and stores the results.
- TableRow is one line of the table. StandingsCalculator builds and sorts the table.
- FileLoader reads teams.csv. InvalidDataException is the custom exception for bad data.
- GameMenu handles the console (reading the user's choices and printing the results), and Application connects everything.

## Technical decisions
1. Position is an enum. There are only 4 positions in football and they never change, so I list them once in the code and the compiler rejects anything else. This also makes parsing the CSV safer.
2. Each position is its own class extending Player, because each position rates a player differently. The subclass sets its own position, so a Defender can never be labelled a Striker. Name, age and position are final because they do not change after the player is created. That is a design choice, and I could remove final if I needed to.
3. Team stores its players in a List<Player>, and Match takes two Team objects. This keeps the squad management and the match simulation separate.
4. The Random object is passed into the simulator instead of being created inside it. With a fixed seed the same match gives the same result, so I can test it.
5. The table is not stored. It is recalculated from the saved match results every time, so it can never be out of sync with them.

## Where inheritance is used
Goalkeeper, Defender, Midfielder and Striker extend Player. They share the name, age, rating and position, and each one adds its own four stats.

## Where polymorphism is used
getOverallRating() is overridden in each subclass. Team.getStrength() goes through a List<Player> and calls it without knowing which subclass each player is, so the formula used depends on the real object. League also uses whichever MatchSimulator it is given.

## Where the abstract class and interface are used
Player is abstract because it holds what every player shares, but a player with no position makes no sense, and it forces each subclass to write its own getOverallRating(). MatchSimulator is an interface because it describes one thing (turn two teams into a result) that could be done in several ways, and League only depends on that interface.

## Custom exception
InvalidDataException is a checked exception that carries the line number. FileLoader throws it when the file is missing, a line has the wrong number of columns, a value is not a number, the position is unknown or the rating is outside 1 to 100. Application catches it and prints the message.

## How the application loads its data
When it starts, Application calls FileLoader to read teams.csv. Each line is: team,name,age,rating,position,stat1,stat2,stat3,stat4. Players are grouped by team name, and the position decides which subclass is created. The stats are in this order: goalkeeper has diving, handling, positioning, reflexes; defender has tackling, heading, physical, positioning; midfielder has passing, stamina, dribbling, shooting; striker has finishing, pace, positioning, shooting.

## How I applied SOLID
- Single responsibility: FixtureScheduler makes the calendar, League plays matches and stores results, StandingsCalculator ranks the teams, FileLoader reads the file, GameMenu only handles the console interaction and contains no game logic, and Application only connects the parts.
- Open/closed: a new way of simulating is a new MatchSimulator class and League does not change. A new position is a new Player subclass. A new menu option is one register(...) line in GameMenu.
- Liskov substitution: any Player subclass can be used wherever a Player is expected.
- Interface segregation: MatchSimulator has only one method.
- Dependency inversion: League depends on the MatchSimulator interface, and GameMenu gets what it needs through its constructor.

## How to run the tests
Run mvn test, or in IntelliJ right-click src/test/java and choose Run All Tests.