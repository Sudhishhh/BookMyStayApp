import java.util.HashMap;
import java.util.Map;

/**
 * Use Case 3: Centralized Room Inventory Management.
 * Demonstrates centralized availability using HashMap.
 *
 * @author Sudhish
 * @version 3.0
 */
public class UseCase3InventorySetup {

    /**
     * Application entry point for Use Case 3.
     *
     * @param args command-line arguments
     */
    public static void main(String[] args) {
        System.out.println("Welcome to Book My Stay App");
        System.out.println("Application: Book My Stay App | Version: v3.0");
        System.out.println();

        RoomInventory inventory = new RoomInventory();

        System.out.println("Initial Room Inventory:");
        inventory.displayInventory();

        System.out.println();
        System.out.println("Updating inventory (Single: -2, Suite: +1)...");
        inventory.updateAvailability("Single", inventory.getAvailability("Single") - 2);
        inventory.updateAvailability("Suite", inventory.getAvailability("Suite") + 1);

        System.out.println();
        System.out.println("Updated Room Inventory:");
        inventory.displayInventory();
    }
}

/**
 * Centralized inventory manager for room availability.
 */
class RoomInventory {
    private final Map<String, Integer> availabilityMap;

    /**
     * Initializes room availability.
     */
    public RoomInventory() {
        this.availabilityMap = new HashMap<>();
        availabilityMap.put("Single", 10);
        availabilityMap.put("Double", 6);
        availabilityMap.put("Suite", 3);
    }

    /**
     * Returns current availability for a room type.
     *
     * @param roomType room category key
     * @return available count, or 0 if room type does not exist
     */
    public int getAvailability(String roomType) {
        return availabilityMap.getOrDefault(roomType, 0);
    }

    /**
     * Updates availability for an existing room type.
     *
     * @param roomType room category key
     * @param newCount new available count
     */
    public void updateAvailability(String roomType, int newCount) {
        if (!availabilityMap.containsKey(roomType)) {
            System.out.println("Room type not found: " + roomType);
            return;
        }

        if (newCount < 0) {
            System.out.println("Invalid availability for " + roomType + ": " + newCount);
            return;
        }

        availabilityMap.put(roomType, newCount);
    }

    /**
     * Prints current inventory state.
     */
    public void displayInventory() {
        for (Map.Entry<String, Integer> entry : availabilityMap.entrySet()) {
            System.out.println(entry.getKey() + " Rooms Available: " + entry.getValue());
        }
    }
}
