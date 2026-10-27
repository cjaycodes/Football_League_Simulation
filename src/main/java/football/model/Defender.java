package football.model;

public class Defender extends Player{
    private final int tackling;
    private final int heading;
    private final int physical;
    private final int positioning;
    public Defender(String name, int age,int rating,int tackling,int heading,int physical,int positioning){
    super(name, age,rating,Position.Defender);
    this.tackling = tackling;
    this.heading = heading;
    this.physical = physical;
    this.positioning = positioning;
}



    @Override
    public int getOverallRating() {
        double overall = (tackling * 0.4) + (heading * 0.25) + (positioning * 0.2) + (physical * 0.30);
        return (int) Math.round(overall);
    }
}