package football.model;

public class Mildfielder extends Player {
    private final int passing;
    private final int stamina;
    private final int dribbling;
    private final int shooting;
    ;

    public Mildfielder(String name, int age,int rating,int passing,int stamina,int dribbling, int shooting){
        super(name, age,rating,Position.Midfielder);
        this.passing = passing;
        this.stamina = stamina;
        this.dribbling = dribbling;
        this.shooting = shooting;
    }


    @Override
    public int getOverallRating() {
        double overall = (getRating() * 0.20)
                + (passing * 0.28)
                + (shooting * 0.20)
                + (stamina * 0.16)
                + (dribbling * 0.16);
        return (int) Math.round(overall);
    }
}

