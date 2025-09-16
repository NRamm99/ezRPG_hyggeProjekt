public class Character {

    // felter
    String name;
    int health;
    int attack;
    int defence;

    // Constructor
    public Character(String name, int health, int attack, int defence) {
        this.name = name;
        this.health = health;
        this.attack = attack;
        this.defence = defence;
    }

    public void printStats() {
        System.out.println("---------- STATS ----------");
        System.out.println("Name: " + name);
        System.out.println("HP: " + health);
        System.out.println("ATK: " + attack);
        System.out.println("DEF: " + defence);
        System.out.println("---------------------------");
        System.out.println();
    }

    void takeDamage(int damage) {
        this.health -= damage;
    }

}
