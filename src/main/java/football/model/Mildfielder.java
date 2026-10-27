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
        double overall = (passing * 0.4) + (stamina * 0.25) + (dribbling * 0.2) + (shooting* 0.30);
        return (int)Math.round(overall);
    }
}

