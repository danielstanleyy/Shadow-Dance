import bagel.*;

/**
 * Skeleton Code for SWEN20003 Project 2, Semester 2, 2023
 * Please enter your name below
 * @Daniel Stanley Angdinata, 1360689
 */
public class ShadowDance extends AbstractGame {
    /** Title of the game. */
    private final static String GAME_TITLE = "SHADOW DANCE";

    /** Message displayed when the level is cleared. */
    private final static String CLEAR_MESSAGE = "CLEAR!";

    /** Message displayed when the player needs to try again. */
    private final static String TRY_AGAIN_MESSAGE = "TRY AGAIN";

    /** Instructions message displayed on the screen. */
    private final static String INSTRUCTION_MSG = "SELECT LEVELS WITH\nNUMBER KEYS\n\n   1        2        3";

    /** Message displayed on the end screen. */
    private final static String END_SCREEN_MSG = "PRESS SPACE TO RETURN TO LEVEL SELECTION.";

    /** Width of the game window. */
    private final static int WINDOW_WIDTH = 1024;

    /** Height of the game window. */
    private final static int WINDOW_HEIGHT = 768;

    /** X-coordinate for the game title's position. */
    private final static int TITLE_X = 220;

    /** Y-coordinate for the game title's position. */
    private final static int TITLE_Y = 250;

    /** Offset for X-coordinate for displaying instructions. */
    private final static int INS_X_OFFSET = 100;

    /** Offset for Y-coordinate for displaying instructions. */
    private final static int INS_Y_OFFSET = 190;

    /** X-coordinate location for the score display. */
    private final static int SCORE_LOCATION = 35;

    /** Y-coordinate for the game status's position. */
    private final static int STATUS_Y = 300;

    /** Y-coordinate for the end message's position. */
    private final static int END_Y = 500;

    /** Target score for Level 1. */
    private final static int TARGET_SCORE_1 = 150;

    /** Target score for Level 2. */
    private final static int TARGET_SCORE_2 = 400;
    /** Target score for Level 3. */
    private final static int TARGET_SCORE_3 = 350;

    /** Message to be displayed on the screen. */
    protected static String message;

    /** Duration for which the message should be displayed on screen. */
    protected static int messageTime = -1;

    /** Counter for the game frames. */
    protected static int frameCounter = 0;

    /** Total score accumulated by the player. */
    protected static int totalScore = 0;

    /** Speed at which objects move in the game. */
    protected static int motionSpeed = 2;

    /** Image for the game background. */
    private final Image BACKGROUND_IMAGE = new Image("res/background.png");

    /** Font for headings. */
    private final Font HEADING_FONT = new Font("res/FSO8BITR.ttf", 64);

    /** Font for game messages. */
    private final Font MESSAGE_FONT = new Font("res/FSO8BITR.ttf", 40);

    /** Font for score display. */
    private final Font SCORE_FONT = new Font("res/FSO8BITR.ttf", 30);

    /** Standard font for game text. */
    private final Font NORMAL_FONT = new Font("res/FSO8BITR.ttf", 24);

    /** Flag indicating if the game is in the main menu. */
    private boolean inMainMenu = true;

    /** Flag indicating if the end screen is active. */
    private boolean endScreen = false;

    /** Time until which the double score effect lasts. */
//    protected static int doubleScoreEndTime = -1;

    /** Current world or level in the game. */
    private World world;


    public ShadowDance() {
        super(WINDOW_WIDTH, WINDOW_HEIGHT, GAME_TITLE);
    }

    /**
     * The entry point for the program.
     */
    public static void main(String[] args) {
        ShadowDance game = new ShadowDance();
        game.run();
    }

    /**
     * Performs a state update.
     * Allows the game to exit when the escape key is pressed.
     */
    @Override
    protected void update(Input input) {

        // Closes the window if the player terminates the game
        if (input.wasPressed(Keys.ESCAPE)) Window.close();
        BACKGROUND_IMAGE.draw(Window.getWidth() / 2.0, Window.getHeight() / 2.0);

        // Checks if the game hasn't been started
        if (inMainMenu) {
            // Draws the game title and instruction message on screen
            HEADING_FONT.drawString(GAME_TITLE, TITLE_X, TITLE_Y);
            NORMAL_FONT.drawString(INSTRUCTION_MSG, TITLE_X + INS_X_OFFSET, TITLE_Y + INS_Y_OFFSET);

            // Checks if the key "1" was pressed
            if (input.wasPressed(Keys.NUM_1)) {
                // Load the CSV and start the game on level 1
                world = new Level1();
                inMainMenu = false;
            // Checks if the key "2" was pressed
            } else if (input.wasPressed(Keys.NUM_2)) {
                // Load the CSV and start the game on level 2
                world = new Level2();
                inMainMenu = false;
            // Checks if the key "3" was pressed
            } else if (input.wasPressed(Keys.NUM_3)) {
                // Load the CSV and start the game on level 3
                world = new Level3();
                inMainMenu = false;
            }
        }

        // Checks if the game has started and hasn't finished
        if (!inMainMenu && !endScreen) {
            // Renders the lane to draw each lane's image
            Lane.render();
            // Render notes on screen based on their current state and frame counter
            Note.render(frameCounter, motionSpeed, Window.getHeight());

            // Check if the current game world is set to "Level3" for level-specific logic and features
            if (world instanceof Level3) {
                // Creates a random enemy every 600 frames and enemy must not spawn immediately (from demo video)
                if(frameCounter % 600 == 0 && frameCounter > 0) {
                    Enemy.createRandomEnemy();
                }
                // Fires a projectile from the guardian when the left shift key is pressed
                if (input.wasPressed(Keys.LEFT_SHIFT)) {
                    Guardian.fire();
                }
                // Renders the enemies, guardian, and projectiles on the screen
                Enemy.render();
                Guardian.render();
                Projectile.render();
            }

            // Checks if the desired key was pressed and whether that lane type exists, activating desired note if these conditions fulfilled
            if (input.wasPressed(Keys.UP) && Lane.findXByLaneType("Up") > 0) {
                Note.activate("up");
            }
            if (input.wasPressed(Keys.DOWN) && Lane.findXByLaneType("Down") > 0) {
                Note.activate("down");
            }
            if (input.wasPressed(Keys.LEFT) && Lane.findXByLaneType("Left") > 0) {
                Note.activate("left");
            }
            if (input.wasPressed(Keys.RIGHT) && Lane.findXByLaneType("Right") > 0) {
                Note.activate("right");
            }
            if (input.wasPressed(Keys.SPACE) && Lane.findXByLaneType("Special") > 0) {
                Note.activate("special");
            }

            // Checks if the desired key was released and whether that lane type exists, releasing desired note if these conditions fulfilled
            if (input.wasReleased(Keys.UP) && Lane.findXByLaneType("Up") > 0) {
                Note.release("up");
            }
            if (input.wasReleased(Keys.DOWN) && Lane.findXByLaneType("Down") > 0) {
                Note.release("down");
            }
            if (input.wasReleased(Keys.LEFT) && Lane.findXByLaneType("Left") > 0) {
                Note.release("left");
            }
            if (input.wasReleased(Keys.RIGHT) && Lane.findXByLaneType("Right") > 0) {
                Note.release("right");
            }

            // Draws the total score on the top left of the screen
            SCORE_FONT.drawString("SCORE " + totalScore, SCORE_LOCATION, SCORE_LOCATION);
            // Checks if the current frame count is within the duration to show the message
            if (frameCounter <= messageTime) {
                // Draws the score message on the centre of the screen
                MESSAGE_FONT.drawString(message,
                        WINDOW_WIDTH / 2.0 - MESSAGE_FONT.getWidth(message) / 2.0, WINDOW_HEIGHT / 2.0);
            }
            for (int i = 0; i < Reward.doubles.size(); i++) {
                if (Reward.doubles.get(i) > 0 && frameCounter > Reward.doubles.get(i)) {
                    Reward.doubles.set(i, -1);
                }
            }

            // Checks if all notes have already been played or removed
            if (Note.getNotes().isEmpty()) {
                // Transition to the end screen
                endScreen = true;
            }
            frameCounter++;
        }

        // Check if the game has reached the end screen state.
        if (endScreen) {
            // Checks if the total score has reached the target score for level 1
            if (hasPlayerReachedTargetScore()) {
                // Display a clear message to the player
                HEADING_FONT.drawString(CLEAR_MESSAGE,
                        WINDOW_WIDTH / 2.0 - HEADING_FONT.getWidth(CLEAR_MESSAGE) / 2.0, STATUS_Y);
            }
            // Prompts player to try again, displaying a try again message
            else {
                HEADING_FONT.drawString(TRY_AGAIN_MESSAGE,
                        WINDOW_WIDTH / 2.0 - HEADING_FONT.getWidth(TRY_AGAIN_MESSAGE) / 2.0, STATUS_Y);
            }
            // Display an additional end screen message below the main message.
            NORMAL_FONT.drawString(END_SCREEN_MSG,
                    WINDOW_WIDTH / 2.0 - NORMAL_FONT.getWidth(END_SCREEN_MSG) / 2.0, END_Y);
        }

        // Check if we are in a level menu and if Space key is pressed to return to the main menu
        if (endScreen && input.wasPressed(Keys.SPACE)) restart();

    }

    /**
     * Restarts the game by resetting all game-related variables and clearing lanes and notes.
     */
    private void restart() {
        // Clear all lanes, notes, and enemies
        Lane.clearLanes();
        Note.clearNotes();
        Enemy.clearEnemies();
        // Set the game state to main menu
        inMainMenu = true;
        // Reset the endScreen flag
        endScreen = false;
        // Reset the frame counter, total score, and message display time
        frameCounter = 0;
        totalScore = 0;
        messageTime = 0;
        // Clear any displayed message
        message = "";
        // Reset the end time counter, doubleScore end time, and motion speed
        for (int i = 0; i < Reward.doubles.size(); i++) {
            Reward.doubles.set(i, -1);
        }
        motionSpeed = 2;
    }

    /**
     * Checks if the player has reached or exceeded the target score required for the current level.
     * @return true if the player's total score meets or exceeds the target score, false otherwise.
     */
    private boolean hasPlayerReachedTargetScore() {
        int targetScore = 0;

        // Set the target score based on the current level
        if (world instanceof Level1) {
            targetScore = TARGET_SCORE_1;
        }
        else if (world instanceof Level2) {
            targetScore = TARGET_SCORE_2;
        } else if (world instanceof Level3) {
            targetScore = TARGET_SCORE_3;
        }

        // Check if the player has met or exceeded the target score
        return totalScore >= targetScore;
    }
}