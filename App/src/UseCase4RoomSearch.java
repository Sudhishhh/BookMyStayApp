import java.util.HashMap;
import java.util.LinkedHashMap;
import java.util.Map;

/**
 * Use Case 4: Room Search and Availability Check.
 * Demonstrates read-only search behavior without inventory mutation.
 *
 * @author Sudhish
 * @version 4.0
 */
public class UseCase4RoomSearch {

    /**
     * Application entry point for Use Case 4.
     *
     * @param args command-line arguments
     */
    public static void main(String[] args) {
        System.out.println("Welcome to Book My Stay App");
        System.out.println("Application: Book My Stay App | Version: v4.0");
        System.out.println();

        CentralRoomInventory inventory = new CentralRoomInventory();
        Map<String, HotelRoom> roomCatalog = buildRoomCatalog();
        RoomSearchService searchService = new RoomSearchService(inventory, roomCatalog);

        System.out.println("Available Room Options:");
        searchService.displayAvailableRooms();
    }

    private static Map<String, HotelRoom> buildRoomCatalog() {
        Map<String, HotelRoom> roomCatalog = new LinkedHashMap<>();
        roomCatalog.put("Single", new SingleHotelRoom());
        roomCatalog.put("Double", new DoubleHotelRoom());
        roomCatalog.put("Suite", new SuiteHotelRoom());
        return roomCatalog;
    }
}

/**
 * Abstract room domain model.
 */
abstract class HotelRoom {
    private final String roomType;
    private final int beds;
    private final double pricePerNight;

    protected HotelRoom(String roomType, int beds, double pricePerNight) {
        this.roomType = roomType;
        this.beds = beds;
        this.pricePerNight = pricePerNight;
    }

    public String getRoomType() {
        return roomType;
    }

    public String getDisplayDetails() {
        return "Type: " + roomType + " | Beds: " + beds + " | Price/Night: Rs." + pricePerNight;
    }
}

class SingleHotelRoom extends HotelRoom {
    public SingleHotelRoom() {
        super("Single", 1, 2500.0);
    }
}

class DoubleHotelRoom extends HotelRoom {
    public DoubleHotelRoom() {
        super("Double", 2, 4200.0);
    }
}

class SuiteHotelRoom extends HotelRoom {
    public SuiteHotelRoom() {
        super("Suite", 3, 8000.0);
    }
}

/**
 * Centralized inventory state holder.
 */
class CentralRoomInventory {
    private final Map<String, Integer> availabilityByType;

    public CentralRoomInventory() {
        availabilityByType = new HashMap<>();
        availabilityByType.put("Single", 8);
        availabilityByType.put("Double", 0);
        availabilityByType.put("Suite", 4);
    }

    public int getAvailability(String roomType) {
        return availabilityByType.getOrDefault(roomType, 0);
    }
}

/**
 * Read-only room search service.
 */
class RoomSearchService {
    private final CentralRoomInventory inventory;
    private final Map<String, HotelRoom> roomCatalog;

    public RoomSearchService(CentralRoomInventory inventory, Map<String, HotelRoom> roomCatalog) {
        this.inventory = inventory;
        this.roomCatalog = roomCatalog;
    }

    public void displayAvailableRooms() {
        boolean found = false;

        for (Map.Entry<String, HotelRoom> entry : roomCatalog.entrySet()) {
            String roomType = entry.getKey();
            HotelRoom room = entry.getValue();
            int availableCount = inventory.getAvailability(roomType);

            if (availableCount > 0) {
                found = true;
                System.out.println(room.getDisplayDetails() + " | Available: " + availableCount);
            }
        }

        if (!found) {
            System.out.println("No rooms are currently available.");
        }
    }
}
