package football.model;

public class Fixture {
    private final Team home;
    private final Team away;

    public Fixture(Team home, Team away) {
        this.home = home;
        this.away = away;
    }

    public Team getHome() { return home; }
    public Team getAway() { return away; }
}