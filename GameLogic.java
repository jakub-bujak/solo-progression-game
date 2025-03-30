import java.util.Scanner;
import java.util.ArrayList;
import java.util.List;

// GameLogic class - manages the overall game loop and interactions
class GameLogic {
    private Scanner scanner;
    private Player player;
    private Dungeon dungeon;
    private List<DungeonLevel> dungeonLevels;

    // Constructor - sets up initial game state
    public GameLogic() {
        this.scanner = new Scanner(System.in); // For player input
        this.dungeon = new Dungeon(); // Creates the dungeon instance
        this.dungeonLevels = new ArrayList<>(); // List of available dungeon levels
        setupDungeons(); // Populates dungeonLevels with predefined difficulties
    }

    // Defines available dungeon levels with increasing difficulty and rewards
    private void setupDungeons() {
        dungeonLevels.add(new DungeonLevel(1, 1.0, 1.0)); // Easy Dungeon
        dungeonLevels.add(new DungeonLevel(3, 1.5, 1.2)); // Medium Dungeon
        dungeonLevels.add(new DungeonLevel(5, 2.0, 1.5)); // Hard Dungeon
        dungeonLevels.add(new DungeonLevel(0, 3.0, 3.0)); // 🛑 Final Boss Dungeon (no level requirement)
    }

    // Starts the game loop
    public void startGame() {
        System.out.println("Welcome to Solo Progression: Text Adventure!");

        // Prompt the player to enter their name
        String playerName = getPlayerName();
        player = new Player(playerName); // Create player instance

        // Automatically start with Dungeon Level 1
        dungeon.setDungeonLevel(dungeonLevels.get(0));

        System.out.println("Welcome, " + player.getName() + ". Your journey as a hunter begins now!");

        // Main game loop: keeps going while player is alive
        while (player.getHealth() > 0) {
            System.out.println("\n--- What would you like to do? ---");
            System.out.println("1. Enter a Dungeon");
            System.out.println("2. Check Status");
            System.out.println("3. Inventory");
            System.out.println("4. Select Dungeon Level");
            System.out.println("5. Quit Game");

            int choice = getValidatedInput(scanner, "Choose an action (1-5): ", 1, 5);

            switch (choice) {
                case 1:
                    dungeon.explore(player); // Go into combat
                    break;
                case 2:
                    showStatus(); // Display player stats
                    break;
                case 3:
                    handleInventory(); // Show and use inventory items
                    break;
                case 4:
                    selectDungeonLevel(); // Change dungeon difficulty
                    break;
                case 5:
                    System.out.println("Thank you for playing Solo Progression: Text Adventure!");
                    return; // Exit game
            }
        }
    }

    // Prompt for player name and validate it's not blank
    private String getPlayerName() {
        String name;
        while (true) {
            System.out.print("Enter your name, Hunter: ");
            name = scanner.nextLine().trim();
            if (!name.isEmpty()) {
                return name;
            } else {
                System.out.println("Name cannot be blank. Please enter a valid name.");
            }
        }
    }

    // Show current player status: name, level, health, etc.
    private void showStatus() {
        System.out.println("\n--- Status ---");
        System.out.println("Name: " + player.getName());
        System.out.println("Level: " + player.getLevel());
        System.out.println("Health: " + player.getHealth());
        System.out.println("Attack: " + player.getAttack());
        System.out.println("Experience: " + player.getExperience() + "/" + (player.getLevel() * 50));
    }

    // Displays and allows using inventory items
    private void handleInventory() {
        player.getInventory().showInventory();

        if (player.getInventory().getSize() > 0) {
            System.out.print("Select an item to use (0 to cancel): ");
            int choice = getValidatedInput(scanner, "", 0, player.getInventory().getSize());

            if (choice > 0) {
                player.getInventory().useItem(choice - 1, player);
            }
        } else {
            System.out.println("Your inventory is empty.");
        }
    }

    // Lets the player change dungeon difficulty and adds boss warning for Final Boss
    private void selectDungeonLevel() {
        System.out.println("\n--- Available Dungeons ---");

        for (int i = 0; i < dungeonLevels.size(); i++) {
            DungeonLevel dl = dungeonLevels.get(i);
            if (dl.getLevelRequirement() == 0) {
                System.out.println((i + 1) + ". 🛑 BOSS Dungeon (No Level Requirement)");
            } else {
                System.out.println((i + 1) + ". Dungeon Level " + (i + 1) +
                        " (Required Level: " + dl.getLevelRequirement() + ")");
            }
        }

        int choice = getValidatedInput(scanner, "Select a dungeon (1-" + dungeonLevels.size() + "): ", 1, dungeonLevels.size());
        DungeonLevel selectedDungeon = dungeonLevels.get(choice - 1);

        // Special warning and confirmation for Final Boss
        if (selectedDungeon.getLevelRequirement() == 0) {
            System.out.println("⚠️ WARNING: You are about to enter the Final Boss Dungeon!");
            System.out.println("Once you enter, there's no turning back.");
            System.out.println("Are you sure you want to proceed?\n1. Yes\n2. No");

            int confirm = getValidatedInput(scanner, "Choose: ", 1, 2);
            if (confirm == 1) {
                dungeon.setDungeonLevel(selectedDungeon);
                System.out.println("You march bravely into the BOSS Dungeon...");
            } else {
                System.out.println("You backed away from the boss door...");
            }
        } else {
            if (player.getLevel() >= selectedDungeon.getLevelRequirement()) {
                dungeon.setDungeonLevel(selectedDungeon);
                System.out.println("You selected Dungeon Level " + choice + ".");
            } else {
                System.out.println("You do not meet the level requirement for this dungeon.");
            }
        }
    }

    // Utility method to safely read an integer between min and max
    public static int getValidatedInput(Scanner scanner, String prompt, int min, int max) {
        int input;
        while (true) {
            System.out.print(prompt);
            if (scanner.hasNextInt()) {
                input = scanner.nextInt();
                scanner.nextLine(); // Clear buffer
                if (input >= min && input <= max) {
                    return input;
                } else {
                    System.out.println("Invalid choice. Please choose a number between " + min + " and " + max + ".");
                }
            } else {
                System.out.println("Invalid input. Please enter a valid number.");
                scanner.next(); // Clear invalid input
            }
        }
    }
}
