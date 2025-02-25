/**
 * Represents a SpeedUpNote in the game, which has the capability to increase the game's motion speed upon activation.
 * Upon activation, if the SpeedUpNote is within a certain distance threshold, the game's motion speed is increased,
 * affecting the movement of game elements. The class extends the SpecialNote class, inheriting its fundamental properties
 * while adding a unique activation behavior.
 */
public class SpeedUpNote extends SpecialNote{
    /**
     * Constructor for creating an instance of a SpeedUpNote.
     * @param lane          The lane in which the note is positioned.
     * @param type          The type of the note.
     * @param frameNumber   The frame at which the note will start its motion.
     */
    protected SpeedUpNote(String lane, String type, int frameNumber) {
        super(lane, type, frameNumber);
    }
    /**
     * Activates the effect of the SpeedUpNote.
     */
    @Override
    public void activate() {
        // Check if the note's distance is within the specified threshold
        if(isActivatable()) {
            //Apply the speed-increasing effect
            Reward.getReward("SPEED UP");
        }
    }
}
