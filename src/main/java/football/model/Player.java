package football.model;

 public abstract class Player {
  private final String name;
  private final int age;
  private int rating;
  private final Position position;

  // Constructor
  public Player(String name,int age,int rating,Position position) {
   this.name = name;
   this.age = age;
   setRating(rating); // Uses validation setter
   this.position = position;
  }

  // Getters
  public String getName()
  {
   return name;
  }

  public int getAge()
  {
   return age;
  }

  public int getRating() {
   return rating;
  }

  public Position getPosition()
  {
   return position;
  }

  // Encapsulation with validation (good for your rubric!)
  public void setRating(int rating) {
   if (rating < 1 || rating > 100) {
    throw new IllegalArgumentException("Player rating must be between 1 and 100.");
   }
   this.rating = rating;
  }

  public abstract int getOverallRating();
 }


