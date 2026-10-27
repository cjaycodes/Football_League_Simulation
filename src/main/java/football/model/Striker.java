package football.model;

public class Striker extends Player{
    private final int  finishing;
    private final int pace;
    private final int positioning;
    private final int shooting;


    public Striker(String name, int age,int rating, int finishing,int pace,int positioning,int shooting){
        super(name, age,rating,Position.Striker);
        this.finishing = finishing;
        this.pace= pace;
        this.positioning = positioning;
        this.shooting = shooting;
    }


    @Override
        public int getOverallRating() {
            double overall = (finishing * 0.4) + (pace * 0.25) + (positioning * 0.2) + (shooting * 0.15);
            return (int) Math.round(overall);
    }
}


