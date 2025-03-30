// Dungeon class
import java.util.Scanner;
import java.util.Random;

class Dungeon {
    // Holds the currently selected dungeon level
    private DungeonLevel currentDungeonLevel;

    // Allows the GameLogic class to set the current dungeon level
    public void setDungeonLevel(DungeonLevel dungeonLevel) {
        this.currentDungeonLevel = dungeonLevel;
    }

    // Main method for exploring the dungeon — contains the combat loop
    public void explore(Player player) {
        // Make sure a dungeon level has been selected before exploring
        if (currentDungeonLevel == null) {
            System.out.println("Please select a dungeon level first.");
            return;
        }

        Scanner scanner = new Scanner(System.in);
        Random random = new Random();
        Monster monster;

        // Check if this is the Final Boss (levelRequirement == 0)
        if (currentDungeonLevel.getLevelRequirement() == 0) {
            System.out.println("⚔️ FINAL BOSS ENCOUNTER ⚔️");
            monster = new Monster(1000, 50, 100); // Boss: 1000 HP, 50–100 Attack (used internally)
        } else {
            // Normal monster based on dungeon level and difficulty
            monster = new Monster(
                    (int) currentDungeonLevel.getDifficultyMultiplier(),
                    currentDungeonLevel.getLevelRequirement()
            );
        }

        // Boss has mysterious stats on appearance
        System.out.println("You enter Dungeon Level " + currentDungeonLevel.getLevelRequirement() + "...");
        if (currentDungeonLevel.getLevelRequirement() == 0) {
            System.out.println("A monstrous presence appears with " + monster.getHealth() + " HP and ??? Attack!");
        } else {
            System.out.println("A monster appears with " + monster.getHealth() + " HP and " + monster.getAttack() + " Attack!");
        }

        // Combat loop: continues while both are alive
        while (monster.getHealth() > 0 && player.getHealth() > 0) {
            System.out.println("\nYour HP: " + player.getHealth() + " / " + player.getMaxHealth()
                    + " | Monster HP: " + monster.getHealth());

            // Show valid action options (Run disabled for Boss)
            if (currentDungeonLevel.getLevelRequirement() == 0) {
                System.out.println("1. Attack\n2. Magic");
            } else {
                System.out.println("1. Attack\n2. Magic\n3. Run");
            }

            int maxOption = (currentDungeonLevel.getLevelRequirement() == 0) ? 2 : 3;
            int choice = GameLogic.getValidatedInput(scanner, "Choose your action: ", 1, maxOption);

            if (choice == 1) { // Player chooses to attack
                int playerDamage = random.nextInt((int) (player.getAttack() * 0.5)) + (int) (player.getAttack() * 0.5);
                monster.setHealth(monster.getHealth() - playerDamage);
                System.out.println("You dealt " + playerDamage + " damage to the monster!");

                // Monster counterattacks if still alive
                if (monster.getHealth() > 0) {
                    if (currentDungeonLevel.getLevelRequirement() == 0) {
                        // Final Boss uses custom dramatic attack system
                        monster.performBossAttack(player);
                    } else {
                        int monsterDamage = random.nextInt(monster.getAttack() + 1);
                        player.setHealth(player.getHealth() - monsterDamage);
                        System.out.println("The monster dealt " + monsterDamage + " damage to you!");
                    }
                } else {
                    // Monster defeated
                    System.out.println("You defeated the monster!");

                    // Victory condition for Final Boss
                    if (currentDungeonLevel.getLevelRequirement() == 0) {
                        System.out.println("\n🎉 You have defeated the Final Boss!");
                        System.out.println("🏆 Victory! You cleared Solo Progression: Text Adventure!");
                        System.exit(0); // Ends the game
                    }

                    // Normal monster: reward player
                    int expGained = (int) (20 * currentDungeonLevel.getLevelRequirement() * currentDungeonLevel.getExperienceMultiplier());
                    player.addExperience(expGained);
                    System.out.println("You gained " + expGained + " experience points!");

                    String droppedItem = monster.dropItem();
                    if (droppedItem != null) {
                        player.getInventory().addItem(droppedItem);
                    }
                }

            } else if (choice == 2) { // Player chooses Magic (Heal or Dodge)
                int healAmount = player.calculatePotentialHeal();
                String healingMessage = player.getHealingMessage(healAmount);
                System.out.println(healingMessage);
                System.out.println("Are you sure?\n1. Dodge\n2. Heal");

                int healChoice = GameLogic.getValidatedInput(scanner, "Choose your action: ", 1, 2);

                if (healChoice == 1) {
                    System.out.println("You dodged the monster's attack!");
                } else {
                    player.heal(healAmount);

                    if (monster.getHealth() > 0) {
                        if (currentDungeonLevel.getLevelRequirement() == 0) {
                            monster.performBossAttack(player); // Boss counterattack with drama
                        } else {
                            int monsterDamage = random.nextInt(monster.getAttack() + 1);
                            player.setHealth(player.getHealth() - monsterDamage);
                            System.out.println("The monster took advantage and dealt " + monsterDamage + " damage to you!");
                        }
                    }
                }

            } else if (choice == 3) { // Player runs from normal dungeon
                System.out.println("You escaped the dungeon safely!");
                break;
            }
        }

        // Game over condition
        if (player.getHealth() <= 0) {
            System.out.println("You have fallen in battle. Game over, " + player.getName() + ".");
        }
    }
}
