/**
 * Represents a hold note in the game, requiring players to hold their input as it moves vertically in a lane.
 */
import bagel.Image;

public class HoldNote extends Note {
    /**
     * Added or subtracted offset to the centre y-coordinate
     */
    private static final int HEIGHT_OFFSET = 82;
    /**
     * Constructor for the HoldNote class. Initializes a new instance based on the provided lane, note type,
     * and frame number, and then sets its texture based on the lane type.
     * @param lane          The lane in which the hold note appears.
     * @param type          The type of note.
     * @param frameNumber   The frame number when the hold note should start moving.
     */
    protected HoldNote(String lane, String type, int frameNumber) {
        // Calls the constructor of the parent Note class
        super(lane, type, frameNumber);
        // Sets the Y-coordinate of the hold note
        this.y = ENTRY_HOLD;
        // Sets the texture/image for the hold note based on the lane type
        setTexture();
    }
    /**
     * Sets the texture for the HoldNote based on its lane assignment.
     * Different lanes have corresponding visual representations.
     */
    @Override
    protected void setTexture() {
        // Check the lane of the note and assign the corresponding image
        switch (lane.toLowerCase()) {
            case "up":
                // Assign the image for the "up" lane
                image = new Image("res/holdNoteUp.png");
                break;
            case "down":
                // Assign the image for the "down" lane
                image = new Image("res/holdNoteDown.png");
                break;
            case "right":
                // Assign the image for the "right" lane
                image = new Image("res/holdNoteRight.png");
                break;
            case "left":
                // Assign the image for the "left" lane
                image = new Image("res/holdNoteLeft.png");
                break;
            default:
                // No image assignment for unrecognized lanes
                break;
        }
    }
    /**
     * Calculates the distance between the hold note's current position and the stationary note position.
     * The distance calculation is affected by whether the note has been pressed or not.
     * @return The calculated distance value.
     */
    @Override
    protected int calculateDistance() {
        // Checks if the note has been pressed or not
        if (!this.isPressed) {
            // Calculate distance with an offset of -82 from the stationary Y position
            return Math.abs(stationaryNoteY - this.y - HEIGHT_OFFSET);
        }
        // Calculate distance with an offset of +82 from the stationary Y position
        return Math.abs(stationaryNoteY - this.y + HEIGHT_OFFSET);
    }
}
