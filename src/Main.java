import java.util.Random;
import java.util.Scanner;


public class Main {
    public static void main(String[] args) {
        Scanner input = new Scanner(System.in);
        int mapLevel = 1;
        GameMap map = new GameMap(mapLevel);

        // Welcome screen
        welcomeScreen(input);

        // Character initialization and name chooser
        String name = nameChooser(input);
        Character[] characters = characterInitializer(name);

        Hero hero = (Hero) characters[0];
        Enemy enemy1 = (Enemy) characters[1];
        Enemy enemy2 = (Enemy) characters[2];

        // Character stats
        titleArt();
        hero.printStats();
        waitForInput(input);

        // main menu
        mainMenuController(input, hero, enemy1, enemy2, map, mapLevel);

        // Game over
        clearConsole();
        hero.printStats();
        System.out.println();
        System.out.println("GAME OVER");
    }

    public static void clearConsole() {
        for (int n = 0; n < 20; n++) {
            System.out.println();
        }
    }

    public static void waitForInput(Scanner input) {
        System.out.println("\nPress Enter to continue...");
        input.nextLine();
    }

    public static void fightController(Hero hero, Enemy enemy, Scanner input, int xpYield) {
        while (hero.health > 0 && enemy.health > 0) {
            clearConsole();
            enemy.takeDamage(hero.damageCalculator(hero.attack, enemy.defence));
            System.out.println("You swing your axe the " + enemy.name + " and deal " + (hero.damageCalculator(hero.attack, enemy.defence)) + " dmg! \n");
            System.out.println(hero.name + " HP: " + hero.health);
            System.out.println(enemy.name + " HP: " + enemy.health + "(-" + (hero.damageCalculator(hero.attack, enemy.defence)) + ")");

            // pause mellem attacks
            sleepCatcher(3);

            if (hero.health > 0 && enemy.health > 0) {

                clearConsole();
                hero.takeDamage(hero.damageCalculator(enemy.attack, hero.defence));
                System.out.println("The " + enemy.name + " swings it's hammer against you and deals " + (hero.damageCalculator(enemy.attack, hero.defence)) + " dmg! \n");
                System.out.println(hero.name + " HP: " + hero.health + "(-" + (hero.damageCalculator(enemy.attack, hero.defence)) + ")");
                System.out.println(enemy.name + " HP: " + enemy.health);

                // pause mellem attacks
                sleepCatcher(3);
            }
        }
        // Fight outcome
        System.out.println();
        if (hero.health > 0) { // Hero wins
            clearConsole();
            System.out.println(hero.name + " defeats " + enemy.name + " in battle.");
            System.out.println("Well done " + hero.name + "!");

            hero.addXp(xpYield);
            System.out.println("You've gained " + xpYield + " xp!");
            waitForInput(input);
        } else if (enemy.health > 0) { // Hero dies
            clearConsole();
            System.out.println(enemy.name + " defeats " + hero.name + " in battle.");
            System.out.println("Oh no!");
            waitForInput(input);
        } else { // Error
            clearConsole();
            System.out.println("ERROR - NO WINNER FOUND");
            waitForInput(input);
        }
    }

    public static void sleepCatcher(int seconds) {
        System.out.println();
        for (int i = seconds; i > 0; i--) {
            System.out.print(i + "... ");
            try {
                Thread.sleep(1000); // vent 1 sec
            } catch (InterruptedException e) {
                System.out.println("Sleep was interrupted!");
                Thread.currentThread().interrupt(); // good practice
            }
        }
    }

    public static Character[] characterInitializer(String name) {
        Hero hero = new Hero(name, 100, 10, 5, 0);
        Enemy enemy1 = new Enemy("Orc", 50, 6, 3, 5);
        Enemy enemy2 = new Enemy("Rat", 20, 2, 1, 2);

        return new Character[]{hero, enemy1, enemy2};
    }

    public static void titleArt() {
        System.out.println("""
                _____________________              __________ __________   ________\s
                \\_   _____/\\____    /              \\______   \\\\______   \\ /  _____/\s
                 |    __)_   /     /      ______    |       _/ |     ___//   \\  ___\s
                 |        \\ /     /_     /_____/    |    |   \\ |    |    \\    \\_\\  \\
                /_______  //_______ \\               |____|_  / |____|     \\______  /
                        \\/         \\/                      \\/                    \\/\s""");
        System.out.println();
    }

    public static int mainMenu(Scanner input, Hero hero, GameMap map, int mapLevel) {
        if (hero.xp >= hero.nextXp) {
            hero.levelUp(hero);
        }
        if (hero.health > 0) {
            clearConsole();
            titleArt();
            map.drawMap(mapLevel);
            System.out.println("""
                    >>===================<<
                    ||     Main menu     ||
                    >>===================<<""");
            System.out.println("""
                    1... Look for enemies
                    2... My stats
                    3... Quit
                    """);
            String userInput = input.nextLine();
            return switch (userInput.toLowerCase()) {
                case "look for enemies" -> 1;
                case "my stats" -> 2;
                case "quit" -> 3;
                default -> 0;
            };
        }
        return -10;
    }

    public static void mainMenuController(Scanner input, Hero hero, Enemy enemy1, Enemy enemy2, GameMap map, int mapLevel) {
        boolean quit = false;
        do {
            enemy1.enemyReset(enemy1, enemy2);
            switch (mainMenu(input, hero, map, mapLevel)) {
                case 1: // Looking for enemies
                    enemyEncounter(hero, enemy1, enemy2, input);
                    break;
                case 2: // My stats
                    clearConsole();
                    hero.printStats();
                    waitForInput(input);
                    break;
                case 3: // Quit
                    quit = true;
                    break;
                case -10: // Game over
                    break;
                default: // Invalid input
                    System.out.println("your input was invalid.");
                    break;
            }
        } while (!quit);
    }

    public static void enemyEncounter(Hero hero, Enemy enemy1, Enemy enemy2, Scanner input) {

        Enemy enemy = enemyPicker(enemy1, enemy2);
        boolean activeEncounter = true;
        do {
            clearConsole();
            titleArt();
            System.out.println("you encounter a " + enemy.name + "!");
            System.out.println("""
                    1... Fight
                    2... Check enemy stats
                    3... Flee
                    """);
            String userInput = input.nextLine();
            switch (userInput.toLowerCase()) {
                case "fight":
                    fightController(hero, enemy, input, enemy.xpYield);
                    activeEncounter = false;
                    break;
                case "check enemy stats":
                    clearConsole();
                    titleArt();
                    enemy.printStats();
                    waitForInput(input);
                    break;
                case "flee":
                    activeEncounter = false;
                    break;
                default:
                    System.out.println("ERROR - INVALID INPUT");
                    break;
            }

        } while (activeEncounter);


    }

    public static Enemy enemyPicker(Enemy enemy1, Enemy enemy2) {
        Random rand = new Random();
        int choice = rand.nextInt(2); // 0 or 1
        if (choice == 0) {
            return enemy1;
        } else {
            return enemy2;
        }
    }


    public static void welcomeScreen(Scanner input) {
        clearConsole();
        titleArt();
        System.out.println("""
                ****************************
                * welcome to my first rpg! *
                ****************************""");
        waitForInput(input);
    }

    public static String nameChooser(Scanner input) {
        clearConsole();
        titleArt();
        System.out.println("""
                >>============================<<
                ||     Character creation     ||
                >>============================<<""");
        System.out.print("My hero should be called ");
        return input.nextLine();
    }

}