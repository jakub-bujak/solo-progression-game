import java.util.ArrayList;

// Inventory class - manages the player's items
class Inventory {
    // A list to store item names (e.g., "Health Potion")
    private ArrayList<String> items;

    // Constructor - initializes the inventory as an empty list
    public Inventory() {
        this.items = new ArrayList<>();
    }

    // Adds an item to the inventory and notifies the player
    public void addItem(String item) {
        items.add(item);
        System.out.println("You received: " + item);
    }

    // Displays the contents of the inventory
    public void showInventory() {
        System.out.println("\n--- Inventory ---");

        // If empty, inform the player
        if (items.isEmpty()) {
            System.out.println("Your inventory is empty.");
        } else {
            // Otherwise, list each item with a number
            for (int i = 0; i < items.size(); i++) {
                System.out.println((i + 1) + ". " + items.get(i));
            }
        }
    }

    // Allows the player to use an item by index
    public boolean useItem(int index, Player player) {
        // If the index is invalid (out of bounds), show an error
        if (index < 0 || index >= items.size()) {
            System.out.println("Invalid item selection.");
            return false;
        }

        // Remove the selected item from the list
        String item = items.remove(index);

        // Apply the item's effect
        if (item.equals("Health Potion")) {
            // Heal the player for 50 HP
            player.setHealth(player.getHealth() + 50);
            System.out.println("You used a Health Potion and restored 50 HP!");
        } else {
            // Fallback message for future item types
            System.out.println("You used: " + item);
        }

        return true;
    }

    // Returns how many items are currently in the inventory
    public int getSize() {
        return items.size();
    }
}
