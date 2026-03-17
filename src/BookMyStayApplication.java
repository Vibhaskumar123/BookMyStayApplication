import java.util.*;
public class BookMyStayApplication{
    // -------------------------
    // UC3 & UC10: Inventory with Rollback Support
    // -------------------------
    static class RoomInventory {
        private Map<String, Integer> inventory = new HashMap<>();
        public RoomInventory() {
            inventory.put("Single Room", 5);
            inventory.put("Double Room", 3);
        }
        public void decreaseAvailability(String roomType) {
            inventory.put(roomType, inventory.get(roomType) - 1);
        }
        // UC10: Increment inventory during cancellation
        public void increaseAvailability(String roomType) {
            inventory.put(roomType, inventory.getOrDefault(roomType, 0) + 1);
            System.out.println("Inventory Restored: " + roomType + " count is now " + inventory.get(roomType));
        }
        public void displayInventory() {
            System.out.println("Current Inventory: " + inventory);
        }
    }
    // -------------------------
    // UC10: Cancellation & Rollback Service
    // -------------------------
    static class CancellationService {
        // UC10: Stack to track released room IDs (LIFO Rollback)
        private Stack<String> releasedRoomIds = new Stack<>();
        private Map<String, String> activeBookings = new HashMap<>(); // Guest Name -> Room ID
        public void addConfirmedBooking(String guestName, String roomId) {
            activeBookings.put(guestName, roomId);
        }
        public void cancelBooking(String guestName, String roomType, RoomInventory inventory) {
            System.out.println("\n--- Initiating Cancellation for: " + guestName + " ---");
            // Step 1: Validate existence
            if (!activeBookings.containsKey(guestName)) {
                System.out.println("Error: No active booking found for guest " + guestName);
                return;
            }
            // Step 2: Record Room ID in rollback structure
            String roomId = activeBookings.remove(guestName);
            releasedRoomIds.push(roomId);
            System.out.println("Validation Passed: Booking " + roomId + " found.");
            // Step 3: Inventory Restoration
            inventory.increaseAvailability(roomType);
            // Step 4: Final State Update
            System.out.println("SUCCESS: Cancellation complete. Room " + roomId + " is now back in the pool.");
        }
        public void displayReleasedRooms() {
            System.out.println("Recently Released Room IDs (Stack): " + releasedRoomIds);
        }
    }
    public static void main(String[] args) {
        System.out.println("=== Book My Stay: Booking Cancellation & Rollback ===\n");
        RoomInventory inventory = new RoomInventory();
        CancellationService cancelService = new CancellationService();
        // Setup: Simulating existing bookings
        cancelService.addConfirmedBooking("Alice", "SIN-101");
        cancelService.addConfirmedBooking("Bob", "DOU-201");
        inventory.decreaseAvailability("Single Room");
        inventory.decreaseAvailability("Double Room");
        inventory.displayInventory();
        // Scenario 1: Successful Cancellation
        cancelService.cancelBooking("Alice", "Single Room", inventory);
        // Scenario 2: Duplicate/Invalid Cancellation (Prevention)
        cancelService.cancelBooking("Alice", "Single Room", inventory);
        // Scenario 3: Another Successful Cancellation
        cancelService.cancelBooking("Bob", "Double Room", inventory);
        System.out.println("\n--- Final System Audit ---");
        inventory.displayInventory();
        cancelService.displayReleasedRooms();
    }
}