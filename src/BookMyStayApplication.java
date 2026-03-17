import java.util.*;
// UC9: Custom Exception to represent domain-specific booking errors
class BookingException extends Exception {
    public BookingException(String message) {
        super(message);
    }
}
public class UseCase9ErrorHandlingValidation {
    // -------------------------
    // UC3 & UC9: Room Inventory with State Protection
    // -------------------------
    static class RoomInventory {
        private HashMap<String, Integer> inventory;
        public RoomInventory() {
            inventory = new HashMap<>();
            inventory.put("Single Room", 1); // Limited stock to test failures
            inventory.put("Double Room", 1);
        }
        public boolean exists(String roomType) {
            return inventory.containsKey(roomType);
        }
        public int getAvailability(String roomType) {
            return inventory.getOrDefault(roomType, 0);
        }
        // UC9: Method ensures inventory never goes negative
        public void decreaseAvailability(String roomType) throws BookingException {
            int count = inventory.getOrDefault(roomType, 0);
            if (count <= 0) {
                throw new BookingException("Inconsistency Error: No inventory left for " + roomType);
            }
            inventory.put(roomType, count - 1);
        }
    }
    // -------------------------
    // UC9: Validator (Fail-Fast Logic)
    // -------------------------
    static class BookingValidator {
        public static void validateRequest(String guestName, String roomType, RoomInventory inventory) 
                throws BookingException {
            // Validate Guest Input
            if (guestName == null || guestName.trim().isEmpty()) {
                throw new BookingException("Validation Failed: Guest name cannot be empty.");
            }
            // Validate System Constraints
            if (!inventory.exists(roomType)) {
                throw new BookingException("Validation Failed: Room type '" + roomType + "' does not exist.");
            }
            if (inventory.getAvailability(roomType) <= 0) {
                throw new BookingException("Validation Failed: " + roomType + " is currently sold out.");
            }
        }
    }
    // -------------------------
    // UC6 & UC9: Booking Service with Error Handling
    // -------------------------
    static class BookingService {
        private Set<String> usedRoomIds = new HashSet<>();
        public void processSingleBooking(String guestName, String roomType, RoomInventory inventory) {
            System.out.println(">>> Request: " + guestName + " for " + roomType);
            try {
                // Step 1: Validate Input (Fail-Fast)
                BookingValidator.validateRequest(guestName, roomType, inventory);
                // Step 2: Update State (Protected)
                inventory.decreaseAvailability(roomType);
                // Step 3: Business Logic
                String roomId = roomType.substring(0, 3).toUpperCase() + "-" + (usedRoomIds.size() + 101);
                usedRoomIds.add(roomId);
                System.out.println("CONFIRMED: Booking successful for " + guestName + ". Room: " + roomId);
            } catch (BookingException e) {
                // UC9: Catching specific domain errors and displaying failure messages
                System.err.println("REJECTED: " + e.getMessage());
            } finally {
                // Ensures the process is always concluded
                System.out.println("--- Session Finished ---\n");
            }
        }
    }
    public static void main(String[] args) {
        System.out.println("=== UC9: Error Handling & Validation Test ===\n");
        RoomInventory inventory = new RoomInventory();
        BookingService service = new BookingService();
        // 1. Success Scenario
        service.processSingleBooking("Alice", "Single Room", inventory);
        // 2. Failure: Invalid Room Type
        service.processSingleBooking("Bob", "Luxury Villa", inventory);
        // 3. Failure: Empty Input
        service.processSingleBooking("", "Double Room", inventory);
        // 4. Failure: Sold Out (Double Room available: 1. Charlie takes it, David fails)
        service.processSingleBooking("Charlie", "Double Room", inventory);
        service.processSingleBooking("David", "Double Room", inventory); 
    }
}
