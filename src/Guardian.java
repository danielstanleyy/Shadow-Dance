/**
 * Represents the Guardian entity in the game. The Guardian is responsible for
 * defending against enemies by firing projectiles at them. The class provides
 * methods for rendering the Guardian on the screen, finding the nearest enemy,
 * and firing projectiles based on the enemy's position.
 */
import bagel.Image;

public class Guardian {
    /** The guardian's image asset. */
    private static final Image image = new Image("res/guardian.png");

    /** The horizontal (x) position of the guardian on the screen. */
    private static final int x = 800;

    /** The vertical (y) position of the guardian on the screen. */
    private static final int y = 600;

    /**
     * Render the guardian on the screen.
     */
    public static void render() {
        // Draw the guardian image at its fixed position
        image.draw(x,y);
    }

    /**
     * Fires a projectile towards the nearest enemy.
     */
    public static void fire() {
        // Find the closest enemy
        Enemy nearestEnemy = findNearestEnemy();

        // Checks if an enemy exist
        if (nearestEnemy != null) {
            // Calculate the rotation and create a projectile aiming towards it
            double rotation = aims(nearestEnemy);
            Projectile.createProjectile(x,y,rotation);
        }
    }

    /**
     * Calculates the angle to aim at the given enemy.
     * @param targetEnemy   Enemy to aim at.
     * @return              The angle of rotation in radians.
     */
    private static double aims(Enemy targetEnemy) {
        // Calculate the arctangent of the vertical and horizontal difference between the guardian and target enemy
        return Math.atan2(targetEnemy.getY() - y, targetEnemy.getX() - x);
    }

    /**
     * Finds the nearest enemy to the guardian.
     * @return The closest enemy or null if there are no enemies.
     */
    private static Enemy findNearestEnemy() {
        // Initialize a variable to hold the nearest enemy found, set to null as default.
        Enemy nearestEnemy = null;
        // Initialize a variable to hold the shortest distance found; set to the largest possible value for comparison.
        double minDistance = Double.MAX_VALUE;

        // Loop through all enemies to find the one with the shortest distance to the guardian
        for (Enemy enemy : Enemy.getEnemies()) {
            // Calculate the Euclidean distance between the guardian and the current enemy.
            double distance = Math.sqrt(Math.pow((x - enemy.getX()), 2) + Math.pow((y  - enemy.getY()), 2));

            // Checks if the calculated distance is shorter than the current shortest distance
            if (distance < minDistance) {
                // Update the nearest enemy and shortest distance.
                nearestEnemy = enemy;
                minDistance = distance;
            }
        }

        return nearestEnemy;
    }
}