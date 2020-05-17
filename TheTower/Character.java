import java.awt.Image;
import java.awt.Rectangle;

public abstract class Character
{
    // Attributes
    private final int STEP_SIZE = 5;
    private final int width = 25;
    private final int height = 32;
    
    private String name;
    private Image image;
    private int attackPower, maxHp, currentHp;
    private int x, y;
    private int direction;

    public Character(String name, Image image, int attackPower, int maxHp){
        this.name = name;
        this.attackPower = attackPower;
        this.maxHp = maxHp;
        this.currentHp = maxHp;
        this.image = image;
        this.x = 0;
        this.y = 0;
    }

    // Behaviour
    public int attack(){
        return attackPower;
    }

    // Character step for given direction
    public void step(int direction, int amount){
        if(direction == 0){ y -= amount;}
        else if(direction == 1){ y += amount;}
        else if(direction == 2){ x -= amount;}
        else if(direction == 3){ x += amount;}
    }

    // Getters and setters
    public int getStepSize(){
        return STEP_SIZE;
    }
    
    public Image getImage(){
        return image;
    }

    public void setImage(Image i){
        this.image = i;
    }

    public String getName(){
        return name;
    }

    public void setName(String name){
        this.name = name;
    }

    public int getAttackPower(){
        return attackPower;
    }

    public void setAttackPower(int attackPower){
        this.attackPower = attackPower;
    }

    public int getMaxHp(){
        return maxHp;
    }

    public void setMaxHp(int maxHp){
        this.maxHp = maxHp;
    }

    public int getCurrentHp(){
        return currentHp;
    }

    public void setCurrentHp(int currentHp){
        this.currentHp=currentHp;
    }

    public int getX(){
        return x;
    }

    public int getY(){
        return y;
    }

    public void setX(int x){
        this.x = x; 
    }

    public void setY(int y){
        this.y = y; 
    }

    public int getDirection(){
        return direction;
    }

    public void setDirection(int direction){
        this.direction = direction;
    }

    public int getOppositeDirection(){
        if(direction == 0){ return 1;}
        else if (direction == 1){return 0;}
        else if (direction == 2){return 3;}
        else if (direction == 3){return 2;}
        return 0;
    }

    public Rectangle getBounds(){
        return new Rectangle(x, y, width, height);
    }

    public String getWeapon(){
        return "fists";
    }

}
