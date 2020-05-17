import javax.swing.JOptionPane;

public class HealthPotion extends Potion
{
    private int heal;

    public HealthPotion(String rarity, int quantity)
    {
        super(rarity + " health potion", 0, quantity);
        this.heal = getHealValue(rarity);
        setPrice(getPrice(rarity));

    }

    // Overrides potion use method to heal player
    public void use(Player p){
        if(p.getCurrentHp() < p.getMaxHp()){
            p.setCurrentHp(p.getCurrentHp() + heal);

            if(p.getCurrentHp() > p.getMaxHp()){
                p.setCurrentHp(p.getMaxHp());
            }
            JOptionPane.showMessageDialog(null,getName() + " used.");
            p.getBag().removeItem(this);
        }
        else{
            JOptionPane.showMessageDialog(null, "You are already full HP");
        }

    }

    // Gets the heal value of the specified rarity potion
    public int getHealValue(String rarity){
        String[] r = getRarity();

        for(int i = 0; i < r.length; i++){

            if(r[i].equals(rarity)){
                return (i+1)*50;
            }
        }
        return 5;
    }

    // gets the price of the specified potion rarity
    public int getPrice(String rarity){
        String[] r = getRarity();

        for(int i = 0; i < r.length; i++){

            if(r[i].equals(rarity)){
                return (i+1)*100;
            }
        }
        return 5;
    }

    // Overriding toString method
    public String toString()
    {
        return getName() + " (Restores " + heal + " HP)";
    }
}
