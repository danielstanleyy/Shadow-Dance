/**
 * Represents a lane in the game, determining its visual representation and position.
 * Lanes host notes that players interact with based on their musical timing.
 */
import bagel.Image;
import java.util.ArrayList;
import java.util.List;

public abstract class Lane {
    /**
     * A static list containing all the lanes in the game
     */
    private static final List<Lane> lanes = new ArrayList<>();
    /**
     * The x-coordinate of the lane
     */
    private final int x;
    /**
     * The type of lane
     */
    protected final String type;
    /**
     * Image objects for different type of lanes
     */
    protected Image image;
    /**
     * The constant y-coordinate of all lanes
     */
    private static final int HEIGHT = 384;
    /**
     * An identifier representing no matching lane found
     */
    private static final int NOT_FOUND = -1;

    /**
     * Constructs a new Lane instance with the specified type and x-coordinate.
     * @param type  The type or category of the lane.
     * @param x     The x-coordinate where the lane should be positioned.
     */
    protected Lane(String type, int x) {
        // Initialize lane's type and x-coordinate.
        this.type = type;
        this.x = x;
    }

    /**
     * Adds a new lane of type normal or special lane to the static lanes list.
     * @param type  The type or category of the lane.
     * @param x     The x-coordinate where the lane should be positioned.
     */
    public static void addLane(String type, int x) {
        // Checks if the lane has a type of "special" or "normal"
        if(type.equalsIgnoreCase("special")) {
            // Create a new SpecialLane with specified type and x-coordinate, and add to lanes list.
            lanes.add(new SpecialLane(type, x));
        }
        else {
            // Create a new NormalLane with specified type and x-coordinate, and add to lanes list.
            lanes.add(new NormalLane(type, x));
        }
    }

    /**
     * Retrieves the x-coordinate of the first lane that matches the specified lane type.
     * @param targetLaneType    The type or category of the lane to search for.
     * @return                  The x-coordinate of the matching lane. Returns -1 if no match is found.
     */
    public static int findXByLaneType(String targetLaneType) {
        // Iterate over the lanes list to find a lane with the given type.
        for (Lane lane : lanes) {
            if (lane.type.equalsIgnoreCase(targetLaneType)) return lane.x;
        }
        // Return -1 as a default if no matching lane is found.
        return NOT_FOUND;
    }

    /**
     * Renders all lanes present in the static lanes list.
     * Each lane's associated image is drawn at its specific x-coordinate and the constant HEIGHT.
     */
    public static void render() {
        // Iterate over the lanes list and draw each lane's image.
        for (Lane lane : lanes) lane.image.draw(lane.x, HEIGHT);
    }

    /**
     * Clears all lanes from the static lanes list, effectively resetting it.
     */
    public static void clearLanes() {
        // Clear the lanes list.
        lanes.clear();
    }
    /**
     * Assigns a visual representation to the lane.
     * Subclasses will provide a concrete implementation for this method.
     */
    protected abstract void setTexture();
}