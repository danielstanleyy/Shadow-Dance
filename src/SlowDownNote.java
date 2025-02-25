/**
 * Represents a SlowDownNote in the game which, when activated, slows down some game mechanic.
 * This special note, when close enough to a target (as determined by the `calculateDistance()` method),
 * invokes a reward that results in slowing down the game's motion.
 */
public class SlowDownNote extends SpecialNote {
    /**
     * Constructor for creating an instance of a SlowDownNote.
     * @param lane          The lane in which the note resides.
     * @param type          The type of the special note, expected to be "slowdown" for this subclass.
     * @param frameNumber   The frame number at which the note appears.
     */
    protected SlowDownNote(String lane, String type, int frameNumber) {
        super(lane, type, frameNumber);
    }
    /**
     * Activates the slowdown effect if the note is close enough to the target.
     * The closeness is determined by the `calculateDistance()` method.
     * When activated, it retrieves the "SLOW DOWN" reward.
     */
    @Override
    public void activate() {
        // Checks if the note is close enough to the target
        if (isActivatable()) {
            // Activate the slowdown reward
            Reward.getReward("SLOW DOWN");
        }
    }
}
