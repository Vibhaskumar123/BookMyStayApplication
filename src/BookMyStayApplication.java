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
    }
    // Reservation
    static class Reservation {
        private String guestName;
        private String roomType;

        public Reservation(String guestName, String roomType) {
            this.guestName = guestName;
            this.roomType = roomType;
        }

        public void displayRequest() {
            System.out.println(guestName + " requested " + roomType);
        }
    }
    // Booking Request Queue
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

        public void displayQueue() {
            System.out.println("\nCurrent Booking Request Queue:");
            for (Reservation r : queue) {
                r.displayRequest();
            }
        }
    }
    public static void main(String[] args) {
        BookingRequestQueue requestQueue = new BookingRequestQueue();
        requestQueue.addRequest(new Reservation("Alice", "Single Room"));
        requestQueue.addRequest(new Reservation("Bob", "Suite Room"));
        requestQueue.addRequest(new Reservation("Charlie", "Double Room"));
        requestQueue.displayQueue();
    }
}
