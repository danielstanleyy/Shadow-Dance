/**
 * Represents a BombNote in the game, designed to remove certain notes in the vicinity upon activation.
 * When activated, the BombNote checks for other notes in its lane that are beyond a certain threshold.
 * For regular notes, the threshold is determined based on their type (i.e., HoldNote). Notes beyond the threshold
 * are added to a removal list. The BombNote then facilitates the removal of all these notes from the game.
 */
import java.util.ArrayList;
import java.util.List;

public class BombNote extends SpecialNote {
    /** The entry y-coordinate of a normal note */
    private static final int ENTRY_NORMAL = 100;
    /**
     * Constructor for creating an instance of a BombNote.
     * @param lane          The lane in which the note is positioned.
     * @param type          The type of the note.
     * @param frameNumber   The frame at which the note will start its motion.
     */
    protected BombNote(String lane, String type, int frameNumber) {
        super(lane, type, frameNumber);
    }
    /**
     * Activates the explosive effect of the BombNote.
     * The method inspects other notes in its lane and, depending on their type, determines if they should
     * be removed from the game. All qualifying notes are added to a list, which is then used to remove them
     * from the main notes list.
     */
    @Override
    public void activate() {
        // Create a list to store the indices of notes to be removed
        List<Integer> indicesToRemove = new ArrayList<>();
        int thresholdY;
        // Iterate through the notes
        for (int i = 0; i < notes.size(); i++) {
            Note note = notes.get(i);

            // Check if the note belongs to the target lane and its y value is greater than the threshold
            if(note instanceof HoldNote) {
                thresholdY = ENTRY_HOLD + 1;
            }
            else {
                thresholdY = ENTRY_NORMAL + 1;
            }

            // Add the index to the list of indices to be removed
            if (note.lane.equalsIgnoreCase(this.lane) && note.getY() > thresholdY) indicesToRemove.add(i);
        }

        // Remove notes based on the collected indices (remove in reverse order to avoid index shifting)
        for (int i = indicesToRemove.size() - 1; i >= 0; i--) {
            int indexToRemove = indicesToRemove.get(i);
            notes.remove(indexToRemove);
        }
    }
}
