import javax.swing.ImageIcon;
import java.util.Random;

public class Assassin extends Enemy{
    private final ImageIcon down = new ImageIcon("assassindown.png");
    private final ImageIcon left = new ImageIcon("assassinleft.png");
    private final ImageIcon right = new ImageIcon("assassinright.png");
    private final ImageIcon up = new ImageIcon("assassinup.png");

    private int agility;
    private Weapon weapon;

    // Assassins have high attack damage but low HP
    public Assassin(int floorNumber){
        super("a", floorNumber*20, floorNumber*50  + (1 + new Random().nextInt(10)));
        setImage(new ImageIcon("assassindown.png").getImage());
        setName(randomName() + " the Assassin");
        int first = floorNumber * 2;
        int second = floorNumber* 5;
        int random = first + new Random().nextInt(second);
        this.agility = (int) (random +(floorNumber*10));
        this.weapon = randomWeapon(floorNumber);
    }

    // Overriding Character attack with Assassin attack
    // Based on weapon attack power and agility
    public int attack(){
        return getAttackPower() + weapon.getAttackPower() + agility;
    }

    //Setting image based on direction
    public void move(int height, int width) {
        super.move(height, width);
        if(getDirection() == 0){setImage(up.getImage());}
        else if(getDirection() == 1){setImage(down.getImage());}
        else if(getDirection() == 2){setImage(left.getImage());}
        else if(getDirection() == 3){setImage(right.getImage());}
    }
    
    // Returns a random weapon with attack power based on floorNumber
    public Weapon randomWeapon(int floorNumber){
        Weapon w = new Weapon((5 + new Random().nextInt(11)) * floorNumber,0);
        w.setMaterial("Poison");
        return w;
    }

    // Overriding character getWeapon with assassins weapon
    public String getWeapon(){
        return weapon.getName();
    }
}
