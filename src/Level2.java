/**
 * Represents the second level of the game, extending the base World class.
 * This level is defined and loaded from the "res/level2.csv" file.
 */
public class Level2 extends World {
    /**
     * Constructor for the Level2 class.
     * Calls the parent constructor with the path to the CSV file for level 2
     * and subsequently loads the world.
     */
    public Level2() {
        // Call parent constructor with the path to level 2 CSV file
        super("res/test2.csv");
        // Load the world for this level
        this.loadWorld();
    }
}