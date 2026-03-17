import java.util.*;
public class BookMyStayApplication{
    // -------------------------
    // Reservation Model
    // -------------------------
    static class Reservation {
        private static int counter = 1;
        private String reservationId;
        private String guestName;
        private String roomType;
        public Reservation(String guestName, String roomType) {
            this.guestName = guestName;
            this.roomType = roomType;
            this.reservationId = "RES-" + counter++;
        }
        public String getReservationId() {
            return reservationId;
        }
        public String getGuestName() {
            return guestName;
        }
        public String getRoomType() {
            return roomType;
        }
        public void display() {
            System.out.println(reservationId + " | " + guestName + " | " + roomType);
        }
    }
    // -------------------------
    // Booking History (Storage)
    // -------------------------
    static class BookingHistory {
        private List<Reservation> history;
        public BookingHistory() {
            history = new ArrayList<>();
        }
        // Add confirmed reservation
        public void addReservation(Reservation reservation) {
            history.add(reservation);
            System.out.println("Added to history: " + reservation.getReservationId());
        }
        // Get all reservations
        public List<Reservation> getAllReservations() {
            return history;
        }
        // Display all reservations
        public void displayHistory() {
            System.out.println("\n=== Booking History ===");
            for (Reservation r : history) {
                r.display();
            }
        }
    }
    // -------------------------
    // Booking Report Service
    // -------------------------
    static class BookingReportService {
        // Generate summary report
        public void generateSummaryReport(List<Reservation> reservations) {
            System.out.println("\n=== Booking Summary Report ===");
            if (reservations.isEmpty()) {
                System.out.println("No bookings available.");
                return;
            }
            // Count bookings per room type
            Map<String, Integer> roomCount = new HashMap<>();
            for (Reservation r : reservations) {
                roomCount.put(
                        r.getRoomType(),
                        roomCount.getOrDefault(r.getRoomType(), 0) + 1
                );
            }
            // Display report
            for (Map.Entry<String, Integer> entry : roomCount.entrySet()) {
                System.out.println(entry.getKey() + " → " + entry.getValue() + " bookings");
            }
            System.out.println("Total Bookings: " + reservations.size());
        }
    }
    // -------------------------
    // Main Method
    // -------------------------
    public static void main(String[] args) {
        System.out.println("=== Use Case 8: Booking History & Reporting ===\n");
        // Simulate confirmed bookings
        Reservation r1 = new Reservation("Alice", "Single Room");
        Reservation r2 = new Reservation("Bob", "Suite Room");
        Reservation r3 = new Reservation("Charlie", "Double Room");
        Reservation r4 = new Reservation("David", "Single Room");
        // Booking history
        BookingHistory history = new BookingHistory();
        // Add confirmed bookings
        history.addReservation(r1);
        history.addReservation(r2);
        history.addReservation(r3);
        history.addReservation(r4);
        // Admin views history
        history.displayHistory();
        // Generate report
        BookingReportService reportService = new BookingReportService();
        reportService.generateSummaryReport(history.getAllReservations());
    }
}