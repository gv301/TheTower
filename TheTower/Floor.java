import java.util.ArrayList;
import java.util.Random;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.Timer;
import java.awt.event.ActionListener;
import java.awt.event.ActionEvent;
import java.awt.Graphics;
import java.awt.Image;
import java.awt.Toolkit;
import java.io.FileWriter;
import java.io.PrintWriter;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.ObjectOutputStream;

public class Floor extends JPanel implements ActionListener, java.io.Serializable{
    private static final int FLOOR_HEIGHT = 200;
    private static final int FLOOR_WIDTH = 220;
    private final int DELAY = 10;
    private final String[] floors = {"1st", "2nd", "3rd", "4th", "5th", "6th", "7th", "8th", "9th", "10th"};

    private Player player;
    private int floorNumber;
    private int totalFloors;
    private int totalEnemies;
    private ArrayList<Enemy> enemies;
    private int count = 0;
    private Image image;
    private Random r;
    private Shop shop;
    Timer timer = new Timer(DELAY, this);

    // Floor setup
    public Floor(Player player, int floorNumber, int totalFloors, int totalEnemies)
    {
        this.player = player;
        this.floorNumber = floorNumber;
        this.totalFloors = totalFloors;
        this.totalEnemies = totalEnemies;
        enemies = new ArrayList<Enemy>();
        r = new Random();

        createEnemies(floorNumber);
        setEnemyPosition();

        // Background image (Floor map)
        image = Toolkit.getDefaultToolkit().createImage("map.png");

        // Player movement
        addKeyListener(player);
        setFocusable(true);

        // Timer for actionListener
        timer.start();

    }

    // Draws to the screen
    public void paintComponent(Graphics g) {
        super.paintComponent(g);

        // Draw map
        g.drawImage(image,0,0,null);

        // Draw enemies
        for(Enemy e: enemies){
            g.drawImage(e.getImage(), e.getX(), e.getY(), this);
        }

        //Draw player
        g.drawImage(player.getImage(), player.getX(), player.getY(), this);

    }

    // Paints, starts fight if player is in enemy position, when floor is cleared starts new floor
    public void actionPerformed(ActionEvent e) {
        repaint();  

        count++;
        if(count == 20){
            enemyMovement();
            count = 0;
        }

        startCombat();

        if(enemies.size() == 0 && floorNumber < totalFloors){
            repaint();
            newFloor();
        }
    }

    // Sets up a new floor
    public void newFloor(){  
        setPlayerStats(floorNumber);
        menu();
        floorNumber++;
        createEnemies(floorNumber);
        setEnemyPosition();
        resetPlayer();
    }

    // Menu of choices at end of each floor
    public void menu(){
        timer.stop();
        while(true){
            String choice = JOptionPane.showInputDialog(Printer.printLine(25) + Printer.printHeader("End of " + floors[floorNumber-1] + " floor",35,35) + Printer.printLine(25)
                    +"\n(1) Enter shop" 
                    +"\n(2) Enter " + floors[floorNumber] + " floor"
                    +"\n(3) Save game"
                    +"\n(4) Exit");

            if(choice.equals("1")){ 
                if(shop != null) {shop.shopMenu(player);} else {shop = new Shop(floorNumber, player);}}
            else if (choice.equals("2")){ break;}
            else if (choice.equals("3")){ saveFloor();}
            else if (choice.equals("4")){ System.exit(0);}
        }
        timer.start();
    }

    // Starts a new fight (handled by combat class) if the player is within enemy bounds
    public void startCombat(){
        for(Enemy ee: enemies){
            if(player.getBounds().intersects(ee.getBounds())){
                new Combat(player, ee, enemies).fight();
                break;
            }
        }
    }

    // Saves everything in the floor to a file
    public void saveFloor(){
        try{
            // Saving floor variables
            PrintWriter printWriter = new PrintWriter(new FileWriter("floorVariables.txt"));
            printWriter.println(floorNumber);
            printWriter.println(totalFloors);
            printWriter.println(totalEnemies);
            printWriter.close();

            savePlayer();
            saveShop();

            JOptionPane.showMessageDialog(null, "Game saved");
        }
        catch(IOException e)
        {
            JOptionPane.showMessageDialog(null, "Error saving game");
        }
    }

    public void savePlayer() throws IOException{
        // Saving player variables
        PrintWriter printWriter = new PrintWriter(new FileWriter("playerVariables.txt"));
        printWriter.println(player.getName());
        printWriter.println(player.getGold());
        printWriter.println(player.getAttackPower());
        printWriter.println(player.getMaxHp());
        printWriter.close();

        // Player objects
        FileOutputStream fileOut = new FileOutputStream("playerBag.ser");
        ObjectOutputStream objectOut = new ObjectOutputStream(fileOut);
        objectOut.writeObject(player.getBag());
        fileOut = new FileOutputStream("playerWeapon.ser");
        objectOut = new ObjectOutputStream(fileOut);
        objectOut.writeObject(player.getEquippedWeapon());

        objectOut.close();
        fileOut.close();
    }

    public void saveShop() throws IOException{
        FileOutputStream fileOut = new FileOutputStream("shop.ser");
        ObjectOutputStream objectOut = new ObjectOutputStream(fileOut);
        objectOut.writeObject(shop);
        objectOut.close();
        fileOut.close();
    }

    // Sets enemy to a random position within the floor that is not overlapping or start player position
    public void setEnemyPosition() {
        while (isEnemyOverlap() || isPlayerOverlap()) {
            for (Enemy e : enemies) {
                e.setX(r.nextInt(FLOOR_WIDTH));
                e.setY(r.nextInt(FLOOR_HEIGHT));
            }
        }
    }

    //Checks if the player is at the position given
    public boolean isPlayerPosition(int x, int y) {
        if (player.getX() == x && player.getY() == y) {
            return true;
        }
        return false;
    }

    //Checks if an enemy is at the position given
    public boolean isEnemyPosition(int x, int y) {
        for (Enemy e : enemies) {
            if (e.getX() == x && e.getY() == y) {
                return true;
            }
        }
        return false;
    }

    // Checks if any enemies are within 50 units of each other
    public boolean isEnemyOverlap() {
        for (int i = 0; i < enemies.size() - 1; i++) {
            for (int j = i + 1; j < enemies.size(); j++) {
                if ((Math.abs(enemies.get(i).getX()- enemies.get(j).getX()) <= 50)
                && (Math.abs(enemies.get(i).getY() - enemies.get(j).getY()) <= 50)) {
                    return true;
                }
            }
        }
        return false;
    }

    // Checks if any enemies are within 50 units of the player
    public boolean isPlayerOverlap(){
        for (Enemy e : enemies) {
            if (e.getX()<= 50 && e.getY() <= 50) {
                return true;
            }
        }
        return false;
    }

    // Substitution principle
    // Populates list of enemies with random enemies, stronger as floor number increases
    public void createEnemies(int floorNumber) {
        for (int i = 0; i < totalEnemies; i++) {
            int rr = r.nextInt(3);
            if (rr == 0) {enemies.add(new Wizard(floorNumber));} 
            else if (rr == 1) {enemies.add(new Assassin(floorNumber));} 
            else {enemies.add(new Brawler(floorNumber));}
        }
    }

    // Moves every enemy once ensuring they are not overlapping
    public void enemyMovement() {
        for (Enemy e : enemies) {
            int initialX = e.getX();
            int initialY = e.getY();
            e.move(FLOOR_HEIGHT, FLOOR_WIDTH);

            while(isEnemyOverlap()){
                e.setX(initialX);
                e.setY(initialY);
                e.move(FLOOR_HEIGHT, FLOOR_WIDTH);
            }
        }
    }

    public void resetPlayer(){
        player.setCurrentHp(player.getMaxHp());
        player.setAttackPower(player.getAttackPower()-player.getBoost());
        player.setBoost(0);
        player.setX(0);
        player.setY(0);
    }

    // Random bonuses given based on floor number
    public void setPlayerStats(int floorNumber){
        int gold = 250 + ((floorNumber * 50) + new Random().nextInt((floorNumber * 100)));
        player.setGold(player.getGold() + gold);

        int hp = 100 + ((floorNumber * 10) + new Random().nextInt((floorNumber * 20)));
        int ap = 10 + new Random().nextInt((floorNumber * 10));
        JOptionPane.showMessageDialog(null, "You have successfully defeated all enemies on this floor, gaining " + gold + " bonus gold, " + hp + " bonus hp, and " + ap + " bonus attack power as a reward.");

        player.setCurrentHp(player.getMaxHp() + hp);
        player.setMaxHp(player.getCurrentHp());

        player.setAttackPower(player.getAttackPower() + ap);
    }

    public void setShop(Shop s){
        shop = s;
    }

    public static int getFloorWidth(){
        return FLOOR_WIDTH;
    }

    public static int getFloorHeight(){
        return FLOOR_HEIGHT;
    }

}
