/**
 * Represents the first level of the game, extending the base World class.
 * This level is defined and loaded from the "res/level1.csv" file.
 */
public class Level1 extends World {
    /**
     * Constructor for the Level1 class.
     * Calls the parent constructor with the path to the CSV file for level 1
     * and subsequently loads the world.
     */
    public Level1() {
        // Call parent constructor with the path to level 1 CSV file
        super("res/level1.csv");
        // Load the world for this level
        this.loadWorld();
    }
}
