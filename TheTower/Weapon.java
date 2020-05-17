import java.util.Random;
import javax.swing.JOptionPane;

public class Weapon extends Item
{
    private String material;
    private String type;
    private int attackPower;

    public Weapon(int attackPower, int price)
    {
        super("null",price, 1);
        this.attackPower = attackPower;
        this.material = getMaterial(attackPower);
        this.type = getType();
        setName(material + " " + type);
    }
    // Equips the weapon if it is not already equipped
    public void use(Player p){
        if (p.getEquippedWeapon().getName().equals(getName()) && p.getEquippedWeapon().getAttackPower() == getAttackPower()){
            JOptionPane.showMessageDialog(null, getName() + " is already equipped.");
        }
        else{
            JOptionPane.showMessageDialog(null, getName() + " equipped.");
            p.equipWeapon(this);
        }

    }

    public String getName(){
        return material + " " + type;
    }
    
    public int getAttackPower(){
        return attackPower;
    }

    // Material based on attackPower
    public String getMaterial(int attackPower){
        String [] materials = {"Wooden", "Bronze", "Iron", "Steel", "Mithril", "Adamantite", "Obsidian, Dragonforged"};

        for(int i =0 ; i < materials.length; i++){
            if(attackPower <= (i+1)*20){
                return materials[i];
            }
        }
        return "Unidentified";
    }

    public void setMaterial(String material){
        this.material = material;
    }

    // Random weapon type
    public String getType(){
        String[] types = {"Mace", "Battle Axe", "Longsword", "Sword", "Warhammer","Halberd", "Spear", "Dagger", "Claws"};
        return types[new Random().nextInt(9)];
    }

    public void setType(String type){
        this.type = type;
    }

    public String toString(){
        return getName() + " (" + attackPower + " Attack Power)";
    }

}
