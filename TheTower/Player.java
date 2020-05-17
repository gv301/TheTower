import java.awt.event.KeyListener;
import java.awt.event.KeyEvent;
import javax.swing.ImageIcon;
import java.util.Random;

public class Player extends Character implements KeyListener
{
    private final ImageIcon down = new ImageIcon("playerdown.png");
    private final ImageIcon left = new ImageIcon("playerleft.png");
    private final ImageIcon right = new ImageIcon("playerright.png");
    private final ImageIcon up = new ImageIcon("playerup.png");
    
    private Bag bag;
    private Weapon weapon;
    private int gold;
    private int boost;

    //Player starting information
    public Player(String name)
    {
        super(name, null, 30,100);
        setImage(down.getImage());
        bag = new Bag(10);
        weapon = new Weapon(5, 0);
        super.setAttackPower(super.getAttackPower() + weapon.getAttackPower());
        gold = 0;
        bag.addItem(new HealthPotion("Common", 1));
        bag.addItem(new HealthPotion("Common", 1));
        bag.addItem(new HealthPotion("Common", 1));
        bag.addItem(new HealthPotion("Common", 1));
        bag.addItem(new HealthPotion("Common", 1));
        bag.addItem(new AttackPotion("Common", 1));
        bag.addItem(weapon);
        boost = 0;
    }

    //Overriding Character attack with player attack
    // Based on weapon attack power and 25% chance to crit (1.5x dmg)
    // Returns -1 x attackPower if it is a critical hit
    public int attack(){
        int standardAttack = getAttackPower();
        if(Math.random() <= 0.25){
            return (int) -(standardAttack * 1.5);
        }
        return standardAttack;
    }

    // Changing the weapon of the player
    public void equipWeapon(Weapon w){
        // Set player attack power to base attack power without weapon
        setAttackPower(getAttackPower()- weapon.getAttackPower());

        // Set player attack power to base attack power + weapon attack power being equipped
        setAttackPower(getAttackPower() + w.getAttackPower());
        weapon = w;

        if(weapon.getAttackPower() == 0 && weapon.getPrice() == 0){
            weapon.setName("Fists");
        }
    }
    
    // When w, a, s, or d is pressed
    public void keyPressed(KeyEvent e) {
        int key = e.getKeyCode();
        if (key == KeyEvent.VK_W && getY() > 0) {
            setImage(up.getImage());
            setDirection(0);
            step(0, getStepSize());
        }
        if (key == KeyEvent.VK_S && getY() < Floor.getFloorHeight()) {
            setImage(down.getImage());
            setDirection(1);
            step(1, getStepSize());
        }
        if (key == KeyEvent.VK_A && getX() > 0) {
            setImage(left.getImage());
            setDirection(2);
            step(2, getStepSize());
        }
        if (key == KeyEvent.VK_D && getX() < Floor.getFloorWidth()) {
            setImage(right.getImage());
            setDirection(3);
            step(3, getStepSize());
        }
    }

    public void keyReleased(KeyEvent e){}

    public void keyTyped(KeyEvent e){}
   
    public Bag getBag(){
        return bag;
    }
    
    public void setBag(Bag bag){
        this.bag = bag;
    }

    public int getGold(){
        return gold;
    }

    public void setGold(int gold){
        this.gold = gold;
    }

    public String getWeapon(){
        if(this.weapon.getName().equals("null")){
            return "fists";
        }
        return weapon.getName();
    }

    public int getBoost(){
        return boost;
    }

    public void setBoost(int boost){
        this.boost = boost;
    }

    public Weapon getEquippedWeapon(){
        return weapon;
    }
    
    public void setEquippedWeapon(Weapon weapon){
        this.weapon = weapon;
    }
        
    public String printInfo() {
        String s ="";
        s+= Printer.printHeader("Player: " + getName(),20, 20);
        s+= Printer.printHeader("HP: " + getCurrentHp() + "/" + getMaxHp()
            + "\nAttack Power: " + getAttackPower()
            + "\nWeapon: " + getWeapon(), 0, 40);
        s+=("Inventory: " + (bag.getBagCapacity() - bag.getBagSpace()) + "/" + bag.getBagCapacity() 
            + "             Gold: " + getGold());
        s+="\n";

        for (int i = 0; i < bag.getItems().size(); i++) {
            s+=(bag.getItems().get(i).getQuantity() + "x " + bag.getItems().get(i));
            s+="\n";
        }
        s+=Printer.printLine(40);
        return s;
    }

    // Overriding toString of character
    public String toString(){
        return "Name: " + getName()
        +"\nWeapon: " + weapon 
        +"\nBag space: " + bag.getBagSpace();
    }

}