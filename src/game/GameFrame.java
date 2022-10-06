package game;
import javax.swing.*;
public class GameFrame extends JFrame {
    GameFrame(){
        ImageIcon icon = new ImageIcon("logo.png");
        add(new GamePanel());
        setIconImage(icon.getImage());
        setTitle("Snake");
        setLocation(510,50);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setResizable(false);
        pack();
        setVisible(true);
    }
}
