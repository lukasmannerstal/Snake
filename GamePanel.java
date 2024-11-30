import java.awt.*;
import java.awt.event.*;
import javax.swing.*;
import java.util.Random;

/**
 * The GamePanel class ensures functionality och visualisation of the game.
 *
 * @author Lukas Mannerstål
 * @version 2024-11-30
 */
public class GamePanel extends JPanel implements ActionListener {

    /**
     * Constants
     */
    static final int SCREEN_WIDTH = 900;
    static final int SCREEN_HEIGHT = 900;
    static final int UNIT_SIZE = 30;
    static final int GAME_UNITS = (SCREEN_WIDTH * SCREEN_HEIGHT) / UNIT_SIZE;
    static final int DELAY = 75;

    /**
     * Snake parts
     */
    final int x[] = new int[GAME_UNITS];
    final int y[] = new int[GAME_UNITS];

    /**
     * Instances
     */
    Timer timer;
    Random random;
    GameState state;

    GamePanel() {
        random = new Random();
        state = new GameState();
        this.setPreferredSize(new Dimension(SCREEN_WIDTH, SCREEN_HEIGHT));
        this.setBackground(Color.black);
        this.setFocusable(true);
        this.addKeyListener(new MyKeyAdapter());
        startGame();
    }

    public void startGame() {
        newApple();
        state.setRunning(true);
        timer = new Timer(DELAY, this);
        timer.start();
    }

    public void paintComponent(Graphics g) {
        super.paintComponent(g);
        draw(g);
    }

    public void draw(Graphics g) {
        if(state.getRunning()) {
            g.setColor(Color.red);
            g.fillRect(state.getAppleX(), state.getAppleY(), UNIT_SIZE, UNIT_SIZE);

            for (int i = 0; i < state.getBodyParts(); i++) {
                if (i == 0) {
                    g.setColor(Color.green);
                    g.fillRect(x[i], y[i], UNIT_SIZE, UNIT_SIZE);
                } else {
                    g.setColor(Color.blue);
                    g.fillRect(x[i], y[i], UNIT_SIZE, UNIT_SIZE);
                }
            }
        } else {
            gameOver(g);
        }
    }

    public void newApple() {
        state.setAppleX(random.nextInt((int) (SCREEN_WIDTH / UNIT_SIZE)) * UNIT_SIZE);
        state.setAppleY(random.nextInt((int) (SCREEN_HEIGHT / UNIT_SIZE)) * UNIT_SIZE);
    }

    public void move() {
        for (int i = state.getBodyParts(); i > 0; i--) {
            x[i] = x[i - 1];
            y[i] = y[i - 1];
        }

        switch (state.getDirection()) {
            case 'U':
                y[0] = y[0] - UNIT_SIZE;
                break;
            case 'D':
                y[0] = y[0] + UNIT_SIZE;
                break;
            case 'L':
                x[0] = x[0] - UNIT_SIZE;
                break;
            case 'R':
                x[0] = x[0] + UNIT_SIZE;
                break;
        }
    }

    public void checkApple() {
        if((x[0] == state.getAppleX()) && (y[0] == state.getAppleY())) {
            state.setBodyParts(state.getBodyParts() + 1);
            state.setApplesEaten(state.getApplesEaten() + 1);
            newApple();
        }
    }

    public void checkCollisions() {
        for (int i = state.getBodyParts(); i > 0; i--) {
            if ((x[0] == x[i]) && (y[0] == y[i])) {
                state.setRunning(false);
            }
        }

        if (x[0] < 0 || x[0] > SCREEN_WIDTH || y[0] < 0 || y[0] > SCREEN_HEIGHT) {
            state.setRunning(false);
        }

        if (!state.getRunning()) {
            timer.stop();
        }
    }

    public void gameOver(Graphics g) {
        System.exit(0);
    }

    @Override
    public void actionPerformed(ActionEvent e) {

        if (state.getRunning()) {
            move();
            checkApple();
            checkCollisions();
        }
        repaint();
    }

    public class MyKeyAdapter extends KeyAdapter {
        @Override
        public void keyPressed(KeyEvent e) {
            switch (e.getKeyCode()) {
                case KeyEvent.VK_LEFT:
                    if (state.getDirection() != 'R')
                        state.setDirection('L');
                    break;
                case KeyEvent.VK_RIGHT:
                    if (state.getDirection() != 'L')
                        state.setDirection('R');
                    break;
                case KeyEvent.VK_UP:
                    if (state.getDirection() != 'D')
                        state.setDirection('U');
                    break;
                case KeyEvent.VK_DOWN:
                    if (state.getDirection() != 'U')
                        state.setDirection('D');
                    break;
                default:
                    break;
            }
        }
    }
}
