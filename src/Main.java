import java.util.Random;
import java.util.Scanner;


public class Main {
    public static void main(String[] args) {
        Scanner input = new Scanner(System.in);


        // Welcome screen
        welcomeScreen(input);

        // Character initialization and name chooser
        String name = nameChooser(input);
        Character[] character = characterInitializer(name);
        Character hero = character[0];
        Character enemy1 = character[1];
        Character enemy2 = character[2];

        // Character stats
        titleArt();
        hero.printStats();
        waitForInput(input);

        // main menu
        mainMenuController(input, hero, enemy1, enemy2);

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

    public static void fightController(Character hero, Character enemy, Scanner input, int xpGained) {
        while (hero.health > 0 && enemy.health > 0) {
            clearConsole();
            enemy.takeDamage(hero.attack - enemy.defence);
            System.out.println("You swing your axe the " + enemy.name + " and deal " + (hero.attack - enemy.defence) + " dmg! \n");
            System.out.println(hero.name + " HP: " + hero.health);
            System.out.println(enemy.name + " HP: " + enemy.health + "(-" + (hero.attack - enemy.defence) + ")");

            // pause mellem attacks
            sleepCatcher(3);

            if (hero.health > 0 && enemy.health > 0) {

                clearConsole();
                hero.takeDamage(enemy.attack - hero.defence);
                System.out.println("The " + enemy.name + " swings it's hammer against you and deals " + (enemy.attack - hero.defence) + " dmg! \n");
                System.out.println(hero.name + " HP: " + hero.health + "(-" + (enemy.attack - hero.defence) + ")");
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

            hero.addXp(xpGained);
            System.out.println("You've gained " + xpGained + " xp!");
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
                e.printStackTrace();
            }
        }
    }
    public static Character[] characterInitializer(String name) {
        // Hero instance creation
        Character hero = new Character(name, 100, 15, 10, 0);
        clearConsole();

        // Enemy creation
        Character enemy1 = new Character("Orc", 50, 15, 5, 10);
        Character enemy2 = new Character("Rat", 20, 10, 0, 2);
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

    public static int mainMenu(Scanner input, Character hero) {
        if(hero.xp >= hero.nextXp){
            hero.levelUp(hero);
        }
        if (hero.health > 0) {
            clearConsole();
            titleArt();
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

    public static void mainMenuController(Scanner input, Character hero, Character enemy1, Character enemy2) {
        boolean quit = false;
        do {
            hero.enemyReset(enemy1, enemy2);
            switch (mainMenu(input, hero)) {
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

    public static void enemyEncounter(Character hero, Character enemy1, Character enemy2, Scanner input) {

        Character enemy = enemyPicker(enemy1, enemy2);
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
                    fightController(hero, enemy, input, enemy.xp);
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

    public static Character enemyPicker(Character enemy1, Character enemy2) {
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