import java.util.*;
public class BookMyStayApplication {
    // -------------------------
    // Room Model
    // -------------------------
    static abstract class Room {
        protected int beds;
        protected double size;
        protected double price;
        public Room(int beds, double size, double price) {
            this.beds = beds;
            this.size = size;
            this.price = price;
        }
        public abstract String getRoomType();
    }
    static class SingleRoom extends Room {
        public SingleRoom() { super(1, 20, 100); }
        public String getRoomType() { return "Single Room"; }
    }
    static class DoubleRoom extends Room {
        public DoubleRoom() { super(2, 30, 180); }
        public String getRoomType() { return "Double Room"; }
    }
    static class SuiteRoom extends Room {
        public SuiteRoom() { super(3, 50, 350); }
        public String getRoomType() { return "Suite Room"; }
    }
    // -------------------------
    // UC3: Inventory
    // -------------------------
    static class RoomInventory {
        private HashMap<String, Integer> inventory;
        public RoomInventory() {
            inventory = new HashMap<>();
            inventory.put("Single Room", 5);
            inventory.put("Double Room", 3);
            inventory.put("Suite Room", 2);
        }
        public int getAvailability(String roomType) {
            return inventory.getOrDefault(roomType, 0);
        }
        public void decreaseAvailability(String roomType) {
            int count = inventory.getOrDefault(roomType, 0);
            if (count > 0) {
                inventory.put(roomType, count - 1);
            }
        }
        public void displayInventory() {
            System.out.println("\nCurrent Inventory:");
            for (Map.Entry<String, Integer> entry : inventory.entrySet()) {
                System.out.println(entry.getKey() + " → Available: " + entry.getValue());
            }
        }
    }
    public static void main(String[] args) {
        Room single = new SingleRoom();
        Room doubleRoom = new DoubleRoom();
        Room suite = new SuiteRoom();
        Room[] rooms = {single, doubleRoom, suite};
        RoomInventory inventory = new RoomInventory();
        inventory.displayInventory();
        System.out.println("\nChecking Availability:");
        for (Room room : rooms) {
            System.out.println(room.getRoomType() + " Available: " +
                    inventory.getAvailability(room.getRoomType()));
        }
    }
}
