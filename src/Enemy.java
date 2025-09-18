public class Enemy extends Character {

    int xpYield;

    public Enemy(String name, int health, int attack, int defence, int xpYield) {
        super(name, health, attack, defence, 0);
        this.xpYield = xpYield;
    }


}