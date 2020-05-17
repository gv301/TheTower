import java.util.ArrayList;

public class Bag implements java.io.Serializable
{
    private ArrayList<Item> items;
    private int capacity;

    public Bag(int capacity)
    {
        this.items = new ArrayList<Item>();
        this.capacity = capacity;
    }

    // Adds an item to the bag
    public void addItem(Item i){
        boolean found = false;
        for(Item ii: items){
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
                items.add(cloned);
            }
            catch(CloneNotSupportedException c){
            }
        }
    }

    public void removeItem(Item i){
        Item playerItem = null;
        for(Item ii: items){
            if(ii.getName().equals(i.getName())){
                if(i instanceof Weapon ){
                    playerItem = ii;
                    Weapon w = (Weapon) i;
                    Weapon ww = (Weapon) ii;
                    if(w.getAttackPower() == ww.getAttackPower()){
                        ii.setQuantity(ii.getQuantity()-1); 
                    }
                }
                else if(i instanceof Potion){
                    playerItem = ii;
                    ii.setQuantity(ii.getQuantity()-1);
                }

            }
        }
        
        if(playerItem.getQuantity() <= 0){
            items.remove(playerItem);
        }
    }

    // Returns the bag space
    public int getBagSpace(){
        int total = 0;
        for(Item i: items){
            total += i.getQuantity();
        }
        return capacity - total;
    }


    // Returns the bag
    public ArrayList<Item> getItems(){
        return items;
    }
    // Returns the bag capacity
    public int getBagCapacity(){
        return capacity;
    }
}
