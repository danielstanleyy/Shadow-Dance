/**
 * Represents an enemy in the game that moves horizontally on the screen and checks for collisions with notes.
 * Enemies can reverse their initialDirection when they reach specified x-coordinate boundaries.
 * The collisions between enemies and notes result in the removal of the collided notes, except special and hold notes.
 */
import bagel.Image;
import java.util.ArrayList;
import java.util.List;
import java.util.Random;

public class Enemy {
    /** A list containing all instances of enemies. */
    private static final List<Enemy> enemies = new ArrayList<>();

    /** The image representation of an enemy. */
    private static final Image image = new Image("res/enemy.png");

    /** The constant speed at which enemies move. */
    private static final int ENEMY_SPEED = 1;

    /** The distance threshold for checking collisions between enemies and notes. */
    private static final int COLLISION_DISTANCE = 104;

    /** Upper bound for generating random x-coordinates for enemies. */
    private static final int X_RANDOM = 801;

    /** Upper bound for generating random y-coordinates for enemies. */
    private static final int Y_RANDOM = 401;

    /** Starting coordinate offset used for generating random enemy positions. */
    private static final int START_COORDINATE = 100;

    /** Minimum x-coordinate boundary at which the enemy changes direction. */
    private static final int MIN_X = 100;

    /** Maximum x-coordinate boundary at which the enemy changes direction. */
    private static final int MAX_X = 900;

    /** The y-coordinate of this enemy. This value remains constant. */
    private final int y;

    /** The x-coordinate of this enemy. This value changes as the enemy moves. */
    private int x;

    /** Represents the horizontal speed of the enemy, which can be 1 or -1. */
    private int dx;

    /** Default direction of the enemy, initialized to 1. */
    private static final int initialDirection = 1;

    private Enemy(int x, int y, int dx) {
        this.x = x;
        this.y = y;
        this.dx = dx;
    }

    /**
     * Creates a new enemy with random x, y coordinates and direction.
     * The generated enemy is added to the static list of enemies.
     */
    public static void createRandomEnemy() {
        Random random = new Random();
        // Random x between 100 and 900
        int startX = random.nextInt(X_RANDOM) + START_COORDINATE;
        // Random y between 100 and 500
        int startY = random.nextInt(Y_RANDOM) + START_COORDINATE;
        // Random initialDirection (1 or -1)
        int startDX = (random.nextBoolean()) ? initialDirection : -initialDirection;
        enemies.add(new Enemy(startX, startY, startDX));
    }
    /**
     * Renders all enemies on the screen and updates their state.
     */
    public static void render() {
        // Draws each enemy and updates it
        for (Enemy enemy : enemies) Enemy.image.draw(enemy.x, enemy.y);
        Enemy.update();
    }
    /**
     * Updates the state of all enemies, moving them and checking for collisions.
     */
    private static void update() {
        // Move each enemy and check for collisions with notes
        for (Enemy enemy : enemies) {
            enemy.move();
            enemy.checkCollisions();
        }
    }
    /**
     * Moves the enemy horizontally. If the enemy reaches the boundaries, its direction is reversed.
     */
    private void move() {
        // Adjust the x-coordinate
        x += dx * ENEMY_SPEED;
        // Reverse direction if boundaries are reached
        if (x <= MIN_X || x >= MAX_X) {
            dx *= -initialDirection;
        }
    }
    /**
     * Checks if this enemy has collided with any notes.
     * If a collision with a normal note is detected, the note is marked for removal.
     */
    private void checkCollisions() {
        List<Note> notesToRemove = new ArrayList<>();
        for (Note note : Note.getNotes()) {

            // Calculate the Euclidean distance between the enemy's position and the note's position
            double distance = Math.sqrt(Math.pow((x - note.getX()), 2) + Math.pow((y - note.getY()), 2));
            // Check if collision distance is breached and the note is not a special type or hold note
            if (distance <= COLLISION_DISTANCE && !(note instanceof SpecialNote) && !(note instanceof HoldNote)) {
                notesToRemove.add(note);
            }
        }
        // Remove collided notes
        for (Note note : notesToRemove) {
            Note.getNotes().remove(note);
        }
    }
    /**
     * Returns a list containing all existing enemies.
     * @return List of enemies.
     */
    public static List<Enemy> getEnemies() {
        return enemies;
    }

    /**
     * Clears the list of enemies.
     */
    public static void clearEnemies() {
        enemies.clear();
    }

    /**
     * Gets the x-coordinate of this enemy.
     * @return x-coordinate.
     */
    public int getX() {
        return x;
    }

    /**
     * Gets the y-coordinate of this enemy.
     * @return y-coordinate.
     */
    public int getY() {
        return y;
    }
}
