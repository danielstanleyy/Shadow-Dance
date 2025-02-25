/**
 * Represents a DoubleScoreNote in the game which, when activated, doubles the score for certain actions.
 * This special note, when close enough to a target (as determined by the `calculateDistance()` method),
 * invokes a reward that results in doubling the score.
 */
public class DoubleScoreNote extends SpecialNote {
    /**
     * Constructor for creating an instance of a DoubleScoreNote.
     * @param lane          The lane in which the note resides.
     * @param type          The type of the special note, expected to be "doublescore" for this subclass.
     * @param frameNumber   The frame number at which the note appears.
     */
    protected DoubleScoreNote(String lane, String type, int frameNumber) {
        super(lane, type, frameNumber);
    }
    /**
     * Activates the score doubling effect if the note is close enough to the target.
     * The closeness is determined by the `calculateDistance()` method.
     * When activated, it retrieves the "DOUBLE SCORE" reward.
     */
    @Override
    public void activate() {
        // Checks if the note is close enough to the target
        if (isActivatable()) {
            // Activate the double score reward
            Reward.getReward("DOUBLE SCORE");
        }
    }
}
