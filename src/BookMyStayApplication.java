import java.util.*;
public class BookMyStayApplication{
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
            System.out.println("Room Type: " + getRoomType());
            System.out.println("Beds: " + beds);
            System.out.println("Size: " + size + " sqm");
            System.out.println("Price: $" + price + " per night");
            System.out.println("----------------------");
        }
        public abstract String getRoomType();
    }
    static class SingleRoom extends Room {
        public SingleRoom() {
            super(1, 20, 100);
        }
        public String getRoomType() {
            return "Single Room";
        }
    }
    static class DoubleRoom extends Room {
        public DoubleRoom() {
            super(2, 30, 180);
        }
        public String getRoomType() {
            return "Double Room";
        }
    }
    static class SuiteRoom extends Room {
        public SuiteRoom() {
            super(3, 50, 350);
        }
        public String getRoomType() {
            return "Suite Room";
        }
    }
    public static void main(String[] args) {
        Room single = new SingleRoom();
        Room doubleRoom = new DoubleRoom();
        Room suite = new SuiteRoom();
        Room[] rooms = {single, doubleRoom, suite};
        for (Room room : rooms) {
            room.displayDetails();
        }
    }
}
