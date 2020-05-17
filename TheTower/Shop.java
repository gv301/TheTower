import java.util.ArrayList;
import java.util.Random;
import javax.swing.JOptionPane;

public class Shop implements java.io.Serializable{

    // Shop has a selection of items in stock (potions and weapons)
    private ArrayList<Item> stock;

    public Shop(int floorNumber, Player player) {
        stock = new ArrayList<Item>();
        stockPotions(floorNumber);
        stockWeapons(floorNumber);
        shopMenu(player);
    }

    public void shopMenu(Player player){
        String furtherChoice = JOptionPane.showInputDialog(player.printInfo() + printStock()
                +"\n(1) Purchase an item"+ "\n(2) Sell an item"+ "\n(3) Leave");
        while(!furtherChoice.equals("3")){
            if(furtherChoice.equals("1")){
                buy(player);
            }
            else if(furtherChoice.equals("2")){
                sell(player);
            }
            else {
                JOptionPane.showMessageDialog(null,"Please choose 1, 2, or 3");
            }
            furtherChoice = JOptionPane.showInputDialog(player.printInfo() + printStock()
                +"\n(1) Purchase an item"+ "\n(2) Sell an item"+ "\n(3) Leave");
        }

    }

    // Handles purchase
    public void buy(Player player){
        String itemChoice = JOptionPane.showInputDialog(printStock()
                +"\n Choose an item");
        for (int j = 0; j < stock.size(); j++) {

            if (itemChoice.equals("" + (j + 1))) {
                Item shopItem = stock.get(j);

                if(player.getGold() >= shopItem.getPrice()){
                    if(player.getBag().getBagSpace()>0){
                        JOptionPane.showMessageDialog(null, shopItem + " purchased for " + shopItem.getPrice() + " gold.");
                        player.setGold(player.getGold()-shopItem.getPrice());
                        player.getBag().addItem(shopItem);
                        removeItem(shopItem);
                    }
                    else {
                        JOptionPane.showMessageDialog(null, "No space in inventory.");
                    }
                }
                else {
                    JOptionPane.showMessageDialog(null, "You do not have enough gold.");
                }
                break;
            }
        }
    }

    // Handles sale
    public void sell(Player player){
        String s = "";
        s+= Printer.printHeader("Inventory: " + (player.getBag().getBagCapacity() - player.getBag().getBagSpace()) + "/" + player.getBag().getBagCapacity() 
            + "             Gold: " + player.getGold(),75, 75);
        for (int k = 0; k < player.getBag().getItems().size(); k++) {
            String bagItem = "(" + (k+1) +") "+ player.getBag().getItems().get(k).getQuantity() + "x " + player.getBag().getItems().get(k);
            s+="\n" + bagItem;
            s+= Printer.printSpace(65 -bagItem.length());
            s+="Price: " + player.getBag().getItems().get(k).getPrice() +" gold";
            s+="\n";
        }
        s+= Printer.printLine(80);

        String itemChoice =JOptionPane.showInputDialog(s + "\nChoose an item: ");
        for (int j = 0; j < player.getBag().getItems().size(); j++) {

            if (itemChoice.equals("" + (j + 1))) {
                Item playerItem = player.getBag().getItems().get(j);

                if(playerItem instanceof Weapon){
                    Weapon w = (Weapon) playerItem;
                    if(w.getName().equals(player.getEquippedWeapon().getName()) && w.getAttackPower() == player.getEquippedWeapon().getAttackPower()){
                        player.equipWeapon(new Weapon(0,0));
                    }
                }
                JOptionPane.showMessageDialog(null, playerItem + " sold for " + playerItem.getPrice() + " gold.");
                player.setGold(player.getGold()+playerItem.getPrice());
                player.getBag().getItems().remove(playerItem);

                addItem(playerItem);

                break;
            }
        }
    }

    // Adds an item to the stock
    public void addItem(Item i){
        boolean found = false;
        for(Item ii: stock){
            if(ii.getName().equals(i.getName())){
                if(i instanceof Weapon ){
                    Weapon w = (Weapon) i;
                    Weapon ww = (Weapon) ii;
                    if(w.getAttackPower() == ww.getAttackPower()){
                        ii.setQuantity(ii.getQuantity()+1);
                        found = true;
                    }
                }
                else if(i instanceof Potion){
                    ii.setQuantity(ii.getQuantity()+1);
                    found = true;
                }
                break;
            }
        }

        if(!found){
            try{
                Item cloned = (Item) i.clone();
                cloned.setQuantity(1);
                stock.add(cloned);
            }
            catch(CloneNotSupportedException c){
            }
        }
    }

    // Removes an item from the stock
    public void removeItem(Item i){
        Item shopItem = null;
        for(Item ii: stock){
            if(ii.getName().equals(i.getName())){
                if(i instanceof Weapon ){
                    shopItem = ii;
                    Weapon w = (Weapon) i;
                    Weapon ww = (Weapon) ii;
                    if(w.getName().equals(ww.getName()) && w.getAttackPower() == ww.getAttackPower()){
                        ii.setQuantity(ii.getQuantity()-1); 
                    }
                }
                else if(i instanceof Potion){
                    shopItem = ii;
                    ii.setQuantity(ii.getQuantity()-1);
                }

            }
        }

        if(shopItem.getQuantity() <= 0){
            stock.remove(shopItem);
        }
    }

    // Stocks between 2 and 5 weapons, attack power based on floor number and price based on attack power 
    private void stockWeapons(int floorNumber) {
        ArrayList<Item> weapons = new ArrayList<Item>();
        int total = (2 + new Random().nextInt(4));
        while (weapons.size() < total) {
            int r = new Random().nextInt(floorNumber * 10);
            Weapon w = new Weapon(r + (floorNumber * 10), 0);
            w.setPrice(w.getAttackPower() * (5 + new Random().nextInt(6)));
            if (!weapons.contains(w)) {
                weapons.add(w);
            }
        }
        stock.addAll(weapons);
    }

    // Stocks between 5 and 10 health and attack potions, rarity based on floorNumber
    private void stockPotions(int floorNumber) {
        ArrayList<Item> potions = new ArrayList<Item>();
        String[] r = Potion.getRarity();
        String rarity = floorNumber <= 4 ? r[1] : floorNumber <= 6 ? r[2] : floorNumber <= 8 ? r[3]
            : floorNumber <= 10 ? r[4] : "null";
        potions.add(new HealthPotion(rarity, 5 + new Random().nextInt(6)));
        potions.add(new AttackPotion(rarity, 5 + new Random().nextInt(6)));
        stock.addAll(potions);
    }

    // Returns the items in stock
    public ArrayList<Item> getStock() {
        return stock;
    }

    public String printStock(){
        String s ="\n";
        s+= Printer.printHeader("Shop",30, 80);
        for (int k = 0; k < stock.size(); k++) {
            String shopItem = "(" + (k+1) +") "+ stock.get(k).getQuantity() + "x " + stock.get(k);
            s+= (shopItem + Printer.printSpace(65 - shopItem.length()) +"Price: " + stock.get(k).getPrice() +" gold");
            s+="\n";
        }
        s+=Printer.printLine(80);

        return s;
    }

}
