import java.io.*;
import java.util.*;
// UC12: Classes must implement Serializable to be saved to a file
static class HotelState implements Serializable {
    private static final long serialVersionUID = 1L;
    public Map<String, Integer> inventory;
    public List<String> bookingHistory;
    public HotelState(Map<String, Integer> inventory, List<String> bookingHistory) {
        this.inventory = inventory;
        this.bookingHistory = bookingHistory;
    }
}
public class BookMyStayApplication{
    private static final String STORAGE_FILE = "hotel_state.ser";
    // -------------------------
    // UC12: Persistence Service
    // -------------------------
    static class PersistenceService {
        public void saveState(Map<String, Integer> inventory, List<String> history) {
            try (ObjectOutputStream oos = new ObjectOutputStream(new FileOutputStream(STORAGE_FILE))) {
                HotelState state = new HotelState(inventory, history);
                oos.writeObject(state);
                System.out.println("[SAVE] System state persisted to " + STORAGE_FILE);
            } catch (IOException e) {
                System.err.println("[ERROR] Failed to save state: " + e.getMessage());
            }
        }
        public HotelState loadState() {
            File file = new File(STORAGE_FILE);
            if (!file.exists()) {
                System.out.println("[RECOVERY] No saved state found. Starting fresh.");
                return null;
            }
            try (ObjectInputStream ois = new ObjectInputStream(new FileInputStream(STORAGE_FILE))) {
                System.out.println("[RECOVERY] Restoring system state from " + STORAGE_FILE + "...");
                return (HotelState) ois.readObject();
            } catch (IOException | ClassNotFoundException e) {
                System.err.println("[ERROR] Recovery failed. Data may be corrupted.");
                return null;
            }
        }
    }
    public static void main(String[] args) {
        System.out.println("=== Use Case 12: Data Persistence & System Recovery ===\n");
        PersistenceService persistence = new PersistenceService();
        // Step 1: Attempt Recovery
        HotelState recoveredState = persistence.loadState();
        Map<String, Integer> currentInventory;
        List<String> currentHistory;
        if (recoveredState != null) {
            currentInventory = recoveredState.inventory;
            currentHistory = recoveredState.bookingHistory;
            System.out.println("Restored Inventory: " + currentInventory);
            System.out.println("Restored History Count: " + currentHistory.size());
        } else {
            // Default initialization if no file exists
            currentInventory = new HashMap<>();
            currentInventory.put("Deluxe Room", 10);
            currentHistory = new ArrayList<>();
        }
        // Step 2: Simulate System Activity
        System.out.println("\n[ACTIVITY] Processing new booking for 'John Doe'...");
        currentInventory.put("Deluxe Room", currentInventory.get("Deluxe Room") - 1);
        currentHistory.add("John Doe booked Deluxe Room on " + new Date());
        // Step 3: Persist State before Shutdown
        persistence.saveState(currentInventory, currentHistory);
        System.out.println("\nFinal State before exit:");
        System.out.println("Inventory: " + currentInventory);
        System.out.println("System Shutting Down...");
    }
}