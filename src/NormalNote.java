/**
 * Represents a normal musical note in the game, which is a type of note that moves vertically in a lane.
 * Normal notes have specific lane assignments and are interacted with by the player based on their timing.
 */
import bagel.Image;
public class NormalNote extends Note{
    /**
     * Constructor for the NormalNote class. Initializes a new instance based on the provided lane, note type,
     * and frame number, and then sets its texture based on the lane type.
     * @param lane          The lane in which the normal note appears.
     * @param type          The type of note.
     * @param frameNumber   The frame number when the normal note should start moving.
     */
    protected NormalNote(String lane, String type, int frameNumber) {
        // Calls the constructor of the parent Note class
        super(lane, type, frameNumber);
        // Sets the image for the normal note based on the lane type
        setTexture();
    }
    /**
     * Sets the image for the NormalNote based on its lane assignment.
     * Different lanes have corresponding visual representations.
     */
    @Override
    protected void setTexture() {
        // Check the lane of the note and assign the corresponding image
        switch (lane.toLowerCase()) {
            case "up":
                // Assign the image for the "up" lane
                image = new Image("res/noteUp.png");
                break;
            case "down":
                // Assign the image for the "down" lane
                image = new Image("res/noteDown.png");
                break;
            case "right":
                // Assign the image for the "right" lane
                image = new Image("res/noteRight.png");
                break;
            case "left":
                // Assign the image for the "left" lane
                image = new Image("res/noteLeft.png");
                break;
            default:
                // No image assignment for unrecognized lanes
                break;
        }
    }
}
