import javax.swing.JOptionPane;

public abstract class Potion extends Item
{
    private static final String [] rarity = {"Common", "Uncommon", "Rare", "Very rare", "Legendary"};

    public Potion(String name, int price, int quantity)
    {
        super(name, price, quantity);
    }
    

    public static String[] getRarity(){
        return rarity;
    }
    
    // Cannot use an unknown potion 
    public void use(Player p){
        JOptionPane.showMessageDialog(null,"Cannot use this potion as it is not a health or attack potion.");
    }
}
