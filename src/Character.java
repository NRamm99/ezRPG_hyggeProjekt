import java.util.Scanner;

public class Character {

    // felter
    String name;
    int health;
    int attack;
    int defence;
    int xp;
    int nextXp;
    int level;

    // Constructor
    public Character(String name, int health, int attack, int defence, int xp) {
        this.name = name;
        this.health = health;
        this.attack = attack;
        this.defence = defence;
        this.xp = xp;
        this.nextXp = 10;
        this.level = 1;
    }

    public void printStats() {
        System.out.println("---------- STATS ----------");
        System.out.println("NAME: " + name);
        System.out.println("HP: " + health);
        System.out.println("ATK: " + attack);
        System.out.println("DEF: " + defence);
        System.out.println("---------------------------");
        System.out.println("Level: " + level);
        System.out.println("XP: " + "( " + xp + " / " + nextXp + " )");
        System.out.println();
    }

    void takeDamage(int damage) {
        this.health -= damage;
    }

    public void addXp(int amount) {
        xp += amount;
    }

    public void addHealth(int amount) {
        health += amount;
    }

    public void addAttack(int amount) {
        attack += amount;
    }

    public void addDefence(int amount) {
        defence += amount;
    }

    public void levelUp(Character hero) {
        level++;
        nextXp = nextXp + 20;
        statChooser(hero);


    }

    public void statChooser(Character hero) {
        Scanner input = new Scanner(System.in);
        Main.clearConsole();
        System.out.println("LEVEL UP!");
        System.out.println("You are now lvl " + hero.level + "!");
        System.out.println("---------- LEVEL UP ----------");
        System.out.println("HP: " + hero.health + " (+30)");
        System.out.println("ATK: " + hero.attack + " (+5)");
        System.out.println("DEF: " + hero.defence + " (+1)");
        System.out.println("------------------------------");
        System.out.println();

        System.out.print("I would like to level up my ");
        String userInput = input.nextLine();

        switch (userInput.toLowerCase()) {
            case "hp":
                addHealth(30);
                break;
            case "atk":
                addAttack(5);
                break;
            case "def":
                addDefence(1);
                break;
            default:
                System.out.println("ERROR");
                break;
        }
    }

    public void enemyReset(Character enemy1, Character enemy2){
        enemy1.health = 50; // Orc
        enemy2.health = 20; // Rat
    }

}
