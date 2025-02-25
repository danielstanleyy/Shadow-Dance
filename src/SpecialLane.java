/**
 * Represents a SpecialLane in the game that has a distinct appearance from regular lanes.
 * The SpecialLane class extends the Lane class and provides a unique texture to represent
 * lanes with special characteristics or functionalities within the game. The specific texture
 * representing the special lane is loaded from a designated image file.
 */
import bagel.Image;

public class SpecialLane extends Lane{
    /**
     * Constructor for creating an instance of a SpecialLane.
     *
     * @param type  The type of the lane.
     * @param x     The x-coordinate position of the lane.
     */
    protected SpecialLane(String type, int x) {
        super(type,x);
        // Set the distinct image for the special lane.
        setTexture();
    }
    /**
     * Sets the unique texture (image) for the special lane.
     * This is used to visually differentiate special lanes from regular lanes.
     */
    @Override
    protected void setTexture() {
        // Load the special lane image from the "resources" directory.
        image = new Image("res/laneSpecial.png");
    }
}

