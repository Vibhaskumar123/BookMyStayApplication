import java.util.*;
public class BookMyStayApplication{
    // -------------------------
    // UC11: Thread-Safe Inventory
    // -------------------------
    static class ThreadSafeInventory {
        private final Map<String, Integer> inventory = new HashMap<>();
        public ThreadSafeInventory() {
            inventory.put("Luxury Suite", 1); // Only ONE room available for simulation
        }
        // The 'synchronized' keyword prevents race conditions
        public synchronized boolean bookRoom(String roomType, String guestName) {
            int available = inventory.getOrDefault(roomType, 0);
            if (available > 0) {
                System.out.println("[PROCESSING] " + guestName + " is attempting to book " + roomType);
                // Simulate a slight delay in processing to highlight concurrency issues
                try { Thread.sleep(100); } catch (InterruptedException e) {}
                inventory.put(roomType, available - 1);
                System.out.println("[SUCCESS] Room confirmed for " + guestName);
                return true;
            } else {
                System.out.println("[FAILED] " + guestName + " found no availability for " + roomType);
                return false;
            }
        }
        public void displayFinalStatus() {
            System.out.println("\nFinal Inventory State: " + inventory);
        }
    }
    // -------------------------
    // UC11: Booking Task (Runnable)
    // -------------------------
    static class BookingTask implements Runnable {
        private ThreadSafeInventory inventory;
        private String guestName;
        private String roomType;
        public BookingTask(ThreadSafeInventory inventory, String guestName, String roomType) {
            this.inventory = inventory;
            this.guestName = guestName;
            this.roomType = roomType;
        }
        @Override
        public void run() {
            inventory.bookRoom(roomType, guestName);
        }
    }
    public static void main(String[] args) {
        System.out.println("=== Use Case 11: Concurrent Booking Simulation ===\n");
        System.out.println("Scenario: 3 Guests competing for 1 Luxury Suite simultaneously.\n");
        ThreadSafeInventory inventory = new ThreadSafeInventory();
        // Create multiple threads representing concurrent users
        Thread t1 = new Thread(new BookingTask(inventory, "Alice", "Luxury Suite"));
        Thread t2 = new Thread(new BookingTask(inventory, "Bob", "Luxury Suite"));
        Thread t3 = new Thread(new BookingTask(inventory, "Charlie", "Luxury Suite"));
        // Start all threads at nearly the same time
        t1.start();
        t2.start();
        t3.start();
        // Wait for all threads to finish
        try {
            t1.join();
            t2.join();
            t3.join();
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
        inventory.displayFinalStatus();
    }
}