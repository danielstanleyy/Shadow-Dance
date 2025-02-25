/**
 * The Projectile class represents a projectile (e.g., an arrow) that can be fired by the guardian.
 * It can move in a direction, check collisions with enemies, and be rendered on the screen.
 */
import bagel.DrawOptions;
import bagel.Image;
import bagel.Window;
import java.util.ArrayList;
import java.util.List;

public class Projectile {
    /** List to store all the projectiles in the game */
    private static final List<Projectile> projectiles = new ArrayList<>();

    /** Image to be used for the projectile */
    private static final Image image = new Image("res/arrow.png");

    /** Constant speed at which the projectile moves */
    private static final double PROJECTILE_SPEED = 6.0;
    private static final int COLLISION_DISTANCE = 62;

    /** Angle at which the projectile is moving */
    private final double rotation;

    /** X and Y coordinates of the projectile */
    private double x;
    private double y;

    /**
     * Initializes a new Projectile with the specified starting coordinates and rotation angle.
     * @param x         Initial x-coordinate of the projectile.
     * @param y         Initial y-coordinate of the projectile.
     * @param rotation  Rotation angle of the projectile in radians, determining its direction.
     */
    private Projectile(int x, int y, double rotation) {
        // Set the x and y coordinate, and the rotation angle of the projectile
        this.x = x;
        this.y = y;
        this.rotation = rotation;
    }

    /**
     * Creates and adds a new projectile to the game.
     * @param x         Starting x-coordinate of the projectile
     * @param y         Starting y-coordinate of the projectile
     * @param rotation  Angle of movement for the projectile
     */
    public static void createProjectile(int x,int y,double rotation) {
        // Create a new projectile and add it to the list
        Projectile projectile = new Projectile(x,y, rotation);
        projectiles.add(projectile);
    }

    /**
     * Renders all the projectiles on the screen.
     */
    public static void render() {
        for (Projectile projectile:projectiles) {
            // Draw each projectile with its specific rotation
            DrawOptions options = new DrawOptions().setRotation(projectile.rotation);
            Projectile.image.draw(projectile.x, projectile.y,options);
        }
        // Update the state of each projectile
        Projectile.update();
    }

    /**
     * Updates the position of each projectile and checks for collisions.
     */
    private static void update() {
        // List to store projectiles that need to be removed
        List<Projectile> projectilesToRemove = new ArrayList<>();

        for (Projectile projectile : projectiles) {
            // Update the projectile's position based on its speed and direction
            double newX = projectile.x + PROJECTILE_SPEED * Math.cos(projectile.rotation);
            double newY = projectile.y + PROJECTILE_SPEED * Math.sin(projectile.rotation);

            // If the projectile collides with an enemy or goes off the screen, mark it for removal
            if (handleProjectileCollisions(newX, newY)) {
                projectilesToRemove.add(projectile);
            } else {
                // Otherwise, update the projectile's position
                projectile.x = newX;
                projectile.y = newY;
            }
        }
        // Remove all the projectiles that were marked for removal
        projectiles.removeAll(projectilesToRemove);
    }

    /**
     * Checks if a projectile has collided with an enemy or gone off the screen.
     * @param newX  Updated x-coordinate of the projectile
     * @param newY  Updated y-coordinate of the projectile
     * @return true if there's a collision or the projectile is out of bounds, false otherwise
     */
    private static boolean handleProjectileCollisions(double newX,double newY) {
        for (Enemy enemy : Enemy.getEnemies()) {
            // If the distance between the projectile and an enemy is less than or equal to 62, it's a collision
            if (Math.sqrt(Math.pow((newX - enemy.getX()), 2) + Math.pow((newY - enemy.getY()), 2)) <= COLLISION_DISTANCE) {
                Enemy.getEnemies().remove(enemy);
                return true;
            }
        }
        // Check if the projectile has gone off the screen
        return newX < 0 || newX > Window.getWidth() || newY < 0 || newY > Window.getHeight();
    }
}
