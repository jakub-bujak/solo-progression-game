import java.util.Random;

public class Monster {
    // Monster's health and attack stats
    private int health;
    private int attack;

    // For boss monsters only: min and max attack range
    private int minAttack;
    private int maxAttack;
    private boolean isBoss = false; // Flag to indicate if this is a boss monster

    // Constructor: creates a standard monster with stats scaled by difficulty and level
    public Monster(double difficultyMultiplier, int levelRequirement) {
        Random random = new Random();

        // Health is randomized a little (+0 to 19), and scales with level and difficulty
        this.health = random.nextInt(20) + (int) (50 + levelRequirement * 50 * difficultyMultiplier);

        // Attack is randomized a little (+0 to 9), and scales similarly but with smaller growth
        this.attack = random.nextInt(10) + (int) (10 + levelRequirement * 5 * difficultyMultiplier);
    }

    // Overloaded constructor: used for custom monsters like the Final Boss
    public Monster(int fixedHealth, int minAttack, int maxAttack) {
        this.health = fixedHealth;
        this.minAttack = minAttack;
        this.maxAttack = maxAttack;
        this.isBoss = true; // Mark this monster as a boss
    }

    // Returns the monster's current health
    public int getHealth() {
        return health;
    }

    // Sets (updates) the monster's health
    public void setHealth(int health) {
        this.health = health;
    }

    // Returns the monster's attack stat
    // If it's a boss, this is no longer used (custom attack system is used instead)
    public int getAttack() {
        if (isBoss) {
            Random random = new Random();
            return random.nextInt(maxAttack - minAttack + 1) + minAttack;
        }
        return attack;
    }

    // Boss-specific attack system — deals damage and prints dramatic text
    public int performBossAttack(Player player) {
        Random random = new Random();
        int roll = random.nextInt(100) + 1;
        int damage;

        // 30% chance — light attack
        if (roll <= 30) {
            damage = random.nextInt(11) + 10; // 10–20
            System.out.println("💢 The boss lashes out casually.");
        }
        // 50% chance — normal attack
        else if (roll <= 80) {
            damage = random.nextInt(21) + 30; // 30–50
            System.out.println("🗡️ The boss slashes with fierce precision!");
        }
        // 15% chance — heavy strike
        else if (roll <= 95) {
            damage = random.nextInt(31) + 51; // 51–81
            System.out.println("💥 The boss roars with rage and slams the ground!");
        }
        // 5% chance — devastating blow
        else {
            damage = 100;
            System.out.println("🔥 The boss unleashes a devastating strike of pure fury!");
        }

        // Apply the damage
        player.setHealth(player.getHealth() - damage);
        System.out.println("You take " + damage + " damage!");

        return damage;
    }

    // Determines if the monster drops an item when defeated
    public String dropItem() {
        Random random = new Random();
        int chance = random.nextInt(100);

        // 25% chance to drop a Health Potion
        if (chance < 25) {
            return "Health Potion";
        }

        return null;
    }
}
