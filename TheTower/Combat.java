import java.util.ArrayList;
import javax.swing.JOptionPane;

// Battle system between player and enemy
public class Combat implements java.io.Serializable
{
    private Enemy e;
    private Player player;
    private ArrayList<Enemy> enemies;

    public Combat(Player player, Enemy e, ArrayList<Enemy> enemies)
    {   
        this.player = player;
        this.e = e;
        this.enemies = enemies;
    }

    public void fight(){
        while (true) {
            String choice = fightMenu();
            // Attack
            if (choice.equals("1")) {
                playerAttack();
                // Enemy attacks if they are alive otherwise they have been defeated, end program if player dies
                if(e.getCurrentHp()>0){
                    enemyAttack();
                    endIfPlayerDies();
                }
                else{
                    JOptionPane.showMessageDialog(null, "You have defeated " + e.getName());
                    enemies.remove(e);
                    break;
                }
            } 
            // Use item 
            // (Polymorphism (different behaviour based on item (health, attack potion, and weapon)
            else if (choice.equals("2")) {
                //Print player items
                ArrayList<Item> bag = player.getBag().getItems();

                String s ="Inventory (" + (player.getBag().getBagCapacity()-player.getBag().getBagSpace()) +"/" + player.getBag().getBagCapacity() +") :\n";
                for (int i = 0; i < bag.size(); i++) {
                    s+=("(" + (i + 1) + ") " + bag.get(i).getQuantity() + "x " + bag.get(i));
                    s+="\n";
                }
                s+="(" + (bag.size()+1) + ") Exit";
                String itemChoice = JOptionPane.showInputDialog(s);

                for (int i = 0; i < bag.size(); i++) {
                    if (itemChoice.equals("" + (i + 1))) {
                        bag.get(i).use(player);
                        break;
                    }
                }
            } 
            else if (choice.equals("3")){
                disengage();
                break;
            }
        }
    }

    // Prints the options available to the player
    public String fightMenu(){
        return JOptionPane.showInputDialog(printEnemyStats() + printPlayerStats()
            +"\n(1) Attack"
            + "\n(2) Use item"
            +"\n(3) Run");
    }

    public void playerAttack(){
        // Attack values of player
        int playerAttack = player.attack();

        // isCritical set to true if playerAttack is negative
        boolean isCritical = playerAttack > 0 ? false : true;

        // playerAttack is turned positive it if is it is critical (negative previously)
        playerAttack = isCritical ? playerAttack * -1 : playerAttack;

        // Message to print based on whether it is a normal or critical hit
        String normOrCrit = isCritical ? " (critical hit)." : ".";

        // Updating enemy current hp based on the attack
        e.setCurrentHp(e.getCurrentHp() - playerAttack);

        // Printing player attack
        JOptionPane.showMessageDialog(null,"You attack " + e.getName() + " with your "
            + player.getWeapon() + " dealing " + playerAttack + " damage" + normOrCrit);
    }

    public void enemyAttack(){
        // Polymporphism and dynamic binding(Enemy is either a Wizard, Assassin, or Brawler which overrides default Enemy superclass attack method)
        int enemyAttack = e.attack();

        // Updating player current hp based on the attack
        player.setCurrentHp(player.getCurrentHp() - enemyAttack);

        // Printing enemy attack
        JOptionPane.showMessageDialog(null,e.getName() + " attacks you with their " + e.getWeapon() + " dealing "
            + enemyAttack + " damage.");

    }

    // Disengage enemy and player contact
    public void disengage(){
        player.step(player.getOppositeDirection(),10);

    }

    public void endIfPlayerDies(){
        if (player.getCurrentHp() <= 0) {
            JOptionPane.showMessageDialog(null, "Your journey ends here."
                +"\nYou have been defeated by " + e.getName() + "."
                +"\nGoodbye.");
            System.exit(0);
        }
    }

    public String printPlayerStats(){
        String ap = (player.getBoost() > 0) ? "/" + (player.getAttackPower()-player.getBoost()): "";
        return Printer.printHeader(player.getName()
            + "\nHP: " + player.getCurrentHp() + "/" + player.getMaxHp()
            + "\nAttack Power: " + player.getAttackPower() + ap
            +"\nWeapon: " + player.getWeapon(),25, 25);
    }

    public String  printEnemyStats(){
        return Printer.printHeader(e.getName()
            + "\nHP: " + e.getCurrentHp() + "/" + e.getMaxHp()
            + "\nAttack Power: " + e.attack()+"\n", 25, 0);
    }

}

