import javax.swing.JFrame;
import javax.swing.JOptionPane;
import java.io.FileInputStream;
import java.io.ObjectInputStream;
import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.io.FileNotFoundException;

public class Game extends JFrame {
    private Floor floor;
    private Player player;
    private final int TOTAL_FLOORS = 10;
    private ObjectInputStream in;
    private BufferedReader br;

    public Game() {
        setSize(267, 289);
        setResizable(false);

        setLocationRelativeTo(null);

        setTitle("The Tower");
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setVisible(false);
    }

    public void newGame(){
        // Menu msg of the game
        menuMsg();

        // Creates a player
        createPlayer();

        // Asks player if they want to start the game by entering the first floor
        enterFloorChoice();

        // Creating a new floor and adding it to the JFrame
        floor = new Floor(player,1,TOTAL_FLOORS,5);
        add(floor);
        setVisible(true);
    }

    // Loads game
    public void loadGame(){
        try{
            loadPlayer();
            Shop s = loadShop();
            loadFloor();
            floor.setShop(s);

            in.close();
            br.close();

            add(floor);
            floor.menu();
            setVisible(true);

        }
        catch(IOException | ClassNotFoundException e){
            JOptionPane.showMessageDialog( null,"Error loading game");
        }
    }
   
    // Loads floor
    public void loadFloor() throws IOException, FileNotFoundException{
        br = new BufferedReader(new FileReader ("floorVariables.txt"));
        int floorNumber = Integer.parseInt(br.readLine());       
        int totalFloors = Integer.parseInt(br.readLine());        
        int totalEnemies = Integer.parseInt(br.readLine());
        floor = new Floor(player, floorNumber, totalFloors, totalEnemies);
    }

    // Loads player
    public void loadPlayer() throws IOException, FileNotFoundException, ClassNotFoundException{
        br = new BufferedReader(new FileReader ("playerVariables.txt"));
        String name = br.readLine();       
        int gold = Integer.parseInt(br.readLine());        
        int attackPower = Integer.parseInt(br.readLine());
        int maxHp = Integer.parseInt(br.readLine());
        player = new Player(name);
        player.setGold(gold);
        player.setAttackPower(attackPower);
        player.setCurrentHp(maxHp);
        player.setMaxHp(maxHp);

        in = new ObjectInputStream(new FileInputStream("playerBag.ser"));
        Bag b = (Bag)in.readObject();
        player.setBag(b);

        in = new ObjectInputStream(new FileInputStream("playerWeapon.ser"));
        Weapon w = (Weapon)in.readObject();
        player.setEquippedWeapon(w);
    }

    // Loads shop
    public Shop loadShop() throws IOException, FileNotFoundException, ClassNotFoundException{
        in = new ObjectInputStream(new FileInputStream("shop.ser"));
        Shop s = (Shop)in.readObject();
        return s;
    }

    public void menuMsg() {
        JOptionPane.showMessageDialog( null, 
            Printer.printLine(25) + Printer.printHeader("The Tower",25,25) + Printer.printLine(25) 
            + Printer.printHeader("How to play:",40,0)
            + Printer.printLineBreak(1)
            +"\n" + TOTAL_FLOORS + " floors full of enemies." 
            +"\nDefeat all enemies to move to the next floor."
            +"\nAt the end of each floor is a shop where you can purchase potions and weapons."
            +"\nOnly one attack potion can be used per floor."
            +Printer.printHeader("\nMove with w, a, s, d", 0, 40));
    }

    public void createPlayer(){
        String name = JOptionPane.showInputDialog("\nWhat is your name?");
        player = new Player(name);
    }

    // Asks the player if they want to start the game by entering the first floor
    public void enterFloorChoice(){
        String choice = JOptionPane.showInputDialog("\nAre you ready to enter the 1st floor " + player.getName() + "? (Y/N)");

        if (!choice.equalsIgnoreCase("Y")) {
            JOptionPane.showMessageDialog(null, "Goodbye");
            System.exit(0);
        }
    }

}
