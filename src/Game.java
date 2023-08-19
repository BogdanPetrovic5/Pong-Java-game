import javax.swing.*;
import java.awt.*;

public class Game extends JFrame {
    Game(){
        this.add(new GameFrame());
        this.setTitle("Pong game");
        this.setDefaultCloseOperation(EXIT_ON_CLOSE);
        this.pack();
        this.setResizable(false);
        this.setLocationRelativeTo(null);
        this.setVisible(true);

    }

}
