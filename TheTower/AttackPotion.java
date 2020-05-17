import javax.swing.JOptionPane;

public class AttackPotion extends Potion
{
    private int boost;

    public AttackPotion(String rarity, int quantity){
        super(rarity + " attack potion",0, quantity);
        this.boost = getBoostValue(rarity);
        setPrice(getPrice(rarity));

    }

    // Overrides item use method to boost player attack
    public void use(Player p){
        if(p.getBoost() == 0){
            p.setAttackPower(p.getAttackPower() + boost);
            p.setBoost(boost);

            JOptionPane.showMessageDialog(null,getName() + " used.");
            p.getBag().removeItem(this);
        }
        else{
            JOptionPane.showMessageDialog(null,"You have already used an attack potion for this floor");

        }
    }


    // Gets the boost value of the specified rarity potion
    public int getBoostValue(String rarity){
        String[] r = getRarity();

        for(int i = 0; i < r.length; i++){

            if(r[i].equals(rarity)){
                return (i+1)*10;
            }
        }
        return 10;
    }

    // gets the price of the specified potion rarity
    public int getPrice(String rarity){
        String[] r = getRarity();

        for(int i = 0; i < r.length; i++){

            if(r[i].equals(rarity)){
                return (i+1)*150;
            }
        }
        return 5;
    }

    // Overriding toString method
    public String toString()
    {
        return getName() + " (Boosts Attack Power by " + boost +")";
    }

}
