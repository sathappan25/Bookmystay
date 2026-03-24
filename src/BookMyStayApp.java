
public class BookMyStayApp {
    public static void main(String[] args) {

        RoomInventory inventory = new RoomInventory();
        FilePersistenceService persistence = new FilePersistenceService();

        String filePath = "inventory.txt";

        // 🔄 LOAD (on startup)
        persistence.loadInventory(inventory, filePath);

        inventory.displayInventory();

        // Simulate changes
        inventory.decrementRoom("Single");

        // 💾 SAVE (before shutdown)
        persistence.saveInventory(inventory, filePath);
    }
}