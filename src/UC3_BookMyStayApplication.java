import java.util.*;
public class UC3_BookMyStayApplication{
    // -------------------------
    // UC2: Room Model
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
        public void displayDetails() {
            System.out.println("Beds: " + beds);
            System.out.println("Size: " + size + " sqm");
            System.out.println("Price: $" + price + " per night");
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
        // UC1
        System.out.println("Welcome to the Hotel Booking System");
        System.out.println("Application: Hotel Booking System\n");
        // UC2 Rooms
        Room single = new SingleRoom();
        Room doubleRoom = new DoubleRoom();
        Room suite = new SuiteRoom();
        Room[] rooms = {single, doubleRoom, suite};

        // UC3 Inventory
        RoomInventory inventory = new RoomInventory();
        inventory.displayInventory();

        // show inventory after allocation
        inventory.displayInventory();
    }
}