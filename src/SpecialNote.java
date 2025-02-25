/**
 * Represents a specialized note in the game which has a unique effect or visual representation.
 * This class extends the base Note class and provides custom texture loading based on the type
 * of special note.
 */
import bagel.Image;

public abstract class SpecialNote extends Note {
    /** The distance where the special note is considered "activated" */
    protected static final int ACTIVATE_DISTANCE = 50;
    /**
     * Constructor for creating an instance of a SpecialNote.
     * @param lane          The lane in which the note resides.
     * @param type          The type of the special note.
     * @param frameNumber   The frame number at which the note appears.
     */
    protected SpecialNote(String lane, String type, int frameNumber) {
        super(lane, type, frameNumber);
        // Load the image based on the note type.
        setTexture();
    }
    /**
     * Loads the appropriate image for the special note based on its type.
     */
    @Override
    protected void setTexture() {
        // Determine the image based on the note type.
        switch (type.toLowerCase()) {
            case "bomb":
                image = new Image("res/noteBomb.png");
                break;
            case "doublescore":
                image = new Image("res/note2x.png");
                break;
            case "speedup":
                image = new Image("res/noteSpeedUp.png");
                break;
            case "slowdown":
                image = new Image("res/noteSlowDown.png");
                break;
            default:
                // Do nothing for unrecognized types.
                break;
        }
    }
    /**
     * Determines if the note is close enough to the target to be activated.
     *
     * @return true if the note is within the activation distance, false otherwise.
     */
    protected boolean isActivatable() {
        return this.calculateDistance() <= ACTIVATE_DISTANCE;
    }
}
