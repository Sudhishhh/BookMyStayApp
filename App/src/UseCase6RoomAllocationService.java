import java.util.HashMap;
import java.util.HashSet;
import java.util.LinkedHashSet;
import java.util.LinkedList;
import java.util.Map;
import java.util.Queue;
import java.util.Set;

/**
 * Use Case 6: Reservation Confirmation and Room Allocation.
 * Demonstrates FIFO processing, unique room assignment, and immediate inventory sync.
 *
 * @author Sudhish
 * @version 6.0
 */
public class UseCase6RoomAllocationService {

    /**
     * Application entry point for Use Case 6.
     *
     * @param args command-line arguments
     */
    public static void main(String[] args) {
        System.out.println("Welcome to Book My Stay App");
        System.out.println("Application: Book My Stay App | Version: v6.0");
        System.out.println();

        UC6InventoryService inventoryService = new UC6InventoryService();
        UC6BookingRequestQueue requestQueue = new UC6BookingRequestQueue();
        UC6BookingService bookingService = new UC6BookingService(inventoryService);

        requestQueue.submit(new UC6ReservationRequest("REQ-001", "Aarav", "Single", 2));
        requestQueue.submit(new UC6ReservationRequest("REQ-002", "Diya", "Suite", 3));
        requestQueue.submit(new UC6ReservationRequest("REQ-003", "Vikram", "Single", 1));
        requestQueue.submit(new UC6ReservationRequest("REQ-004", "Meera", "Double", 2));
        requestQueue.submit(new UC6ReservationRequest("REQ-005", "Rohan", "Suite", 1));

        System.out.println("Initial Inventory:");
        inventoryService.displayInventory();

        System.out.println();
        System.out.println("Processing Booking Requests (FIFO):");
        while (!requestQueue.isEmpty()) {
            UC6ReservationRequest request = requestQueue.dequeue();
            bookingService.confirmReservation(request);
        }

        System.out.println();
        System.out.println("Final Inventory:");
        inventoryService.displayInventory();

        System.out.println();
        System.out.println("Allocated Room IDs by Type:");
        bookingService.displayAllocatedRoomsByType();
    }
}

/**
 * Booking request model.
 */
class UC6ReservationRequest {
    private final String requestId;
    private final String guestName;
    private final String roomType;
    private final int nights;

    public UC6ReservationRequest(String requestId, String guestName, String roomType, int nights) {
        this.requestId = requestId;
        this.guestName = guestName;
        this.roomType = roomType;
        this.nights = nights;
    }

    public String getRequestId() {
        return requestId;
    }

    public String getGuestName() {
        return guestName;
    }

    public String getRoomType() {
        return roomType;
    }

    public int getNights() {
        return nights;
    }
}

/**
 * FIFO request queue.
 */
class UC6BookingRequestQueue {
    private final Queue<UC6ReservationRequest> requestQueue;

    public UC6BookingRequestQueue() {
        this.requestQueue = new LinkedList<>();
    }

    public void submit(UC6ReservationRequest request) {
        requestQueue.offer(request);
    }

    public UC6ReservationRequest dequeue() {
        return requestQueue.poll();
    }

    public boolean isEmpty() {
        return requestQueue.isEmpty();
    }
}

/**
 * Inventory service for room counts.
 */
class UC6InventoryService {
    private final Map<String, Integer> availability;

    public UC6InventoryService() {
        availability = new HashMap<>();
        availability.put("Single", 2);
        availability.put("Double", 1);
        availability.put("Suite", 1);
    }

    public int getAvailability(String roomType) {
        return availability.getOrDefault(roomType, 0);
    }

    public void decrementAvailability(String roomType) {
        int current = getAvailability(roomType);
        if (current > 0) {
            availability.put(roomType, current - 1);
        }
    }

    public void displayInventory() {
        System.out.println("Single: " + getAvailability("Single"));
        System.out.println("Double: " + getAvailability("Double"));
        System.out.println("Suite: " + getAvailability("Suite"));
    }
}

/**
 * Booking processor that confirms reservations and allocates unique room IDs.
 */
class UC6BookingService {
    private final UC6InventoryService inventoryService;
    private final Set<String> allocatedRoomIds;
    private final Map<String, Set<String>> allocatedRoomsByType;
    private final Map<String, Integer> roomTypeCounters;

    public UC6BookingService(UC6InventoryService inventoryService) {
        this.inventoryService = inventoryService;
        this.allocatedRoomIds = new HashSet<>();
        this.allocatedRoomsByType = new HashMap<>();
        this.roomTypeCounters = new HashMap<>();
    }

    public void confirmReservation(UC6ReservationRequest request) {
        String roomType = request.getRoomType();
        int available = inventoryService.getAvailability(roomType);

        if (available <= 0) {
            System.out.println("Rejected " + request.getRequestId()
                    + " for " + request.getGuestName()
                    + " (" + roomType + "): No availability");
            return;
        }

        String allocatedRoomId = generateUniqueRoomId(roomType);

        allocatedRoomIds.add(allocatedRoomId);
        allocatedRoomsByType.computeIfAbsent(roomType, key -> new LinkedHashSet<>()).add(allocatedRoomId);
        inventoryService.decrementAvailability(roomType);

        System.out.println("Confirmed " + request.getRequestId()
                + " | Guest: " + request.getGuestName()
                + " | Room Type: " + roomType
                + " | Nights: " + request.getNights()
                + " | Allocated Room ID: " + allocatedRoomId);
    }

    private String generateUniqueRoomId(String roomType) {
        String prefix = roomType.substring(0, Math.min(2, roomType.length())).toUpperCase();

        while (true) {
            int nextNumber = roomTypeCounters.getOrDefault(roomType, 0) + 1;
            roomTypeCounters.put(roomType, nextNumber);
            String candidate = prefix + String.format("-%03d", nextNumber);

            if (!allocatedRoomIds.contains(candidate)) {
                return candidate;
            }
        }
    }

    public void displayAllocatedRoomsByType() {
        if (allocatedRoomsByType.isEmpty()) {
            System.out.println("No rooms allocated yet.");
            return;
        }

        for (Map.Entry<String, Set<String>> entry : allocatedRoomsByType.entrySet()) {
            System.out.println(entry.getKey() + " -> " + entry.getValue());
        }
    }
}
