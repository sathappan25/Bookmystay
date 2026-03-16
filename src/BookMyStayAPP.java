/**
 * BookMyStayAPP.java
 *
 * UC1: Application startup and welcome message.
 * UC2: Room modeling with abstract classes, inheritance, and static availability.
 * UC3: Centralized inventory management using HashMap.
 * UC4: Room search & availability check (read-only access).
 * UC5: Booking request intake using Queue (FIFO).
 * UC6: Reservation confirmation & room allocation with uniqueness enforcement.
 *
 * @author Aanish
 * @version 6.1
 * @version 5.1
 * @version 4.1
 *
 * @author Aanish
 * @version 3.1
 */

import java.util.*;

// Abstract class representing a generalized Room
abstract class Room {
    private String roomType;
    private int numberOfBeds;
    private double pricePerNight;

    public Room(String roomType, int numberOfBeds, double pricePerNight) {
        this.roomType = roomType;
        this.numberOfBeds = numberOfBeds;
        this.pricePerNight = pricePerNight;
    }

    public String getRoomType() {
        return roomType;
    }

    public int getNumberOfBeds() {
        return numberOfBeds;
    }

    public double getPricePerNight() {
        return pricePerNight;
    }

    public abstract void displayRoomDetails();
}

// Concrete room classes
class SingleRoom extends Room {
    public SingleRoom() {
        super("Single Room", 1, 1500.0);
    }
    @Override
    public void displayRoomDetails() {
        System.out.println("Room Type: " + getRoomType() +
                " | Beds: " + getNumberOfBeds() +
                " | Price: ₹" + getPricePerNight());
    }
}

class DoubleRoom extends Room {
    public DoubleRoom() {
        super("Double Room", 2, 2500.0);
    }
    @Override
    public void displayRoomDetails() {
        System.out.println("Room Type: " + getRoomType() +
                " | Beds: " + getNumberOfBeds() +
                " | Price: ₹" + getPricePerNight());
    }
}

class SuiteRoom extends Room {
    public SuiteRoom() {
        super("Suite Room", 3, 5000.0);
    }
    @Override
    public void displayRoomDetails() {
        System.out.println("Room Type: " + getRoomType() +
                " | Beds: " + getNumberOfBeds() +
                " | Price: ₹" + getPricePerNight());
    }
}

// UC3: Centralized Inventory Management
class RoomInventory {
    private Map<String, Integer> inventory;

    public RoomInventory() {
        inventory = new HashMap<>();
    }

    public void addRoomType(String roomType, int availability) {
        inventory.put(roomType, availability);
    }

    public int getAvailability(String roomType) {
        return inventory.getOrDefault(roomType, 0);
    }

    public void updateAvailability(String roomType, int newAvailability) {
        if (inventory.containsKey(roomType)) {
            inventory.put(roomType, newAvailability);
        }
    }

    public void displayInventory() {
        System.out.println("\n--- Current Room Inventory ---");
        for (Map.Entry<String, Integer> entry : inventory.entrySet()) {
            System.out.println(entry.getKey() + " -> " + entry.getValue() + " rooms available");
        }
    }
}

// UC4: Search Service (Read-only access)
class SearchService {
    private RoomInventory inventory;

    public SearchService(RoomInventory inventory) {
        this.inventory = inventory;
    }

    public void searchAvailableRooms(Room[] rooms) {
        System.out.println("\n--- Search Results: Available Rooms ---");
        for (Room room : rooms) {
            int availability = inventory.getAvailability(room.getRoomType());
            if (availability > 0) {
                room.displayRoomDetails();
                System.out.println("Availability: " + availability + " rooms\n");
            }
        }
    }
}

// UC5: Reservation and Booking Request Queue
class Reservation {
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

    public void displayReservation() {
        System.out.println("Reservation Request -> Guest: " + guestName +
                " | Room Type: " + roomType);
    }
}

class BookingRequestQueue {
    private Queue<Reservation> requestQueue;

    public BookingRequestQueue() {
        requestQueue = new LinkedList<>();
    }

    public void addRequest(Reservation reservation) {
        requestQueue.add(reservation);
        System.out.println("Booking request added for " + reservation.getGuestName() +
                " (" + reservation.getRoomType() + ")");
    }

    public Queue<Reservation> getQueue() {
        return requestQueue;
    }

    public void displayQueue() {
        System.out.println("\n--- Booking Request Queue (FIFO) ---");
        for (Reservation r : requestQueue) {
            r.displayReservation();
        }
    }
}

// UC6: Booking Service (Reservation Confirmation & Allocation)
class BookingService {
    private RoomInventory inventory;
    private Map<String, Set<String>> allocatedRooms;

    public BookingService(RoomInventory inventory) {
        this.inventory = inventory;
        this.allocatedRooms = new HashMap<>();
    }

    // Process requests from queue
    public void processRequests(BookingRequestQueue bookingQueue) {
        Queue<Reservation> queue = bookingQueue.getQueue();

        System.out.println("\n--- Processing Booking Requests ---");
        while (!queue.isEmpty()) {
            Reservation request = queue.poll();
            String roomType = request.getRoomType();
            int availability = inventory.getAvailability(roomType);

            if (availability > 0) {
                // Generate unique room ID
                String roomId = generateRoomId(roomType);

                // Ensure uniqueness
                allocatedRooms.putIfAbsent(roomType, new HashSet<>());
                if (!allocatedRooms.get(roomType).contains(roomId)) {
                    allocatedRooms.get(roomType).add(roomId);

                    // Update inventory
                    inventory.updateAvailability(roomType, availability - 1);

                    System.out.println("CONFIRMED -> Guest: " + request.getGuestName() +
                            " | Room Type: " + roomType +
                            " | Room ID: " + roomId);
                }
            } else {
                System.out.println("FAILED -> Guest: " + request.getGuestName() +
                        " | Room Type: " + roomType +
                        " | Reason: No availability");
            }
        }
    }

    // Generate unique room ID
    private String generateRoomId(String roomType) {
        return roomType.replace(" ", "_") + "_" + UUID.randomUUID().toString().substring(0, 6);
    }
}

// Application Entry Point
public class BookMyStayAPP {
    public static void main(String[] args) {
        // UC1: Welcome message
        System.out.println("=======================================");
        System.out.println("   Welcome to Book My Stay App!");
        System.out.println("   Hotel Booking Management System v6.1");
        System.out.println("   Hotel Booking Management System v5.1");
        System.out.println("   Hotel Booking Management System v4.1");
        System.out.println("   Hotel Booking Management System v3.1");
        System.out.println("=======================================\n");

        // UC2: Initialize room objects
        Room single = new SingleRoom();
        Room doubleRoom = new DoubleRoom();
        Room suite = new SuiteRoom();
        Room[] rooms = { single, doubleRoom, suite };

        // UC3: Centralized Inventory
        RoomInventory inventory = new RoomInventory();
        inventory.addRoomType(single.getRoomType(), 2);
        inventory.addRoomType(doubleRoom.getRoomType(), 1);
        inventory.addRoomType(suite.getRoomType(), 0); // Suite fully booked
        inventory.displayInventory();

        // UC4: Room Search (Read-only)
        SearchService searchService = new SearchService(inventory);
        searchService.searchAvailableRooms(rooms);

        // UC5: Booking Request Queue
        BookingRequestQueue bookingQueue = new BookingRequestQueue();
        bookingQueue.addRequest(new Reservation("Alice", "Single Room"));
        bookingQueue.addRequest(new Reservation("Bob", "Double Room"));
        bookingQueue.addRequest(new Reservation("Charlie", "Suite Room"));
        bookingQueue.addRequest(new Reservation("Diana", "Single Room"));
        bookingQueue.displayQueue();

        // UC6: Process requests and confirm reservations
        BookingService bookingService = new BookingService(inventory);
        bookingService.processRequests(bookingQueue);

        // Final inventory state
        // Search again after update
        searchService.searchAvailableRooms(rooms);
        single.displayRoomDetails();
        doubleRoom.displayRoomDetails();
        suite.displayRoomDetails();

        // UC3: Centralized Inventory
        RoomInventory inventory1 = new RoomInventory();
        inventory1.addRoomType(single.getRoomType(), 5);
        inventory1.addRoomType(doubleRoom.getRoomType(), 3);
        inventory1.addRoomType(suite.getRoomType(), 2);

        // Display inventory
        inventory1.displayInventory();

        // Example update
        inventory1.updateAvailability("Single Room", 4);
        System.out.println("\nAfter booking one Single Room:");
        inventory1.displayInventory();
    }
}