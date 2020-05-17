import java.util.Random;

public abstract class Enemy extends Character {

    public Enemy(String name, int attackPower, int maxHp){
        super(name, null, attackPower, maxHp);
    }

    // Generates a random name
    public String randomName(){
        String [] names = 
            {"Cassemir","Cecimeric","Bafon","Gustall","Enwyld",
                "Aldwulf","Bamadis","Brythan","Namior","Evarius",
                "Vainira","Kessia","K'lani", "Eissen", "Wrena",
                "Halline","Favena","Lyndys","Ometa","Iowyn",
                "Eloso","Roclus","Del","Waul","Diadys",
                "Hadwyn","Maxith","Gaemo","Lashur","Cedoric",
                "Jefor","Rillon","Carpef","Killan","Kondor",
                "Evior","Etior","Pevras","Groshan","Iroqihr",
                "Elin","Shosior","Ebeus","Uwyn","Irune"};

        return names[new Random().nextInt(names.length)];
    }

    // Enemy movement algorithm. 80% chance to move in same direction, 20% chance to change direction
    public void move(int height, int width) {
        Random r = new Random();
        int previousDirection = getDirection();
        int newDirection = r.nextInt(4);
        while(previousDirection == newDirection){
            newDirection = r.nextInt(4);
        }

        int direction = (Math.random()<= 0.2) ? newDirection: previousDirection; 

        if (direction == 0) {
            if (getY() > 5) {
                setDirection(0);
                step(0, getStepSize());
            }
        } else if (direction == 1) {
            if (getY() < height - 5) {
                setDirection(1);
                step(1, getStepSize());
            }
        } else if (direction == 2) {
            if (getX() > 5) {
                setDirection(2);
                step(2, getStepSize());
            }
        } else if (direction == 3) {
            if (getX() < width - 5) {
                setDirection(3);
                step(3, getStepSize());
            }
        }
    }

}

 