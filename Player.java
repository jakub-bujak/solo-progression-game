import java.util.Random;

// Player class - represents the user-controlled character
public class Player {
    // Player attributes
    private String name;
    private int health;
    private int maxHealth;
    private int attack;
    private int level;
    private int experience;

    // Player's inventory (stores usable items)
    private Inventory inventory;

    // Constructor - initializes player stats and inventory
    public Player(String name) {
        this.name = name;
        this.level = 1;
        this.health = 100;
        this.maxHealth = 100;
        this.attack = 20;
        this.experience = 0;
        this.inventory = new Inventory();
    }

    // Get the player's name
    public String getName() {
        return name;
    }

    // Get current HP
    public int getHealth() {
        return health;
    }

    // Set current HP (capped at maxHealth)
    public void setHealth(int health) {
        this.health = Math.min(health, maxHealth);
    }

    // Get max HP
    public int getMaxHealth() {
        return maxHealth;
    }

    // Get current attack stat
    public int getAttack() {
        return attack;
    }

    // Get current level
    public int getLevel() {
        return level;
    }

    // Get current experience
    public int getExperience() {
        return experience;
    }

    // Get inventory object
    public Inventory getInventory() {
        return inventory;
    }

    // Add experience and check if the player should level up
    public void addExperience(int exp) {
        experience += exp;

        // Required XP to level up is 50 × current level
        int nextLevelExp = level * 50;

        // If enough XP is gained, level up
        if (experience >= nextLevelExp) {
            levelUp();
        }
    }

    // Level up the player and increase stats
    private void levelUp() {
        level++;
        experience = 0;

        // Increase attack and max health based on new level
        attack += level * 10;
        maxHealth += level * 50;

        // Fully heal on level up
        health = maxHealth;

        // Level-up message
        System.out.println("Congratulations! You leveled up to Level " + level + "!");
    }

    // Calculate how much the player could heal for based on RNG
    public int calculatePotentialHeal() {
        Random random = new Random();
        int roll = random.nextInt(100) + 1; // Roll a number between 1–100
        int healPercentage;

        // 30% chance to be unlucky → heals 0–19%
        if (roll <= 30) {
            healPercentage = random.nextInt(20); // 0–19%
        }
        // 50% chance to be lucky → heals 20–30%
        else if (roll <= 80) {
            healPercentage = random.nextInt(11) + 20; // 20–30%
        }
        // 15% chance to be extremely lucky → heals 31–50%
        else if (roll <= 95) {
            healPercentage = random.nextInt(20) + 31; // 31–50%
        }
        // 5% chance to get a full heal
        else {
            return maxHealth;
        }

        // Convert percentage into actual heal amount
        return (maxHealth * healPercentage) / 100;
    }

    // Returns a healing message based on the effectiveness of the heal
    public String getHealingMessage(int healAmount) {
        double healRatio = (double) healAmount / this.maxHealth;

        if (healRatio <= 0.19) {
            return "I am feeling unlucky today.";
        } else if (healRatio <= 0.30) {
            return "I am feeling lucky today.";
        } else if (healRatio <= 0.50) {
            return "I am feeling extremely lucky today.";
        } else {
            return "Restoration spell active.";
        }
    }

    // Apply healing to player and print the result
    public void heal(int healAmount) {
        this.health = Math.min(this.health + healAmount, this.maxHealth);
        System.out.println("You healed for " + healAmount + " HP. Current HP: " + this.health + " / " + this.maxHealth);
    }
}
