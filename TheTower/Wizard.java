import javax.swing.ImageIcon;
import java.util.Random;

public class Wizard extends Enemy
{
    private final ImageIcon down = new ImageIcon("wizarddown.png");
    private final ImageIcon left = new ImageIcon("wizardleft.png");
    private final ImageIcon right = new ImageIcon("wizardright.png");
    private final ImageIcon up = new ImageIcon("wizardup.png");

    private Weapon weapon;
    private int magic;

    // Wizards have high HP but low attack power
    public Wizard(int floorNumber){
        super("a", floorNumber*5, floorNumber*120  + (1 + new Random().nextInt(10)));
        setImage(new ImageIcon("wizarddown.png").getImage());
        setName(randomName() + " the Wizard");
        this.magic = (int) (Math.random() * (floorNumber*10));
        this.weapon = randomWeapon(floorNumber);
    }

    // Returns a random Wizard weapon with attack power based on floorNumber
    public Weapon randomWeapon(int floorNumber){
        String[] type = {"Greatsword", "Mace", "Sceptre", "Staff", "Sword", "Rod", "Wand"};

        Weapon w = new Weapon((5 + new Random().nextInt(11)) * floorNumber,0);
        w.setMaterial("Magical");
        w.setType(type[new Random().nextInt(type.length)]);
        return w;
    }

    // Overriding Character attack with Wizard attack
    // Based on weapon attack power and magic
    public int attack(){
        return getAttackPower() + weapon.getAttackPower() + magic;
    }

    //Setting image based on direction
    public void move(int height, int width) {
        super.move(height, width);
        if(getDirection() == 0){setImage(up.getImage());}
        else if(getDirection() == 1){setImage(down.getImage());}
        else if(getDirection() == 2){setImage(left.getImage());}
        else if(getDirection() == 3){setImage(right.getImage());}
    }
    
    // Overriding character getWeapon with Wizards weapon
    public String getWeapon(){
        return weapon.getName();
    }

}
