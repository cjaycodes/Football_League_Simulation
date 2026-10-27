package football.model;

public class Goalkeeper extends Player {
    private final int reflexes;
    private final int handling;
    private final int diving;
    private final int positioning;

    public Goalkeeper(String name, int age, int rating, int reflexes, int handling,int diving,int positioning) {
        super(name, age, rating, Position.Goalkeeper);
        this.reflexes = reflexes;
        this.handling = handling;
        this.diving =  diving;
        this.positioning = positioning;
    }


    @Override
    public int getOverallRating() {
        double overall = (diving* 0.4) + (handling * 0.25) + (positioning * 0.2) + (reflexes * 0.30);
        return (int) Math.round(overall);
    }
}
