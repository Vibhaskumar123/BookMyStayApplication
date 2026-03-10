import java.util.*;
public class BookMyStayApplication {
    // Room Model
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
    // Room Inventory
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
        public void displayInventory() {
            System.out.println("\nCurrent Inventory:");
            for (Map.Entry<String, Integer> entry : inventory.entrySet()) {
                System.out.println(entry.getKey() + " → Available: " + entry.getValue());
            }
        }
    }
    // UC4: Search Service
    static class SearchService {
        public void searchAvailableRooms(RoomInventory inventory, Room[] rooms) {

            System.out.println("\nAvailable Rooms:\n");

            for (Room room : rooms) {
                int available = inventory.getAvailability(room.getRoomType());

                if (available > 0) {
                    System.out.println(room.getRoomType());
                    room.displayDetails();
                    System.out.println("Available: " + available);
                    System.out.println();
                }
            }
        }
    }
    public static void main(String[] args) {
        Room single = new SingleRoom();
        Room doubleRoom = new DoubleRoom();
        Room suite = new SuiteRoom();
        Room[] rooms = {single, doubleRoom, suite};
        RoomInventory inventory = new RoomInventory();
        SearchService searchService = new SearchService();
        searchService.searchAvailableRooms(inventory, rooms);
    }
}
