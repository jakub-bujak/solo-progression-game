// DungeonLevel class
class DungeonLevel {
    // Minimum player level required to enter this dungeon
    private int levelRequirement;

    // Multiplier to scale monster health and attack
    private double difficultyMultiplier;

    // Multiplier to increase or decrease experience gained from monsters
    private double experienceMultiplier;

    // Constructor: initializes a dungeon level with difficulty and reward scaling
    public DungeonLevel(int levelRequirement, double difficultyMultiplier, double experienceMultiplier) {
        this.levelRequirement = levelRequirement;
        this.difficultyMultiplier = difficultyMultiplier;
        this.experienceMultiplier = experienceMultiplier;
    }

    // Returns the minimum level required to enter this dungeon
    public int getLevelRequirement() {
        return levelRequirement;
    }

    // Returns the difficulty multiplier used for monster stat scaling
    public double getDifficultyMultiplier() {
        return difficultyMultiplier;
    }

    // Returns the experience multiplier used when calculating XP rewards
    public double getExperienceMultiplier() {
        return experienceMultiplier;
    }
}
