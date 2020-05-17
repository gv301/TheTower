import javax.swing.JOptionPane;

public abstract class Item implements Cloneable, java.io.Serializable
{
    private String name;
    private int price;
    private int quantity;

    public Item(String name, int price, int quantity)
    {
        this.name = name;
        this.price = price;
        this.quantity = quantity;
    }

    public String getName(){
        return name;
    }

    public void setName(String name){
        this.name = name;
    }

    public int getPrice(){
        return price;
    }

    public void setPrice(int price){
        this.price = price;
    }

    public int getQuantity(){
        return quantity;
    }

    public void setQuantity(int quantity){
        this.quantity = quantity;
    }

    // Cannot use an unknown item
    public void use(Player p){
        JOptionPane.showMessageDialog(null,"Cannot use this item as it is unidentified.");
    }

    // Cloning the item
    public Object clone()throws CloneNotSupportedException{  
        return super.clone();  
    }  

}
