import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * Use Case 7: Add-On Service Selection.
 * Demonstrates optional service attachment without changing core booking logic.
 *
 * @author Sudhish
 * @version 7.0
 */
public class UseCase7AddOnServiceSelection {

    /**
     * Application entry point for Use Case 7.
     *
     * @param args command-line arguments
     */
    public static void main(String[] args) {
        System.out.println("Welcome to Book My Stay App");
        System.out.println("Application: Book My Stay App | Version: v7.0");
        System.out.println();

        UC7AddOnServiceManager addOnManager = new UC7AddOnServiceManager();

        String reservationId1 = "RES-1001";
        String reservationId2 = "RES-1002";

        addOnManager.addService(reservationId1, new UC7AddOnService("Breakfast", 500.0));
        addOnManager.addService(reservationId1, new UC7AddOnService("Airport Pickup", 1200.0));
        addOnManager.addService(reservationId1, new UC7AddOnService("Late Checkout", 700.0));

        addOnManager.addService(reservationId2, new UC7AddOnService("Spa Access", 1800.0));
        addOnManager.addService(reservationId2, new UC7AddOnService("Dinner Package", 1500.0));

        System.out.println("Selected Add-On Services:");
        addOnManager.displayServicesForReservation(reservationId1);
        addOnManager.displayServicesForReservation(reservationId2);

        System.out.println();
        System.out.println("Total Additional Costs:");
        System.out.println(reservationId1 + " -> Rs." + addOnManager.calculateTotalAdditionalCost(reservationId1));
        System.out.println(reservationId2 + " -> Rs." + addOnManager.calculateTotalAdditionalCost(reservationId2));

        System.out.println();
        System.out.println("Note: Add-on services are managed independently. Booking allocation and inventory state are unchanged.");
    }
}

/**
 * Represents an optional service that can be attached to a reservation.
 */
class UC7AddOnService {
    private final String serviceName;
    private final double cost;

    public UC7AddOnService(String serviceName, double cost) {
        this.serviceName = serviceName;
        this.cost = cost;
    }

    public String getServiceName() {
        return serviceName;
    }

    public double getCost() {
        return cost;
    }
}

/**
 * Manages reservation-to-services association.
 */
class UC7AddOnServiceManager {
    private final Map<String, List<UC7AddOnService>> servicesByReservation;

    public UC7AddOnServiceManager() {
        this.servicesByReservation = new HashMap<>();
    }

    /**
     * Attaches a service to a reservation.
     *
     * @param reservationId reservation identifier
     * @param service add-on service
     */
    public void addService(String reservationId, UC7AddOnService service) {
        servicesByReservation.computeIfAbsent(reservationId, key -> new ArrayList<>()).add(service);
    }

    /**
     * Gets selected services for a reservation.
     *
     * @param reservationId reservation identifier
     * @return list of services, empty if none found
     */
    public List<UC7AddOnService> getServices(String reservationId) {
        return servicesByReservation.getOrDefault(reservationId, new ArrayList<>());
    }

    /**
     * Calculates total additional service cost for a reservation.
     *
     * @param reservationId reservation identifier
     * @return total add-on amount
     */
    public double calculateTotalAdditionalCost(String reservationId) {
        double total = 0.0;
        for (UC7AddOnService service : getServices(reservationId)) {
            total += service.getCost();
        }
        return total;
    }

    /**
     * Prints selected services for a reservation.
     *
     * @param reservationId reservation identifier
     */
    public void displayServicesForReservation(String reservationId) {
        List<UC7AddOnService> services = getServices(reservationId);
        System.out.println("Reservation " + reservationId + ":");

        if (services.isEmpty()) {
            System.out.println("  No add-on services selected.");
            return;
        }

        for (UC7AddOnService service : services) {
            System.out.println("  - " + service.getServiceName() + " (Rs." + service.getCost() + ")");
        }
    }
}
