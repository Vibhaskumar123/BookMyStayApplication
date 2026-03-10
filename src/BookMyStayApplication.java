import java.util.*;
public class BookMyStayApplication {
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
    // Reservation
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
    // Booking Request Queue
    static class BookingRequestQueue {
        private Queue<Reservation> queue;
        public BookingRequestQueue() {
            queue = new LinkedList<>();
        }
        public void addRequest(Reservation reservation) {
            queue.add(reservation);
            reservation.displayRequest();
        }
        public Queue<Reservation> getQueue() {
            return queue;
        }
    }
    // UC6: Booking Service
    static class BookingService {
        private HashMap<String, Set<String>> allocatedRooms;
        private Set<String> usedRoomIds;
        public BookingService() {
            allocatedRooms = new HashMap<>();
            usedRoomIds = new HashSet<>();
        }
        public void processBookings(BookingRequestQueue requestQueue, RoomInventory inventory) {
            Queue<Reservation> queue = requestQueue.getQueue();
            System.out.println("\nProcessing Booking Requests...\n");
            while (!queue.isEmpty()) {
                Reservation reservation = queue.poll();
                String roomType = reservation.getRoomType();
                int available = inventory.getAvailability(roomType);
                if (available > 0) {
                    String roomId = generateRoomId(roomType);
                    allocatedRooms
                            .computeIfAbsent(roomType, k -> new HashSet<>())
                            .add(roomId);
                    usedRoomIds.add(roomId);
                    inventory.decreaseAvailability(roomType);
                    System.out.println("Reservation Confirmed:");
                    System.out.println(reservation.getGuestName() +
                            " → " + roomType +
                            " | Room ID: " + roomId + "\n");
                } else {
                    System.out.println("Reservation Failed for "
                            + reservation.getGuestName()
                            + " (No rooms available for "
                            + roomType + ")\n");
                }
            }
        }
        private String generateRoomId(String roomType) {
            String prefix = roomType.replace(" ", "").substring(0,3).toUpperCase();
            String roomId;
            do {
                roomId = prefix + "-" + (usedRoomIds.size() + 1);
            } while (usedRoomIds.contains(roomId));
            return roomId;
        }
    }
    public static void main(String[] args) {
        RoomInventory inventory = new RoomInventory();
        BookingRequestQueue requestQueue = new BookingRequestQueue();
        requestQueue.addRequest(new Reservation("Alice", "Single Room"));
        requestQueue.addRequest(new Reservation("Bob", "Suite Room"));
        requestQueue.addRequest(new Reservation("Charlie", "Double Room"));
        BookingService bookingService = new BookingService();
        bookingService.processBookings(requestQueue, inventory);
        inventory.displayInventory();
    }
}
