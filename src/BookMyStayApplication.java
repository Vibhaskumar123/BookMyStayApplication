import java.util.*;
public class BookMyStayApplication{
    // -------------------------
    // Reservation (Minimal for UC7)
    // -------------------------
    static class Reservation {
        private static int counter = 1;
        private String reservationId;
        private String guestName;
        public Reservation(String guestName) {
            this.guestName = guestName;
            this.reservationId = "RES-" + counter++;
        }
        public String getReservationId() {
            return reservationId;
        }
        public String getGuestName() {
            return guestName;
        }
        public void display() {
            System.out.println(reservationId + " | Guest: " + guestName);
        }
    }
    // -------------------------
    // Add-On Service
    // -------------------------
    static class AddOnService {
        private String name;
        private double cost;
        public AddOnService(String name, double cost) {
            this.name = name;
            this.cost = cost;
        }
        public double getCost() {
            return cost;
        }
        public void display() {
            System.out.println(name + " ($" + cost + ")");
        }
    }
    // -------------------------
    // Add-On Service Manager
    // -------------------------
    static class AddOnServiceManager {
        // Reservation ID → List of Services
        private Map<String, List<AddOnService>> serviceMap = new HashMap<>();
        // Add service
        public void addService(String reservationId, AddOnService service) {
            serviceMap
                    .computeIfAbsent(reservationId, k -> new ArrayList<>())
                    .add(service);
            System.out.println("Added service to " + reservationId);
        }
        // Display services
        public void displayServices(String reservationId) {
            System.out.println("\nServices for " + reservationId + ":");
            List<AddOnService> services = serviceMap.get(reservationId);
            if (services == null || services.isEmpty()) {
                System.out.println("No services selected.");
                return;
            }
            for (AddOnService s : services) {
                s.display();
            }
        }
        // Calculate total cost
        public double calculateTotalCost(String reservationId) {
            List<AddOnService> services = serviceMap.get(reservationId);
            double total = 0;
            if (services != null) {
                for (AddOnService s : services) {
                    total += s.getCost();
                }
            }
            return total;
        }
    }
    // -------------------------
    // Main Method
    // -------------------------
    public static void main(String[] args) {
        System.out.println("=== Use Case 7: Add-On Service Selection ===\n");
        // Create reservation
        Reservation r1 = new Reservation("Alice");
        r1.display();
        // Create services
        AddOnService wifi = new AddOnService("WiFi", 10);
        AddOnService breakfast = new AddOnService("Breakfast", 20);
        AddOnService spa = new AddOnService("Spa", 50);
        // Manager
        AddOnServiceManager manager = new AddOnServiceManager();
        // Add services
        manager.addService(r1.getReservationId(), wifi);
        manager.addService(r1.getReservationId(), breakfast);
        manager.addService(r1.getReservationId(), spa);
        // Display services
        manager.displayServices(r1.getReservationId());
        // Total cost
        double total = manager.calculateTotalCost(r1.getReservationId());
        System.out.println("\nTotal Add-On Cost: $" + total);
    }
}