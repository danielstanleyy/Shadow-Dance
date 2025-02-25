/**
 * Represents a standard lane in the game.
 * Initializes its texture based on the given lane type.
 */
import bagel.Image;
public class NormalLane extends Lane{
    /**
     * Constructs a NormalLane object and sets its texture.
     * @param type The type of lane.
     * @param x    The x-coordinate where the lane should be positioned.
     */
    protected NormalLane(String type, int x) {
        // Calls the constructor of the parent Lane class
        super(type,x);
        // Sets the texture of the lane based on its type
        setTexture();
    }
    /**
     * Assigns a visual representation to the lane based on its type.
     */
    @Override
    protected void setTexture() {
        // Checks the type of lane to determine the appropriate image asset
        switch (type.toLowerCase()){
            case "up":
                image = new Image("res/laneUp.png");
                break;
            case "down":
                image = new Image("res/laneDown.png");
                break;
            case "left":
                image = new Image("res/laneLeft.png");
                break;
            case "right":
                image = new Image("res/laneRight.png");
                break;
            // Do nothing for unrecognized lane types
            default:
                break;
        }
    }
}
