import javax.swing.JFrame;

/**
 * The GameFrame class sets up the frame of the game and initializes the GamePanel.
 *
 * @author Lukas Mannerstål
 * @version 2024-11-30
 */
public class GameFrame extends JFrame{

    GameFrame() {
        this.add(new GamePanel());
        this.setTitle("Snake");
        this.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        this.setResizable(false);
        this.pack();
        this.setVisible(true);
        this.setLocationRelativeTo(null);
    }
}
