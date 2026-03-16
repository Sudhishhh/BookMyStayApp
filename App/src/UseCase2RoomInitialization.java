/**
 * Use Case 2: Basic Room Types and Static Availability.
 * Demonstrates abstraction, inheritance, and static availability variables.
 *
 * @author Sudhish
 * @version 2.0
 */
public class UseCase2RoomInitialization {

    /**
     * Application entry point for Use Case 2.
     *
     * @param args command-line arguments
     */
    public static void main(String[] args) {
        System.out.println("Welcome to Book My Stay App");
        System.out.println("Application: Book My Stay App | Version: v2.0");
        System.out.println();

        Room singleRoom = new SingleRoom();
        Room doubleRoom = new DoubleRoom();
        Room suiteRoom = new SuiteRoom();

        int singleRoomAvailability = 10;
        int doubleRoomAvailability = 6;
        int suiteRoomAvailability = 3;

        printRoomWithAvailability(singleRoom, singleRoomAvailability);
        printRoomWithAvailability(doubleRoom, doubleRoomAvailability);
        printRoomWithAvailability(suiteRoom, suiteRoomAvailability);
    }

    private static void printRoomWithAvailability(Room room, int availability) {
        System.out.println(room.getRoomDetails());
        System.out.println("Current Availability: " + availability);
        System.out.println("-----------------------------------------");
    }
}

/**
 * Abstract base class for all room types.
 */
abstract class Room {
    private final String roomType;
    private final int beds;
    private final int sizeInSqFt;
    private final double pricePerNight;

    protected Room(String roomType, int beds, int sizeInSqFt, double pricePerNight) {
        this.roomType = roomType;
        this.beds = beds;
        this.sizeInSqFt = sizeInSqFt;
        this.pricePerNight = pricePerNight;
    }

    public String getRoomDetails() {
        return "Room Type: " + roomType
                + " | Beds: " + beds
                + " | Size: " + sizeInSqFt + " sq.ft"
                + " | Price/Night: Rs." + pricePerNight;
    }
}

/**
 * Concrete room type: Single.
 */
class SingleRoom extends Room {
    public SingleRoom() {
        super("Single", 1, 180, 2500.0);
    }
}

/**
 * Concrete room type: Double.
 */
class DoubleRoom extends Room {
    public DoubleRoom() {
        super("Double", 2, 280, 4200.0);
    }
}

/**
 * Concrete room type: Suite.
 */
class SuiteRoom extends Room {
    public SuiteRoom() {
        super("Suite", 3, 450, 8000.0);
    }
}
