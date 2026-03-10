import java.util.*;
public class UC5_BookMyStayApplication{
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
    // -------------------------
    // UC4: Search Service
    // -------------------------
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
    // -------------------------
    // UC5: Reservation
    // -------------------------
    static class Reservation {
        private String guestName;
        private String roomType;
        public Reservation(String guestName, String roomType) {
            this.guestName = guestName;
            this.roomType = roomType;
        }
        public String getGuestName() {
            return guestName;
        }
        public String getRoomType() {
            return roomType;
        }
        public void displayRequest() {
            System.out.println(guestName + " requested " + roomType);
        }
    }
    // -------------------------
    // UC5: Booking Queue
    // -------------------------
    static class BookingRequestQueue {
        private Queue<Reservation> queue;
        public BookingRequestQueue() {
            queue = new LinkedList<>();
        }
        public void addRequest(Reservation reservation) {
            queue.add(reservation);
            System.out.println("Request added:");
            reservation.displayRequest();
        }
        public Queue<Reservation> getQueue() {
            return queue;
        }
        public void displayQueue() {
            System.out.println("\nCurrent Booking Request Queue:");
            for (Reservation r : queue) {
                r.displayRequest();
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

        // UC4 Search
        SearchService searchService = new SearchService();
        searchService.searchAvailableRooms(inventory, rooms);

        // UC5 Request Queue
        BookingRequestQueue requestQueue = new BookingRequestQueue();

        requestQueue.addRequest(new Reservation("Alice", "Single Room"));
        requestQueue.addRequest(new Reservation("Bob", "Suite Room"));
        requestQueue.addRequest(new Reservation("Charlie", "Double Room"));

        requestQueue.displayQueue();

        // show inventory after allocation
        inventory.displayInventory();
    }
}