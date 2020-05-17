import javax.swing.ImageIcon;
import java.util.Random;

public class Brawler extends Enemy{
    private final ImageIcon down = new ImageIcon("brawlerdown.png");
    private final ImageIcon left = new ImageIcon("brawlerleft.png");
    private final ImageIcon right = new ImageIcon("brawlerright.png");
    private final ImageIcon up = new ImageIcon("brawlerup.png");

    // No weapon, brawler uses their fists
    private int strength;

    // Brawlers have balanced HP and strength
    public Brawler(int floorNumber){
        super("a", floorNumber*10, floorNumber*80  + (1 + new Random().nextInt(10)));
        setImage(new ImageIcon("brawlerdown.png").getImage());
        setName(randomName() + " the Brawler");
        int first = floorNumber * 5;
        int second = floorNumber* 10;
        int random = first + new Random().nextInt(second);
        this.strength = (int) (random +(floorNumber*10));
    }

    // Overriding Character attack with Brawler attack
    // Based on strength
    public int attack(){
        return getAttackPower() + strength;
    }

    //Setting image based on direction
    public void move(int height, int width) {
        super.move(height, width);
        if(getDirection() == 0){setImage(up.getImage());}
        else if(getDirection() == 1){setImage(down.getImage());}
        else if(getDirection() == 2){setImage(left.getImage());}
        else if(getDirection() == 3){setImage(right.getImage());}
    }

}
