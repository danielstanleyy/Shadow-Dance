/**
 * Represents the third level of the game, extending the base World class.
 * This level is defined and loaded from the "res/level3.csv" file.
 */
public class Level3 extends World{
    /**
     * Constructor for the Level3 class.
     * Calls the parent constructor with the path to the CSV file for level 1
     * and subsequently loads the world.
     */
    public Level3(){
        // Call parent constructor with the path to level 3 CSV file
        super("res/level3.csv");
        // Load the world for this level
        this.loadWorld();
    }
}