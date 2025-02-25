/**
 * Represents a game world, initialized from a specified CSV file.
 * The World class is responsible for loading and managing game entities
 * such as lanes and notes. It provides mechanisms to interpret a CSV file that
 * describes the layout and composition of the game world.
 */
import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;

public abstract class World {
    /**
     * Path to the CSV file used for data storage or retrieval.
     */
    protected String csvFilePath;

    /**
     * Constructs a new World instance with the specified CSV file path.
     * @param csvFilePath   the path to the CSV file that describes the world layout.
     */
    protected World(String csvFilePath){
        // Set the path for the CSV file that describes the world layout.
        this.csvFilePath = csvFilePath;
    }

    /**
     * Loads the game entities from a specified CSV file.
     * The method reads the CSV line by line, and based on its content,
     * populates the game world with lanes and notes.
     */
    protected void loadWorld() {
        // Initialize a reader for the CSV file.
        try (BufferedReader br = new BufferedReader(new FileReader(this.csvFilePath))) {
            String line;
            // Read the file line by line.
            while ((line = br.readLine()) != null) {

                // Split each line by comma.
                String[] values = line.split(",");

                // Process the values from the split line.
                String firstValue = values[0];
                String secondValue = values[1];
                int thirdValue = Integer.parseInt(values[2]);

                // Populate game entities based on the processed values.
                if ("Lane".equalsIgnoreCase(firstValue)) Lane.addLane(secondValue, thirdValue);
                else Note.addNote(firstValue, secondValue, thirdValue);

            }
        } catch (IOException e) {
            // Print any exceptions related to file operations.
            e.printStackTrace();
        }
    }
}
