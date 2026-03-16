import java.util.LinkedList;
import java.util.Queue;

/**
 * Use Case 5: Booking Request (First-Come-First-Served).
 * Demonstrates fair booking request intake using a FIFO queue.
 *
 * @author Sudhish
 * @version 5.0
 */
public class UseCase5BookingRequestQueue {

    /**
     * Application entry point for Use Case 5.
     *
     * @param args command-line arguments
     */
    public static void main(String[] args) {
        System.out.println("Welcome to Book My Stay App");
        System.out.println("Application: Book My Stay App | Version: v5.0");
        System.out.println();

        BookingRequestQueue bookingQueue = new BookingRequestQueue();

        bookingQueue.submitRequest(new Reservation("R001", "Aarav", "Single", 2));
        bookingQueue.submitRequest(new Reservation("R002", "Diya", "Suite", 3));
        bookingQueue.submitRequest(new Reservation("R003", "Vikram", "Double", 1));
        bookingQueue.submitRequest(new Reservation("R004", "Meera", "Single", 4));

        System.out.println();
        System.out.println("Current Booking Request Queue (FIFO Order):");
        bookingQueue.displayPendingRequests();

        System.out.println();
        System.out.println("Note: Requests are queued only. No room allocation or inventory update is performed in this use case.");
    }
}

/**
 * Represents a guest booking intent.
 */
class Reservation {
    private final String requestId;
    private final String guestName;
    private final String roomType;
    private final int nights;

    public Reservation(String requestId, String guestName, String roomType, int nights) {
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
 * Queue manager for booking requests.
 */
class BookingRequestQueue {
    private final Queue<Reservation> requestQueue;

    public BookingRequestQueue() {
        this.requestQueue = new LinkedList<>();
    }

    /**
     * Adds a booking request to the queue in arrival order.
     *
     * @param reservation booking request
     */
    public void submitRequest(Reservation reservation) {
        requestQueue.offer(reservation);
        System.out.println("Request accepted: " + reservation.getRequestId() + " for " + reservation.getGuestName());
    }

    /**
     * Displays queued requests without removing them.
     */
    public void displayPendingRequests() {
        if (requestQueue.isEmpty()) {
            System.out.println("No pending booking requests.");
            return;
        }

        int position = 1;
        for (Reservation reservation : requestQueue) {
            System.out.println(
                    "Position " + position
                            + " | RequestId: " + reservation.getRequestId()
                            + " | Guest: " + reservation.getGuestName()
                            + " | Room Type: " + reservation.getRoomType()
                            + " | Nights: " + reservation.getNights());
            position++;
        }
    }
}
