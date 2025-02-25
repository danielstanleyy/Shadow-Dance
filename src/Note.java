/**
 * Represents a musical note in the game that moves vertically and can be interacted with by the player.
 */
import bagel.Image;
import java.util.ArrayList;
import java.util.List;

public abstract class Note {
    /**
     * A static list containing all notes in the game
     */
    protected static final List<Note> notes = new ArrayList<>();
    /**
     * The y-coordinate of the centre of the stationary note in the lanes
     */
    protected static int stationaryNoteY = 657;
    /**
     * The frame number when the note should start moving
     */
    private final int frameNumber;
    /**
     * The x-coordinate of the note's position within the game window
     */
    private final int x;
    /**
     * The lane in which the note appears
     */
    protected final String lane;
    /**
     * The type or category of the note.
     */
    protected final String type;
    /**
     * The image representation of the note.
     */
    protected Image image;
    /**
     * The y-coordinate of the note's position within the game window.
     */
    protected int y = 100;
    /**
     * The radius threshold within which if the note is missed, it's considered a 'miss'.
     */
    private static final int MISS_RADIUS = 200;
    /**
     * Flag indicating whether the note has been pressed or acted upon by the player.
     */
    protected boolean isPressed = false;
    /**
     * The entry y-coordinate of hold note
     */
    protected static final int ENTRY_HOLD = 24;

    /**
     * Constructor for the Note class.
     * @param lane          The lane in which the note appears.
     * @param type          The type of note.
     * @param frameNumber   The frame number when the note should start moving.
     */
    protected Note(String lane, String type, int frameNumber) {
        this.lane = lane;
        this.type = type;
        this.frameNumber = frameNumber;
        this.x = Lane.findXByLaneType(lane);
    }

    /**
     * Adds a note of the specified type to the notes list.
     * @param lane          The lane in which the note appears.
     * @param type          The type of note.
     * @param frameNumber   The frame number when the note should start moving.
     */
    public static void addNote(String lane, String type, int frameNumber) {
        // Based on the note type, create an appropriate Note instance and add it to the note list
        switch (type.toLowerCase()){
            case "normal":
                notes.add(new NormalNote(lane, type, frameNumber));
                break;
            case "hold":
                notes.add(new HoldNote(lane, type, frameNumber));
                break;
            case "bomb":
                notes.add(new BombNote(lane, type, frameNumber));
                break;
            case "speedup":
                notes.add(new SpeedUpNote(lane, type, frameNumber));
                break;
            case "slowdown":
                notes.add(new SlowDownNote(lane, type, frameNumber));
                break;
            case "doublescore":
                notes.add(new DoubleScoreNote(lane, type, frameNumber));
                break;
            default:
                // No action taken if the note type is not recognized
                break;
        }
    }
    public static List<Note> getNotes() {
        return notes;
    }
    public static void clearNotes() {
        notes.clear();
    }

    /**
     * Renders the notes on screen based on their current state and the frame counter.
     * @param frameCounter  The current frame number.
     * @param motionSpeed   The speed at which notes move vertically.
     * @param windowHeight  The height of the game window.
     */
    public static void render(int frameCounter,int motionSpeed, int windowHeight) {
        List<Note> notesToRemove = new ArrayList<>();
        for (Note note:notes) {
            // Check if the current frame surpasses the note's designated frame number
            if (frameCounter > note.frameNumber){

                // Render the note if it's within the window bounds
                if((note instanceof HoldNote && note.y - MISS_RADIUS < windowHeight) || (!(note instanceof HoldNote) && note.y < windowHeight)) {
                    // Draw the note on screen at its current position
                    note.image.draw(note.x, note.y);
                    // Update the Y-coordinate of the note based on motion speed, moving the note vertically
                    note.y += motionSpeed;
                }
                else {
                    // Check if the note is not of type SpecialNote
                    if(!(note instanceof SpecialNote)) {
                        // Register it as a "MISS" in the game's reward system
                        Reward.getReward("MISS");
                    }
                    // Add notes out of bounds to the removal list
                    notesToRemove.add(note);
                }
            }
        }
        // Remove notes that are out of bounds
        for (Note note : notesToRemove) notes.remove(note);
    }

    /**
     * Searches for and returns the note with the highest Y-coordinate (i.e., the lowest position on screen)
     * within the specified lane. If no note is found, it returns null.
     * @param targetLane    The lane to search within.
     * @return              The Note instance with the highest Y-coordinate in the specified lane, or null if none is found.
     */
    public static Note findLowestNote(String targetLane) {
        // Initialize maxY with an invalid value to start comparisons.
        int maxY = -1;
        // Initialize maxIndex with an invalid value to track the index of the note with the highest Y-coordinate.
        int maxIndex = -1;
        // Iterate through the list of notes.
        for (int i = 0; i < Note.getNotes().size(); i++) {
            Note note = Note.getNotes().get(i);

            // Check if the note's lane matches the target and its Y-coordinate is greater than the current maxY.
            if (note.lane.equalsIgnoreCase(targetLane)  && note.getY() > maxY) {
                // Update maxY to the Y-coordinate of the current note and maxIndex to the index of the current note.
                maxY = note.getY();
                maxIndex = i;
            }
        }
        // Check if a matching note was found and returns the note with highest Y-coordinate if it was found.
        if(maxIndex>-1) return Note.getNotes().get(maxIndex);
        return null;
    }

    /**
     * Activates a note in the game, triggering the associated reward based on the note's current state.
     * The note is marked as pressed, and if it's not a HoldNote, it is removed from the active notes list.
     * @param targetLane    The lane of the note that needs to be activated.
     */
    public static void activate(String targetLane){
        // Check if there are any active notes.
        if(!notes.isEmpty()){
            // Find the note closest to the target position in the specified lane.
            Note note = findLowestNote(targetLane);
            // Checks if a valid note was found, triggering the reward mechanism and marking note as pressed.
            if(note != null){
                note.activate();
                note.isPressed = true;
                // Check if the note is not of type HoldNote, hence removing it from the active list.
                if(!(note instanceof HoldNote)) notes.remove(note);
            }
        }
    }

    /**
     * Handles the release action for a note in a given lane.
     * If the note is of type HoldNote and is currently pressed, it's activated
     * and subsequently removed from the active notes list.
     * @param targetLane    The lane of the note that needs to be released.
     */
    public static void release(String targetLane){
        // Check if there are any active notes.
        if(!notes.isEmpty()){
            // Find the note closest to the target position in the specified lane.
            Note note = Note.findLowestNote(targetLane);
            // Check if the found note is of type HoldNote and currently pressed.
            if(note instanceof HoldNote && note.isPressed ) {
                // Trigger the reward mechanism for the note and remove note from active list.
                note.activate();
                notes.remove(note);
            }
        }
    }

    /**
     * Activates the current note, triggering the appropriate reward based on its distance
     * from the target position.
     */
    public void activate() {
        // Get the type of reward for this note based on its distance from the target and trigger it.
        Reward.getReward(Reward.getRewardType(this));
    }

    /**
     * Calculates the vertical distance of the note from the stationary reference point.
     * @return  The absolute value of the distance between the note's current y-position and the stationary y-reference point.
     */
    protected int calculateDistance() {
        // Compute and return the absolute difference between the reference y-value and this note's y-position.
        return Math.abs(stationaryNoteY - this.y);
    }
    /**
     * Retrieves the y-coordinate of the note.
     * @return The current y-position of the note.
     */
    public int getY() {
        // Return the y-coordinate of this note.
        return y;
    }
    /**
     * Retrieves the x-coordinate of the note.
     * @return The current x-position of the note.
     */
    public int getX() {
        // Return the x-coordinate of this note.
        return x;
    }
    /**
     * Assigns a visual representation to the note.
     * Subclasses will provide a concrete implementation for this method.
     */
    protected abstract void setTexture();
}
