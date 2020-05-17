import javax.swing.JFrame;
import javax.swing.JButton;
import javax.swing.JOptionPane;
import java.awt.FlowLayout;
import java.awt.Dimension;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.FileInputStream;
import java.io.ObjectInputStream;
import java.io.IOException;

public class MainMenu extends JFrame implements ActionListener
{
    // Setting up the JFrame
    public MainMenu()
    {
        super("The Tower");
        setLayout(new FlowLayout());

        JButton newGame = new JButton("New game");
        JButton loadGame = new JButton("Load game");

        newGame.setPreferredSize(new Dimension(150, 50));
        loadGame.setPreferredSize(new Dimension(150, 50));

        add(newGame);
        add(loadGame);

        newGame.addActionListener(this);
        loadGame.addActionListener(this);

        pack();
        setLocationRelativeTo(null);

        show();

    }
    
    // Starts a new game or loads game based on button pressed
    public void actionPerformed(ActionEvent e){
        super.dispose();
        if(e.getActionCommand().equals("New game")){
            new Game().newGame();
        }
        else {
            new Game().loadGame();
        }
    }

    public static void main(String[] args){
        new MainMenu();

    }
}
